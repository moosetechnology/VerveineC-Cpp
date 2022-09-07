
# General behavior of the visitors

## Data

Visitors are passed three data (see the constructors of `AbstractDispatcherVisitor` and `AbstractVisitor`):
- `dico`: the `CDictionnary` that creates Famix entities into the `Repository`;
- `index`: a CDT structure that contains the ASTs of the different C/C++ files. It also contains the "bindings" for the CDT entities;
- `projectPrefix`: the directory path to the project `rootFolder`. This is removed from the absolute path of the files to get their relative path in the `rootFolder`.

In addition, some other data that visitors usually contain are (see `AbstractVisitor`):
- `context`: A stack (`CppEntityStack`) of the entities visited: file, class, method, ... For any declaration, this gives the parent of the declared entity. It is actually stored in the `resolver`;
- `filename`: name of the current file (TranslationUnit) being visited;
- `resolver`: An object responsible for resolving names.
- `nodeName`: A variable used in many visit methods to store or get the name of the current node;
- `nodeBnd`:  A variable used in many visit methods to store or get the "binding" (see [Name resolving](#name-resolving)) of the current node

## Name resolving

The importer has two major tasks:
- creating entities declared ("Def" visitors) and putting them into their "context" (their parent entity);
- creating relationships between entities ("Ref" visitors), i.e. name resolving, when a name is found in the code, finding where this name was declared.

Name resolving is mostly handled by CDT itself with the `index` where each entity has a `Binding`.
When a name is found, we ask CDT for its "binding".
The `CDictionnary` keeps a map of the CDT bindings and the Famix entities created, so from the CDT binding we can find back the corresponding Famix entity.
Unfortunately sometimes CDT does not give us a binding, so we try to create a meaningful `StubBinding`.
These `StubBinding`s are not used for references, but in to put the entity on the context stack in another visitor.

## ASTVisitor

CDT defines this abstract visitor that we will extend.
It must be noted that it does not offer (declare) a `visit` method for all possible types of nodes in the AST

In `ASTVisitor` the visit node start in the corresponding `visit` method (eg. `visit(IASTDeclaration node)`).
If this method returns the value `ASTVisitor.PROCESS_CONTINUE`, then the visitor will visit the children of the node.
After that, it will "close" the visit by calling a corresponding `leave` method (eg. `leave(IASTDeclaration node)`).

Other possible return values for `visit` methods are: `PROCESS_SKIP` and `PROCESS_ABORT`.

## AbstractDispatcherVisitor

This is the abstract visitor inherited by all the other ones.

`AbstractDispatcherVisitor` adds new visit methods for the types that `ASTVisitor` does not consider.
For this it "re-dispatches" the generic existing ones into more specific ones (by testing the exact type of the node).

For example `ASTVisitor` has `visit(IASTDeclaration)`, but there are various interesting subclasses of `IASTDeclaration`.
Therefore `AbstractDispatcherVisitor` test the exact type of the node and calls various new visit methods for each subclass:

```Java
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
```

`AbstractDispatcherVisitor` also merges the two abstract visitors of CDT (`ICElementVisitor` and `ASTVisitor`).
Visiting a project starts with `visit(ICElement)` (required by `ICElementVisitor`).
The implementation visits the children of the project.
This is handled by the methods declared in CDT's `ICElementVisitor` for the four types: `ICProject`, `ICContainer`, `ITranslationUnit`, and `IInclude` (this last one being ignored by default).

When reaching `visit(ITranslationUnit tu)` the visit switches to the methods provided by CDT's `ASTVisitor`.
Simplified code:

```Java
public void visit(ITranslationUnit tu) {
	tu.getAST(index, ITranslationUnit.AST_CONFIGURE_USING_SOURCE_CONTEXT | ITranslationUnit.AST_SKIP_INDEXED_HEADERS).accept(this);
```

Note that the visiting process remains a bit different for both parts because `ICElementVisitor` does not provide the visit of the children of the `leave` methods (see above [ASTVisitor](#astvisitor)).
Visiting the children in this case is implemented in `AbstractDispatcherVisitor.visitChildren(IParent)` that is called in all visit methods for `ICElementVisitor` types.

## AbstractContextVisitor

This is a subclass of `AbstractDispatcherVisitor` inherited by most of the other visitors.
It creates a context stack and offers accessor methods to it.
It also handles the current filename of the AST visited, and the current path of this file (its parent directory).
 
It gets the `nodeBnd` and `nodeName` (see [Data](#data) above) for the main entities visited (e.g. declaration of class (`ICASTCompositeTypeSpecifier`), function (`IASTFunctionDeclarator`), parameter (`IASTParameterDeclaration`), ...).
Basically these are the entities that are represented in Famix.

`nodeBnd` of a node is obtain through the `resolver` (again see [Data](#data)).

A generic algorithm for the `visit` methods is:
- get the name of the node
- get the binding of the node
- if the binding could not be obtain, create a `StubBinding`

For example:

```Java
protected int visit(IASTCompositeTypeSpecifier node) {
	nodeName = node.getName();
	nodeBnd = resolver.getBinding(nodeName);

	if (nodeBnd == null) {
		nodeBnd = resolver.mkStubKey(nodeName, Class.class);
	}
	return PROCESS_CONTINUE;
}
```

`AbstractContextVisitor` does not push the entities on the context stack because it is extended by most visitors, many of which are used when the entities are not yet created (because it is the work of these visitors).
For example `BehaviouralDefVisitor` creates method entities, therefore `AbstractContextVisitor` cannot automatically push methods on the stack because when extended by `BehaviouralDefVisitor` the methods do not exist yet.

## Def visitors

They are the visitors that create in Famix the declared entities.
Each one as a "main" visit method (or several) that creates a Famix entity for the type of entities the visitor is concerned with.
This main method may or not use the super visit to get the name and binding of the CDT entity.
If this type of entity has children in Famix, then it is pushed on the context stack to make it available to the children (pointing back to their parent).

Simplified example:

```Java
TypeDefVisitor.visit(ICASTCompositeTypeSpecifier) {
		Class fmx;

		super.visit(node);
		fmx = createClass(node);

		this.getContext().push(fmx);
		return PROCESS_CONTINUE;
	}
```

Therefore, these visitors must push on the context stack the entities they are visiting, for example the visitor creating methods must put on the context stack the class owning the methods.
For this, these visitors typically invoke the method defined in super to recover the binding of a node and from this binding finds the Famix entity corresponding to the parent and push it on the context stack.

Here is a simplified example of pushing a class on the context stack in the visitor creating methods and attributes (i.e. class members):

```Java
ClassMemberDefVisitor.visit(IASTCompositeTypeSpecifier node) {
		Class fmx;

		super.visit(node);
		fmx = dico.getEntityByKey(Class.class, nodeBnd);

		this.getContext().push(fmx);
		return PROCESS_CONTINUE;
	}
```
Entities are removed from the context stack in the various `leave(...)` methods:

There is more or less one "Def visitor" for each type of entity in Famix (packages, types, methods/functions, variables, ...).
This results in visitors that are much simpler because they deal with only one kind of entity.
By using the visitors in the right order (see `VerveineCParser.runAllVisitors()` and in [archi.md](archi.md#visitors)), this also simplifies the process by ensuring that all used entities are known (created) before being referred to.

The "Def visitors" are `PackageDefVisitor`, `NamespaceDefVisitor`, `TypeDefVisitor`, `BehaviouralDefVisitor`, `TemplateParameterDefVisitor`, `AttributeGlobalVarDefVisitor`, `CommentDefVisitor`, `PreprocessorStmtDefVisitor`, and `ClassMemberDefVisitor` (an abstract super class for `BehaviouralDefVisitor` and `AttributeGlobalVarDefVisitor`).


## Ref visitors

They are the visitors for reference to entities already declared, class inheritance, method call, variable uses, ...

There is one `AbstractRefVisitor`, that extends `AbstractContextVisitor`, and that the others extend: `DeclaredTypeRefVisitor`, `InvocationAccessRefVisitor`, and `ReferenceRefVisitor`.
There is a fourth "Ref visitor" (`InheritanceRefVisitor`) that does not extend `AbstractRefVisitor` but directly `AbstractContextVisitor`.

If `AbstractContextVisitor` recovers the name and binding of the nodes, it does not push them automatically on the context stack.
This is because it is extended by visitors that create the entities.
`AbstractRefVisitor` provides this additional behavior, because it is extends by visitors that are called when all entities have been created (see order of visitors execution in [Visitors](archi.md#visitors)).

A generic algorithm for a visit method in `AbstractRefVisitor` is:
- call super `visit` (in `AbstractContextVisitor`) to recover the binding of the node;
- recover the corresponding Famix entity from the binding of the node;
- pushd the Famix entity on the context stack;
- return `ASTVisitor.PROCESS_CONTINUE` to allow visiting the children of the node.

Simplified example:

```Java
protected int visit(ICPPASTCompositeTypeSpecifier node) {
	Class fmx;
	super.visit(node);
	fmx = dico.getEntityByKey(Class.class, nodeBnd);
	this.contextPush(fmx);
	return PROCESS_CONTINUE;
}
```
Entities are removed from the context stack in the various `leave(...)` methods.