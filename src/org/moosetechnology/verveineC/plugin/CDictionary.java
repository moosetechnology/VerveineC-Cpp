package org.moosetechnology.verveineC.plugin;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.moosetechnology.famix.cpp.AbstractFileAnchor;
import org.moosetechnology.famix.cpp.Access;
import org.moosetechnology.famix.cpp.Association;
import org.moosetechnology.famix.cpp.Attribute;
import org.moosetechnology.famix.cpp.BehaviouralEntity;
import org.moosetechnology.famix.cpp.BehaviouralReference;
import org.moosetechnology.famix.cpp.CFile;
import org.moosetechnology.famix.cpp.Comment;
import org.moosetechnology.famix.cpp.CompilationUnit;
import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.DereferencedInvocation;
import org.moosetechnology.famix.cpp.Entity;
import org.moosetechnology.famix.cpp.Enum;
import org.moosetechnology.famix.cpp.EnumValue;
import org.moosetechnology.famix.cpp.Function;
import org.moosetechnology.famix.cpp.GlobalVariable;
import org.moosetechnology.famix.cpp.Header;
import org.moosetechnology.famix.cpp.ImplicitVariable;
import org.moosetechnology.famix.cpp.Include;
import org.moosetechnology.famix.cpp.IndexedFileAnchor;
import org.moosetechnology.famix.cpp.Inheritance;
import org.moosetechnology.famix.cpp.Invocation;
import org.moosetechnology.famix.cpp.LocalVariable;
import org.moosetechnology.famix.cpp.Method;
import org.moosetechnology.famix.cpp.Module;
import org.moosetechnology.famix.cpp.MultipleFileAnchor;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.Namespace;
import org.moosetechnology.famix.cpp.Package;
import org.moosetechnology.famix.cpp.Parameter;
import org.moosetechnology.famix.cpp.ParameterType;
import org.moosetechnology.famix.cpp.ParameterizableClass;
import org.moosetechnology.famix.cpp.ParameterizedType;
import org.moosetechnology.famix.cpp.PreprocessorIfdef;
import org.moosetechnology.famix.cpp.PrimitiveType;
import org.moosetechnology.famix.cpp.Reference;
import org.moosetechnology.famix.cpp.ScopingEntity;
import org.moosetechnology.famix.cpp.SourceAnchor;
import org.moosetechnology.famix.cpp.SourcedEntity;
import org.moosetechnology.famix.cpp.StructuralEntity;
import org.moosetechnology.famix.cpp.Type;
import org.moosetechnology.famix.cpp.TypeAlias;
import org.moosetechnology.famix.cpp.UnknownVariable;
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
	protected Map<IBinding,NamedEntity> keyToEntity;
	/**
	 * A reverse dictionary (see {@link Dictionary#keyToEntity}) to find the key of an entity.
	 */
	protected Map<NamedEntity,IBinding> entityToKey;

	/**
	 * Another dictionary to map a name to FAMIX Entities with this name
	 */
	protected Map<String,Collection<NamedEntity>> nameToEntity;

	/**
	 * Yet another dictionary for implicit variables ('self' and 'super')
	 * Because they are implicit, they may not have a binding provided by the parser,
	 * or may have the same binding than their associated type so they can't be kept easily in {@link Dictionary#keyToEntity}
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
		
		this.keyToEntity = new Hashtable<IBinding,NamedEntity>();
		this.entityToKey = new Hashtable<NamedEntity,IBinding>();
		this.nameToEntity = new Hashtable<String,Collection<NamedEntity>>();
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

	protected void mapEntityToName(String name, NamedEntity ent) {
		Collection<NamedEntity> l_ent = nameToEntity.get(name);
		if (l_ent == null) {
			l_ent = new LinkedList<NamedEntity>();
		}
		l_ent.add(ent);
		nameToEntity.put(name, l_ent);
	}

	public void removeEntity( NamedEntity ent) {
		IBinding key;
		key = entityToKey.get(ent);
		entityToKey.remove(ent);
		keyToEntity.remove(key);

		Collection<NamedEntity> l_ent = nameToEntity.get(ent.getName());
		l_ent.remove(ent);

		famixRepo.getElements().remove(ent);
	}
	
	protected void mapEntityToKey(IBinding key, NamedEntity ent) {
		NamedEntity old = keyToEntity.get(key);
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
	public <T extends NamedEntity> Collection<T> getEntityByName(Class<T> fmxClass, String name) {
		Collection<T> ret = new LinkedList<T>();
		Collection<NamedEntity> l_name = nameToEntity.get(name);
		
		if (l_name != null ) {
			for (NamedEntity obj : l_name) {
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
	public NamedEntity getEntityByKey(IBinding key) {
		if (key == null) {
			return null;
		}
		else {
			return keyToEntity.get(key);
		}
	}

	/**
	 * Returns the key associated to a Famix Entity.
	 * @param e -- the Named entity
	 * @return the key associated to this entity or null if none
	 */
	public IBinding getEntityKey(NamedEntity e) {
		return entityToKey.get(e);
	}

	@SuppressWarnings("unchecked")
 	public <T extends NamedEntity> T getEntityByKey(Class<T> clazz, IBinding key) {
 		NamedEntity found = getEntityByKey(key); 
		if ((found != null) && ! clazz.isInstance(found)) {
			WrongClassGuessException.reportWrongClassGuess(clazz, found);
			return null;
		}
		else {
			return (T) found;
		}
 	}

	@SuppressWarnings("unchecked")
	protected <T extends NamedEntity> T getEntityIfNotNull(Class<T> clazz, IBinding key) {
		if (key == null) {
			return null;
		}
		else {
			NamedEntity found = keyToEntity.get(key);
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
	public SourceAnchor addSourceAnchor(SourcedEntity fmx, String filename, IASTFileLocation anchor) {

		if (anchor == null) {
			return null;
		}
		else {
			int beg = anchor.getNodeOffset();
			int end = beg + anchor.getNodeLength();

			return addSourceAnchor( fmx, filename, beg, end);
		}
	}

	public SourceAnchor addSourceAnchor(SourcedEntity fmx, String filename, int start, int end) {
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
	public SourceAnchor addSourceAnchorMulti(SourcedEntity fmx, String filename, IASTFileLocation anchor) {

		if (anchor == null) {
			return null;
		}
		else {
			int beg = anchor.getNodeOffset();
			int end = beg + anchor.getNodeLength();
			
			return addSourceAnchorMulti( fmx, filename, beg, end);
		}
	}

	public SourceAnchor addSourceAnchorMulti(SourcedEntity fmx, String filename, int start, int end) {
		MultipleFileAnchor mfa;

		if (fmx == null) {
			return null;
		}

		mfa = (MultipleFileAnchor) fmx.getSourceAnchor();
		if (mfa == null) {
			mfa = new MultipleFileAnchor();
			fmx.setSourceAnchor(mfa);
			famixRepoAdd(mfa);
		}

		// check if we already have this filename in the MultipleFileAnchor
		for (AbstractFileAnchor f : mfa.getAllFiles()) {
			if ( f.getFileName().equals(filename) ) {
				// note: Could check also the position in the file ...
				return mfa;
			}
		}
		
		mfa.addAllFiles( createIndexedSourceAnchor(filename, start, end) );

		return mfa;
	}

	/**
	 * Adds an already created Entity to the FAMIX repository
	 * Used mainly for non-NamedEntity, for example relationships
	 * @param e -- the FAMIX entity to add to the repository
	 */
	public void famixRepoAdd(Entity e) {
		this.famixRepo.add(e);
	}

	/**
	 * Creates and returns a FAMIX Entity of the type <b>fmxClass</b>.
	 * The Entity is always created (see {@link Dictionary#ensureFamixEntity(Class, Object, String, boolean)}).
	 * @param fmxClass -- the FAMIX class of the instance to create
	 * @param name -- the name of the new instance must not be null (and this is not tested)
	 * @param persistIt -- whether the Entity should be persisted in the Famix repository
	 * @return the FAMIX Entity or null in case of a FAMIX error
	 */
	protected <T extends NamedEntity> T createFamixEntity(Class<T> fmxClass, String name, boolean persistIt) {
		T fmx = null;

		if (name == null) {
			return null;
		}
		
		try {
			fmx = fmxClass.newInstance();
		} catch (Exception e) {
			System.err.println("Unexpected error, could not create a FAMIX entity: "+e.getMessage());
			e.printStackTrace();
		}
		
		if (fmx != null) {
			fmx.setName(name);
			fmx.setIsStub(Boolean.TRUE);

			mapEntityToName(name, fmx);
			
			if (persistIt) {
				// put new entity in Famix repository
				this.famixRepo.add(fmx);
			}
		}

		return fmx;
	}
	
	/**
	 * Returns a FAMIX Entity of the type <b>fmxClass</b> and maps it to its binding <b>bnd</b> (if not null).
	 * The Entity is created if it did not exist.
	 * @param fmxClass -- the FAMIX class of the instance to create
	 * @param bnd -- the binding to map to the new instance
	 * @param name -- the name of the new instance (used if <tt>bnd == null</tt>)
	 * @param persistIt -- whether the Entity should be persisted in the Famix repository
	 * @return the FAMIX Entity or null if <b>bnd</b> was null or in case of a FAMIX error
	 */
	@SuppressWarnings("unchecked")
	protected <T extends NamedEntity> T ensureFamixEntity(Class<T> fmxClass, IBinding bnd, String name, boolean persistIt) {
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

		fmx = createFamixEntity(fmxClass, name, persistIt);
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
		return getImplicitVariableByType((org.moosetechnology.famix.cpp.Class)getEntityByKey(bnd), iv_name);
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
	 * @param persistIt -- whether the ImplicitVariable should be persisted in the Famix repository
	 * @return the FAMIX ImplicitVariable or null in case of a FAMIX error
	 */
	public ImplicitVariable ensureFamixImplicitVariable(IBinding key, String name, Type type, Method owner, boolean persistIt) {
		ImplicitVariable fmx;
		fmx = ensureFamixEntity(ImplicitVariable.class, key, name, persistIt);
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
	public Inheritance ensureFamixInheritance(Type sup, Type sub, Association prev) {
		if ( (sup == null) || (sub == null) ) {
			return null;
		}
			
		for (Inheritance i : sup.getSubInheritances()) {
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
	public Reference addFamixReference(BehaviouralEntity src, Type tgt, Association prev) {
		if ( (src == null) || (tgt == null) ) {
			return null;
		}

		if (prev == null) {
			for (Reference ref : src.getOutgoingReferences()) {
				if (ref.getTarget() == tgt) {
					return ref;
				}
			}
		}

		Reference ref = new Reference();
		ref.setTarget(tgt);
		ref.setSource(src);
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
	public Invocation addFamixInvocation(BehaviouralEntity sender, BehaviouralEntity invoked, NamedEntity receiver, String signature, Association prev) {
		if ( (sender == null) || (invoked == null) ) {
			return null;
		}
		Invocation invok = new Invocation();
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
	public Access addFamixAccess(BehaviouralEntity accessor, StructuralEntity var, boolean isWrite, Association prev) {
		if ( (accessor == null) || (var == null) ) {
			return null;
		}
		Access acc = new Access();
		acc.setAccessor(accessor);
		acc.setVariable(var);
		acc.setIsWrite(new Boolean(isWrite));
		chainPrevNext(prev, acc);
		famixRepoAdd(acc);
		
		return acc;
	}

	public DereferencedInvocation addFamixDereferencedInvocation(BehaviouralEntity sender, StructuralEntity referencer, String signature, Association prev) {
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

	public BehaviouralReference addFamixBehaviouralPointer(BehaviouralEntity ref, BehaviouralEntity fmx) {
		BehaviouralReference pointer = new BehaviouralReference();
		pointer.setPointed(fmx);
		pointer.setReferer(ref);
		famixRepoAdd(pointer);
		return pointer;
	}

	public CFile ensureFamixCFile( IBinding key, String name) {
		CFile fmx = nameToFile.get(key);
		if (fmx == null) {
			if (FileUtil.isHeader(name)) {
				fmx = new Header();
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

	public Module ensureFamixModule(IBinding key, String name, Package owner) {
		Module fmx = ensureFamixEntity(Module.class, key, name, /*persistIt*/true);
		fmx.setParentPackage(owner);

		return fmx;
	}

	public Include addFamixInclude(CFile src, CFile tgt) {
		if ( (src == null) || (tgt == null) ) {
			return null;
		}

		Include inc = new Include();
		inc.setTarget(tgt);
		inc.setSource(src);
		famixRepoAdd(inc);

		return inc;
	}

	public PreprocessorIfdef createFamixPreprocIfdef(String macroName) {
		PreprocessorIfdef fmx;

		fmx = new PreprocessorIfdef();
		fmx.setMacro(macroName);
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
	public <T extends NamedEntity> T ensureFamixUniqEntity(Class<T> fmxClass, IBinding key, String name) {
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
				fmx = createFamixEntity(fmxClass, name, /*persistIt*/true);
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
	 * @param persistIt -- whether the Type should be persisted in the Famix repository
	 * @return the FAMIX Class or null in case of a FAMIX error
	 */
	public Type ensureFamixType(IBinding key, String name, ContainerEntity owner, boolean persistIt) {
		Type fmx = ensureFamixEntity(Type.class, key, name, persistIt);
		fmx.setContainer(owner);
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
	public org.moosetechnology.famix.cpp.Class ensureFamixClass(IBinding key, String name, ContainerEntity owner, boolean persistIt) {
		org.moosetechnology.famix.cpp.Class fmx = ensureFamixEntity(org.moosetechnology.famix.cpp.Class.class, key, name, persistIt);
		fmx.setContainer(owner);
		return fmx;
	}

	/**
	 * Returns a FAMIX ParameterizableClass with the given <b>name</b>, creating it if it does not exist yet
	 * In the second case, sets some default properties: not Abstract, not Final, not Private, not Protected, not Public, not Interface
	 * @param name -- the name of the FAMIX Class
	 * @param persistIt -- whether the ParameterizableClass should be persisted in the Famix repository
	 * @return the FAMIX Class or null in case of a FAMIX error
	 */
	public ParameterizableClass ensureFamixParameterizableClass(IBinding key, String name, ContainerEntity owner, boolean persistIt) {
		ParameterizableClass fmx = ensureFamixEntity(ParameterizableClass.class, key, name, persistIt);
		fmx.setContainer(owner);
		return fmx;
	}

	/**
	 * Returns a FAMIX ParameterizableType with the given <b>name</b>, creating it if it does not exist yet
	 * @param name -- the name of the FAMIX Type
	 * @param persistIt -- whether the ParameterizableClass should be persisted in the Famix repository
	 * @return the FAMIX ParameterizableType or null in case of a FAMIX error
	 */
	public ParameterizedType ensureFamixParameterizedType(IBinding key, String name, ParameterizableClass generic, ContainerEntity owner, boolean persistIt) {
		ParameterizedType fmx = ensureFamixEntity(ParameterizedType.class, key, name, persistIt);
		fmx.setContainer(owner);
		fmx.setParameterizableClass(generic);
		return fmx;
	}

	/**
	 * Returns a FAMIX ParameterType (created by a FAMIX ParameterizableClass) with the given <b>name</b>, creating it if it does not exist yet
	 * In the second case, sets some default properties: not Abstract, not Final, not Private, not Protected, not Public
	 * @param name -- the name of the FAMIX ParameterType
	 * @param persistIt -- whether the ParameterType should be persisted in the Famix repository
	 * @return the FAMIX ParameterType or null in case of a FAMIX error
	 */
	public ParameterType ensureFamixParameterType(IBinding key, String name, ContainerEntity owner, boolean persistIt) {
		ParameterType fmx = ensureFamixEntity(ParameterType.class, key, name, persistIt);
		fmx.setContainer(owner);
		return fmx;
	}

	public Enum ensureFamixEnum(IBinding key, String name,	ContainerEntity owner, boolean persistIt) {
		Enum fmx = ensureFamixEntity(Enum.class, key, name, persistIt);
		fmx.setContainer(owner);
		return fmx;
	}

	public EnumValue ensureFamixEnumValue(IBinding key, String name, Enum owner, boolean persistIt) {
		EnumValue fmx = ensureFamixEntity(EnumValue.class, key, name, persistIt);
		fmx.setParentEnum(owner);
		return fmx;
	}

	/**
	 * Returns a FAMIX PrimitiveType with the given <b>name</b>, creating it if it does not exist yet
	 * We assume that PrimitiveType must be uniq for a given name
	 * @param name -- the name of the FAMIX PrimitiveType
	 * @return the FAMIX PrimitiveType or null in case of a FAMIX error
	 */
	public PrimitiveType ensureFamixPrimitiveType(IBinding key, String name) {
		return  ensureFamixUniqEntity(PrimitiveType.class, key, name);
	}
	
	/**
	 * Returns a FAMIX Method with the given <b>name</b>, creating it if it does not exist yet
	 * @param key to which the entity will be mapped (may be null, but then it will be difficult to recover the entity)
	 * @param name -- the name of the FAMIX Method (MUST NOT be null, but this is not checked)
	 * @param sig -- method's signature, including type of parameters and return type (should not be null, but it will work if it is)
	 * @param ret -- Famix Type returned by the method (ideally should only be null in case of a constructor, but will accept it in any case)
	 * @param owner -- type defining the method (should not be null, but it will work if it is)
	 * @param persistIt -- whether the Method should be persisted in the Famix repository
	 * @return the FAMIX Method or null in case of a FAMIX error
	 */
	public Method ensureFamixMethod(IBinding key, String name, String sig, Type ret, Type owner, boolean persistIt) {
		Method fmx = (Method) ensureFamixEntity(Method.class, key, name, persistIt);
		fmx.setSignature(sig);
		fmx.setDeclaredType(ret);
		fmx.setParentType(owner);
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
	public Function ensureFamixFunction(IBinding key, String name, String sig, Type ret, ContainerEntity owner, boolean persistIt) {
		Function fmx = (Function) ensureFamixEntity(Function.class, key, name, persistIt);
		fmx.setSignature(sig);
		fmx.setDeclaredType(ret);
		fmx.setContainer(owner);;
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
	public Attribute ensureFamixAttribute(IBinding key, String name, Type type, Type owner, boolean persistIt) {
		Attribute fmx = ensureFamixEntity(Attribute.class, key, name, persistIt);
		fmx.setParentType(owner);
		fmx.setDeclaredType(type);
		return fmx;
	}

	/**
	 * Returns a FAMIX LocalVariable with the given <b>name</b>, creating it if it does not exist yet
	 * @param name -- the name of the FAMIX LocalVariable
	 * @param persistIt -- whether the LocalVariable should be persisted in the Famix repository
	 * @return the FAMIX LocalVariable or null in case of a FAMIX error
	 */
	public LocalVariable ensureFamixLocalVariable(IBinding key, String name, Type type, BehaviouralEntity owner, boolean persistIt) {
		LocalVariable fmx = ensureFamixEntity(LocalVariable.class, key, name, persistIt);
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
	public Comment createFamixComment(String cmt, SourcedEntity owner) {
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
	public Parameter createFamixParameter(IBinding key, String name, Type type, BehaviouralEntity owner, boolean persistIt) {
		Parameter fmx = ensureFamixEntity(Parameter.class, key, name, persistIt);
		fmx.setParentBehaviouralEntity(owner);
		fmx.setDeclaredType(type);
		
		return fmx;
	}
	
	public UnknownVariable ensureFamixUnknownVariable(IBinding key, String name, Package parent) {
		UnknownVariable fmx = null;
		
		if (key != null) {
			fmx = getEntityByKey(UnknownVariable.class, key);
		}

		if (fmx == null) {
			fmx = ensureFamixEntity(UnknownVariable.class, key, name, /*persistIt*/true);
			fmx.setParentPackage(parent);
		}
		
		return fmx;
	}

	public GlobalVariable ensureFamixGlobalVariable(IBinding key, String name, ScopingEntity parent) {
		GlobalVariable fmx;
		fmx = ensureFamixEntity(GlobalVariable.class, key, name, /*persistIt*/true);
		fmx.setParentScope(parent);

		return fmx;
	}

	public <T extends NamedEntity> T ensureFamixEntity(Class<T> fmxClass, IBinding key, String name) {
		return ensureFamixEntity(fmxClass, key, name, /*persistIt*/true);
	}

	public Namespace ensureFamixNamespace(IBinding key, String name, ScopingEntity parent) {
		Namespace fmx = ensureFamixNamespace(key, name);
		/*System.out.println(this.getEntityByKey(key));
		if ((parent != null) && (fmx.getParentScope() !=null)) {
			if (parent != fmx.getParentScope()) {
				fmx.getName();
			}
		}*/
		if (parent != null) {
			fmx.setParentScope(parent);
		}
		return fmx;
	}

	/**
	 * Returns a FAMIX Namespace with the given <b>name</b>, creating it if it does not exist yet
	 * We assume that Namespaces must be uniq for a given name
	 * @param name -- the name of the FAMIX Namespace
	 * @return the FAMIX Namespace or null in case of a FAMIX error
	 */
	public Namespace ensureFamixNamespace(IBinding key, String name) {
		return ensureFamixUniqEntity(Namespace.class, key, name);
	}

	public Package ensureFamixPackage(IBinding key, String name, Package parent) {
		Package fmx = ensureFamixEntity(Package.class, key, name, /*persitIt*/true);
		fmx.setIsStub(false);
		if (parent != null) {
			fmx.setParentPackage(parent);
		}
		return fmx;
	}

	public TypeAlias ensureFamixTypeAlias(IBinding key, String name, ContainerEntity owner) {
		TypeAlias fmx;

		fmx = ensureFamixEntity(TypeAlias.class, key, name, /*persistIt*/true);
		fmx.setContainer(owner);

		return fmx;
	}

	public Type ensureFamixType(IBinding key, String name, ContainerEntity owner) {
		Type fmx = getEntityIfNotNull(Type.class, key);

		if (fmx == null) {
			fmx = ensureFamixType(key, name, owner, /*persistIt*/true);
		}
		
		return fmx;
	}

	public org.moosetechnology.famix.cpp.Class ensureFamixClass(IBinding key, String name, ContainerEntity owner) {
		org.moosetechnology.famix.cpp.Class fmx = getEntityIfNotNull(org.moosetechnology.famix.cpp.Class.class, key);

		if (fmx == null) {
			fmx = ensureFamixClass(key, name, owner, /*persistIt*/true);
		}
		
		return fmx;
	}

	public ParameterizableClass ensureFamixParameterizableClass(IBinding key, String name, ContainerEntity owner) {
		ParameterizableClass fmx = getEntityIfNotNull(ParameterizableClass.class, key);

		if (fmx == null) {
			fmx = ensureFamixParameterizableClass(key, name, owner, /*persistIt*/true);
		}
		
		return fmx;
	}

	public ParameterType ensureFamixParameterType(IBinding key, String name, ContainerEntity owner) {
		ParameterType fmx = getEntityIfNotNull(ParameterType.class, key);

		if (fmx == null) {
			fmx = ensureFamixParameterType(key, name, owner, /*persistIt*/true);
		}
		return fmx;
	}

	public ParameterizedType ensureFamixParameterizedType(IBinding key, String name, ParameterizableClass generic, ContainerEntity owner) {
		ParameterizedType fmx = getEntityIfNotNull(ParameterizedType.class, key);

		if (fmx == null) {
			fmx = ensureFamixParameterizedType(key, name, generic, owner, /*persistIt*/true);
		}

		return fmx;
	}

	/** 
	 * Creating a "parameter type" depends on the context
	 * <ul>
	 * <li> If it is a ParameterizableClass (e.g. "<code>template &lt;class T&gt; class C</code> ...", we create a ParameterType
	 * <li> If it is a Method (e.g. "<code>template &lt;class T&gt; void fct(T)</code> ..."), we create a Type
	 * </ul>
	 */
	public org.moosetechnology.famix.cpp.Type createParameterType(String name, ContainerEntity owner) {
		// apparently CDT gives a binding to the parameterType at its declaration ("template <class T> ...")
		// but not when used ("... mth(T)") so we ignore CDT binding and always use our custom build one
    	IBinding bnd;
    	bnd = StubBinding.getInstance(Type.class, mooseName(owner, name));

		if (owner instanceof ParameterizableClass) {
			return ensureFamixParameterType(bnd, name, owner);
		}
		else {
			return ensureFamixType(bnd, name, owner);
		}
	}

	/**
	 * May return null
	 */
	public Type ensureFamixPrimitiveType(int type) {
		StubBinding bnd = StubBinding.getInstance(Type.class, "_primitive_/"+type);
		return ensureFamixPrimitiveType(bnd, primitiveTypeName(type));
	}

	public Function ensureFamixFunction(IBinding key, String name, String sig, ContainerEntity parent) {
		Function fmx = getEntityIfNotNull(Function.class, key);

		if (fmx == null) {
			fmx = ensureFamixFunction(key, name, sig, /*returnType*/null, parent, /*persistIt*/true);
			fmx.setCyclomaticComplexity(1);
			fmx.setNumberOfStatements(0);
		}
		return fmx;
	}

	public Method ensureFamixMethod(IBinding key, String name, String signature, Type parent) {
		Method fmx = getEntityIfNotNull(Method.class, key);

		if (fmx == null) {
			fmx = ensureFamixMethod(key, name, signature, /*returnType*/null, parent, /*persistIt*/true);
			fmx.setCyclomaticComplexity(1);
			fmx.setNumberOfStatements(0);
		}

		return fmx;
	}

	public Attribute ensureFamixAttribute(IBinding key, String name, Type parent) {
		Attribute fmx = getEntityIfNotNull(Attribute.class, key);

		if (fmx == null) {
			fmx = ensureFamixAttribute(key, name, /*type*/null, parent, /*persistIt*/true);
		}

		return fmx;
	}

	/**
	 * Returns a Famix Parameter associated with the IBinding.
	 * The Entity is created if it does not exist.<br>
	 * Params: see {@link Dictionary#ensureFamixParameter(Object, String, Type, org.moosetechnology.famix.cpp.BehaviouralEntity, boolean)}.
	 * @param persistIt -- whether to persist or not the entity eventually created
	 * @return the Famix Entity found or created. May return null if "bnd" is null or in case of a Famix error
	 */
	public Parameter ensureFamixParameter(IBinding bnd, String name, BehaviouralEntity owner) {
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

	public ImplicitVariable ensureFamixImplicitVariable(String name, Type type, Method owner) {
		IBinding bnd = StubBinding.getInstance(Type.class, mooseName(owner, name));
		return ensureFamixImplicitVariable( bnd, name, type, owner, /*persistIt*/true);
	}

	/**
	 * Sets the visibility of a FamixNamedEntity.
	 * <code>null</code> visibility (e.g. in the case of a function) is silently ignored.
	 */
	public void setVisibility(NamedEntity fmx, Visibility visi) {
		if (visi != null) {
			fmx.addModifiers(visi.toString());
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
	 * This is a convenience method that delegates to one of {@link #mooseName(Function, String)}; {@link #mooseName(Method, String)}; {@link #mooseName(Namespace, String)};
	 * {@link #mooseName(Package, String)}; or {@link #mooseName(Type, String)}
	 * And this is required because at some point we need to call it with an unknown ContainerEntity :-(
	 */
	static public String mooseName(ContainerEntity parent, String name) {
		if (parent instanceof Namespace) {
			return mooseName((Namespace)parent, name);
		}
		else if (parent instanceof Package) {
			return mooseName((Package)parent, name);
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
	 * Computes moose name for a Namespace child.
	 * MooseName is the concatenation of the moosename of the parent Namescape with the simple name of the child
	 */
	static public String mooseName(Namespace parent, String name) {
		String ret;
		if (parent != null) {
			ret = concatMooseName( mooseName((Namespace)parent.getParentScope(), parent.getName()) , name);
		}
		else {
			ret = name;
		}
		return ret;
	}
	
	/**
	 * Computes moose name for a Package child
	 * MooseName is the concatenation of the moosename of the parent Package with the simple name of the child
	 */
	static public String mooseName(Package parent, String name) {
		if (parent != null) {
			return concatMooseName( mooseName(parent.getParentPackage(), parent.getName()) , name);
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
			return concatMooseName( mooseName(parent.getParentType(), parent.getSignature()) , name);
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
			return concatMooseName( mooseName(parent.getContainer(), parent.getSignature()) , name);
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
			return concatMooseName( mooseName(parent.getContainer(), parent.getName()) , name);
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
