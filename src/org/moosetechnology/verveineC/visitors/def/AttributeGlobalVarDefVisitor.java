package org.moosetechnology.verveineC.visitors.def;

import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.index.IIndex;
import org.moosetechnology.famix.cpp.Attribute;
import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.Enum;
import org.moosetechnology.famix.cpp.EnumValue;
import org.moosetechnology.famix.cpp.GlobalVariable;
import org.moosetechnology.famix.cpp.Namespace;
import org.moosetechnology.famix.cpp.Package;
import org.moosetechnology.famix.cpp.ScopingEntity;
import org.moosetechnology.famix.cpp.StructuralEntity;
import org.moosetechnology.famix.cpp.Type;
import org.moosetechnology.famix.cpp.UnknownContainerEntity;
import org.moosetechnology.famix.cpp.UnknownVariable;
import org.moosetechnology.verveineC.plugin.CDictionary;
import org.moosetechnology.verveineC.utils.resolution.QualifiedName;

public class AttributeGlobalVarDefVisitor extends ClassMemberDefVisitor {

	/**
	 * A small enumeration to differentiate the different kind of variable we can create
	 */
	private enum VariableKind {
		GLOBAL, ATTRIBUTE, UNKNOWN;
	}

	public AttributeGlobalVarDefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
	}

	protected String msgTrace() {
		return "creating attributes and struct members";
	}

	/*
	 * To avoid type name with "parameter" as in: aType<aParam>
	 */
	@Override
	protected int visit(IASTNamedTypeSpecifier node) {
		return PROCESS_SKIP;
	}

	/*
	 * To avoid type name with "parameter" as in: aType<aParam>
	 */
	@Override
	public int visit(IASTElaboratedTypeSpecifier node) {
		return PROCESS_SKIP;
	}

	/*
	 * Prune inheritance declaration
	 */
	@Override
	public int visit(ICPPASTBaseSpecifier node) {
		return PROCESS_SKIP;
	}

	/*
	 * to avoid parameter or local variable declarations
	 * May miss anonymous class definition ... (but they are less likely to have attributes)
	 */
	@Override
	protected int visit(IASTFunctionDeclarator node) {
		// get node name and bnd in case we have a function pointer such as var in "int (*var)(int param1, char param2)"
		super.visit( node);
		if (nodeBnd instanceof IVariable) {
			nodeName = node.getNestedDeclarator().getName();
			ensureVariableKind(nodeBnd, nodeName);
		}

		return PROCESS_SKIP;
	}

	/*
	 * to avoid parameter or local variable declarations
	 */
	@Override
	protected int visit(ICPPASTFunctionDefinition node) {
		return PROCESS_SKIP;
	}

	/*
	 * to avoid parameter or local variable declarations
	 */
	@Override
	protected int visit(IASTFunctionDefinition node) {
		return PROCESS_SKIP;
	}

	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		// prunes parameter types in templates
		return PROCESS_SKIP;
	}

	/*
	 * We mostly get here in the case of a variable declaration: an attribute or a "global" variable
	 * Also reached in case of strange uses of macros :-(
	 * For example in "void fct() THE_MACRO((MACRO_ARG));" the IASTDeclarator is "((MACRO_ARG))"
	 */
	@Override
	public int visitInternal(IASTDeclarator node) {
		nodeName = node.getName();
		nodeBnd = resolver.getBinding(nodeName);
		StructuralEntity fmx;

		// In the case of the strange macro use, we found empty names
		if (nodeName.toString().length() > 0) {
			fmx = ensureVariableKind(nodeBnd, nodeName);
			dico.setVisibility(fmx, currentVisibility);

			/* For attributes (ICPPASTDeclarator) the location is that of the parent AST node, i.e. the declaration
			 * For example, in "int a,b;" the declaration starts at "int" whereas there are 2 declarators: a and b
			 */
			dico.addSourceAnchor(fmx, filename, node.getParent().getFileLocation());
		}

		return PROCESS_SKIP;
	}

	/**
	 * ensure a StructuralEntity of the proper kind (GlobalVariable, Attribute, UnknownVariable) 
	 */
	protected StructuralEntity ensureVariableKind(IBinding bnd, IASTName name) {
		ContainerEntity parent;
		VariableKind kind;
		StructuralEntity fmx = null;

		if (QualifiedName.isFullyQualified(name)) {
			parent = (ContainerEntity) resolver.resolveOrCreate( QualifiedName.parentNameFromEntityFullname(name.toString()), /*mayBeNull*/false, UnknownContainerEntity.class);
		}
		else {
			parent = (ContainerEntity) getContext().top();
		}

		if ( (parent == null) || (parent instanceof Namespace) ) {
			kind = VariableKind.GLOBAL;
		}
		else if (parent instanceof Type) {
			kind = VariableKind.ATTRIBUTE;
		}
		else {
			parent = getContext().topPckg();
			kind = VariableKind.UNKNOWN;
		}

		if (bnd == null) {
			switch (kind) {
			case GLOBAL:
				bnd = resolver.mkStubKey(name, GlobalVariable.class);
				break;
			case ATTRIBUTE:
				bnd = resolver.mkStubKey(name, Attribute.class);
				break;
			case UNKNOWN:
				bnd = resolver.mkStubKey(name, UnknownVariable.class);
				break;
			}
		}

		switch (kind) {
		case GLOBAL:
			fmx = dico.ensureFamixGlobalVariable(bnd, name.toString(), (ScopingEntity) parent);
			break;
		case ATTRIBUTE:
			fmx = dico.ensureFamixAttribute(bnd, name.toString(), (Type) parent);
			break;
		case UNKNOWN:
			try {			
			fmx = dico.ensureFamixUnknownVariable(bnd, name.toString(), (Package) parent);
			break;}  catch( ClassCastException ex) {  
			// issue #910 hack
			// faced a case where an expected UnknownVariable was finally resolved as an Attribute.
			// don't know the parser enough to understand precisely where it goes wrong
			// my solution here is to give a try to resolve as an Attribute first.
			// if it raise an error, go on with the initial behavior
			fmx = dico.ensureFamixAttribute(bnd, name.toString(), (Type) parent);
			break;
			}
		
		}

		fmx.setIsStub(false);

		return fmx;
	}

	@Override
	protected int visit(IASTSimpleDeclaration node) {

		if (declarationIsTypedef(node)) {
			node.getDeclSpecifier().accept(this);
			// skip declarators
			return PROCESS_SKIP;
		}

		return PROCESS_CONTINUE;
	}

	@Override
	protected int visit(IASTEnumerationSpecifier node) {
		nodeBnd = null;
		nodeName = node.getName();

		if (nodeName.equals("")) {
			nodeBnd = resolver.mkStubKey(""+node.getFileLocation().getNodeOffset(), org.moosetechnology.famix.cpp.Enum.class);
		}
		else {
			nodeBnd = resolver.getBinding(nodeName);
			if (nodeBnd == null) {
				nodeBnd = resolver.mkStubKey(nodeName.toString(), org.moosetechnology.famix.cpp.Enum.class);
			}
		}

		this.getContext().push(dico.getEntityByKey(nodeBnd));
		for (IASTEnumerator decl : node.getEnumerators()) {
			decl.accept(this);
		}
		returnedEntity = getContext().pop();

		return PROCESS_SKIP;
	}

	@Override
	public int visit(IASTEnumerator node) {
		EnumValue fmx;

		nodeBnd = null;
		nodeName = node.getName();
		if (nodeBnd == null) {
		}
		fmx = dico.ensureFamixEnumValue(nodeBnd, nodeName.toString(), (Enum)getContext().top(), /*persistIt*/true);
		fmx.setIsStub(false);
		dico.addSourceAnchor(fmx, filename, node.getFileLocation());

		return PROCESS_SKIP;
	}

}
