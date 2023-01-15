package org.moosetechnology.verveineC.plugin;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IBinding;

import org.moosetechnology.famix.famixcentities.Access;
import org.moosetechnology.famix.famixcentities.AliasType;
import org.moosetechnology.famix.famixcentities.Association;
import org.moosetechnology.famix.famixcentities.Attribute;
import org.moosetechnology.famix.famixcentities.BehaviouralEntity;
import org.moosetechnology.famix.famixcentities.BehaviouralPointer;
import org.moosetechnology.famix.famixcentities.Comment;
import org.moosetechnology.famix.famixcentities.ContainerEntity;
import org.moosetechnology.famix.famixcentities.DereferencedInvocation;
import org.moosetechnology.famix.famixcentities.Enum;
import org.moosetechnology.famix.famixcentities.EnumValue;
import org.moosetechnology.famix.famixcentities.Function;
import org.moosetechnology.famix.famixcentities.IndexedFileAnchor;
import org.moosetechnology.famix.famixcentities.LocalVariable;
import org.moosetechnology.famix.famixcentities.MultipleFileAnchor;
import org.moosetechnology.famix.famixcentities.Parameter;
import org.moosetechnology.famix.famixcentities.PrimitiveType;
import org.moosetechnology.famix.famixcentities.Reference;
import org.moosetechnology.famix.famixcentities.SourceAnchor;
import org.moosetechnology.famix.famixcentities.Type;
import org.moosetechnology.famix.famixcentities.UnknownVariable;
import org.moosetechnology.famix.famixcppentities.ImplicitVariable;
import org.moosetechnology.famix.famixcppentities.Inheritance;
import org.moosetechnology.famix.famixcppentities.Method;
import org.moosetechnology.famix.famixcppentities.Namespace;
import org.moosetechnology.famix.famixcppentities.OOInvocation;
import org.moosetechnology.famix.famixcppentities.ParameterType;
import org.moosetechnology.famix.famixcppentities.ParameterizableClass;
import org.moosetechnology.famix.famixcppentities.ParameterizedType;
import org.moosetechnology.famix.famixcpreprocentities.CFile;
import org.moosetechnology.famix.famixcpreprocentities.CompilationUnit;
import org.moosetechnology.famix.famixcpreprocentities.HeaderFile;
import org.moosetechnology.famix.famixcpreprocentities.Include;
import org.moosetechnology.famix.famixcpreprocentities.PreprocessorIfdef;
import org.moosetechnology.famix.famixtraits.TFileAnchor;
import org.moosetechnology.famix.famixtraits.TInheritance;
import org.moosetechnology.famix.famixtraits.TInvocationsReceiver;
import org.moosetechnology.famix.famixtraits.TNamedEntity;
import org.moosetechnology.famix.famixtraits.TReference;
import org.moosetechnology.famix.famixtraits.TSourceEntity;
import org.moosetechnology.famix.famixtraits.TStructuralEntity;
import org.moosetechnology.famix.famixtraits.TWithAttributes;
import org.moosetechnology.famix.famixtraits.TWithComments;
import org.moosetechnology.famix.famixtraits.TWithDereferencedInvocations;
import org.moosetechnology.famix.famixtraits.TWithMethods;
import org.moosetechnology.famix.famixtraits.TWithParameters;
import org.moosetechnology.famix.famixtraits.TWithTypes;

import org.moosetechnology.famix.moose.Entity;

import org.moosetechnology.verveineC.utils.Visibility;
import org.moosetechnology.verveineC.utils.WrongClassGuessException;
import org.moosetechnology.verveineC.utils.fileAndStream.FileUtil;
import org.moosetechnology.verveineC.utils.resolution.StubBinding;

import ch.akuhn.fame.Repository;

/**
 * A dictionnary of Famix entities to help create them and find them back
 * @author anquetil
 *
 * Entities are mapped to keys that are the <code>IBinding</code> provided by the
 * CTD parser
 */
public class CDictionary {

	public static final String DEFAULT_PCKG_NAME = "<Default Package>";
	public static final String STUB_METHOD_CONTAINER_NAME = "<StubMethodContainer>";
	public static final String SELF_NAME = "self";
	public static final String SUPER_NAME = "super";
	
	/**
	 * Separator in fully qualified package name
	 */
	public static final String MOOSE_NAME_SEPARATOR = "::";

	/*
	 * names for primitive types
	 */
	private static final String PRIM_T_UNSPECIFIED = "unspecified";   // default to "int" but might also be for constructors (no return type)
	private static final String PRIM_T_BOOLEAN = "boolean";
	private static final String PRIM_T_INT = "int";
	private static final String PRIM_T_REAL = "real";
	private static final String PRIM_T_CHAR = "char";
	private static final String PRIM_T_UNKNOWN = "unknownPrimitiveType";

	protected Map<IBinding,CFile> nameToFile;


	/*
	 * Famix markers for methods
	 */
	public static final String CONSTRUCTOR_KIND_MARKER = "constructor";
	public static final String DESTRUCTOR_KIND_MARKER = "destructor";

	/**
	 * The FAMIX repository where all FAMIX entities are created and stored
	 */
	protected Repository famixRepo;

	/**
	 * A dictionary to map a key (provided by the user) to FAMIX Entity
	 */
	protected Map<IBinding,TNamedEntity> keyToEntity;
	/**
	 * A reverse dictionary (see {@link Dictionary#keyToEntity}) to find the key of an entity.
	 */
	protected Map<TNamedEntity,IBinding> entityToKey;

