
# Architecture of the plugin

## Packages

- `plugin` : Top level executed files:
  - `Activator` : plugin activation, called by Eclipse at start and last to execute (Stop)
  - `PluginApplication` also launched by Eclipse automatically, calls the parsing and outputs the resulting model
  - `VerveineCParser` : parses the C/C++ code and populates the model
  - `CDictionnary` : class creating entities in the model
- `utils` : various utilities used in the process (exception, file management
- `visitors` : abstract visitors of AST created by CDT, some abstract inherited by other (see def./ref.), other very specific (errors, includes, generator of method heading)
- `visitors.def` : visitors for declaration of entities like type, method, variable declaration
- `visitors.ref` : visitors for reference to entities already declared, class inheritance, method call, ...
