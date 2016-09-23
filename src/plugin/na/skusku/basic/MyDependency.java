package plugin.na.skusku.basic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class MyDep stands for My Dependency and is used for collecting data from
 * POM file and user input data such as scmURL nad scmRev. Also collects version
 * from Dependency Analysis.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyDependency {
	private String groupId;
	private String artifactId;
	private String version;
	private List<String> availableVersions;
	private String bestMatchVersion;
	private String scmURL;
	private String scmRev;

	/**
	 * Instantiates a new my dep also with the information about the PNC version
	 * of the dependency.
	 *
	 * @param groupId
	 *            the group id
	 * @param artifactId
	 *            the artifact id
	 * @param version
	 *            the version
	 * @param pncVersion
	 *            the pnc version
	 */
	public MyDependency(String groupId, String artifactId, String version, List<String> availableVersions) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.availableVersions = availableVersions;
		this.scmURL = "";
		this.scmRev = "";
	}

	/**
	 * Instantiates a new plain only with data from POM file my dep.
	 *
	 * @param groupId
	 *            the group id of the dependency from POM file
	 * @param artifactId
	 *            the artifact id of the dependency from POM file
	 * @param version
	 *            the version of the dependency from POM file
	 */
	public MyDependency(String groupId, String artifactId, String version) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.availableVersions = null;
		this.scmURL = null;
		this.scmRev = null;
	}

	/**
	 * Gets the group id.
	 *
	 * @return the group id
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Sets the group id.
	 *
	 * @param groupId
	 *            the new group id
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Gets the artifact id.
	 *
	 * @return the artifact id
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * Sets the artifact id.
	 *
	 * @param artifactId
	 *            the new artifact id
	 */
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the pnc version.
	 *
	 * @return the pnc version
	 */
	public List<String> getAvailableVersions() {
		return this.availableVersions;
	}

	/**
	 * Sets the pnc version.
	 *
	 * @param pncVersion
	 *            the new pnc version
	 */
	public void setAvailableVersions(List<String> availableVersions) {
		this.availableVersions = availableVersions;
	}

	/**
	 * Gets the scm url.
	 *
	 * @return the scm url
	 */
	public String getScmURL() {
		return scmURL;
	}

	/**
	 * Sets the scm url.
	 *
	 * @param scmURL
	 *            the new scm url
	 */
	public void setScmURL(String scmURL) {
		this.scmURL = scmURL;
	}

	/**
	 * Gets the scm rev.
	 *
	 * @return the scm rev
	 */
	public String getScmRev() {
		return scmRev;
	}

	/**
	 * Sets the scm rev.
	 *
	 * @param scmRev
	 *            the new scm rev
	 */
	public void setScmRev(String scmRev) {
		this.scmRev = scmRev;
	}

	public String toString() {
		return "Gourp ID: " + groupId + " ,Artifact ID: " + artifactId + " ,version: " + version
				+ " ,available versions: " + availableVersions.toString() + " ,Scm URL: " + scmURL + " ,Scm Revision: "
				+ scmRev;
	}

	public String getBestMatchVersion() {
		return bestMatchVersion;
	}

	public void setBestMatchVersion(String bestMatchVersion) {
		this.bestMatchVersion = bestMatchVersion;
	}

}