	/**
	 * Another dictionary to map a name to FAMIX Entities with this name
	 */
	protected Map<String,Collection<TNamedEntity>> nameToEntity;

	/**
	 * Yet another dictionary for implicit variables ('self' and 'super')
	 * Because they are implicit, they may not have a binding provided by the parser,
	 * or may have the same binding than their associated type so they can't be kept easily in {@link Dictionary#keyToEntity}
	 * TODO should be Class, not Type
	 */
	@Deprecated
	protected Map<Type,ImplicitVars> typeToImpVar;

	/**
	 * Used to keep the two possible ImplicitVariable for a given Class binding
	 * @author anquetil
	 */
	@Deprecated
	protected class ImplicitVars {
		public ImplicitVariable self_iv;
		public ImplicitVariable super_iv;
	}
	
 	public CDictionary(Repository famixRepo) {
		this.famixRepo = famixRepo;
		
		this.keyToEntity = new Hashtable<IBinding,TNamedEntity>();
		this.entityToKey = new Hashtable<TNamedEntity,IBinding>();
		this.nameToEntity = new Hashtable<String,Collection<TNamedEntity>>();
		this.typeToImpVar = new Hashtable<Type,ImplicitVars>();
		this.nameToFile = new Hashtable<IBinding,CFile>();
	}

 	/**
 	 * for debugging purpose
 	 * @return number of entities in the repository (hence the dictionary)
 	 */
 	public int size() {
 		return famixRepo.size();
 	}

	protected void mapEntityToName(String name, TNamedEntity ent) {
		Collection<TNamedEntity> l_ent = nameToEntity.get(name);
		if (l_ent == null) {
			l_ent = new LinkedList<TNamedEntity>();
		}
		l_ent.add(ent);
		nameToEntity.put(name, l_ent);
	}

	public void removeEntity( TNamedEntity ent) {
		IBinding key;
		key = entityToKey.get(ent);
		entityToKey.remove(ent);
		keyToEntity.remove(key);

		Collection<TNamedEntity> l_ent = nameToEntity.get(ent.getName());
		l_ent.remove(ent);

		famixRepo.getElements().remove(ent);
	}
	
	protected void mapEntityToKey(IBinding key, TNamedEntity ent) {
		TNamedEntity old = keyToEntity.get(key);
		if (old != null) {
			entityToKey.remove(old);
		}
		keyToEntity.put(key, ent);
		entityToKey.put(ent, key);
	}
	
	/**
	 * Returns all the Famix Entity with the given name and class 
	 * @param fmxClass -- the subtype of Famix Entity we are looking for
	 * @param name -- the name of the entity
	 * @return the Collection of Famix Entities with the given name and class (possibly empty)
	 */
	@SuppressWarnings("unchecked")
	public <T extends TNamedEntity> Collection<T> getEntityByName(Class<T> fmxClass, String name) {
		Collection<T> ret = new LinkedList<T>();
		Collection<TNamedEntity> l_name = nameToEntity.get(name);
		
		if (l_name != null ) {
			for (TNamedEntity obj : l_name) {
				if (fmxClass.isInstance(obj)) {
					ret.add((T) obj);
				}
			}
		}

		return ret;
	}

	/**
	 * Returns the Famix Entity associated to the given key.
	 * <b>Note</b>: Be careful than ImplicitVariables share the same binding than their associated Class and cannot be retrieved with this method.
	 * In such a case, this method will always retrieve the Class associated to the key.
	 * To get an ImplicitVariable from the key, use {@link Dictionary#getImplicitVariableByBinding(Object, String)}
	 * @param key -- the key
	 * @return the Famix Entity associated to the binding or null if not found
	 */
	public TNamedEntity getEntityByKey(IBinding key) {
		if (key == null) {
			return null;
		}
		else {
			return keyToEntity.get(key);
		}
	}

	/**
	 * Returns the Famix Entity associated, raises an error if it does not have the required type
	 */
	@SuppressWarnings("unchecked")
 	public <T extends TNamedEntity> T getEntityByKey(Class<T> clazz, IBinding key) {
 		TNamedEntity found = getEntityByKey(key); 
		if ((found != null) && ! clazz.isInstance(found)) {
			WrongClassGuessException.reportWrongClassGuess(clazz, found);
			return null;
		}
		else {
			return (T) found;
		}
 	}

	/**
	 * Returns the key associated to a Famix Entity.
	 * @param e -- the Named entity
	 * @return the key associated to this entity or null if none
	 */
	public IBinding getEntityKey(TNamedEntity e) {
		return entityToKey.get(e);
	}

	@SuppressWarnings("unchecked")
	protected <T extends TNamedEntity> T getEntityIfNotNull(Class<T> clazz, IBinding key) {
		if (key == null) {
			return null;
		}
		else {
			TNamedEntity found = keyToEntity.get(key);
			if ((found != null) && ! clazz.isInstance(found)) {
				WrongClassGuessException.reportWrongClassGuess(clazz, found);
				return null;
			}
			else {
				return (T)found;
			}
		}
	}

	protected IndexedFileAnchor createIndexedSourceAnchor(String filename, int beg, int end) {
		IndexedFileAnchor fa;

		fa = new IndexedFileAnchor();
		fa.setStartPos(beg+1);
		fa.setEndPos(end+1);
		fa.setFileName( filename);
		famixRepoAdd(fa);

		return fa;
	}
	
