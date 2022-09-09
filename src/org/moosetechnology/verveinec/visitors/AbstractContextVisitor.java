package org.moosetechnology.verveinec.visitors;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTVisibilityLabel;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.Path;
import org.moosetechnology.verveinec.plugin.CDictionary;
import org.moosetechnology.verveinec.utils.AnonymousName;
import org.moosetechnology.verveinec.utils.CppEntityStack;
import org.moosetechnology.verveinec.utils.NameResolver;
import org.moosetechnology.verveinec.utils.StubBinding;
import org.moosetechnology.verveinec.utils.files.FileUtil;
import org.moosetechnology.verveineCore.gen.famix.BehaviouralEntity;
import org.moosetechnology.verveineCore.gen.famix.Class;
import org.moosetechnology.verveineCore.gen.famix.NamedEntity;
import org.moosetechnology.verveineCore.gen.famix.Namespace;
import org.moosetechnology.verveineCore.gen.famix.Package;
import org.moosetechnology.verveineCore.gen.famix.Parameter;
import org.moosetechnology.verveineCore.gen.famix.SourcedEntity;

/**
 * Visitor that gets the {@link #nodeBnd} and {@link #nodeName} for the main entities.
 * It manages the context stack, the current path, and the current filename.
 * It uses a {@link #resolver} to recover the binding of the nodes
 */
public abstract class AbstractContextVisitor extends AbstractDispatcherVisitor {

	public static final String ANONYMOUS_PARAMETER_DECLARATION = "--AnonymousParameterDeclaration--";

	/**
	 * Prefix to remove from file names
	 */
	protected String rootFolder;

	/**
	 * Current path of the directory being visited (used to compute packages StubBinding)
	 */
	protected Path currentPath;

	/**
	 * An object responsible for resolving names.
	 * This implies keeping track of the current context stack as well as handling the CDT IIndex, finding bindings for names,
	 * or dealing with name (fully-qualified or not)
	 */
	protected NameResolver resolver;

	/**
	 *  name of the current file (TranslationUnit) being visited
	 */
	protected String filename;

	/**
	 * A variable used in many visit methods to store the name of the current node
	 */
	protected IASTName nodeName;

	/**
	 * A variable used in many visit methods to store the binding of the current node
	 */
	protected IBinding nodeBnd;

	/**
	 * FamixSourcedEntity created as a result of a visitor.
	 * This is required to treat it in a parent visit method or a potential parent visitor.
	 * However, return value of Visitors is already codified by {@link ASTVisitor}
	 * (see {@link ASTVisitor#PROCESS_CONTINUE}m {@link ASTVisitor#PROCESS_ABORT}m and {@link ASTVisitor#PROCESS_SKIP}.
	 * This attributes allows to hold "another return value" (together with a getter)
	 */
	protected SourcedEntity returnedEntity;

	/**
	 * A flag to allow visiting separately header files (.h) and source files (.c)
	 * The idea is to visit first the header files
	 */
	protected boolean visitHeaders;

	/**
	 * Indice in the behavioural list of parameters of the current parameter
	 */
	protected int iParam;

