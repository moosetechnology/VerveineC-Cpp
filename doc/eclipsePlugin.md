
# Eclipse plugin

The configuration of the plugin is flaky as there appears to be some bugs in Eclipse.

## Running the plugin

In Eclipse, create a "Run Configuration" of type "Eclipse Application".

## - Main tab

- "Workspace Data": This is the workspace that the plugin will use to create a c/C++ project where to copy the project to analyze. For "Location", use for example: `${workspace_loc}/../tempWS`
It is a good idea to select "Clear:" "workspace" and unselect "Ask for confirmation before cleaning".
- "Program to Run": Select "Run an application" with the name `org.moostechnology.verveineC`
- "Java Runtime Environment": successfully tested with Java-17

### - Arguments tab

- "Program arguments": give the directory of a C/C++ project that you want to anlayze. This path can be relative to "Working directory" (see below) or absolute. Other arguments can be added, see `VerveineCParser.usage()` for list of options
- "VM arguments": none required
- "Working directory": Where Eclipse will be executing. Unless specified in program arguments `-o <filename>`, this is where the resulting model will be created (by default `output.mse`)

### - Plugins tab

It should work with: "Launch with" = "All worpkspace and enabled target Plug-ins".
However, this gives validation warning upon execution, so unselect "Validate Plug-ins automatically prior to launching".

It should be possible to be more restrictive in the plug-ins selection: "Launch with" = "Plug-ins selected below" and select only the org.moostechnology.verveineC plug-in and its required plugins.
However, it does not seem to be able to detect all required plug-ins so that there is an error at execution.

## Testing the plugin

Very similar to running it.
The "Run Configuration" should be a "JUnit Plug-in Test".

### - Test tab

- Select to "Run all tests in the selected project, package or source folder"
- "Test runner" = "JUnit 5"

## - Main tab

- "Workspace Data": Use the same workspace as for running the plugin. Putting something else does not seem to work (use the workspace for running the plugin anyway).
"Clear:" same as for running the plugin.
- "Program to Run": Select "Run an application" = "[No Application] - Headless Mode"
- "Java Runtime Environment": same as for running the plugin.

### - Arguments tab

- "Program arguments": none required
- "VM arguments": none required
- "Working directory": Select "Other" and indicate the directory of the plugin `${workspace_loc}/org.moosetechnology.verveineC`

### - Plugins tab

Same as for running the plugin.