	/**
	 * Adds location information to a Famix Entity.
	 * Location informations are: <b>name</b> of the source file and <b>position</b> in this file.
	 * @param fmx -- Famix Entity to add the anchor to
	 * @param filename -- name of the file being visited
	 * @param ast -- ASTNode, where the information are extracted
	 * @return the Famix SourceAnchor added to fmx. May be null in case of incorrect/null parameter
	 */
	public SourceAnchor addSourceAnchor(TSourceEntity fmx, String filename, IASTFileLocation anchor) {

		if (anchor == null) {
			return null;
		}
		else {
			int beg = anchor.getNodeOffset();
			int end = beg + anchor.getNodeLength();

			return addSourceAnchor( fmx, filename, beg, end);
		}
	}

	public SourceAnchor addSourceAnchor(TSourceEntity fmx, String filename, int start, int end) {
			IndexedFileAnchor fa = null;

			if (fmx == null) {
				return null;
			}

			fa = createIndexedSourceAnchor(filename, start, end);
			fmx.setSourceAnchor(fa);

			return fa;
		}

	/**
	 * Adds location information to a Famix Entity that may be defined/declared in various files.
	 * Currently only used for BehaviouralEntities (functions, methods).
	 * Location informations are: <b>name</b> of the source file and <b>position</b> in this file.
	 * @param fmx -- Famix Entity to add the anchor to
	 * @param filename -- name of the file being visited
	 * @param ast -- ASTNode, where the information are extracted
	 * @return the Famix SourceAnchor added to fmx. May be null in case of incorrect/null parameter
	 */
	public SourceAnchor addSourceAnchorMulti(TSourceEntity fmx, String filename, IASTFileLocation anchor) {
		if (fmx == null) {
			return null;
		}

		if (anchor == null) {
			return null;
		}

		int start = anchor.getNodeOffset();
		int end = start + anchor.getNodeLength();

		MultipleFileAnchor mfa = ensureMultipleFileAnchor(fmx);

		// check if we already have this filename in the MultipleFileAnchor
		for (TFileAnchor f : mfa.getFileAnchors()) {
			if ( f.getFileName().equals(filename) ) {
				// note: Could check also the position in the file ...
				return mfa;
			}
		}

		mfa.addFileAnchors( createIndexedSourceAnchor(filename, start, end) );
		return mfa;
	}

	private MultipleFileAnchor ensureMultipleFileAnchor(TSourceEntity fmx) {
		MultipleFileAnchor mfa = (MultipleFileAnchor) fmx.getSourceAnchor();
		if (mfa == null) {
			mfa = new MultipleFileAnchor();
			fmx.setSourceAnchor(mfa);
			famixRepoAdd(mfa);
		}
		return mfa;
	}

	/**
	 * Adds an already created Entity to the FAMIX repository
	 * Used mainly for non-TNamedEntity, for example relationships
	 * @param e -- the FAMIX entity to add to the repository
	 */
	public void famixRepoAdd(Entity e) {
		this.famixRepo.add(e);
	}

	/**
	 * Creates and returns a FAMIX Entity of the type <b>fmxClass</b>.
	 * The Entity is always created (see {@link Dictionary#ensureFamixEntity(Class, Object, String)}).
	 * @param fmxClass -- the FAMIX class of the instance to create
	 * @param name -- the name of the new instance must not be null (and this is not tested)
	 * @return the FAMIX Entity or null in case of a FAMIX error
	 */
	protected <T extends TNamedEntity> T createFamixEntity(Class<T> fmxClass, String name) {
		T fmx = null;

		if (name == null) {
			return null;
		}
		
		try {
			fmx = fmxClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			System.err.println("Unexpected error, could not create a FAMIX entity: "+e.getMessage());
			e.printStackTrace();
		}
		
		if (fmx != null) {
			fmx.setName(name);
			// TODO move away from here ?
			((TSourceEntity)fmx).setIsStub(Boolean.TRUE);

			mapEntityToName(name, fmx);
			this.famixRepo.add(fmx);
		}

		return fmx;
	}
	
	/**
	 * Returns a FAMIX Entity of the type <b>fmxClass</b> and maps it to its binding <b>bnd</b> (if not null).
	 * The Entity is created if it did not exist.
	 * @param fmxClass -- the FAMIX class of the instance to create
	 * @param bnd -- the binding to map to the new instance
	 * @param name -- the name of the new instance (used if <tt>bnd == null</tt>)
	 * @return the FAMIX Entity or null if <b>bnd</b> was null or in case of a FAMIX error
	 */
	@SuppressWarnings("unchecked")
	public <T extends TNamedEntity> T ensureFamixEntity(Class<T> fmxClass, IBinding bnd, String name) {
		T fmx = null;
		if (bnd != null) {
			fmx = (T) getEntityByKey(bnd);
			if (fmx != null) {
				return fmx;
			}
		}
		// else
		// Unfortunately different entities with the same name and same type may exist
		// e.g. 2 parameters of 2 different methods but having the same name
		// so we cannot recover just from the name

		fmx = createFamixEntity(fmxClass, name);
		if ( (bnd != null) && (fmx != null) ) {
			keyToEntity.put(bnd, fmx);
			entityToKey.put(fmx, bnd);
		}
		
		return fmx;
	}

	///// Special Case: ImplicitVariables /////

