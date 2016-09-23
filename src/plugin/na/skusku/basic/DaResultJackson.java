package plugin.na.skusku.basic;

import java.util.List;

public class DaResultJackson {
	private String bestMatchVersion;
	private List<String> availableVersions;
	private boolean blacklisted;
	private List<String> whitelisted;
	private String artifactId;
	private String version;
	private String groupId;

	public String getBestMatchVersion() {
		return bestMatchVersion;
	}

	public void setBestMatchVersion(String bestMatchVersion) {
		this.bestMatchVersion = bestMatchVersion;
	}

	public List<String> getAvailableVersions() {
		return availableVersions;
	}

	public void setAvailableVersions(List<String> availableVersions) {
		this.availableVersions = availableVersions;
	}

	public boolean isBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	public List<String> getWhitelisted() {
		return whitelisted;
	}

	public void setWhitelisted(List<String> whiteListed) {
		this.whitelisted = whiteListed;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String toString(){
		return "()";
	}

}

