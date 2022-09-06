
# General behavior of the visitors

## Data

Visitors are passed three data (see the constructors of `AbstractDispatcherVisitor` and `AbstractVisitor`):
- `dico`: the `CDictionnary` that creates Famix entities into the `Repository`;
- `index`: a CDT structure that contains the ASTs of the different C/C++ files. It also contains the "bindings" for the CDT entities;
- `projectPrefix`: the directory path to the project `rootFolder`. This is removed from the fullpath of the files to get their relative path in the `rootFolder`.

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

