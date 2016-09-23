package plugin.na.skusku.basic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class PomPath returns the path to your projects POM file.
 */

public class PomActions {

	private final String FILENAME = "pom.xml";
	private File pom = null;
	private String absoluteFilePath = "";
	private Model model = null;
	private FileReader reader = null;
	private MavenProject project;
	private List<Dependency> dependencies;
	private List<MyDependency> gavDependencies = new ArrayList<MyDependency>();
	private ObjectMapper mapper = new ObjectMapper();
	private String jsonString;
	private PncHttp pnc = new PncHttp();

	/**
	 * Gets the pom by selecting the workbench, then gets the location of the
	 * selected project of that workbench. Then it adds the "pom.xml" string to
	 * the path and returns it.
	 *
	 * @return the pom
	 */
	// TODO: dorobit exception ked nenajde file!
	// works with new Eclipse instance, returns your project's pom, doesnt work
	// wile testing
	public void projectPom() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IAdaptable) {
				IProject project = (IProject) ((IAdaptable) firstElement).getAdapter(IProject.class);
				IPath WorkingDirectoryPath = project.getLocation();
				System.out.println("Current working directory" + WorkingDirectoryPath);
				absoluteFilePath = WorkingDirectoryPath + File.separator + FILENAME;
				pom = new File(absoluteFilePath);
				System.out.println("POM's absolute filepath : " + absoluteFilePath);
			}
		}
		System.out.println("Cant find your projects POM file.");
	}
	// works while testing

	public void projectPomTest() {
		String workingDirectory = System.getProperty("user.dir");
		System.out.println("Current working directory = " + workingDirectory);

		String absoluteFilePath = "";

		absoluteFilePath = workingDirectory + File.separator + FILENAME;

		System.out.println("Final filepath : " + absoluteFilePath);

		pom = new File(absoluteFilePath);

	}

	/**
	 * sets your POM as Maven Project.
	 */
	public void pomToMavenProject() {
		MavenXpp3Reader mavenreader = new MavenXpp3Reader();
		try {
			reader = new FileReader(pom);

			model = mavenreader.read(reader);

			model.setPomFile(pom);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		project = new MavenProject(model);

	}

	// nastavi do dependencies vsetky dependencies a potom do myDependencies
	// ulozi iba zavislosti s potrebnymi udajmi
	public void setDependencies() {
		dependencies = project.getDependencies();
		System.out.println(dependencies.toString());
		for (Dependency dep : dependencies) {
			MyDependency mydep = new MyDependency(dep.getGroupId(), dep.getArtifactId(), dep.getVersion());
			gavDependencies.add(mydep);
		}
	}

	// nastavi mapper, mozno by bolo lepsie spravit v konstruktore.
	// ulozi do jsonString a strings GAV z pom zavislosti.
	public void setJackson() {
		StringWriter strings = new StringWriter();
		try {
			jsonString = mapper.writeValueAsString(gavDependencies);
			System.out.println("json String: " + jsonString);
			mapper.writeValue(strings, gavDependencies);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("strings: " + strings);

	}

	// metoda, ktora zavola pnc.post metodu vytvorenu pri volani konstruktora.
	// Potom do kazdej mojej MyDependency ulozi availableVersion
	// a bestmatchversion z DA
	public void getDa() {
		try {
			DaResultJackson[] result = mapper.readValue(
					pnc.post(jsonString,
							"http://pnc-da-branch-nightly.cloud.pnc.devel.engineering.redhat.com/da/rest/v-0.4/reports/lookup/gavs"),
					DaResultJackson[].class);
			int i = 0;
			for (DaResultJackson item : result) {
				gavDependencies.get(i).setAvailableVersions(item.getAvailableVersions());
				gavDependencies.get(i).setBestMatchVersion(item.getBestMatchVersion());
				i++;
			}
		} catch (

		IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PomActions actions = new PomActions();
		actions.projectPomTest();
		actions.pomToMavenProject();
		actions.setDependencies();
		System.out.println("/n");
		actions.setJackson();
		actions.getDa();
		for (MyDependency dep : actions.gavDependencies) {
			System.out.println(dep.toString());

		}

	}

	public File getPom() {
		return pom;
	}

	public void setPom(File pom) {
		this.pom = pom;
	}

	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}

	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public FileReader getReader() {
		return reader;
	}

	public void setReader(FileReader reader) {
		this.reader = reader;
	}

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public List<MyDependency> getGavDependencies() {
		return gavDependencies;
	}

	public void setGavDependencies(List<MyDependency> gavDependencies) {
		this.gavDependencies = gavDependencies;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public PncHttp getPnc() {
		return pnc;
	}

	public void setPnc(PncHttp pnc) {
		this.pnc = pnc;
	}

	public String getFILENAME() {
		return FILENAME;
	}

}