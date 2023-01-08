package org.moosetechnology.verveineC.visitors.ref;

import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IProblemBinding;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNameSpecifier;
import org.eclipse.cdt.core.index.IIndex;
import org.moosetechnology.famix.famixcentities.Type;
import org.moosetechnology.famix.famixcppentities.Inheritance;
import org.moosetechnology.verveineC.plugin.CDictionary;
import org.moosetechnology.verveineC.visitors.AbstractVisitor;

public class InheritanceRefVisitor extends AbstractVisitor {

	private Inheritance lastInheritance;

	public InheritanceRefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
	}

	protected String msgTrace() {
		return "creating inheritance relationships";
	}

	/*
	 * Recovering the sub-class
	 */
	@Override
	protected int visit(ICPPASTCompositeTypeSpecifier node) {
		org.moosetechnology.famix.famixcppentities.Class subClass = null;
		// compute nodeName and binding
		super.visit(node);
		subClass = dico.getEntityByKey(org.moosetechnology.famix.famixcppentities.Class.class, nodeBnd);

		getContext().push(subClass);
		lastInheritance = null;

		for (ICPPASTBaseSpecifier base : node.getBaseSpecifiers()) {
			base.accept(this);
		}

		this.getContext().pop();

		return PROCESS_SKIP;
	}

	@Override
	public int visit(ICPPASTBaseSpecifier node) {
		org.moosetechnology.famix.famixcppentities.Class subClass = (org.moosetechnology.famix.famixcppentities.Class) getContext().top();
		org.moosetechnology.famix.famixcppentities.Class supClass = null;

		// why isn't it an IASTName like everywhere else?
		ICPPASTNameSpecifier baseName = node.getNameSpecifier(); 
		
		nodeBnd = baseName.resolveBinding();
		if ( (nodeBnd == null) || (nodeBnd instanceof IProblemBinding) ) {
			nodeBnd = resolver.mkStubKey((IASTName)baseName, org.moosetechnology.famix.famixcppentities.Class.class);
		}
		supClass = dico.getEntityByKey(org.moosetechnology.famix.famixcppentities.Class.class, nodeBnd);

		if (supClass == null) {
			supClass = (org.moosetechnology.famix.famixcppentities.Class) resolver.resolveOrCreate( baseName.toString(), /*mayBeNull*/false, /*asType*/org.moosetechnology.famix.famixcppentities.Class.class);
		}

		if (supClass != null) { 
			lastInheritance = (Inheritance) dico.ensureFamixInheritance(supClass, subClass, lastInheritance);
		}

		return PROCESS_SKIP;
	}


}