	/**
	 * Returns the Famix ImplicitVariable associated to the given binding and name (self or super).
	 * See also {@link Dictionary#getEntityByKey(Object)}
	 * @param bnd -- the binding
	 * @return the Famix Entity associated to the binding or null if not found
	 */
	@Deprecated
	public ImplicitVariable getImplicitVariableByBinding(IBinding bnd, String iv_name) {
		return getImplicitVariableByType((org.moosetechnology.famix.famixcppentities.Class)getEntityByKey(bnd), iv_name);
	}
	
	/**
	 * Returns the Famix ImplicitVariable associated to the given FamixType.
	 * @param type -- the FamixType
	 * @param name -- name of the ImplicitVariable (should be Dictionary.SELF_NAME or Dictionary.SUPER_NAME)
	 * @return the Famix ImplicitVariable associated to the Type or null if not found
	 */
	@Deprecated
	public ImplicitVariable getImplicitVariableByType(Type type, String name) {
		ImplicitVars iv = typeToImpVar.get(type);
		ImplicitVariable ret = null;
		
		if (iv == null) {
			iv = new ImplicitVars();
		}
		
		if (name.equals(SELF_NAME)) {
			ret = iv.self_iv;
		}
		else if (name.equals(SUPER_NAME)) {
			ret = iv.super_iv;
		}

		return ret;
	}

	/**
	 * Returns a FAMIX ImplicitVariable with the given <b>name</b> ("self" or "super") and corresponding to the <b>type</b>.
	 * If this ImplicitVariable does not exist yet, it is created
	 * @param name -- the name of the FAMIX ImplicitVariable (should be Dictionary.SELF_NAME or Dictionary.SUPER_NAME)
	 * @param type -- the Famix Type for this ImplicitVariable (should not be null)
	 * @param owner -- the ContainerEntity where the implicit variable appears (should be a method inside <b>type</b>)
	 * @return the FAMIX ImplicitVariable or null in case of a FAMIX error
	 */
	public ImplicitVariable ensureFamixImplicitVariable(String name, Type type, Method owner) {
		ImplicitVariable fmx;
		IBinding bnd = StubBinding.getInstance(Type.class, mooseName(owner, name));
		fmx = ensureFamixEntity(ImplicitVariable.class, bnd, name);
		fmx.setParentBehaviouralEntity(owner);
		return fmx;
	}

	///// ensure Famix Relationships /////

	protected void chainPrevNext(Association prev, Association next) {
		if (prev != null) {
			next.setPrevious(prev);  // not yet implemented in importer
		}
	}
	
	/**
	 * Returns a Famix Inheritance relationship between two Famix Classes creating it if needed
	 * @param sup -- the super class
	 * @param sub -- the sub class
	 * @param prev -- previous inheritance relationship in the same context
	 * @return the Inheritance relationship
	 */
	public TInheritance ensureFamixInheritance(org.moosetechnology.famix.famixcppentities.Class sup, org.moosetechnology.famix.famixcppentities.Class sub, Association prev) {
		if ( (sup == null) || (sub == null) ) {
			return null;
		}
			
		for (TInheritance i : sup.getSubInheritances()) {
			if (i.getSubclass() == sub) {
				return i;
			}
		}
		Inheritance inh = new Inheritance();
		inh.setSuperclass(sup);
		inh.setSubclass(sub);
		chainPrevNext(prev,inh);
		famixRepoAdd(inh);
		return inh;
	}

	/**
	 * Returns a Famix Reference between two Famix Entities creating it if needed.<br>
	 * If <code>prev == null</code> and a similar reference already exist (same <code>src</code>, same <code>tgt</code>), does not create a new one
	 * @param src -- source of the reference
	 * @param tgt -- target of the reference
	 * @param prev -- previous reference relationship in the same context
	 * @return the FamixReference
	 */
	public TReference addFamixReference(BehaviouralEntity src, Type tgt, Association prev) {
		if ( (src == null) || (tgt == null) ) {
			return null;
		}

		if (prev == null) {
			for (TReference ref : src.getOutgoingReferences()) {
				if (ref.getReferredType() == tgt) {
					return ref;
				}
			}
		}

		Reference ref = new Reference();
		ref.setReferredType(tgt);
		ref.setReferencer(src);
		chainPrevNext(prev,ref);
		famixRepoAdd(ref);

		return ref;
	}

	/**
	 * Returns a Famix Invocation between two Famix Entities creating it if needed
	 * @param sender of the invocation
	 * @param invoked -- method invoked
	 * @param receiver of the invocation
	 * @param signature -- i.e. actual invocation code
	 * @param prev -- previous invocation relationship in the same context
	 * @return the FamixInvocation
	 */
	public OOInvocation addFamixInvocation(BehaviouralEntity sender, BehaviouralEntity invoked, TInvocationsReceiver receiver, String signature, Association prev) {
		if ( (sender == null) || (invoked == null) ) {
			return null;
		}
		OOInvocation invok = new OOInvocation();
		invok.setReceiver(receiver);
		invok.setSender(sender);
		invok.setSignature((signature== null) ? invoked.getSignature() : signature);
		invok.addCandidates(invoked);
		chainPrevNext(prev,invok);
		famixRepoAdd(invok);
		
		return invok;
	}

