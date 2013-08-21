package org.jboss.jdf.stacks.model;

import java.util.Properties;

public interface BomVersion {

	public String getId();

	public Bom getBom();

	public String getVersion();

	public Properties getLabels();

}