	public AbstractContextVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index);
		this.rootFolder = rootFolder;
		this.resolver = new NameResolver(dico, index);
		currentPath = new Path("");
	}

	/** Basically this is a <code>visit(ICContainer)</code> method, but we cannot visit its children (as done in super class) here,
	 * so we put it under a different name so that visitors can call it easily.
	 * It is only needed in PackageDefVisitor and TypeDefVisitor as they are the only one that may define entities that are direct
	 * children of packages
	 */
	protected void visitInternal(ICContainer elt) {
		currentPath = (Path) currentPath.append(elt.getElementName());

	    if (currentPath.segmentCount() > 2) {  // i.e. skip the project directories: tempProj/src
	    	nodeBnd = StubBinding.getInstance(Package.class, currentPath.toString());
	    }
	    else {
	    	nodeBnd = null;
	    }
	}

	protected void leave(ICContainer elt) {
		currentPath = (Path) currentPath.removeLastSegments(1);
	}

	
	/*
	 * be careful, overriden in some subclasses so that this one is not called
	 */
	@Override
	public void visit(ITranslationUnit elt) {
		setContext(new CppEntityStack());
		this.filename = FileUtil.localized(elt.getFile().getRawLocation().toString(), rootFolder);
		super.visit(elt);
	}

	@Override
	public int visit(ICPPASTNamespaceDefinition node) {
		Namespace fmx;
	

		nodeBnd = resolver.getBinding(node.getName());

		fmx = dico.getEntityByKey(Namespace.class, nodeBnd);
		
		// Namespaces are created very early in the process, so we can rely on them being already created here 
		contextPush(fmx);

		return PROCESS_CONTINUE;
	}

	@Override
	public int leave(ICPPASTNamespaceDefinition node) {
		contextPop();
		return super.leave(node);
	}

	/*
	 * Visiting a class definition to get its key (IBinding) associated with the famix type entity
	 * +merging ICPPASTCompositeTypeSpecifier and ICASTCompositeTypeSpecifier
	 */
	@Override
	protected int visit(ICPPASTCompositeTypeSpecifier node) {
		return this.visit( (IASTCompositeTypeSpecifier)node);
	}

	/*
	 * Visiting a class definition to get its key (IBinding) associated with the famix type entity
	 * +merging ICPPASTCompositeTypeSpecifier and ICASTCompositeTypeSpecifier
	 */
	@Override
	protected int visit(ICASTCompositeTypeSpecifier node) {
		return this.visit((IASTCompositeTypeSpecifier)node);
	}

	/*
	 * merging ICPPASTCompositeTypeSpecifier and ICASTCompositeTypeSpecifier
	 */
	@Override
	protected int visit(IASTCompositeTypeSpecifier node) {
		nodeName = node.getName();
		nodeBnd = resolver.getBinding(nodeName);

		if (nodeBnd == null) {
			nodeBnd = resolver.mkStubKey(nodeName, Class.class);
		}

		return PROCESS_CONTINUE;
	}

	/*
	 * merging ICASTElaboratedTypeSpecifier and ICPPASTElaboratedTypeSpecifier
	 * to recover node_name and node_bnd
	 */
	@Override
	protected int visit(IASTElaboratedTypeSpecifier node) {
		nodeName = node.getName();
		nodeBnd = resolver.getBinding(nodeName);
		
		if (nodeBnd == null) {
			nodeBnd = resolver.mkStubKey(nodeName, Class.class);
		}

		return PROCESS_CONTINUE;
	}

	/*
	 * merging ICASTElaboratedTypeSpecifier and ICPPASTElaboratedTypeSpecifier
	 * to recover node_name and node_bnd
	 */
	@Override
	protected int visit(ICASTElaboratedTypeSpecifier node) {
		return this.visit((IASTElaboratedTypeSpecifier)node);
	}

	/*
	 * merging ICASTElaboratedTypeSpecifier and ICPPASTElaboratedTypeSpecifier
	 * to recover node_name and node_bnd
	 */
	@Override
	protected int visit(ICPPASTElaboratedTypeSpecifier node) {
		return this.visit((IASTElaboratedTypeSpecifier)node);
	}

	@Override
	protected int visit(IASTEnumerationSpecifier node) {
		nodeName = node.getName();
		if (nodeName.equals("")) {
			// case of anonymous enum: it is probably within a typedef and will never be used directly
			// so the key is mostly irrelevant, only used to find back the type when creating its enumerated values 
			nodeBnd = resolver.mkStubKey(""+node.getFileLocation().getNodeOffset(), org.moosetechnology.verveineCore.gen.famix.Enum.class);
		}
		else {
			nodeBnd = resolver.getBinding(nodeName);
			if (nodeBnd == null) {
				nodeBnd = resolver.mkStubKey(nodeName, org.moosetechnology.verveineCore.gen.famix.Enum.class);
			}
		}

		return PROCESS_CONTINUE;
	}

	/**
	 * All functions treated equally here
	 * Inheritance hierarchy of function declarator nodes:
	 * <ul>
	 * <li> IASTFunctionDeclarator</li>
	 * <li> - IASTStandardFunctionDeclarator</li>
	 * <li> - - ICPPASTFunctionDeclarator</li>
	 * <li> - - - ICPPASTFunctionTryBlockDeclarator</li>
	 * <li> - ICASTKnRFunctionDeclarator</li>
	 * </ul>
	 */
	@Override
	protected int visit(IASTFunctionDeclarator node) {
		nodeName = node.getName();
		nodeBnd = resolver.getFunctionBinding(node, nodeName);

		return PROCESS_CONTINUE;
	}

	@Override
	public int visit(IASTParameterDeclaration node) {
		nodeBnd = null;
		nodeName = node.getDeclarator().getName();

		if (isVoidDecl(node.getDeclSpecifier()) && ! isPointerDecl(node.getDeclarator())) {
			// case of a "mth(void)" declaration, seen as a parameter with no name
			// the declaration is of type "void" but not a pointer (as in "void*")
			return PROCESS_SKIP;
		}
		
		if (nodeName.toString().length() == 0) {
			nodeName = new AnonymousName(nodeName, "Parameter"+iParam);
		}

		nodeBnd = resolver.getBinding(nodeName);
		if (nodeBnd == null) {
			nodeBnd = resolver.mkStubKey(nodeName.toString(),Parameter.class);
		}

		return PROCESS_CONTINUE;
	}

	protected void visitParameters(IASTNode[] params, BehaviouralEntity fmx) {
		contextPush(fmx);
		
		// iParam allow to keep track of which parameter we are processing in the behavioural list of parameters
		iParam = 0;
		for (IASTNode param : params) {
			param.accept(this);
			iParam++;
		}
		contextPop();
	}

	@Override
	protected int visit(ICPPASTVisibilityLabel node) {
		return PROCESS_CONTINUE;
	}

	// UTILITIES ======================================================================================================

	private boolean isPointerDecl(IASTDeclarator declarator) {
		
		return declarator.getPointerOperators().length > 0;
	}

	private boolean isVoidDecl(IASTDeclSpecifier declSpecifier) {
		if (declSpecifier instanceof IASTSimpleDeclSpecifier) {
			return ((IASTSimpleDeclSpecifier)declSpecifier).getType() == IASTSimpleDeclSpecifier.t_void;
		}
		else {
			return false;
		}
	}

	protected boolean declarationIsTypedef(IASTSimpleDeclaration node) {
		return (node.getDeclSpecifier().getStorageClass() == IASTDeclSpecifier.sc_typedef);
	}

	protected boolean nodeParentIsClass(IASTNode node) {
		return node.getParent() instanceof IASTCompositeTypeSpecifier;
	}

	public void setVisitHeaders(boolean visitHeaders) {
		this.visitHeaders = visitHeaders;
	}

	protected boolean checkHeader(ITranslationUnit tu) {
		if (visitHeaders) {
			return FileUtil.isHeader(tu);
		}
		else {
			return (! FileUtil.isHeader(tu) );
		}
	}


	protected CppEntityStack getContext() {
		return resolver.getContext();
	}

	protected void setContext(CppEntityStack context) {
		resolver.setContext( context);
	}

	protected void contextPush(NamedEntity entity) {
		getContext().push(entity);
	}

	protected NamedEntity contextPop() {
		return getContext().pop();
	}

	protected NamedEntity contextTop() {
		return getContext().top();
	}
}