	/**
	 * Returns a Famix Access between two Famix Entities creating it if needed
	 * @param accessor -- the entity (presumably a method) accessing the attribute
	 * @param var -- the variable accessed
	 * @param isWrite -- whether this is an access for reading or writing in the variable
	 * @param prev -- previous access relationship in the same context
	 * @return the FamixAccess
	 */
	public Access addFamixAccess(BehaviouralEntity accessor, TStructuralEntity var, boolean isWrite, Association prev) {
		if ( (accessor == null) || (var == null) ) {
			return null;
		}
		Access acc = new Access();
		acc.setAccessor(accessor);
		acc.setVariable(var);
		acc.setIsWrite( isWrite);
		chainPrevNext(prev, acc);
		famixRepoAdd(acc);
		
		return acc;
	}

	public DereferencedInvocation addFamixDereferencedInvocation(BehaviouralEntity sender, TWithDereferencedInvocations referencer, String signature, Association prev) {
		DereferencedInvocation invok = new DereferencedInvocation();
		invok.setSender(sender);
		invok.setReferencer(referencer);
		chainPrevNext(prev, invok);
		famixRepoAdd(invok);

		if (signature != null) {
			invok.setSignature(signature);
		}

		return invok;
	}

	public BehaviouralPointer addFamixBehaviouralPointer(BehaviouralEntity ref, BehaviouralEntity fmx) {
		BehaviouralPointer pointer = new BehaviouralPointer();
		pointer.setBehaviouralPointed(fmx);
		pointer.setReferer(ref);
		famixRepoAdd(pointer);
		return pointer;
	}

	public CFile ensureFamixCFile( IBinding key, String name) {
		CFile fmx = nameToFile.get(key);
		if (fmx == null) {
			if (FileUtil.isHeader(name)) {
				fmx = new HeaderFile();
			}
			else {
				fmx = new CompilationUnit();
			}
			fmx.setName(name);
			famixRepo.add(fmx);
			nameToFile.put(key, fmx);
		}
		
		return fmx;
	}

	public Include addFamixInclude(CFile src, CFile tgt) {
		if ( (src == null) || (tgt == null) ) {
			return null;
		}

		Include inc = new Include();
		inc.setIncluded(tgt);
		inc.setIncludedBy(src);
		famixRepoAdd(inc);

		return inc;
	}

	public PreprocessorIfdef createFamixPreprocIfdef(String macroName) {
		PreprocessorIfdef fmx;

		fmx = new PreprocessorIfdef();
		fmx.setPreprocessorMacro(macroName);
		this.famixRepo.add(fmx);

		return fmx;
	}

	/**
	 * Creates or recovers a Famix Named Entity uniq for the given name.
	 * For some specific entities we don't allow two of them with the same name.
	 * This is the case e.g. for the default package, or the Java class "Object" and its package "java.lang".
	 * @param fmxClass -- the FAMIX class of the instance to create
	 * @param key -- a potential binding for the entity
	 * @param name -- the name of the new instance (used if <tt>bnd == null</tt>)
	 * @return the uniq Famix Entity for this binding and/or name
	 */
	@SuppressWarnings("unchecked")
	public <T extends TNamedEntity> T ensureFamixUniqEntity(Class<T> fmxClass, IBinding key, String name) {
		T fmx = null;
		
		if (name == null) {
			return null;
		}
		
		if (key != null) {
			fmx = (T) getEntityByKey(key);
		}
		
		if (fmx == null) {
			Collection<T> l = getEntityByName( fmxClass, name);
			if (l.size() > 0) {
				fmx = l.iterator().next();
			}
			else {
				// may be we should be careful not to persist all these special entities?
				fmx = createFamixEntity(fmxClass, name);
			}
			
			if (key != null) {
				// may happen for example if the entity was first created without binding
				// and we find a binding for it later
				keyToEntity.put(key, fmx);
			}
		}

		return fmx;
	}

	///// ensure Famix Entities /////

	/**
	 * Returns a FAMIX Type with the given <b>name</b>, creating it if it does not exist yet.
	 * In the second case, sets some default properties: not Abstract, not Final, not Private, not Protected, not Public, not Interface
	 * @param name -- the name of the FAMIX Class
	 * @return the FAMIX Class or null in case of a FAMIX error
	 */
	public Type ensureFamixType(IBinding key, String name, TWithTypes owner) {
		Type fmx = getEntityIfNotNull(Type.class, key);

		if (fmx == null) {
			fmx = ensureFamixEntity(Type.class, key, name);
			fmx.setTypeContainer(owner);
		}
		
		return fmx;
	}

	/**
	 * Returns a FAMIX Class with the given <b>name</b>, creating it if it does not exist yet.
	 * @param key to which the entity will be mapped (may be null, but then it will be difficult to recover the entity)
	 * @param name -- the name of the FAMIX Method (MUST NOT be null, but this is not checked)
	 * @param owner -- type defining the method (should not be null, but it will work if it is) 
	 * @param persistIt -- whether the Class should be persisted in the Famix repository
	 * @return the FAMIX Class or null in case of a FAMIX error
	 */
	public org.moosetechnology.famix.famixcppentities.Class ensureFamixClass(IBinding key, String name, TWithTypes owner) {
		org.moosetechnology.famix.famixcppentities.Class fmx = getEntityIfNotNull(org.moosetechnology.famix.famixcppentities.Class.class, key);

		if (fmx == null) {
			fmx = ensureFamixEntity(org.moosetechnology.famix.famixcppentities.Class.class, key, name);
			fmx.setTypeContainer(owner);
		}
		
		return fmx;
	}

