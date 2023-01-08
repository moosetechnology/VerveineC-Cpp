package org.moosetechnology.verveineC.visitors.def;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.index.IIndex;
import org.moosetechnology.famix.famixcppentities.Namespace;
import org.moosetechnology.verveineC.plugin.CDictionary;
import org.moosetechnology.verveineC.visitors.AbstractVisitor;

public class NamespaceDefVisitor extends AbstractVisitor {

	public NamespaceDefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
	}

	@Override
	public int visit(IASTDeclaration node) {
		/* Pruning AST visit on any declaration
		 * (Namespace declarations have their own visit method in CDT's ASTVisitor)
		 */
		return PROCESS_SKIP;
	}

	protected String msgTrace() {
		return "creating namespaces";
	}

	@Override
	public int visit(ICPPASTNamespaceDefinition node) {
		Namespace fmx;
		IASTName localNodeName = nodeName = node.getName();
		if (! nodeName.toString().equals("")) {
			nodeBnd = resolver.getBinding(nodeName);

			fmx = dico.ensureFamixNamespace(nodeBnd, nodeName.toString(), (Namespace) this.getContext().top());
			fmx.setIsStub(false);
			
			this.getContext().push(fmx);
		}

		// may change global nodeName
		for (IASTDeclaration decl : node.getDeclarations()) {
			decl.accept(this);
		}

		if (! localNodeName.toString().equals("")) {
			getContext().pop();
		}

		return PROCESS_SKIP;
	}

}