package org.moosetechnology.verveinec.visitors.def;

import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ICContainer;
import org.moosetechnology.verveineCore.gen.famix.Class;
import org.moosetechnology.verveineCore.gen.famix.ContainerEntity;
import org.moosetechnology.verveineCore.gen.famix.NamedEntity;
import org.moosetechnology.verveineCore.gen.famix.Package;
import org.moosetechnology.verveineCore.gen.famix.Type;
import org.moosetechnology.verveineCore.gen.famix.TypeAlias;
import org.moosetechnology.verveinec.plugin.CDictionary;
import org.moosetechnology.verveinec.utils.files.FileUtil;
import org.moosetechnology.verveinec.visitors.AbstractContextVisitor;

public class TypeDefVisitor extends AbstractContextVisitor {

	/**
	 * The file directory being visited at any given time
	 */
	protected Package currentPackage;

	public Package getCurrentPackage() {
		return currentPackage;
	}

	public void setCurrentPackage(Package currentPackage) {
		this.currentPackage = currentPackage;
	}

	/**
	 * used between {@link #visit(ICPPASTTemplateDeclaration)} and {@link #visit(ICPPASTCompositeTypeSpecifier)}
	 * to mark class definitions that are FAMIXParameterizableClass
	 */
	protected boolean definitionOfATemplate;
	private NamedEntity popedFriendContext;

	// CONSTRUCTOR ==========================================================================================================================

