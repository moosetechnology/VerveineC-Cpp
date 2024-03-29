package org.moosetechnology.verveineC.visitors.def;


import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.moosetechnology.famix.cpp.Package;
import org.moosetechnology.verveineC.plugin.CDictionary;
import org.moosetechnology.verveineC.visitors.AbstractVisitor;

public class PackageDefVisitor extends AbstractVisitor {

	/**
	 * The file directory being visited at any given time
	 */
	protected org.moosetechnology.famix.cpp.Package currentPackage;

	public PackageDefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
		currentPackage = null;
	}

	protected String msgTrace() {
		return "creating packages";
	}

	/**
	 * File directories are treated as Package
	 */
	@Override
	public void visit(ICContainer elt) {
		Package fmx = null;

		enterPath(elt);
		if (nodeBnd != null) {
			fmx = dico.ensureFamixPackage(nodeBnd, elt.getElementName(), currentPackage);
			fmx.setIsStub(false);
			currentPackage = fmx;
		}

		super.visit(elt);   // visiting children

		if (currentPackage != null) {
			currentPackage = currentPackage.getParentPackage();
		}
		leavePath(elt);
	}


	/**
	 * Files are treated as Modules
	 */
	@Override
	public void visit(ITranslationUnit elt) {

	}

}
