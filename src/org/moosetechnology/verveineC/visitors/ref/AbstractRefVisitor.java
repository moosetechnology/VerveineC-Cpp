package org.moosetechnology.verveineC.visitors.ref;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNameOwner;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTConstructorChainInitializer;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPTemplateInstance;
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator;
import org.eclipse.cdt.core.index.IIndex;
import org.moosetechnology.famix.cpp.Access;
import org.moosetechnology.famix.cpp.Association;
import org.moosetechnology.famix.cpp.BehaviouralEntity;
import org.moosetechnology.famix.cpp.BehaviouralReference;
import org.moosetechnology.famix.cpp.Class;
import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.DereferencedInvocation;
import org.moosetechnology.famix.cpp.Invocation;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.ParameterizableClass;
import org.moosetechnology.famix.cpp.ParameterizedType;
import org.moosetechnology.famix.cpp.SourcedEntity;
import org.moosetechnology.famix.cpp.StructuralEntity;
import org.moosetechnology.famix.cpp.Type;
import org.moosetechnology.verveineC.plugin.CDictionary;
import org.moosetechnology.verveineC.utils.Trace;
import org.moosetechnology.verveineC.utils.resolution.QualifiedName;
import org.moosetechnology.verveineC.utils.resolution.StubBinding;
import org.moosetechnology.verveineC.visitors.AbstractVisitor;

/**
 * Abstract superclass for Reference visitors.<BR>
 * It defines some utility methods to create references to names.
 * @author anquetil
 */
@SuppressWarnings("unused")
public abstract class AbstractRefVisitor extends AbstractVisitor {

	/**
	 * see {@link #returnedEntity}
	 */
	public SourcedEntity returnedEntity() {
		return returnedEntity;
	}


	public AbstractRefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
	}


	/*
	 * putting class definition on the context stack
	 */
	@Override
	protected int visit(ICPPASTCompositeTypeSpecifier node) {
		Class fmx;

		/* Gets the key (IBinding) of the node to recover the famix type entity */
		super.visit(node);

		fmx = dico.getEntityByKey(Class.class, nodeBnd);

		this.getContext().push(fmx);
		for (IASTDeclaration decl : node.getDeclarations(/*includeInactive*/true)) {
		}
		returnedEntity = getContext().pop();

		return PROCESS_SKIP;
	}

	@Override
	public int visit(IASTSimpleDeclSpecifier node) {
		returnedEntity = dico.ensureFamixPrimitiveType( ((IASTSimpleDeclSpecifier) node).getType());
		return PROCESS_SKIP;
	}

	@Override
	protected int visit(IASTFunctionDeclarator node) {
		// get node name and bnd
		super.visit(node);
		if (nodeBnd instanceof IVariable) {
			// declaration of a function pointer such as var in "int (*var)(int param1, char param2)"
			returnedEntity = null;
		}
		else {
			returnedEntity = resolver.ensureBehavioural(node, nodeBnd, nodeName);
		}

		return PROCESS_SKIP;
	}

	@Override
	protected int visit(IASTFunctionDefinition node) {
		returnedEntity = null;
		node.getDeclarator().accept(this);
		this.getContext().push((BehaviouralEntity)returnedEntity);
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		returnedEntity = this.getContext().pop();

		return PROCESS_SKIP;
	}

	/*
	 * need to have ICPPASTFunctionDefinition also because it has specificities not found in IASTFunctionDefinition
	 * (MemberInitializers)
	 */
	@Override
	protected int visit(ICPPASTFunctionDefinition node) {
		returnedEntity = null;

		visit( (IASTFunctionDefinition)node);  // visit declarator and body (see above)

		this.getContext().push((BehaviouralEntity)returnedEntity);

		for (ICPPASTConstructorChainInitializer init : node.getMemberInitializers()) {
			init.accept(this);
		}

		returnedEntity = this.getContext().pop();

		return PROCESS_SKIP;
	}

	/**
	 * Records an Invocation of a famixBehaviouralEntity and sets lastInvocation attribute.
	 * Assumes the context is correctly set (i.e. top contains a BehaviouralEntity that makes the invocation) 
	 * @param fmx -- invoked BehaviouralEntity
	 * @return the invocation created
	 */
	protected Invocation invocationOfBehavioural(BehaviouralEntity fmx) {
		BehaviouralEntity accessor = this.getContext().topBehaviouralEntity();
		Invocation invok = dico.addFamixInvocation(accessor, fmx, /*receiver*/null, /*signature*/null, getContext().getLastInvocation());
		getContext().setLastInvocation(invok);
		return invok;
	}

	/**
	 * Records an Invocation of a BehaviouralEntity referenced by a variable (a pointer) and sets lastInvocation attribute.
	 * Assumes the context is correctly set (i.e. top contains a BehaviouralEntity that makes the invocation) 
	 * @param fmx -- StructuralEntity pointing to a BehaviouralEntity invoked
	 * @return the invocation created
	 */
	protected DereferencedInvocation dereferencedInvocation(StructuralEntity fmx, String sig) {
		BehaviouralEntity accessor = this.getContext().topBehaviouralEntity();
		DereferencedInvocation invok = dico.addFamixDereferencedInvocation(accessor, fmx, /*signature*/sig, getContext().getLastInvocation());
		getContext().setLastInvocation(invok);
		return invok;
	}

	/**
	 * Records a reference (pointer) to a famixBehaviouralEntity.
	 * Assumes the context is correctly set (i.e. top contains another BehaviouralEntity that makes the reference) 
	 * @param fmx -- referenced BehaviouralEntity
	 * @return the reference created
	 */
	protected BehaviouralReference behaviouralPointer(BehaviouralEntity fmx) {
		BehaviouralEntity referer = this.getContext().topBehaviouralEntity();
		BehaviouralReference ref = dico.addFamixBehaviouralPointer(referer, fmx);
		return ref;
	}


}