	/**
	 * Default constructor for definition pass
	 * @param dico where entities are created
	 * @param index CDT index containing bindings
	 */
	public TypeDefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
		currentPackage = null;
		definitionOfATemplate = false;
	}

	protected String msgTrace() {
		return "creating classes and types";
	}

	/**
	 * get Package associated to file directory
	 */
	@Override
	public void visit(ICContainer elt) {

		visitInternal(elt);
		if (nodeBnd != null) {
			currentPackage = dico.getEntityByKey(Package.class, nodeBnd);
		}
		else {
			currentPackage = null;
		}

		super.visit(elt);

		if (currentPackage != null) {
			currentPackage = currentPackage.getParentPackage();    // back to parent package
		}
		leave(elt);
	}

	@Override
	protected int visit(IASTSimpleDeclaration node) {
		TypeAlias aliasType = null;
		Type concreteType = null;

		if (declarationIsTypedef(node)) {
//			boolean functionPointerTypedef = false;
			String tmpFilename;

//			if (isFunctionPointerTypedef(node)) {
//				concreteType = null;  // TODO create a FunctionPointer special type?
//				functionPointerTypedef = true;
//			}
//			else {
				returnedEntity = null;
				node.getDeclSpecifier().accept(this);
				concreteType = (Type) returnedEntity;
//			}

			for (IASTDeclarator declarator : node.getDeclarators()) {
			// this is a typedef, so the declarator(s) should be FAMIXType(s)

//				if (functionPointerTypedef) {
//					nodeName = declarator.getNestedDeclarator().getName();
//				}
//				else {
					nodeName = declarator.getName();
//				}

				nodeBnd = resolver.getBinding(nodeName);

				if (nodeBnd == null) {
					// create one anyway, assume this is a Type
					nodeBnd = resolver.mkStubKey(nodeName, Type.class);
				}

				aliasType = dico.ensureFamixTypeAlias(nodeBnd, nodeName.toString(), (ContainerEntity)contextTop());
				aliasType.setIsStub(false);
				aliasType.setParentPackage(currentPackage);
				aliasType.setAliasedType(concreteType);
				/* We can be in a case where the class declaration being processed belongs to a file imported using an #includes statement
				 In such a case, filename instance variable will be initialized with location of the file *including* the external file. 
				 We should not use filename to generate the source anchor entity, as this is not reliable.
				 Instead, we should recompute a file location based on the current processed ast node.*/
				tmpFilename = FileUtil.localized( node.getFileLocation().getFileName(), rootFolder);
				dico.addSourceAnchor(aliasType, tmpFilename, node.getFileLocation());
				
				// if it is a function pointer typedef, don't process the function definition any further
				// is there any other cases where we wouldn't want to do it?
				if (! isFunctionPointerTypedef(node)) {
					declarator.accept(this);
				}
			}
			
			return PROCESS_SKIP;  // typedef already handled
		} 
		// else includes such statements as: "class XYZ;" and treated by the normal process (i.e. return PROCESS_CONTINUE)

		return PROCESS_CONTINUE;
	}
	
	/** Visiting a class or struct definition
	 */
	@Override
	protected int visit(IASTCompositeTypeSpecifier node) {
		Class fmx;

		super.visit(node);		// compute nodeName and binding
		fmx = createClass(node);
		fmx.setIsStub(false);

		this.contextPush(fmx);

		return PROCESS_CONTINUE;
	}
	
	@Override
	protected int leave(IASTCompositeTypeSpecifier node) {
		returnedEntity = contextPop();
		
		return PROCESS_CONTINUE;
	}

	@Override
	protected int visit(ICPPASTTemplateDeclaration node) {
		definitionOfATemplate = true;
		
		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(ICPPASTTemplateDeclaration node) {
		definitionOfATemplate = false;
		return PROCESS_CONTINUE;
	}

	/**
	 * a class declaration such as "class XYZ;" but also structs
	 */
	@Override
	protected int visit(IASTElaboratedTypeSpecifier node) {
		popedFriendContext = null;

		super.visit(node);

		if (isCppFriendDeclaration(node)) {
			/* bug if we have several friend declarations, each one will pop out a context */
			popedFriendContext = contextPop();
		}

		switch (node.getKind()) {
		case IASTElaboratedTypeSpecifier.k_struct:
		case IASTElaboratedTypeSpecifier.k_union:
		case ICPPASTElaboratedTypeSpecifier.k_class:
			createClass(node);
			break;
		case IASTElaboratedTypeSpecifier.k_enum:
			createEnum(node);
			break;
		default:
			// should not happen
		}
		return PROCESS_CONTINUE;
	}
	
	@Override
	protected int leave(IASTElaboratedTypeSpecifier node) {
		if (popedFriendContext != null) {
			contextPush(popedFriendContext);
		}

		return PROCESS_CONTINUE;
	}

	@Override
	protected int visit(IASTFunctionDeclarator node) {
		if (isFunctionPointerTypedef(node)) {
			createFunctionPointerType(node);
		}
		// turn off definitionOfATemplate in case this is a method template
		definitionOfATemplate = false;

		return PROCESS_SKIP;
	}

	@Override
	protected int visit(IASTEnumerationSpecifier node) {
		org.moosetechnology.verveineCore.gen.famix.Enum fmx;

		super.visit(node);

		fmx = createEnum(node);
		fmx.setIsStub(false);

		return PROCESS_CONTINUE;
	}

	/**
	 * Explicitly skipping CastExpressions because they may contain "TypeDeclaration" 
	 */
	@Override
	protected int visit(IASTCastExpression node) {
		return PROCESS_SKIP;
	}

	
	// ---- UTILITIES ----

	private Type createFunctionPointerType(IASTFunctionDeclarator node) {
		nodeName = node.getNestedDeclarator().getName();
		nodeBnd = resolver.getBinding(nodeName);

		if (nodeBnd == null) {
			// create one anyway, assume this is a Type
			nodeBnd = resolver.mkStubKey(nodeName, Type.class);
		}

		return dico.ensureFamixType(nodeBnd, nodeName.toString(), (ContainerEntity)contextTop());
	}

	/**
	 * Common code to create a class that can be a template.
	 * Used for ICPPASTCompositeTypeSpecifier, ICASTCompositeTypeSpecifier, ICPPASTElaboratedTypeSpecifier
	 */
	protected Class createClass(IASTDeclSpecifier node) {
		Class fmx;
		String tmpFilename;
		boolean isTemplate = definitionOfATemplate;
		definitionOfATemplate = false;   // Immediately turn it off because it could pollute visiting the children

		if (isTemplate) {
			fmx = dico.ensureFamixParameterizableClass(nodeBnd, nodeName.toString(), (ContainerEntity)contextTop());
		}
		else {
			// if node is a stub with a fully qualified name, its parent is not context.top() :-(
			fmx = dico.ensureFamixClass(nodeBnd, nodeName.toString(), (ContainerEntity)contextTop());
		}
		fmx.setParentPackage(currentPackage);
		
		/* We can be in a case where the class declaration being processed belongs to a file imported using an #includes statement
		 In such a case, filename instance variable will be initialized with location of the file including the external file. 
		 We should not use filename to generate the source anchor entity, as this is not reliable.
		 Instead, we should recompute a file location based on the current processed AST node.*/
		tmpFilename = FileUtil.localized( node.getFileLocation().getFileName(), rootFolder);
		
		// dealing with template class/struct
		if (isTemplate) {
			dico.addSourceAnchor(fmx, tmpFilename, ((ICPPASTTemplateDeclaration)node.getParent().getParent()).getFileLocation());
		}
		else {
	
			dico.addSourceAnchor(fmx, tmpFilename, node.getFileLocation());
		}
		return fmx;
	}

	
	/**
	 * Common code to create an enumeratedType.
	 * Used for IASTEnumerationSpecifier, IASTElaboratedTypeSpecifier
	 */
	protected org.moosetechnology.verveineCore.gen.famix.Enum createEnum(IASTDeclSpecifier node) {
		org.moosetechnology.verveineCore.gen.famix.Enum fmx;

		fmx = dico.ensureFamixEnum(nodeBnd, nodeName.toString(), (ContainerEntity)contextTop(), /*persistIt*/true);
		dico.addSourceAnchor(fmx, filename, node.getFileLocation());

		returnedEntity = fmx;

		return fmx;
	}

	protected boolean isFunctionPointerTypedef(IASTNode node) {
		if (node == null) {
			return false;
		}
		if (! (node instanceof IASTSimpleDeclaration) ) {
			return false;
		}
		
		IASTDeclarator[] declarators = ((IASTSimpleDeclaration)node).getDeclarators();
		return (declarators.length>0) &&                       // should always be the case, no?
				(declarators[0].getNestedDeclarator()!=null);
	}
	
	private boolean isCppFriendDeclaration(IASTElaboratedTypeSpecifier node) {
		if (node instanceof ICPPASTElaboratedTypeSpecifier) {
			return ((ICPPASTDeclSpecifier) node).isFriend();
		}
		else {
			return false;
		}
	}

}
