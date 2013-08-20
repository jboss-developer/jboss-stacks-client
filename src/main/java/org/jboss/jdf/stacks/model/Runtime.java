package org.jboss.jdf.stacks.model;

import java.util.List;
import java.util.Properties;

public interface Runtime {

	public String getId();

	public String getName();

	public String getGroupId();

	public String getArtifactId();

	public String getVersion();

	public String getUrl();

	public String getDownloadUrl();

	public Properties getLabels();

	public List<BomVersion> getBoms();

	public BomVersion getDefaultBom();

	public List<ArchetypeVersion> getArchetypes();

	public ArchetypeVersion getDefaultArchetype();

	public String getLicense();

}