	/**
	 * Returns a FAMIX ParameterizableClass with the given <b>name</b>, creating it if it does not exist yet
	 * In the second case, sets some default properties: not Abstract, not Final, not Private, not Protected, not Public, not Interface
	 * @param name -- the name of the FAMIX Class
	 * @return the FAMIX Class or null in case of a FAMIX error
	 */
	public ParameterizableClass ensureFamixParameterizableClass(IBinding key, String name, TWithTypes owner) {
		ParameterizableClass fmx = getEntityIfNotNull(ParameterizableClass.class, key);

		if (fmx == null) {
			fmx = ensureFamixEntity(ParameterizableClass.class, key, name);
			fmx.setTypeContainer(owner);
		}
		
		return fmx;
	}

	/**
	 * Returns a FAMIX ParameterizableType with the given <b>name</b>, creating it if it does not exist yet
	 * @param name -- the name of the FAMIX Type
	 * @return the FAMIX ParameterizableType or null in case of a FAMIX error
	 */
	public ParameterizedType ensureFamixParameterizedType(IBinding key, String name, ParameterizableClass generic, TWithTypes owner) {
		ParameterizedType fmx = getEntityIfNotNull(ParameterizedType.class, key);

		if (fmx == null) {
			fmx = ensureFamixEntity(ParameterizedType.class, key, name);
			fmx.setTypeContainer(owner);
			fmx.setParameterizableClass(generic);
		}

		return fmx;
	}

	/**
	 * Returns a FAMIX ParameterType (created by a FAMIX ParameterizableClass) with the given <b>name</b>, creating it if it does not exist yet
	 * In the second case, sets some default properties: not Abstract, not Final, not Private, not Protected, not Public
	 * @param name -- the name of the FAMIX ParameterType
	 * @return the FAMIX ParameterType or null in case of a FAMIX error
	 */
	public ParameterType ensureFamixParameterType(IBinding key, String name, TWithTypes owner) {
		ParameterType fmx = getEntityIfNotNull(ParameterType.class, key);

		if (fmx == null) {
			fmx = ensureFamixEntity(ParameterType.class, key, name);
			fmx.setParentParameterizableClass((ParameterizableClass) owner);
		}
		return fmx;
	}

	public Enum ensureFamixEnum(IBinding key, String name,	TWithTypes owner, boolean persistIt) {
		Enum fmx = ensureFamixEntity(Enum.class, key, name);
		fmx.setTypeContainer(owner);
		return fmx;
	}

	public EnumValue ensureFamixEnumValue(IBinding key, String name, Enum owner, boolean persistIt) {
		EnumValue fmx = ensureFamixEntity(EnumValue.class, key, name);
		fmx.setParentEnum(owner);
		return fmx;
	}

	/**
	 * Returns a FAMIX PrimitiveType with the given <b>name</b>, creating it if it does not exist yet
	 * We assume that PrimitiveType must be uniq for a given name
	 * @param name -- the name of the FAMIX PrimitiveType
	 * @return the FAMIX PrimitiveType or null in case of a FAMIX error
	 */
	public Type ensureFamixPrimitiveType(int type) {
		StubBinding bnd = StubBinding.getInstance(Type.class, "_primitive_/"+type);
		return  ensureFamixUniqEntity(PrimitiveType.class, bnd, primitiveTypeName(type));
	}

	/**
	 * Returns a FAMIX Method with the given <b>name</b>, creating it if it does not exist yet
	 * @param key to which the entity will be mapped (may be null, but then it will be difficult to recover the entity)
	 * @param name -- the name of the FAMIX Method (MUST NOT be null, but this is not checked)
	 * @param signature -- method's signature, including type of parameters and return type (should not be null, but it will work if it is)
	 * @param parent-- type defining the method (should not be null, but it will work if it is)
	 * @return the FAMIX Method or null in case of a FAMIX error
	 */
	public Method ensureFamixMethod(IBinding key, String name, String signature, TWithMethods parent) {
		Method fmx = getEntityIfNotNull(Method.class, key);

		if (fmx == null) {
			fmx = ensureFamixEntity(Method.class, key, name);
			fmx.setCyclomaticComplexity(1);
			fmx.setNumberOfStatements(0);
		}

		fmx.setSignature(signature);
		fmx.setParentType(parent);

		return fmx;
	}

	/**
	 * Returns a FAMIX Function with the given <b>name</b>, creating it if it does not exist yet
	 * @param key to which the entity will be mapped (may be null, but then it will be difficult to recover the entity)
	 * @param name -- the name of the FAMIX Function (MUST NOT be null, but this is not checked)
	 * @param sig -- method's signature, including type of parameters and return type (should not be null, but it will work if it is)
	 * @param ret -- Famix Type returned by the method (ideally should only be null in case of a constructor, but will accept it in any case)
	 * @param owner -- container defining the method (should not be null, but it will work if it is)
	 * @param persistIt -- whether the Function should be persisted in the Famix repository
	 * @return the FAMIX Method or null in case of a FAMIX error
	 */
	public Function ensureFamixFunction(IBinding key, String name, String sig, Namespace parent) {
		Function fmx = getEntityIfNotNull(Function.class, key);

		if (fmx == null) {
			fmx = (Function) ensureFamixEntity(Function.class, key, name);
			fmx.setSignature(sig);
			fmx.setFunctionOwner(parent);
			fmx.setCyclomaticComplexity(1);
			fmx.setNumberOfStatements(0);
		}
		return fmx;
	}

