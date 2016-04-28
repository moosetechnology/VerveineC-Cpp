package eu.synectique.verveine.extractor.ref;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IProblemBinding;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTRangeBasedForStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTryBlockStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPBase;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPConstructor;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.index.IIndexBinding;
import org.eclipse.cdt.core.index.IIndexName;
import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateDeclaration;
import org.eclipse.core.runtime.CoreException;

import eu.synectique.verveine.core.Dictionary;
import eu.synectique.verveine.core.EntityStack2;
import eu.synectique.verveine.core.gen.famix.Access;
import eu.synectique.verveine.core.gen.famix.Association;
import eu.synectique.verveine.core.gen.famix.Attribute;
import eu.synectique.verveine.core.gen.famix.BehaviouralEntity;
import eu.synectique.verveine.core.gen.famix.Class;
import eu.synectique.verveine.core.gen.famix.ContainerEntity;
import eu.synectique.verveine.core.gen.famix.Function;
import eu.synectique.verveine.core.gen.famix.Inheritance;
import eu.synectique.verveine.core.gen.famix.Invocation;
import eu.synectique.verveine.core.gen.famix.Method;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import eu.synectique.verveine.core.gen.famix.Namespace;
import eu.synectique.verveine.core.gen.famix.Package;
import eu.synectique.verveine.core.gen.famix.ScopingEntity;
import eu.synectique.verveine.core.gen.famix.StructuralEntity;
import eu.synectique.verveine.core.gen.famix.Type;
import eu.synectique.verveine.extractor.def.CDictionaryDef;
import eu.synectique.verveine.extractor.utils.NullTracer;
import eu.synectique.verveine.extractor.utils.Tracer;

public class RefVisitor extends AbstractVisitor implements ICElementVisitor {

	/**
	 * A stack that keeps the current definition context (package/class/method)
	 */
	protected EntityStack2 context;

	/**
	 * The source code of the visited AST.
	 * Used to find back the contents of non-javadoc comments
	 */
	protected RandomAccessFile source;

	/**
	 * Whether a variable access is lhs (write) or not
	 */
	protected boolean inAssignmentLHS = false;

	// CONSTRUCTOR ==========================================================================================================================

	/**
	 * Default constructor, dicoDef contains entities created during def pass
	 * @param dicoDef contains entities created during def pass
	 * @param dicoRef where entities are created during ref pass (the current pass)
	 * @param index CDT index containing bindings
	 */
	public RefVisitor(CDictionary dico, IIndex index) {
		super(dico, index, /*visitNodes*/true);

		tracer = new Tracer("REF>");
	}

	// VISITING METODS ON ICELEMENT HIERARCHY ==============================================================================================

	/**
	 * Visiting a source file
	 */
	@Override
	public void visit(ITranslationUnit tu) {
		tracer.up("ITranslationUnit: "+tu.getElementName());
		context = new EntityStack2();    // "reseting" context
		super.visit(tu);
		tracer.down();
	}


	// CDT VISITING METODS ON AST ==========================================================================================================

	@Override
	public int visit(ICPPASTNamespaceDefinition node) {
		tracer.up("ICPPASTNamespaceDefinition: "+node.getName());
		Namespace fmx;
		IASTName nodeName = node.getName();
		IIndexBinding bnd = null;

		nodeName = node.getName();

		try {
			bnd = index.findBinding(nodeName);
		}
		catch (CoreException e) {
			e.printStackTrace();
		}

		if (bnd == null) {
			return PROCESS_SKIP;
		}

		fmx = (Namespace) dico.getEntityByKey(bnd);

		if (fmx != null) {
			this.context.push(fmx);
		}

		return super.visit(node);
	}

	@Override
	public int leave(ICPPASTNamespaceDefinition node) {
		this.context.pop();
		tracer.down();
		return super.leave(node);
	}

	@Override
	public int visit(IASTDeclaration node) {
		// includes CPPASTVisibilityLabel
//		tracer.msg("IASTDeclaration");
		if (node instanceof ICPPASTFunctionDefinition) {
			return visit((ICPPASTFunctionDefinition)node);
		}
		return super.visit(node);
	}

