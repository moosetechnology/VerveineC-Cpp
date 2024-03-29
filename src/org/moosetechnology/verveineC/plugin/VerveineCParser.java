package org.moosetechnology.verveineC.plugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.index.IIndexManager;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.IPathEntry;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.moosetechnology.famix.cpp.CSourceLanguage;
import org.moosetechnology.famix.cpp.CppSourceLanguage;
import org.moosetechnology.famix.cpp.Entity;
import org.moosetechnology.famix.cpp.FAMIXModel;
import org.moosetechnology.famix.cpp.SourceLanguage;
import org.moosetechnology.verveineC.utils.Constants;
import org.moosetechnology.verveineC.utils.Trace;
import org.moosetechnology.verveineC.utils.fileAndStream.FileUtil;
import org.moosetechnology.verveineC.visitors.AbstractIssueReporterVisitor;
import org.moosetechnology.verveineC.visitors.ErrorVisitor;
import org.moosetechnology.verveineC.visitors.IncludeVisitor;
import org.moosetechnology.verveineC.visitors.def.AttributeGlobalVarDefVisitor;
import org.moosetechnology.verveineC.visitors.def.BehaviouralDefVisitor;
import org.moosetechnology.verveineC.visitors.def.CommentDefVisitor;
import org.moosetechnology.verveineC.visitors.def.NamespaceDefVisitor;
import org.moosetechnology.verveineC.visitors.def.PackageDefVisitor;
import org.moosetechnology.verveineC.visitors.def.PreprocessorStmtDefVisitor;
import org.moosetechnology.verveineC.visitors.def.TemplateParameterDefVisitor;
import org.moosetechnology.verveineC.visitors.def.TypeDefVisitor;
import org.moosetechnology.verveineC.visitors.ref.DeclaredTypeRefVisitor;
import org.moosetechnology.verveineC.visitors.ref.InheritanceRefVisitor;
import org.moosetechnology.verveineC.visitors.ref.InvocationAccessRefVisitor;
import org.moosetechnology.verveineC.visitors.ref.ReferenceRefVisitor;

import ch.akuhn.fame.Repository;


public class VerveineCParser {

	/**
	 * Name of the default file where to put the MSE model
	 */
	public final static String OUTPUT_FILE = "output.mse";
	
	/**
	 * Name of the file where to put the MSE model.
	 * Defaults to {@link VerveineParser#OUTPUT_FILE}
	 */
	private String outputFileName;   // name of the MSE file to output the model
	private boolean incrementalParsing = false;   // if true we add the model to an existing one (found in 'outputFileName')


	/**
	 * Famix repository where the entities are stored
	 */
	private Repository famixRepo;

	public static final String WORKSPACE_NAME = "tempWS";

	public static final String DEFAULT_PROJECT_NAME = "tempProj";

	public static final String SOURCE_ROOT_DIR = "src";

	/**
	 * Directory where the project to analyze is located
	 */
	private String userProjectDir;

	/**
	 * Default include paths for Linux
	 */
    public static final String[] LINUX_DEFAULT_INCLUDE = new String[] {
			 "/usr/include" ,
			 "/usr/local/include"
    };

    /**
     * The name of a file in current directory containing a description of the version of the parser.
     * Useful for error reporting.
     */
	private static final String VERSION_FILE = "verveine.vers";

    /**
     * Name of a file containing list of include dirs
     */
    protected String includeConfigFile;

	/**
	 * Temporary variable to gather include paths from the command line.
	 */
	private List<String> argIncludes;

	/**
	 * Temporary variable to gather macros defined from the command line
	 */
	private Map<String,String> argDefined;

	/**
	 * Whether this is a "windows" project.
	 * "Windows" project have file names where the case is not significant,
	 * thus AFile.c is the same as AFILE.c or aFILE.c
	 */
	private boolean windows;

	/**
	 * Eclipse CDT indexer
	 */
	private IIndex index = null;

