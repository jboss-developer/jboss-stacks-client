package org.jboss.jdf.stacks.model;

import java.util.List;

public interface MajorRelease {

	public String getName();

	public String getVersion();

	public RuntimeImpl getRecommendedRuntime();

	public List<MinorRelease> getMinorReleases();

}