	@Override
	public int leave(IASTDeclaration node) {
		if (node instanceof ICPPASTFunctionDefinition) {
			return leave((ICPPASTFunctionDefinition)node);
		}
		return super.leave(node);
	}

	@Override
	public int visit(IASTExpression node) {
		if (node instanceof IASTFieldReference) {
			// can also be a method invocation
			((IASTFieldReference)node).getFieldOwner().accept(this);
			((IASTFieldReference) node).getFieldName().accept(this);
			return PROCESS_SKIP;
		}
		else if (node instanceof IASTFunctionCallExpression) {
			visit((IASTFunctionCallExpression)node);
			return PROCESS_CONTINUE;  // should be SKIP because we already visited the FunctionNameExpression?
		}
		else if (node instanceof IASTIdExpression) {
			referenceToName(((IASTIdExpression) node).getName());
			return PROCESS_SKIP;
		}
		else if (node instanceof IASTBinaryExpression) {
			visit((IASTBinaryExpression)node);   // to check whether this is an assignement
			return PROCESS_SKIP;
		}
		else if (node instanceof IASTLiteralExpression) {
			if ( ((IASTLiteralExpression)node).getKind() == ICPPASTLiteralExpression.lk_this ) {
				if (context.topType() != null) {
					accessToVar(dico.ensureFamixImplicitVariable(Dictionary.SELF_NAME, /*type*/context.topType(), /*owner*/context.topMethod(), /*persistIt*/true));
				}
			}
			return PROCESS_SKIP;
		}

		return super.visit(node);
	}

	@Override
	public int visit(IASTName node) {
		referenceToName(((IASTName) node).getLastName());
		return ASTVisitor.PROCESS_SKIP;
	}


	// ADDITIONAL VISITING METODS ON AST ==================================================================================================