	/**
	 * Returns a FAMIX Attribute with the given <b>name</b>, creating it if it does not exist yet
	 * @param key to which the entity will be mapped (may be null, but then it will be difficult to recover the entity)
	 * @param name -- the name of the FAMIX Attribute (MUST NOT be null, but this is not checked)
	 * @param type -- Famix Type of the Attribute (should not be null, but it will work if it is)
	 * @param owner -- type defining the Attribute (should not be null, but it will work if it is)
	 * @param persistIt -- whether the Attribute should be persisted in the Famix repository
	 * @return the FAMIX Attribute or null in case of a FAMIX error
	 */
	public Attribute ensureFamixAttribute(IBinding key, String name, TWithAttributes parent) {
		Attribute fmx = getEntityIfNotNull(Attribute.class, key);

		if (fmx == null) {
			fmx = ensureFamixEntity(Attribute.class, key, name);
			fmx.setParentType(parent);
		}

		return fmx;
	}

	/**
	 * Returns a FAMIX LocalVariable with the given <b>name</b>, creating it if it does not exist yet
	 * @param name -- the name of the FAMIX LocalVariable
	 * @return the FAMIX LocalVariable or null in case of a FAMIX error
	 */
	public LocalVariable ensureFamixLocalVariable(IBinding key, String name, Type type, ContainerEntity owner) {
		LocalVariable fmx = ensureFamixEntity(LocalVariable.class, key, name);
		fmx.setParentBehaviouralEntity(owner);
		fmx.setDeclaredType(type);
		return fmx;
	}

	/**
	 * Creates and returns a FAMIX Comment not associated to any Entity
	 * @param cmt -- the content (String) of the comment 
	 * @return the FAMIX Comment
	 */
	public Comment createFamixComment(String cmt) {
		Comment fmx = null;
		
		if (cmt != null) {
			fmx = new Comment();
			fmx.setContent(cmt);
			this.famixRepo.add(fmx);
		}
		return fmx;
	}

	/**
	 * Creates and returns a FAMIX Comment and associates it with an Entity (ex: for Javadocs)
	 * @param cmt -- the content (String) of the comment 
	 * @param owner -- the entity concerned by this comment
	 * @return the FAMIX Comment
	 */
	public Comment createFamixComment(String cmt, TWithComments owner) {
		Comment fmx = null;
		
		if ( (cmt != null) && (owner != null) ) {
			fmx = createFamixComment(cmt);
			fmx.setContainer(owner);
		}
		return fmx;
	}
	
	/**
	 * Creates and returns a FAMIX Parameter and associates it with a BehaviouralEntity
	 * @param name -- the name of the parameter
	 * @param type -- the type of the parameter
	 * @param owner -- the entity concerned by this parameter
	 * @param persistIt -- whether the Parameter should be persisted in the Famix repository
	 * @return the FAMIX parameter
	 */
	public Parameter createFamixParameter(IBinding key, String name, Type type, TWithParameters owner, boolean persistIt) {
		Parameter fmx = ensureFamixEntity(Parameter.class, key, name);
		fmx.setParentBehaviouralEntity(owner);
		fmx.setDeclaredType(type);
		
		return fmx;
	}
	
	public UnknownVariable ensureFamixUnknownVariable(IBinding key, String name, Namespace parent) {
		UnknownVariable fmx = null;
		
		if (key != null) {
			fmx = getEntityByKey(UnknownVariable.class, key);
		}

		if (fmx == null) {
			fmx = ensureFamixEntity(UnknownVariable.class, key, name);
			//TODO fmx.setParentPackage(parent);
		}
		
		return fmx;
	}

	public Namespace ensureFamixNamespace(IBinding key, String name, Namespace parent) {
		Namespace fmx = ensureFamixEntity(Namespace.class, key, name);
		fmx.setIsStub(false);
		if (parent != null) {
			fmx.setParentNamespace(parent);
		}
		return fmx;
	}

	public AliasType ensureFamixTypeAlias(IBinding key, String name, TWithTypes owner) {
		AliasType fmx;

		fmx = ensureFamixEntity(AliasType.class, key, name);
		fmx.setTypeContainer(owner);

		return fmx;
	}

	/** 
	 * Creating a "parameter type" depends on the context
	 * <ul>
	 * <li> If it is a ParameterizableClass (e.g. "<code>template &lt;class T&gt; class C</code> ...", we create a ParameterType
	 * <li> If it is a Method (e.g. "<code>template &lt;class T&gt; void fct(T)</code> ..."), we create a Type
	 * </ul>
	 */
	public Type createParameterType(String name, TWithTypes owner) {
		// apparently CDT gives a binding to the parameterType at its declaration ("template <class T> ...")
		// but not when used ("... mth(T)") so we ignore CDT binding and always use our custom build one
    	IBinding bnd;
    	bnd = StubBinding.getInstance(Type.class, mooseName((ContainerEntity) owner, name));

		if (owner instanceof ParameterizableClass) {
			return ensureFamixParameterType(bnd, name, owner);
		}
		else {
			return ensureFamixType(bnd, name, owner);
		}
	}

