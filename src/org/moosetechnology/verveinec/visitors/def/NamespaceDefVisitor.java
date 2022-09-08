package org.moosetechnology.verveinec.visitors.def;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.index.IIndex;
import org.moosetechnology.verveineCore.gen.famix.Namespace;
import org.moosetechnology.verveinec.plugin.CDictionary;
import org.moosetechnology.verveinec.visitors.AbstractContextVisitor;

public class NamespaceDefVisitor extends AbstractContextVisitor {

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
		nodeName = node.getName();
		if (! nodeName.toString().equals("")) {
			nodeBnd = resolver.getBinding(nodeName);

			// contextTop() contains a Namespace or null
			fmx = dico.ensureFamixNamespace(nodeBnd, nodeName.toString(), (Namespace) this.contextTop());
			fmx.setIsStub(false);
			
			this.contextPush(fmx);
		}
		return PROCESS_CONTINUE;
	}
	
	
	@Override
	public int leave(ICPPASTNamespaceDefinition node) {
		if (! nodeName.toString().equals("")) {
			contextPop();
		}
		return PROCESS_CONTINUE;
	}

}