	/**
	 * flag telling whether we need to look for all possible include dir
	 */
	private boolean autoinclude;

	/**
	 * flag telling whether we want to create a C or a C++ model.
	 * Defaults to C++ (cModel == false)
	 */
	private boolean cModel;
	
	/**
	 * Prefix to remove from file names
	 */
	protected String projectPrefix = null;

	/**
	 * Dictionary used to create all entities. Contains a Famix repository
	 */
	private CDictionary dico;

	/**
	 * whether to add a .h extension to the includes that do not specify one (e.g. <code>#include &lt;string&gt;</code>).
	 * The idea is to help the include resolver
	 */
	private boolean forceIncludeH;

	/** 
	 * whether to check for include error and parsing errors
	 */
	private boolean errorlog;

	public static String projectSourcePath() {
		return File.separator + DEFAULT_PROJECT_NAME + File.separator + SOURCE_ROOT_DIR + File.separator;
	}
	
	public VerveineCParser() {
		setOutputFileName(OUTPUT_FILE);
		setFamixRepo(new Repository(FAMIXModel.metamodel()));
		this.argIncludes = new ArrayList<String>();
		this.argDefined = new HashMap<String,String>();
		this.forceIncludeH = false;
		this.autoinclude = false;
		this.windows = false;
		this.cModel = false;
		this.errorlog = false;
		this.includeConfigFile = null;
		this.userProjectDir = null;

		dico = new CDictionary(getFamixRepo());
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * "closes" the repository, by adding to it a SourceLanguage entity if their is none.
	 * The SourceLanguage entity is the one returned by getMyLgge().
	 * Also outputs repository to a MSE file
	 */
	public void emitMSE() {
		this.emitMSE(this.outputFileName);
	}

	public void emitMSE(String outputFile) {
		try {
			emitMSE(new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void emitMSE(OutputStream output) {
		// Adds default SourceLanguage for the repository
		if ( (listAll(SourceLanguage.class).size() == 0) && (getMyLgge() != null) ) {
			getFamixRepo().add( getMyLgge());
		}

		// Outputting to a file
		try {
			//famixRepo.exportMSE(new FileWriter(OUTPUT_FILE));
			famixRepo.exportMSE(new BufferedWriter(new OutputStreamWriter(output,"UTF8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a Collection of all FAMIXEntities in the repository of the given fmxClass
	 */
	public <T extends Entity> Collection<T> listAll(Class<T> fmxClass) {
		return getFamixRepo().all(fmxClass);
	}

	public Repository getFamixRepo() {
		return famixRepo;
	}

	public void setFamixRepo(Repository famixRepo) {
		this.famixRepo = famixRepo;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public boolean parse() {
		System.out.println("Copying source files in local project");
        ICProject cproject = createEclipseProject(DEFAULT_PROJECT_NAME, userProjectDir);
        if (cproject == null) {
        	// could not create the project :-(
        	return false;
        }
        projectPrefix = cproject.getLocationURI().getPath() + File.separator + (windows ? SOURCE_ROOT_DIR.toLowerCase() : SOURCE_ROOT_DIR) + File.separator;

        configIndexer(cproject);
		computeIndex(cproject);

        try {
    		runAllVisitors(dico, cproject);
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}

        return true;
	}

	private void runAllVisitors(CDictionary dico, ICProject cproject) throws CoreException {
		/*Having very specialized visitors helps because each one is simpler
		 * so it is worth the impact on execution time
		 * Note that the order is important, the visitors are not independent */
Trace.off();
		
		AbstractIssueReporterVisitor issueVisitor;

		issueVisitor = new IncludeVisitor(dico, index, projectPrefix);
		cproject.accept(issueVisitor);
		if (errorlog) {
			issueVisitor.reportIssues();
		}

		// another issue reporter
		issueVisitor = new ErrorVisitor(dico, index, projectPrefix);

		cproject.accept(new PackageDefVisitor(dico, index, projectPrefix));
		if (!cModel) {
			cproject.accept(new NamespaceDefVisitor(dico, index, projectPrefix));
		}
		
		cproject.accept(new TypeDefVisitor(dico, index, projectPrefix));
		if (!cModel) {
			cproject.accept(new InheritanceRefVisitor(dico, index, projectPrefix));
		}
		//cproject.accept(new TypeDefFromRefVisitor(dico, index, projectPrefix));

		BehaviouralDefVisitor behavVisitor = new BehaviouralDefVisitor(dico, index, projectPrefix);		// must be after class definitions
		behavVisitor.setHeaderFiles(true);
		cproject.accept(behavVisitor);
		behavVisitor.setHeaderFiles(false);
		cproject.accept(behavVisitor);
		if (!cModel) {
			cproject.accept(new TemplateParameterDefVisitor(dico, index, projectPrefix));	// must be after method definitions (possible template)
		}

		cproject.accept(new AttributeGlobalVarDefVisitor(dico, index, projectPrefix));			// must be after class/struct/enum definitions
		cproject.accept(new DeclaredTypeRefVisitor(dico, index, projectPrefix));
		cproject.accept(new InvocationAccessRefVisitor(dico, index, projectPrefix));
		cproject.accept(new ReferenceRefVisitor(dico, index, projectPrefix));

		cproject.accept(new CommentDefVisitor(dico, index, projectPrefix));
		cproject.accept(new PreprocessorStmtDefVisitor(dico, index, projectPrefix));

		if (errorlog) {
			cproject.accept(issueVisitor);
			issueVisitor.reportIssues();
		}
	}

	private void configWorkspace(IWorkspace workspace) {
		IWorkspaceDescription workspaceDesc = workspace.getDescription();
		workspaceDesc.setAutoBuilding(false); // we do not want the workspace to rebuild the project every time a new resource is added
		try {
			workspace.setDescription(workspaceDesc);
		} catch (CoreException exc) {
			System.err.println("Error trying to set workspace description: " + exc.getMessage());
		}

	}

	private ICProject createEclipseProject(String projName, String sourcePath) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		configWorkspace(workspace);
		IWorkspaceRoot root = workspace.getRoot();
		// we make a directory at the workspace root to copy source files
		IPath eclipseProjPath = root.getRawLocation().removeLastSegments(1).append(WORKSPACE_NAME).append(projName);

		final IProject project = root.getProject(projName);
		try {
			// delete content if the project exists
			if (project.exists()) {
				project.delete(/*deleteContent*/true, /*force*/true, Constants.NULL_PROGRESS_MONITOR);
				project.refreshLocal(IResource.DEPTH_INFINITE, Constants.NULL_PROGRESS_MONITOR);
			}
		} catch (Exception exc) {
			System.err.println("project path=" + project.getFullPath().toString());
			exc.printStackTrace();
		}

		eclipseProjPath.toFile().mkdirs();  // not really needed
		IProjectDescription eclipseProjDesc = workspace.newProjectDescription(project.getName());
		eclipseProjDesc.setLocation(eclipseProjPath);

		try {
			project.create(eclipseProjDesc, Constants.NULL_PROGRESS_MONITOR);
			project.open(Constants.NULL_PROGRESS_MONITOR);
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		try {
			// now we make it a C project
			CCorePlugin.getDefault().createCProject(eclipseProjDesc, project, Constants.NULL_PROGRESS_MONITOR, project.getName());
			if (!project.isOpen()) {
				project.open(Constants.NULL_PROGRESS_MONITOR);
			}
		} catch (Exception exc) {
			System.err.println("Error ("+exc.getClass().getSimpleName()+") in Project creation: " + exc.getMessage());
			exc.printStackTrace();
		}

		ICProjectDescription cProjectDesc = CoreModel.getDefault().getProjectDescription(project, true);
		cProjectDesc.setCdtProjectCreated();

		File projSrc = new File(sourcePath);
		if (! projSrc.exists()) {
			System.err.println("Project directory "+sourcePath+ " not found !");
			return null;
		}
		FileUtil.copySourceFilesInProject(project, SOURCE_ROOT_DIR, projSrc, /*toLowerCase*/windows, /*addHExtension*/forceIncludeH);
		ICProjectDescriptionManager descManager = CoreModel.getDefault().getProjectDescriptionManager();
        try {
			descManager.updateProjectDescriptions(new IProject[] { project }, Constants.NULL_PROGRESS_MONITOR);
		} catch (CoreException e) {
			e.printStackTrace();
		}

        return CoreModel.getDefault().getCModel().getCProject(project.getName());
	}

	/**
	 * sets include path (system, given by user) and macros into the project
	 */
	private void configIndexer(ICProject proj) {
		IPath projPath = proj.getPath();
		List<String> includeFromConf=new ArrayList<>();
		IPathEntry[] oldEntries=null;
		try {			
			oldEntries = proj.getRawPathEntries();
		} catch (CModelException e) {
			e.printStackTrace();
			return;
		}

		if (includeConfigFile != null) {
			readIncludeConf(includeConfigFile, includeFromConf);
		}
		
		IPathEntry[] newEntries = new IPathEntry[
		                                         oldEntries.length +
		                                         LINUX_DEFAULT_INCLUDE.length +
		                                         argIncludes.size() +
		                                         includeFromConf.size() +
		                                         argDefined.size()];
		int i;

		/* include paths */
		for (i=0; i < oldEntries.length; i++) {
			newEntries[i] = oldEntries[i];
		}
		/* include paths */
		for (String path : LINUX_DEFAULT_INCLUDE) {
			newEntries[i] = CoreModel.newIncludeEntry(projPath, null, new Path(path), /*isSystemInclude*/true);
			i++;
		}
		/* include paths */
		for (String path : argIncludes) {
			newEntries[i] = CoreModel.newIncludeEntry(projPath, null, new Path(path), /*isSystemInclude*/false);
			i++;
		}
		/* include paths */
		for (String path : includeFromConf) {
			newEntries[i] = CoreModel.newIncludeEntry(projPath, null, new Path(path), /*isSystemInclude*/false);
			i++;
		}
		/* macros  defined */
		for (Map.Entry<String, String> macro : argDefined.entrySet()) {
			newEntries[i] = CoreModel.newMacroEntry(projPath, macro.getKey(), macro.getValue());
		}

		try {			
			proj.setRawPathEntries(newEntries, Constants.NULL_PROGRESS_MONITOR);

		} catch (CModelException e) {
			e.printStackTrace();
		}
	}

	private void readIncludeConf(String confFileName, List<String> lines) {
		BufferedReader read = null;
		try {
			read = new BufferedReader( new FileReader(confFileName));
		} catch (FileNotFoundException e) {
			System.err.println("Could not read Include Paths configuration file: " + confFileName);
			e.printStackTrace();
		}

		if (read != null) {
			String line;
			try {
				while ( (line=read.readLine()) != null ) {
					lines.add(line);
				}
			} catch (IOException e) {
				System.err.println("Error reading Include Paths configuration file: " + confFileName);
				e.printStackTrace();
				lines = new ArrayList<>();
			}
		}

		try {
			read.close();
		} catch (IOException e) {
			// ignore
		}
	}

	private void computeIndex(ICProject cproject) {
		System.out.println("Indexing source files");

		IIndexManager imanager = CCorePlugin.getIndexManager();
		imanager.setIndexerId(cproject, "org.eclipse.cdt.core.fastIndexer");
        imanager.reindex(cproject);
        imanager.joinIndexer(IIndexManager.FOREVER, Constants.NULL_PROGRESS_MONITOR );
		try {
			this.index = imanager.getIndex(cproject);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	protected SourceLanguage getMyLgge() {
		if (cModel) {
			return new CSourceLanguage();
		}
		else {
			return new CppSourceLanguage();
		}
	}

	public void setOptions(String[] args) {
		int i = 0;
		while (i < args.length && args[i].trim().startsWith("-")) {
		    String arg = args[i++].trim();

			if (arg.equals("-h")) {
				usage();
			}
			else if (arg.equals("-v")) {
				version();
			}
			else if (arg.startsWith("-c")) {
				cModel = true;
			}
			else if (arg.equals("-autoinclude")) {
				autoinclude = true;
			}
			else if (arg.equals("-errorlog")) {
				errorlog = true;
			}
			else if (arg.equals("-includeconf")) {
				includeConfigFile = args[i++].trim();
			}
			else if (arg.equals("-forceincludeH")) {
				forceIncludeH = true;
			}
			else if (arg.startsWith("-I")) {
				argIncludes.add(arg.substring(2));
			}
			else if (arg.startsWith("-D")) {
				parseMacroDefinition(arg);
			}
			else if (arg.equals("-windows")) {
				windows = true;
			}
			else if (arg.equals("-o")) {
				if (i < args.length) {
					outputFileName = args[i+1];
					i += 2;
				} else {
					System.err.println("-o requires a filename");
				}
			}
			else {
				System.err.println("** Unrecognized option: " + arg);
				usage();
			}
		}

		for ( ; i < args.length; i++) {
			// humm, possible bug here if there are more than 1 remaining args, only the last one is kept
			setUserProjectDir( args[i]);
			
			if (autoinclude) {
				for (String inc : FileUtil.gatherIncludeDirs(args[i])) {
					argIncludes.add(inc);					
				}
			}
		}
	}

	/** For testing purposes
	 */
	public void setUserProjectDir(String dir) {
		userProjectDir = dir;
	}

	private void parseMacroDefinition(String arg) {
		int i;
		String macro;
		String value;

		i = arg.indexOf('=');
		if (i < 0) {
			macro=arg.substring(2);  // remove '-D' at the beginning
			value = "";
		}
		else {
			macro = arg.substring(2, i);
			value = arg.substring(i+1);
		}
		argDefined.put(macro, value);
	}

	protected void version() {
		BufferedReader read = null;

		System.out.println("VerveineC version:");
		try {
			read = new BufferedReader( new FileReader(VERSION_FILE));
		} catch (FileNotFoundException e) {
			System.out.println(" *unknown* (version file not found)");

			System.exit(0);
		}

		if (read != null) {
			String line;
			try {
				while ( (line=read.readLine()) != null ) {
					System.out.println("  " + line);
				}
			} catch (IOException e) {
				// ignore
			}
		}

		try {
			read.close();
		} catch (IOException e) {
			// ignore
		}

		System.exit(0);
	}

	protected void usage() {
		System.err.println("Usage: VerveineC [<options>] <eclipse-Cproject-to-parse>");
		System.err.println("Recognized options:");
		System.err.println("      -h: prints this message");
		System.err.println("      -v: prints the version");
		System.err.println("      -o <output-file-name>: changes the name of the output file (default: output.mse)");
		//System.err.println("      -D<macro>: defines a C/C++ macro");
		System.err.println("      -I<include-dir>: adds a directory containing include files");
		System.err.println("      -includeconf <config-file>: adds the directories listed in config-file in the include paths");
		System.err.println("      -autoinclude: looks for _all_ directories containing .h/.hh files and add them in the include paths");
		System.err.println("      -errorlog: reports unrelsolved includes and parsing errors");
		System.err.println("      <eclipse-Cproject-to-parse>: directory containing the C/C++ project to export in MSE");
		System.exit(0);
	}

	public int nbEntities() {
		return dico.size();
	}
}
