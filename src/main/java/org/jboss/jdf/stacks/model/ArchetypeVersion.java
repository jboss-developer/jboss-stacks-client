package org.jboss.jdf.stacks.model;

import java.util.Properties;

public interface ArchetypeVersion {

	public String getId();

	public Archetype getArchetype();

	public String getVersion();

	public String getRepositoryURL();

	public Properties getLabels();

}
