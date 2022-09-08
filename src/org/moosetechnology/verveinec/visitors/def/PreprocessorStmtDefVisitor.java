package org.moosetechnology.verveinec.visitors.def;

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfdefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.index.IIndex;

import org.moosetechnology.verveineCore.gen.famix.PreprocessorIfdef;
import org.moosetechnology.verveinec.plugin.CDictionary;
import org.moosetechnology.verveinec.visitors.AbstractContextVisitor;

public class PreprocessorStmtDefVisitor extends AbstractContextVisitor {

	public PreprocessorStmtDefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
	}

	@Override
	protected String msgTrace() {
		return "creating preprocessor statements";
	}

	/* "dispatches" preprocessor statements to different visit methods
	 * Similar to what is done in AbstractDispatcherVisitor. We keep it here because it is very specific and useless for all other visitors 
	 */
	@Override
	public int visit(IASTTranslationUnit node) {
		if (fileInsideProject(node.getFilePath())) {
			for (IASTPreprocessorStatement pstmt : node.getAllPreprocessorStatements()) {
				if (pstmt instanceof IASTPreprocessorIfdefStatement) {
					this.visit( (IASTPreprocessorIfdefStatement)pstmt);
				}
				else if (pstmt instanceof IASTPreprocessorIfndefStatement) {
					this.visit( (IASTPreprocessorIfndefStatement)pstmt);
				}
				else if (pstmt instanceof IASTPreprocessorMacroDefinition) {
					this.visit( (IASTPreprocessorMacroDefinition)pstmt);
				}
			}
		}

		return PROCESS_SKIP;
	}

	protected void visit(IASTPreprocessorIfdefStatement node) {
		IASTName macro = node.getMacroReference();
		if (macro != null) {
			PreprocessorIfdef fmx = dico.createFamixPreprocIfdef(macro.toString());
			setIfdefSourceAnchor(fmx, node.getFileLocation());
		}
	}

	protected void visit(IASTPreprocessorIfndefStatement node) {
		IASTName macro = node.getMacroReference();
		if (macro != null) {
			PreprocessorIfdef fmx = dico.createFamixPreprocIfdef(macro.toString());
			fmx.setNegated(true);
			setIfdefSourceAnchor(fmx, node.getFileLocation());
		}
	}

	protected void visit(IASTPreprocessorMacroDefinition node) {
		// nothing for now
	}

	

	private boolean fileInsideProject(String filePath) {
		return filePath.startsWith(rootFolder);
	}

	protected void setIfdefSourceAnchor(PreprocessorIfdef fmx, IASTFileLocation defLoc) {
		int startPos;
		int endPos;

		startPos = defLoc.getNodeOffset();
		endPos = startPos + fmx.getMacro().length();
		dico.addSourceAnchor(fmx, filename, startPos, endPos);
	}

}
