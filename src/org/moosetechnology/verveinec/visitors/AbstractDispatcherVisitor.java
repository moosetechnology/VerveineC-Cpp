package org.moosetechnology.verveinec.visitors;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFieldDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTConstructorChainInitializer;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTConstructorInitializer;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNewExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTVisibilityLabel;
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.IInclude;
import org.eclipse.cdt.core.model.IParent;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.moosetechnology.verveinec.plugin.CDictionary;


/**
 * The superclass of all visitors. These visitors visit an AST to create FAMIX entities.<BR>
 * This abstract class dispatches more finely the visits than what CDT's ASTVisitor normally does. 
 * This visitor also merges two APIs: visit methods on AST (ASTVisitor) and visit methods on ICElements (ICElementVisitor).
 */
public abstract class AbstractDispatcherVisitor extends ASTVisitor implements ICElementVisitor {

	/**
	 * CDT index, required to get AST
	 */
	protected IIndex index;

	/** 
	 * A dictionary allowing to store created FAMIX Entities
	 * <p>
	 * This does not strictly belongs here, but all sub-classes need it,
	 * so it's simpler to have it in their super-class (this one)
	 */
	protected CDictionary dico;


	// CONSTRUCTOR ==========================================================================================================================

	public AbstractDispatcherVisitor(CDictionary dico, IIndex index) {
		super(/*visitNodes*/true);
	    /* fine-tuning if visitNodes=false
	    shouldVisitDeclarations = true;
	    shouldVisitEnumerators = true;
	    shouldVisitProblems = true;
	    shouldVisitTranslationUnit = true;
	    shouldVisit... */
		shouldVisitTemplateParameters = true;
		this.index = index;
		this.dico = dico;

		if (msgTrace() != null ) {
			System.out.println(msgTrace());
		}
	}

	abstract protected String msgTrace();

	// VISITING METODS ON ICELEMENT HIERARCHY (ICElementVisitor) ===========================================================================

	@Override
	public boolean visit(ICElement elt) {
		switch (elt.getElementType()) {
		case ICElement.C_PROJECT:
			visit( (ICProject) elt);
			break;
		case ICElement.C_CCONTAINER:
			visit( (ICContainer) elt);
			break;
		case ICElement.C_UNIT:
			visit( (ITranslationUnit) elt);
			break;
		case ICElement.C_INCLUDE:
			visit( (IInclude) elt);
			break;

		default:
			//  I believe this should never happen
		}
		return false;
	}

	public void visit(ICProject project) {
		visitChildren(project);
	}

	public void visit(ICContainer cont) {
		visitChildren(cont);
	}

	public void visit(IInclude project) {
	}

	public void visit(ITranslationUnit elt) {
		// this is the method merging ICElementVisitor and ASTVisitor

		// visit children (ICElementVisitor) ...
		visitChildren(elt);
		try {
			// ... then switch to AST (visit(IASTTranslationUnit) in ASTVisitor)
			elt.getAST(index, ITranslationUnit.AST_CONFIGURE_USING_SOURCE_CONTEXT | ITranslationUnit.AST_SKIP_INDEXED_HEADERS).accept(this);
		} catch (CoreException e) {
			System.err.println("*** Got CoreException (\""+ e.getMessage() +"\") while getting AST of "+ elt.getElementName() );
		}
	}

