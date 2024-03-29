package org.moosetechnology.verveineC.utils;

import java.util.Stack;

import org.moosetechnology.famix.cpp.Access;
import org.moosetechnology.famix.cpp.AnnotationTypeAttribute;
import org.moosetechnology.famix.cpp.BehaviouralEntity;
import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.Invocation;
import org.moosetechnology.famix.cpp.Method;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.Namespace;
import org.moosetechnology.famix.cpp.Reference;

public class CppEntityStack {
	
	public static final int EMPTY_CYCLO = 0;
	public static final int EMPTY_NOS = 0;

	private Stack<NamedEntity> stack;
	
	private class MetricHolder extends NamedEntity {
		private int metric_cyclo = EMPTY_CYCLO;  // Cyclomatic Complexity
		private int metric_nos = EMPTY_NOS;      // Number Of Statements
		private BehaviouralEntity ent;

		protected MetricHolder(BehaviouralEntity ent) {
			setBelongsTo(ent);
		}
		protected int getCyclo() {
			return metric_cyclo;
		}
		protected void setCyclo(int metric_cyclo) {
			this.metric_cyclo = metric_cyclo;
		}
		protected int getNos() {
			return metric_nos;
		}
		protected void setNos(int metric_nos) {
			this.metric_nos = metric_nos;
		}
		protected BehaviouralEntity getEntity() {
			return ent;
		}
		@Override
		public NamedEntity getBelongsTo() {
			return ent;
		}
		@Override
		public void setBelongsTo(NamedEntity belongsTo) {
			this.ent = (BehaviouralEntity) belongsTo;
		}
	}

	/**
	 * last Invocation registered to set the previous/next
	 */
	Invocation lastInvocation = null;
	
	/**
	 * last Access registered to set the previous/next
	 */
	Access lastAccess = null;
	
	/**
	 * last Reference registered to set the previous/next
	 */
	Reference lastReference = null;
	
	public Access getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Access lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Reference getLastReference() {
		return lastReference;
	}

	public void setLastReference(Reference lastReference) {
		this.lastReference = lastReference;
	}

	public Invocation getLastInvocation() {
		return lastInvocation;
	}

	public void setLastInvocation(Invocation lastInvocation) {
		this.lastInvocation = lastInvocation;
	}

	public CppEntityStack() {
		clearPckg();  // initializes (to empty) Pckgs, classes and methods
	}

	// WRITE ON THE STACK
	
	/**
	 * Pushes an entity on top of the "context stack"
	 * @param e -- the entity
	 */
	public void push(NamedEntity e) {
		stack.push(e);
	}

	/**
	 * Sets the Famix Package on top of the "context stack"
	 * @param e -- the Famix Package
	 */
	public void pushPckg(org.moosetechnology.famix.cpp.Package e) {
		push(e);
	}

	/**
	 * Sets the Famix namespace on top of the "context stack"
	 * @param e -- the Famix namespace
	 */
	public void pushPckg(Namespace e) {
		push(e);
	}

	/**
	 * Pushes a Famix Type on top of the "context type stack"
	 * @param t -- the FamixType
	 */
	public void pushType(org.moosetechnology.famix.cpp.Type t) {
		push(t);
	}

	/**
	 * Pushes a Famix method on top of the "context stack" for the current Famix Type
	 * Adds also a special entity to hold the metrics for the method
	 * @param e -- the Famix method
	 */
	public void pushMethod(Method e) {
		push(e);
		push( new MetricHolder(e) );
	}

	/**
	 * Pushes a Famix BehaviouralEntity on top of the "context stack"
	 * Adds also a special entity to hold the metrics for the BehaviouralEntity
	 * @param e -- the Famix BehaviouralEntity
	 */
	public void pushBehaviouralEntity(BehaviouralEntity e) {
		push(e);
		push( new MetricHolder(e) );
	}

	public void pushAnnotationMember(AnnotationTypeAttribute fmx) {
		push(fmx);	
	}
	
	/**
	 * Empties the context stack of package and associated classes
	 */
	public void clearPckg() {
		stack = new Stack<NamedEntity>();
	}

	/**
	 * Empties the context stack of Famix classes
	 */
	public void clearTypes() {
		while (! (this.top() instanceof Namespace)) {
			this.popUpto(org.moosetechnology.famix.cpp.Type.class);			
		}
	}

	// READ FROM THE STACK

