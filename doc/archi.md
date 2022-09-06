
# Architecture of the plugin

## Packages

- `plugin` : Top level executed files:
    - `Activator.java` : plugin activation, called by Eclipse at start and last to execute (Stop);
    - `PluginApplication.java` also launched by Eclipse automatically, calls the parsing and outputs the resulting model;
    - `VerveineCParser.java` : parses the C/C++ code and populates the model;
    - `CDictionnary.java` : class creating entities in the model.
- `utils` : various utilities used in the process (exception, file management;
- `visitors` : abstract visitors of AST created by CDT, some abstract inherited by other (see def./ref.), other very specific (errors, includes, generator of method heading);
- `visitors.def` : visitors for declaration of entities like type, method, variable declaration;
- `visitors.ref` : visitors for reference to entities already declared, class inheritance, method call, ...

## Main components

The importer is made of:
- `VerveineCParser` the "main" class that controls the whole analysis;
- Visitors that analyze the AST created by CDT and populate the model with entities;
- `CDictionnary` that creates and holds all the Famix entities. They are stored in a `Repository`;
- `Repository` a Fame (meta-meta-model) store that contains Famix entities, ensure relationship between them and can import or export them on file.

## Running

The main() method can be considered to be `PluginApplication.start()`. It creates the parser, the Repository, the dictionnary; it analyses the options given on command line (e.g. to find out where is the directory of the project to analyze); it parses the project; and it exports the resulting model (stored in the Repository) in a file.

```Java
VerveineCParser verveine = new VerveineCParser();
verveine.setOptions(appArgs);
if (verveine.parse()) {
	verveine.emitMSE();
```

## Parsing

`VerveineCParser.java` is the class controlling the parsing:

1. First it creates an Eclipse project, and copies the source code to analyze into this Eclipse project
1. Asks CDT to analyze the project (compute index)
1. then run all visitors on the "index" (basically an AST representation of the code)

```Java
ICProject cproject = createEclipseProject(DEFAULT_PROJECT_NAME, userProjectDir);
...
computeIndex(cproject);
...
runAllVisitors(dico, cproject);
```

## Visitors

Import of the project is done by several visitors visiting the AST created by CDT.

The visitors are, in order:

1. `IncludeVisitor`: Creates CFile entities and include relationships;
1. `ErrorVisitor`: Gathers all issues identified by CDT to list them at the end of the process;
1. `PackageDefVisitor`: Creates Package entities;
1. `NamespaceDefVisitor`: Creates Namespace entities;
1. `TypeDefVisitor`: Creates type (struct, class, enum, ...) entities;
1. `InheritanceRefVisitor`: creates inheritance relationships between classes;
1. `BehaviouralDefVisitor`: Creates method and function entities;
1. `TemplateParameterDefVisitor`: Creates parameter type entities for generics;
1. `AttributeGlobalVarDefVisitor`: Creates classes' attributes, enums' values, and global variables entities;
1. `DeclaredTypeRefVisitor`: Creates references to types in method declarations (return type) and in variable declarations;
1. `InvocationAccessRefVisitor`: Creates invocations to methods and accesses to variables;
1. `ReferenceRefVisitor`: Creates other references to types;
1. `CommentDefVisitor`: Creates comment entities;
1. `PreprocessorStmtDefVisitor`: Creates preprocessors entities.

There are also some abstract visitors that are inherited by the concrete ones listed above:
1. `AbstractDispatcherVisitor`: Most abstract visitor, it dispatches more finely the visits than what CDT's ASTVisitor normally does (adds some `visit()` methods). It also merges two APIs: visit methods on AST (ASTVisitor) and visit methods on ICElements (ICElementVisitor);
1. `AbstractVisitor`: Visitor that gets the binding and name of a node. It also manages the context stack, the current path, and the current filename. It also tries to recover the FAMIX entities from their IBindings.
1. `AbstractIssueReporterVisitor`: Superclass for `IncludeVisitor` and `ErrorVisitor`, it collects issues on the AST and can report them.
1. `AbstractRefVisitor`: Superclass for Reference visitors, it defines some utility methods to create references to names.

There is finally a specialized visitor (`SignatureBuilderVisitor`) to reconstruct the signature of a method/function. It works by visiting a method/function node and gathers its name and parameters, and return type.
 
