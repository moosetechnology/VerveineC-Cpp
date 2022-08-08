package org.moosetechnology.verveinec.utils;

import java.util.Stack;

import org.moosetechnology.verveineCore.EntityStack;

import org.moosetechnology.verveineCore.gen.famix.ContainerEntity;
import org.moosetechnology.verveineCore.gen.famix.Method;
import org.moosetechnology.verveineCore.gen.famix.NamedEntity;
import org.moosetechnology.verveineCore.gen.famix.Namespace;

public class CppEntityStack extends EntityStack {

	public CppEntityStack() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the higher-most namespace in the C++ sense on the EntityStack
	 * C++ namespaces we are interested in are: methods, classes, namespaces
	 * @return null if could not find a C++ namespace
	 */
	public ContainerEntity getTopCppNamespace() {
		Stack<NamedEntity> tmp = new Stack<NamedEntity>();
		NamedEntity top;
		
		top = pop();
		tmp.push(top);
		while ( ! ((top == null) ||
				   (top instanceof Method) ||
				   (top instanceof org.moosetechnology.verveineCore.gen.famix.Class) ||
				   (top instanceof Namespace) )) {
			top = pop();
			tmp.push(top);
		}
		
		while (! tmp.empty()) {
			push( tmp.pop());
		}

		return (ContainerEntity) top;
	}

}