	/** Visiting a class definition
	 */
	@Override
	protected int visit(ICPPASTCompositeTypeSpecifier node) {
		IASTName nodeName;
		IIndexBinding bnd = null;
		Class fmx;

		nodeName = node.getName();
		tracer.up("ICPPASTCompositeTypeSpecifier:"+nodeName.toString());

		try {
			bnd = index.findBinding(nodeName);
		} catch (CoreException e) {
			System.err.println("error getting index");
			e.printStackTrace();
		}

		if (bnd == null) {
			return PROCESS_SKIP;
		}

		fmx = (Class) dico.getEntityByKey(bnd);

		if (fmx == null) {
			return PROCESS_SKIP;
		}

		this.context.push(fmx);

		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(ICPPASTCompositeTypeSpecifier node) {
		context.pop();
		tracer.down();

		return PROCESS_CONTINUE;		
	}

	/**
	 * Visiting a method or function declaration
	 */
	@Override
	protected int visit(IASTFunctionDeclarator node) {
		IASTName nodeName;
		IIndexBinding bnd = null;
		BehaviouralEntity fmx = null;

		nodeName = node.getName();
		tracer.msg("IASTFunctionDeclarator: "+nodeName);

		try {
			bnd = index.findBinding(nodeName);
		} catch (CoreException e) {
			System.err.println("error getting index");
			e.printStackTrace();
		}

		if (bnd == null) {
			return PROCESS_SKIP;
		}

		fmx = (BehaviouralEntity) dico.getEntityByKey(bnd);

		this.context.push(fmx);

		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(IASTFunctionDeclarator node) {
		NamedEntity top = context.top();
		if ( (top != null) &&
			 (top instanceof BehaviouralEntity) ) {
			context.pop();
		}
		return ASTVisitor.PROCESS_CONTINUE;
	}

	/**
	 * Visiting a method or function definition
	 */
	protected int visit(ICPPASTFunctionDefinition node) {
		BehaviouralEntity fmx = null;

		/*
		 * visit declarator to ensure the method definition and to get the
		 * Famix entity (which will be on the top of the context stack)
		 */
		this.visit(node.getDeclarator());

		return PROCESS_CONTINUE;  // we already visited the children
	}

	protected int leave(ICPPASTFunctionDefinition node) {
		return this.leave(node.getDeclarator());
	}

	@Override
	protected int visit(ICPPASTDeclarator node) {
		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(ICPPASTDeclarator node) {
		return PROCESS_CONTINUE;
	}

	/**
	 * Creating FamixInvocation from a function call
	 */
	public int visit(IASTFunctionCallExpression node) {
		IASTName nodeName = null;
		BehaviouralEntity fmx = null;
		IIndexBinding bnd = null;

		if(node.getFunctionNameExpression().getChildren().length > 1)
			nodeName = (IASTName) node.getFunctionNameExpression().getChildren()[1];
		else
			nodeName = (IASTName) node.getFunctionNameExpression().getChildren()[0];
		try {
			bnd = index.findBinding(nodeName);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		if (bnd != null) {
			fmx = (BehaviouralEntity) dico.getEntityByKey(bnd);
		}

		return PROCESS_CONTINUE;
	}

	public int visit(IASTBinaryExpression node) {
		Access prevAccess = context.getLastAccess();
		node.getOperand1().accept(this);
		switch (node.getOperator()) {
		case IASTBinaryExpression.op_assign:
		case IASTBinaryExpression.op_binaryAndAssign:
		case IASTBinaryExpression.op_binaryOrAssign:
		case IASTBinaryExpression.op_binaryXorAssign:
		case IASTBinaryExpression.op_divideAssign:
		case IASTBinaryExpression.op_minusAssign:
		case IASTBinaryExpression.op_moduloAssign:
		case IASTBinaryExpression.op_multiplyAssign:
		case IASTBinaryExpression.op_plusAssign:
		case IASTBinaryExpression.op_shiftLeftAssign:
		case IASTBinaryExpression.op_shiftRightAssign:
			if (context.getLastAccess() != prevAccess) {
				context.getLastAccess().setIsWrite(true);
			}
		}
		node.getOperand2().accept(this);
		
		return PROCESS_CONTINUE;
	}

	@Override
	protected int visit(ICASTCompositeTypeSpecifier node) {
//		tracer.msg("    -> ICASTCompositeTypeSpecifier");
		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(ICASTCompositeTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	@Override
	protected int visit(IASTEnumerationSpecifier node) {
//		tracer.msg("    -> IASTEnumerationSpecifier");
		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(IASTEnumerationSpecifier node) {
		return PROCESS_CONTINUE;
	}

	@Override
	protected int visit(ICPPASTNamedTypeSpecifier node) {
//		tracer.msg("    -> ICPPASTNamedTypeSpecifier");
		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(ICPPASTNamedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}
	

	// UTILITIES ==============================================================================================================================

	/**
	 * Records a reference to a name which can be a variable or behavioral name.
	 * @param nodeName
	 * @return the Access or Invocation created
	 */
	private Association referenceToName(IASTName nodeName) {
		IBinding bnd;
		NamedEntity fmx = null;

		if (nodeName == null) {
			return null;
		}

		bnd = nodeName.resolveBinding();
		if (bnd == null) {
			return null;
		}

		//fmx = dico.getEntityByKey(bnd);
		if (fmx == null) {
			return null;
		}

		if (fmx instanceof StructuralEntity) {
			return accessToVar((StructuralEntity) fmx);
		}
		else if (fmx instanceof BehaviouralEntity) {
			return invocationOfBehavioural((BehaviouralEntity) fmx);
		}

		return null;
	}

	/**
	 * Records an Invocation of a famixBehaviouralEntity and sets lastInvocation attribute
	 * @param fmx
	 * @return the invocation created
	 */
	private Association invocationOfBehavioural(BehaviouralEntity fmx) {
		BehaviouralEntity accessor = this.context.topMethod();
		Invocation invok = dico.addFamixInvocation(accessor, (BehaviouralEntity) fmx, /*receiver*/null, /*signature*/null, context.getLastInvocation());
		if (invok != null) {
			context.setLastInvocation(invok);
		}
		return invok;
	}

	/**
	 * Records an Access to a StructuralEntity and sets lastAccess attribute
	 * @param fmx
	 * @return the Access created
	 */
	private Association accessToVar(StructuralEntity fmx) {
		BehaviouralEntity accessor;
		// put false to isWrite by default, will be corrected in 
		accessor = this.context.topMethod();
		Access acc = dico.addFamixAccess(accessor, (StructuralEntity) fmx, /*isWrite*/false, context.getLastAccess());
		if (acc != null) {
			context.setLastAccess(acc);
		}
		return acc;
	}

}
