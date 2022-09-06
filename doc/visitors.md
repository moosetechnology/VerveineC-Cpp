
# General behavior of the visitors

## Data

Visitors are passed three data (see the constructors of `AbstractDispatcherVisitor` and `AbstractVisitor`):
- `dico`: the `CDictionnary` that creates Famix entities into the `Repository`;
- `index`: a CDT structure that contains the ASTs of the different C/C++ files. It also contains the "bindings" for the CDT entities;
- `projectPrefix`: the directory path to the project `rootFolder`. This is removed from the absolute path of the files to get their relative path in the `rootFolder`.

In addition, some other data that visitors usually contain (see `AbstractVisitor`):
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
Unfortunately sometimes CDT does not give us a binding, so we try to create a `StubBinding`.

## AbstractDispatcherVisitor

This is the abstract visitor inherited by all the other ones.
In CDT their are two abstract visitors (`ICElementVisitor` and `ASTVisitor`) that are merged into this one.
Also `ASTVisitor` does not define a visit method for all possible types of nodes in the AST, so `AbstractDispatcherVisitor` adds these new visit methods and re-dispatches the generic existing ones into these more specific ones (by testing the exact type of the node).

For example:

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

Visiting a project starts with `visit(ICElement)` that visits the children of the project.
This is handled by the methods declared in CDT's `ICElementVisitor` for the four types: `ICProject`, `ICContainer`, `ITranslationUnit`, and `IInclude` (this last one being ignored by default).

When reaching `visit(ITranslationUnit tu)` the visit switches to the methods provided by CDT's `ASTVisitor`:

```Java
tu.getAST(index, ITranslationUnit.AST_CONFIGURE_USING_SOURCE_CONTEXT | ITranslationUnit.AST_SKIP_INDEXED_HEADERS).accept(this);
```

Note that the visiting process remains a bit different for both parts.
`ASTVisitor` implements the visit of all the children of a node.
For this to happen, our visit method needs to return the value `ASTVisitor.PROCESS_CONTINUE`.
`ICElementVisitor` on the other hand is an interface and does not provide the visit of the children.
This is implemented in `AbstractDispatcherVisitor.visitChildren(IParent)` that is called in all visit methods of this visitor.

A related topic is that `ASTVisitor` provides `leave(...)` methods for all the types that have a `visit(...)`.
The `leave` method is called when we are done visiting the node and its children.
`ICElementVisitor` does not declares `leave` methods.

## AbstractVisitor

This is another abstract visitor inherited by most of the other visitors. 
It is itself a subclass of `AbstractDispatcherVisitor`.

It gets the `nodeBnd` and `nodeName` (see [Data](#data) above) for the main entities visited (e.g. declaration of class (`ICASTCompositeTypeSpecifier`), function (`IASTFunctionDeclarator`), parameter (`IASTParameterDeclaration`), ...).
Basically these are the entities that are represented in Famix.

`nodeBnd` of a node is obtain through he `resolver` (again see [Data](#data)).

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

This visitor also offers accessor methods for the `context` stack, the current path, and the current filename.
 
## Def visitors

They are the visitors that create in Famix the declared entities.
Each one as a "main" visit method (or several) that creates a Famix entity for the type of entities the visitor is concerned with.
This main method may or not use the super visit to get the name and binding of the CDT entity.
If this type of entity has children in Famix, then it is pushed on the context stack to make it available to the children (pointing back to their parent)

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

Therefore, these visitors must also handle the context stack, for example the visitor creating methods must put on the context stack the class owning each method.
For this, these visitors typically invoke the method defined in super to recover the binding of a node and from this binding finds the Famix entity corresponding to the parent and pushes it on the context stack.

Simplified example, pushing a class on the context stack for the visitor creating methods and attributes (i.e. class members):

```Java
ClassMemberDefVisitor.visit(IASTCompositeTypeSpecifier node) {
		Class fmx;

		super.visit(node);
		fmx = dico.getEntityByKey(Class.class, nodeBnd);

		this.getContext().push(fmx);
		return PROCESS_CONTINUE;
	}
```

There is more or less one such visitor for each type of entity (packages, types, methods/functions, ...).
This results in visitors that are much simpler because they deal with only one kind of entity.
By using the visitors in the right order (see `VerveineCParser.runAllVisitors()` and in [archi.md](archi.md#visitors)), this also simplifies the process by ensuring that all used entities are known (created) before being referred to.

The "Def visitors" are `PackageDefVisitor`, `NamespaceDefVisitor`, `TypeDefVisitor`, `BehaviouralDefVisitor`, `TemplateParameterDefVisitor`, `AttributeGlobalVarDefVisitor`, `CommentDefVisitor`, `PreprocessorStmtDefVisitor`, and `ClassMemberDefVisitor` (an abstract super class for `BehaviouralDefVisitor` and `AttributeGlobalVarDefVisitor`).