	/**
	 * Returns a Famix Parameter associated with the IBinding.
	 * The Entity is created if it does not exist.<br>
	 * Params: see {@link Dictionary#ensureFamixParameter(Object, String, Type, org.moosetechnology.famix.cpp.BehaviouralEntity, boolean)}.
	 * @param persistIt -- whether to persist or not the entity eventually created
	 * @return the Famix Entity found or created. May return null if "bnd" is null or in case of a Famix error
	 */
	public Parameter ensureFamixParameter(IBinding bnd, String name, TWithParameters owner) {
		Parameter fmx = null;

		// --------------- to avoid useless computations if we can
		fmx = getEntityByKey(Parameter.class, bnd);
		if (fmx != null) {
			return fmx;
		}

		if (fmx == null) {
			fmx = createFamixParameter(bnd, name, /*type*/null, owner, /*persistIt*/true);
		}

		return fmx;
	}

	/**
	 * Sets the visibility of a FamixTNamedEntity.
	 * <code>null</code> visibility (e.g. in the case of a function) is silently ignored.
	 */
	public void setVisibility(TNamedEntity fmx, Visibility visi) {
		if (visi != null) {
//TODO			fmx.addModifiers(visi.toString());
		}
	}

	// UTILITIES =========================================================================================================================================

	static public String primitiveTypeName(int type) {
		String name;
		switch (type) {
		case IASTSimpleDeclSpecifier.t_void:
			// for type void, we return null as in: "void f()"
			// but this might not be a good idea, as in: "void *p"
			return null;
		case IASTSimpleDeclSpecifier.t_bool:
			name = PRIM_T_BOOLEAN;
			break;
		case IASTSimpleDeclSpecifier.t_char:
		case IASTSimpleDeclSpecifier.t_char16_t:
		case IASTSimpleDeclSpecifier.t_char32_t:
		case IASTSimpleDeclSpecifier.t_wchar_t:
			name = PRIM_T_CHAR;
			break;
		case IASTSimpleDeclSpecifier.t_decimal32:
		case IASTSimpleDeclSpecifier.t_decimal64:
		case IASTSimpleDeclSpecifier.t_decimal128:
		case IASTSimpleDeclSpecifier.t_int:
		case IASTSimpleDeclSpecifier.t_int128:
			name = PRIM_T_INT;
			break;
		case IASTSimpleDeclSpecifier.t_float:
		case IASTSimpleDeclSpecifier.t_float128:
		case IASTSimpleDeclSpecifier.t_double:
			name = PRIM_T_REAL;
			break;
		case IASTSimpleDeclSpecifier.t_unspecified:
			name = PRIM_T_UNSPECIFIED;
			break;
		default:
			name = PRIM_T_UNKNOWN+"_"+type;
		}
		return name;
	}

	/**
	 * Computes moose name for an entity in a Container.
	 * This is a convenience method that delegates to one of {@link #mooseName(Function, String)}; {@link #mooseName(Method, String)}; {@link #mooseName(Package, String)};
	 * {@link #mooseName(Package, String)}; or {@link #mooseName(Type, String)}
	 * it would be best implemented with a double-dispatch, but the Famix entities are generated and cannot be modified
	 */
	static public String mooseName(ContainerEntity parent, String name) {
		if (parent instanceof Namespace) {
			return mooseName((Namespace)parent, name);
		}
		else if (parent instanceof Type) {
			return mooseName((Type)parent, name);
		}
		else if (parent instanceof Method) {
			return mooseName((Method)parent, name);
		}
		else if (parent instanceof Function) {
			return mooseName((Function)parent, name);
		}
		else { // e.g. parent == null
			return name;
		}
	}

	/**
	 * Computes moose name for a Package (i.e C++ Namespace) child
	 * MooseName is the concatenation of the moosename of the parent Package with the simple name of the child
	 */
	static public String mooseName(Namespace parent, String name) {
		if (parent != null) {
			return concatMooseName( mooseName( (Namespace)parent.getParentNamespace(), parent.getName()) , name);
		}
		else {
			return name;
		}
	}

	/**
	 * Computes moose name for a Type.
	 * MooseName is the concatenation of the mooseName of the parent package with the simple name of the type
	 */
	static public String mooseName(Type parent, String name) {
		if (parent != null) {
			return concatMooseName( mooseName((ContainerEntity) parent.getTypeContainer(), parent.getName()) , name);
		}
		else {
			return name;
		}
	}

	/**
	 * Computes moose name for a Method child.
	 * MooseName is the concatenation of the moosename of the parent Mathod with the simple name of the child
	 */
	static public String mooseName(Method parent, String name) {
		if (parent != null) {
			return concatMooseName( mooseName((Type)parent.getParentType(), parent.getSignature()) , name);
		}
		else {
			return name;
		}
	}

	/**
	 * Computes moose name for a Function child.
	 * MooseName is the concatenation of the moosename of the parent Function with the simple name of the child
	 */
	static public String mooseName(Function parent, String name) {
		if (parent != null) {
			return concatMooseName( mooseName((Namespace)parent.getFunctionOwner(), parent.getSignature()) , name);
		}
		else {
			return name;
		}
	}

	static protected String concatMooseName(String prefix, String name) {
		return prefix + MOOSE_NAME_SEPARATOR + name;
	}

	/**
	 * Remove a ParameterEntity from the repository
	 * Parameter is also removed from its parentBehaviouralEntity
	 */
	public void removeParameter(Parameter param) {
		param.setParentBehaviouralEntity(null);
		removeEntity(param);
	}

}
