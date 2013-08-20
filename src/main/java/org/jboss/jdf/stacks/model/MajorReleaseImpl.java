/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.jdf.stacks.model;

import java.util.ArrayList;
import java.util.List;

public class MajorReleaseImpl implements MajorRelease{
    
    private String name;

    private String version;

    private Runtime recommendedRuntime;

    private List<MinorRelease> minorReleases = new ArrayList<MinorRelease>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Runtime getRecommendedRuntime() {
        return recommendedRuntime;
    }

    public void setRecommendedRuntime(Runtime recommendedRuntime) {
        this.recommendedRuntime = recommendedRuntime;
    }

    public List<MinorRelease> getMinorReleases() {
        return minorReleases;
    }

    public void setMinorReleases(List<MinorRelease> minorReleases) {
        this.minorReleases = minorReleases;
    }
}