	/** To visit children of ICElements (see ICElementVisitor) because it is not implemented by CDT
	 * ASTVisitor implements its own mechanism for this so we only need to return the value PROCESS_CONTINUE
	 * and the CDT ASTVisitor will ensure children are visited
	 */
	protected void visitChildren(IParent elt) {
		try {
			for (ICElement child : elt.getChildren()) {
				child.accept(this);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	// CDT VISITING METODS ON AST (ASTVisitor) =============================================================================================

	@Override
	public int visit(IASTName node) {
		if (node instanceof ICPPASTTemplateId) {
			return visit((ICPPASTTemplateId)node);
		}

		return super.visit(node);
	}

	@Override
	public int visit(IASTDeclaration node) {
		/* ********************************************************************************************
		 * BE CAREFULL: The order of the tests is important because choices are not mutually exclusive
		 * ex: ICPPASTFunctionDefinition is a sub-interface of IASTFunctionDefinition
		 * ******************************************************************************************** */
		if (node instanceof IASTSimpleDeclaration) {
			return visit((IASTSimpleDeclaration)node);
		}
		else if (node instanceof ICPPASTFunctionDefinition) {
			return visit((ICPPASTFunctionDefinition)node);
		}
		else if (node instanceof IASTFunctionDefinition) {
			return visit((IASTFunctionDefinition)node);
		}
		else if (node instanceof ICPPASTTemplateDeclaration) {
			return visit((ICPPASTTemplateDeclaration)node);
		}
		else if (node instanceof ICPPASTVisibilityLabel) {
			return visit((ICPPASTVisibilityLabel)node);
		}
		//else ICPPASTUsingDirective, ...
	
		return super.visit(node);
	}

	@Override
	public int visit(IASTDeclarator node) {
		/* ********************************************************************************************
		 * BE CAREFULL: The order of the tests is important because choices are not mutually exclusive
		 * ex: ICPPASTFunctionDeclarator is a sub-interface of IASTFunctionDeclarator
		 * ******************************************************************************************** */
		if (node instanceof ICPPASTFunctionDeclarator) {
			return this.visit((ICPPASTFunctionDeclarator)node);
		}
		else if (node instanceof IASTStandardFunctionDeclarator) {
			return this.visit((IASTStandardFunctionDeclarator)node);
		}
		else if (node instanceof ICASTKnRFunctionDeclarator) {
			return this.visit((ICASTKnRFunctionDeclarator)node);
		}
		else if (node instanceof IASTFunctionDeclarator) {
			return this.visit((IASTFunctionDeclarator)node);
		}
		else if (node instanceof IASTFieldDeclarator) {  // actually seems to never occur ???
			return this.visit((IASTFieldDeclarator)node);
		}
/* removed to deal with C attributes: IASTDeclarators
  		else if (node instanceof ICPPASTDeclarator) {
			return this.visit((ICPPASTDeclarator)node);
		}
	replaced by the following:
*/	
  		else {
			return this.visitInternal(node);
		}
	}

	/* new visit method for "true IASTDeclarators" (e.g. attributes of struct in C)
	 * this avoids overriding visit(IASTDeclarator node) in the sub-classes of AbstractDispatcherVisitor
	 * which would hide the dispatch done here
	 */
	protected int visitInternal(IASTDeclarator node) {
		return PROCESS_CONTINUE;
	}


	@Override
	public int visit(IASTDeclSpecifier node) {
		if (node instanceof ICPPASTCompositeTypeSpecifier) {
			// -> class/struct/union
			return this.visit((ICPPASTCompositeTypeSpecifier)node);
		}
		else if (node instanceof ICASTCompositeTypeSpecifier) {
			// -> struct/union in C
			return this.visit((ICASTCompositeTypeSpecifier)node);
		}
		else if (node instanceof ICASTElaboratedTypeSpecifier) {
			/* could also test node.getKind() ???
			 * - IASTElaboratedTypeSpecifier.k_enum:
			 * - IASTElaboratedTypeSpecifier.k_struct:
			 * - IASTElaboratedTypeSpecifier.k_union:
			 * - ICPPASTElaboratedTypeSpecifier.k_class:
			 */
			return visit((ICASTElaboratedTypeSpecifier)node);
		}
		else if (node instanceof ICPPASTElaboratedTypeSpecifier) {
			return visit((ICPPASTElaboratedTypeSpecifier)node);
		}
		else if (node instanceof IASTElaboratedTypeSpecifier) {
			return this.visit((IASTElaboratedTypeSpecifier)node);
		}
		else if (node instanceof IASTSimpleDeclSpecifier) {
			return this.visit((IASTSimpleDeclSpecifier)node);
		}
		else if (node instanceof IASTEnumerationSpecifier) {
			// -> enum
			return this.visit((IASTEnumerationSpecifier)node);
		}
		else if (node instanceof ICPPASTNamedTypeSpecifier) {
			// -> typedef
			return this.visit((ICPPASTNamedTypeSpecifier)node);
		}
		else if (node instanceof IASTNamedTypeSpecifier) {
			// -> typedef
			return this.visit((IASTNamedTypeSpecifier)node);
		}
		else {
			// TODO missing sub-types?
		}
	
		return super.visit(node);
	}

	@Override
	public int visit(IASTInitializer node) {
		if (node instanceof ICPPASTConstructorChainInitializer) {
			return visit( (ICPPASTConstructorChainInitializer)node );
		}
		else if (node instanceof ICPPASTConstructorInitializer) {
			return visit( (ICPPASTConstructorInitializer)node );
		}
		return super.visit(node);
	}

	@Override
	public int visit(IASTExpression node) {
		if (node instanceof IASTFieldReference) {
			return visit((IASTFieldReference)node);
		}
		else if (node instanceof IASTIdExpression) {
			return visit((IASTIdExpression)node);
		}
		else if (node instanceof ICPPASTNewExpression) {
			return visit((ICPPASTNewExpression)node);
		}
		else if (node instanceof IASTFunctionCallExpression) {
			return visit((IASTFunctionCallExpression)node);
		}
		else if (node instanceof IASTUnaryExpression) {
			return visit((IASTUnaryExpression)node);   // to check whether this is an assignement
		}
		else if (node instanceof IASTBinaryExpression) {
			return visit((IASTBinaryExpression)node);   // to check whether this is an assignement
		}
		else if (node instanceof IASTLiteralExpression) {
			return visit((IASTLiteralExpression)node);
		}
		else if (node instanceof IASTTypeIdExpression) {
			return visit((IASTTypeIdExpression)node);
		}
		else if (node instanceof IASTCastExpression) {
			return visit((IASTCastExpression)node);
		}

		return super.visit(node);
	}

	@Override
	public int visit(IASTParameterDeclaration node) {
		if (node instanceof ICPPASTParameterDeclaration) {
			return visit((ICPPASTParameterDeclaration)node);
		}
		// else is a valid choice (presumably for C language)
		return super.visit(node);
	}


	// ADDITIONAL VISITING METODS ON AST ===================================================================================================

	protected int visit(IASTSimpleDeclaration node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTFunctionDefinition node) {
		return this.visit( (IASTFunctionDefinition)node);
	}

	protected int visit(IASTFunctionDefinition node) {
		return PROCESS_CONTINUE;
	}

	/**
	 * By default calls {@link #visit(IASTStandardFunctionDeclarator)}
	 * which in turn calls {@link #visit(IASTFunctionDeclarator)}
	 */
	protected int visit(ICPPASTFunctionDeclarator node) {
		return this.visit( (IASTStandardFunctionDeclarator)node);
	}

	/**
	 * By default calls {@link #visit(IASTFunctionDeclarator)}
	 */
	protected int visit(IASTStandardFunctionDeclarator node) {
		return this.visit( (IASTFunctionDeclarator)node);
	}

	/**
	 * By default calls {@link #visit(IASTFunctionDeclarator)}
	 */
	protected int visit(ICASTKnRFunctionDeclarator node) {
		return this.visit( (IASTFunctionDeclarator)node);
	}

	protected int visit(IASTFunctionDeclarator node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTFieldDeclarator node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTVisibilityLabel node) {
		return PROCESS_CONTINUE;
	}

/* removed to deal with C attributes: IASTDeclarators
	protected int visit(ICPPASTDeclarator node) {
		return PROCESS_CONTINUE;
	}
   replaced by the new visit method visitInternal(IASTDeclarator node) above
*/

	protected int visit(ICASTCompositeTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTCompositeTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTCompositeTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTElaboratedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICASTElaboratedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTElaboratedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTEnumerationSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTNamedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTNamedTypeSpecifier node) {
		return this.visit( (IASTNamedTypeSpecifier)node);
	}

	public int visit(IASTSimpleDeclSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTTemplateDeclaration node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTParameterDeclaration node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTFunctionCallExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTNewExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTUnaryExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTBinaryExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTLiteralExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTFieldReference node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTIdExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTTypeIdExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(IASTCastExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTConstructorChainInitializer node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTConstructorInitializer node) {
		return PROCESS_CONTINUE;
	}

	protected int visit(ICPPASTTemplateId node) {
		return PROCESS_CONTINUE;
	}

	// CDT LEAVE METODS ON AST (ASTVisitor) =============================================================================================
	
	@Override
	public int leave(IASTName node) {
		if (node instanceof ICPPASTTemplateId) {
			return leave((ICPPASTTemplateId)node);
		}

		return super.leave(node);
	}

	/* similar to visit(IASTDeclaration) */
	@Override
	public int leave(IASTDeclaration node) {
		
		if (node instanceof IASTSimpleDeclaration) {
			return leave((IASTSimpleDeclaration)node);
		}
		else if (node instanceof ICPPASTFunctionDefinition) {
			return leave((ICPPASTFunctionDefinition)node);
		}
		else if (node instanceof IASTFunctionDefinition) {
			return leave((IASTFunctionDefinition)node);
		}
		else if (node instanceof ICPPASTTemplateDeclaration) {
			return leave((ICPPASTTemplateDeclaration)node);
		}
		else if (node instanceof ICPPASTVisibilityLabel) {
			return leave((ICPPASTVisibilityLabel)node);
		}
	
		return super.leave(node);
	}

	/* similar to visit(IASTDeclarator) */
	@Override
	public int leave(IASTDeclarator node) {

		if (node instanceof ICPPASTFunctionDeclarator) {
			return this.leave((ICPPASTFunctionDeclarator)node);
		}
		else if (node instanceof IASTStandardFunctionDeclarator) {
			return this.leave((IASTStandardFunctionDeclarator)node);
		}
		else if (node instanceof ICASTKnRFunctionDeclarator) {
			return this.leave((ICASTKnRFunctionDeclarator)node);
		}
		else if (node instanceof IASTFunctionDeclarator) {
			return this.leave((IASTFunctionDeclarator)node);
		}
		else if (node instanceof IASTFieldDeclarator) {
			return this.leave((IASTFieldDeclarator)node);
		}
  		else {
			return PROCESS_CONTINUE;
		}
	}

	/* similar to visit(IASTDeclSpecifier) */
	@Override
	public int leave(IASTDeclSpecifier node) {
		if (node instanceof ICPPASTCompositeTypeSpecifier) {
			return this.leave((ICPPASTCompositeTypeSpecifier)node);
		}
		else if (node instanceof ICASTCompositeTypeSpecifier) {
			return this.leave((ICASTCompositeTypeSpecifier)node);
		}
		else if (node instanceof IASTElaboratedTypeSpecifier) {
			return this.leave((IASTElaboratedTypeSpecifier)node);
		}
		else if (node instanceof IASTSimpleDeclSpecifier) {
			return this.leave((IASTSimpleDeclSpecifier)node);
		}
		else if (node instanceof IASTEnumerationSpecifier) {
			return this.leave((IASTEnumerationSpecifier)node);
		}
		else if (node instanceof ICPPASTNamedTypeSpecifier) {
			return this.leave((ICPPASTNamedTypeSpecifier)node);
		}
		else if (node instanceof IASTNamedTypeSpecifier) {
			return this.leave((IASTNamedTypeSpecifier)node);
		}
	
		return super.leave(node);
	}

	@Override
	public int leave(IASTInitializer node) {
		if (node instanceof ICPPASTConstructorChainInitializer) {
			return leave( (ICPPASTConstructorChainInitializer)node );
		}
		else if (node instanceof ICPPASTConstructorInitializer) {
			return leave( (ICPPASTConstructorInitializer)node );
		}
		return super.leave(node);
	}

	@Override
	public int leave(IASTExpression node) {
		if (node instanceof IASTFieldReference) {
			return leave((IASTFieldReference)node);
		}
		else if (node instanceof IASTIdExpression) {
			return leave((IASTIdExpression)node);
		}
		else if (node instanceof ICPPASTNewExpression) {
			return leave((ICPPASTNewExpression)node);
		}
		else if (node instanceof IASTFunctionCallExpression) {
			return leave((IASTFunctionCallExpression)node);
		}
		else if (node instanceof IASTUnaryExpression) {
			return leave((IASTUnaryExpression)node);
		}
		else if (node instanceof IASTBinaryExpression) {
			return leave((IASTBinaryExpression)node);
		}
		else if (node instanceof IASTLiteralExpression) {
			return leave((IASTLiteralExpression)node);
		}
		else if (node instanceof IASTTypeIdExpression) {
			return leave((IASTTypeIdExpression)node);
		}
		else if (node instanceof IASTCastExpression) {
			return leave((IASTCastExpression)node);
		}

		return super.leave(node);
	}

	@Override
	public int leave(IASTParameterDeclaration node) {
		if (node instanceof ICPPASTParameterDeclaration) {
			return leave((ICPPASTParameterDeclaration)node);
		}
		return super.leave(node);
	}

	// ADDITIONAL LEAVE METODS ON AST ===================================================================================================

	protected int leave(IASTSimpleDeclaration node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTFunctionDefinition node) {
		return this.leave( (IASTFunctionDefinition)node);
	}

	protected int leave(IASTFunctionDefinition node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTFunctionDeclarator node) {
		return this.leave( (IASTStandardFunctionDeclarator)node);
	}

	protected int leave(IASTStandardFunctionDeclarator node) {
		return this.leave( (IASTFunctionDeclarator)node);
	}

	protected int leave(ICASTKnRFunctionDeclarator node) {
		return this.leave( (IASTFunctionDeclarator)node);
	}

	protected int leave(IASTFunctionDeclarator node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTFieldDeclarator node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTVisibilityLabel node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICASTCompositeTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTCompositeTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTCompositeTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTElaboratedTypeSpecifier node) {
		if (node instanceof ICASTElaboratedTypeSpecifier) {
			return leave((ICASTElaboratedTypeSpecifier)node);
		}
		else if (node instanceof ICPPASTElaboratedTypeSpecifier) {
			return leave((ICPPASTElaboratedTypeSpecifier)node);
		}
		return super.leave(node);
	}

	protected int leave(ICASTElaboratedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTElaboratedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTEnumerationSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTNamedTypeSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTNamedTypeSpecifier node) {
		return this.leave( (IASTNamedTypeSpecifier)node);
	}

	public int leave(IASTSimpleDeclSpecifier node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTTemplateDeclaration node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTParameterDeclaration node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTFunctionCallExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTNewExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTUnaryExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTBinaryExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTLiteralExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTFieldReference node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTIdExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTTypeIdExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(IASTCastExpression node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTConstructorChainInitializer node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTConstructorInitializer node) {
		return PROCESS_CONTINUE;
	}

	protected int leave(ICPPASTTemplateId node) {
		return PROCESS_CONTINUE;
	}


}