	@SuppressWarnings("unchecked")
	private <T extends NamedEntity> T popUpto(Class<T> clazz) {
		NamedEntity ent = null;
		while ( (! stack.isEmpty()) && (! clazz.isInstance(ent)) ) {
			ent = this.pop();
		}

		if (stack.isEmpty()) {
			return null;
		}
		else {
			return (T) ent;
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends NamedEntity> T lookUpto(Class<T> clazz) {
		int i=this.stack.size()-1;

		while ( (i >= 0) && (! clazz.isInstance(stack.get(i))) ) {
			i--;
		}

		if (i < 0) {
			return null;
		}
		else {
			return (T)stack.get(i);
		}
	}

	public NamedEntity pop() {
		if (stack.isEmpty()) {
			return null;
		}
		else {
			NamedEntity e = stack.pop();
			if (e instanceof MetricHolder) {
				return stack.pop();
			}
			else {
				return e;
			}
		}
	}

	/**
	 * Removes and returns the Famix package from the "context stack"
	 * Also empties the class stack (which was presumably associated to this package)
	 * Note: does not check that there is such a namespace
	 * @return the Famix method
	 */
	public org.moosetechnology.famix.cpp.Package popPckg() {
		return this.popUpto(org.moosetechnology.famix.cpp.Package.class);
	}

	/**
	 * Pops the top Famix type from the "context stack"<BR>
	 * Note: does not check that there is such a type, so could possibly throw an EmptyStackException
	 * @return the Famix class
	 */
	public org.moosetechnology.famix.cpp.Type popType() {
		return this.popUpto(org.moosetechnology.famix.cpp.Type.class);
	}

	/**
	 * Pops the top Famix Namespace from the "context stack"<BR>
	 * Note: does not check that there is such a namesapce, so could possibly throw an EmptyStackException
	 * @return the Famix Namespace
	 */
	public Namespace popNamespace() {
		return this.popUpto(Namespace.class);
	}

	/**
	 * Pops the top Famix method of the current class on top of the "context stack"
	 * Note: does not check that there is such a class or method, so could possibly throw an Exception
	 * @return the Famix method
	 */
	public Method popMethod() {
		return this.popUpto(Method.class);
	}
	
	public AnnotationTypeAttribute popAnnotationMember() {
		return this.popUpto(AnnotationTypeAttribute.class);
	}

	/**
	 * Returns the Famix entity on top of the "context stack"
	 * Note: does not check that there is such an entity
	 * @return the Famix entity
	 */
	public NamedEntity top() {
		if (stack.isEmpty()) {
			return null;
		}
		else {
			NamedEntity e = stack.peek();
			if (e instanceof MetricHolder) {
				return ((MetricHolder) e).getEntity();
			}
			else {
				return e;
			}
		}
	}

	/**
	 * Returns the Famix package on top of the "context stack"
	 * Note: does not check that there is such a package
	 * @return the Famix namespace
	 */
	public org.moosetechnology.famix.cpp.Package topPckg() {
		return this.lookUpto(org.moosetechnology.famix.cpp.Package.class);
	}

	/**
	 * Returns the Famix type on top of the "context stack"
	 * Note: does not check that there is such a class, so could possibly throw an EmptyStackException
	 * @return the Famix class
	 */
	public org.moosetechnology.famix.cpp.Type topType() {
		return this.lookUpto(org.moosetechnology.famix.cpp.Type.class);
	}

	/**
	 * Returns the Famix Namespace on top of the "context stack"
	 * Note: does not check that there is such a Namespace, so could possibly throw an EmptyStackException
	 * @return the Famix Namespace
	 */
	public Namespace topNamespace() {
		return this.lookUpto(Namespace.class);
	}

	/**
	 * Returns the Famix BehaviouralEntity on top of the "context stack"
	 * Note: does not check that there is such a BehaviouralEntity, so could possibly throw an EmptyStackException
	 * @return the Famix BehaviouralEntity
	 */
	public BehaviouralEntity topBehaviouralEntity() {
		return this.lookUpto(BehaviouralEntity.class);
	}

	/**
	 * Returns the Famix method  of the Famix class on top of the "context stack"
	 * Note: does not check that there is such a class or method, so could possibly throw an EmptyStackException
	 * @return the Famix method
	 */
	public Method topMethod() {
		return this.lookUpto(Method.class);
	}

	public AnnotationTypeAttribute topAnnotationMember() {
		return this.lookUpto(AnnotationTypeAttribute.class);
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
				   (top instanceof org.moosetechnology.famix.cpp.Class) ||
				   (top instanceof Namespace) )) {
			top = pop();
			tmp.push(top);
		}
		
		while (! tmp.empty()) {
			push( tmp.pop());
		}

		return (ContainerEntity) top;
	}

	// PROPERTIES OF THE TOP METHOD

	/**
	 * Returns the Cyclomatic complexity of the Famix Method on top of the context stack
	 */
	public int getTopMethodCyclo() {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			return met.getCyclo();
		}
		else {
			return EMPTY_CYCLO;
		}
	}

	/**
	 * Returns the Number of Statements of the Famix Method on top of the context stack
	 */
	public int getTopMethodNOS() {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			return met.getNos();
		}
		else {
			return EMPTY_NOS;
		}
	}

	/**
	 * Sets the Cyclomatic complexity of the Famix Method on top of the context stack
	 */
	public void setTopMethodCyclo(int c) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setCyclo(c);
		}
	}

	/**
	 * Sets to the Number of Statements of the Famix Method on top of the context stack
	 */
	public void setTopMethodNOS(int n) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setNos(n);
		}
	}
	
	/**
	 * Adds to the Cyclomatic complexity of the Famix Method on top of the context stack
	 */
	public void addTopMethodCyclo(int c) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setCyclo( met.getCyclo()+c );
		}
	}

	/**
	 * Adds to the Number of Statements of the Famix Method on top of the context stack
	 */
	public void addTopMethodNOS(int n) {
		MetricHolder met = this.lookUpto(MetricHolder.class);

		if (met != null) {
			met.setNos( met.getNos()+n );
		}
	}

}
