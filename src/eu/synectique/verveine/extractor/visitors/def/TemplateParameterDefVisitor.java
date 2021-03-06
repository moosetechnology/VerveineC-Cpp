package eu.synectique.verveine.extractor.visitors.def;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTSimpleTypeTemplateParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplatedTypeTemplateParameter;
import org.eclipse.cdt.core.index.IIndex;

import eu.synectique.verveine.core.gen.famix.BehaviouralEntity;
import eu.synectique.verveine.core.gen.famix.Class;
import eu.synectique.verveine.core.gen.famix.ContainerEntity;
import eu.synectique.verveine.core.gen.famix.UnknownVariable;
import eu.synectique.verveine.extractor.plugin.CDictionary;
import eu.synectique.verveine.extractor.visitors.AbstractVisitor;

public class TemplateParameterDefVisitor extends AbstractVisitor {

	public TemplateParameterDefVisitor(CDictionary dico, IIndex index, String rootFolder) {
		super(dico, index, rootFolder);
	}

	protected String msgTrace() {
		return "dealing with templates (generics)";
	}

	/*
	 * Putting class definition on the context stack
	 */
	@Override
	protected int visit(ICPPASTCompositeTypeSpecifier node) {
		Class fmx;

		/* Gets the key (IBinding) of the node to recover the famix type entity */
		super.visit(node);

		fmx = (Class) dico.getEntityByKey(nodeBnd);

		/*
		 * Visiting possible template methods
		 */
		this.getContext().push(fmx);
		for (IASTDeclaration decl : node.getDeclarations(/*includeInactive*/true)) {
			decl.accept(this);
		}
		returnedEntity = getContext().pop();

		return PROCESS_SKIP;
	}

	@Override
	protected int visit(ICPPASTFunctionDeclarator node) {
		// compute nodeName and binding
		super.visit( (IASTFunctionDeclarator)node);

		returnedEntity = (BehaviouralEntity) dico.getEntityByKey(nodeBnd);
		// try harder
		if (returnedEntity == null) {
			returnedEntity = resolver.ensureBehaviouralFromName(node, nodeBnd, nodeName);
		}

		// Not visiting children, because assuming there is no template inside a method
		return PROCESS_SKIP;
	}

	@Override
	protected int visit(ICPPASTTemplateDeclaration node) {
		ContainerEntity theTemplate = null;   // not really needed, but looks nicer

		returnedEntity = null;
		node.getDeclaration().accept(this);
		theTemplate = (ContainerEntity)returnedEntity;

		// template parameters are local to the entity defined in the template declaration
		getContext().push(theTemplate);
		for (ICPPASTTemplateParameter param : node.getTemplateParameters()) {
			String name;

			if (param instanceof ICPPASTSimpleTypeTemplateParameter ) {
				// a variable for a parameter type
				name = ((ICPPASTSimpleTypeTemplateParameter)param).getName().toString();
				dico.createParameterType( name, /*owner*/theTemplate);
			}

			else if (param instanceof ICPPASTTemplatedTypeTemplateParameter ) {
				// a variable for a parameter type that is itself a template
				name = ((ICPPASTTemplatedTypeTemplateParameter)param).getName().toString();
				dico.createParameterType( name, /*owner*/theTemplate);
			}

			else if (param instanceof ICPPASTParameterDeclaration ) {
				IBinding bnd;
				// non type parameter for a template (such has N in "template <Class T, size_t N> class List ...")
				// should represent them as Parameters but implies redefining ParameterizableClass
				// use UnknownVariable as a temporary(!) workaround
				name = ((ICPPASTParameterDeclaration)param).getDeclarator().getName().toString();
				bnd = resolver.mkStubKey( name, UnknownVariable.class);
				dico.ensureFamixUnknownVariable( bnd, name, /*parent*/getContext().topPckg());
			}

			else {
				// should not happen
				throw new IllegalStateException("Unrecognized type for ICPPASTTemplateParameter `"+param.getRawSignature()+"' in file "+node.getContainingFilename());
			}
		}
		getContext().pop();

		return PROCESS_SKIP;
	}

}
