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
import java.util.Properties;

public class RuntimeImpl implements Runtime{

    private String id;

    private String name;

    private String groupId;

    private String artifactId;

    private String version;

    private String url;

    private String downloadUrl;

    private Properties labels = new Properties();

    private List<BomVersion> boms = new ArrayList<BomVersion>();

    private BomVersion defaultBom;

    private List<ArchetypeVersion> archetypes = new ArrayList<ArchetypeVersion>();

    private ArchetypeVersion defaultArchetype;

    private String license;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Properties getLabels() {
        return labels;
    }

    public void setLabels(Properties labels) {
        this.labels = labels;
    }

    public List<BomVersion> getBoms() {
        return boms;
    }

    public void setBoms(List<BomVersion> boms) {
        this.boms = boms;
    }

    public BomVersion getDefaultBom() {
        return defaultBom;
    }

    public void setDefaultBom(BomVersion defaultBom) {
        this.defaultBom = defaultBom;
    }

    public List<ArchetypeVersion> getArchetypes() {
        return archetypes;
    }

    public void setArchetypes(List<ArchetypeVersion> archetypes) {
        this.archetypes = archetypes;
    }

    public ArchetypeVersion getDefaultArchetype() {
        return defaultArchetype;
    }

    public void setDefaultArchetype(ArchetypeVersion defaultArchetype) {
        this.defaultArchetype = defaultArchetype;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof RuntimeImpl)) {
            return false;
        }
        RuntimeImpl other = (RuntimeImpl) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Runtime [id=" + id + "]";
    }

}
