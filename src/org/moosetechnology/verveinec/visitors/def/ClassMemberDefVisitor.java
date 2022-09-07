package org.moosetechnology.verveinec.visitors.def;

import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTVisibilityLabel;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.moosetechnology.verveineCore.gen.famix.Class;
import org.moosetechnology.verveinec.plugin.CDictionary;
import org.moosetechnology.verveinec.utils.Visibility;
import org.moosetechnology.verveinec.visitors.AbstractContextVisitor;

public abstract class ClassMemberDefVisitor extends AbstractContextVisitor {

	protected Visibility currentVisibility;
	
	public ClassMemberDefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
	}

	/**
	 * Overriden to initialize {@link #currentVisibility} to null
	 * (e.g. at the begining of a .c file) 
	 */
	@Override
	public void visit(ITranslationUnit elt) {
		super.visit(elt);
		currentVisibility = null;
	}

	@Override
	protected int visit(ICPPASTVisibilityLabel node) {
		switch (node.getVisibility()) {
		case ICPPASTVisibilityLabel.v_private :   currentVisibility = Visibility.PRIVATE;   break;
		case ICPPASTVisibilityLabel.v_protected : currentVisibility = Visibility.PROTECTED; break;
		case ICPPASTVisibilityLabel.v_public :    currentVisibility = Visibility.PUBLIC;    break;
		}
		return PROCESS_CONTINUE;
	}

	/* merging ICPPASTCompositeTypeSpecifier and ICASTCompositeTypeSpecifier into one visit method here
	 */
	@Override
	protected int visit(ICPPASTCompositeTypeSpecifier node) {
		currentVisibility = Visibility.PRIVATE;
		return visit((IASTCompositeTypeSpecifier)node);
	}
	
	/* merging ICPPASTCompositeTypeSpecifier and ICASTCompositeTypeSpecifier into one visit method here
	 */
	@Override
	protected int visit(ICASTCompositeTypeSpecifier node) {
		currentVisibility = Visibility.PUBLIC;
		return visit((IASTCompositeTypeSpecifier)node);
	}

	/* merging ICPPASTCompositeTypeSpecifier and ICASTCompositeTypeSpecifier into one visit method here
	 */
	@Override
	protected int visit(IASTCompositeTypeSpecifier node) {
		Class fmx;

		super.visit(node);
		fmx = dico.getEntityByKey(Class.class, nodeBnd);

		this.contextPush(fmx);

		return PROCESS_CONTINUE;
	}
	
	/* Could have merged the leave methods too, but does not seem to be worthwhile
	 */
	@Override
	protected int leave(ICPPASTCompositeTypeSpecifier node) {
		returnedEntity = contextPop();
		return PROCESS_CONTINUE;
	}
	
	/* Could have merged the leave methods too, but does not seem to be worthwhile
	 */
	@Override
	protected int leave(ICASTCompositeTypeSpecifier node) {
		returnedEntity = contextPop();
		return PROCESS_CONTINUE;
	}

	@Override
	protected int leave(IASTCompositeTypeSpecifier node) {
		returnedEntity = contextPop();
		return PROCESS_CONTINUE;
	}

	@Override
	protected int visit(IASTCastExpression node) {
		node.getOperand().accept(this);

		return PROCESS_SKIP;
	}

}
