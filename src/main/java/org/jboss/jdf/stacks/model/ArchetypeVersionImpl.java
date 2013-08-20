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

import java.util.Properties;

public class ArchetypeVersionImpl implements ArchetypeVersion{

    private String id;

    private ArchetypeImpl archetype;

    private String version;

    private String repositoryURL;

    private Properties labels = new Properties();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArchetypeImpl getArchetype() {
        return archetype;
    }

    public void setArchetype(ArchetypeImpl archetype) {
        this.archetype = archetype;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRepositoryURL() {
        if (this.repositoryURL == null){
            return getArchetype().getRepositoryURL();
        }
        return repositoryURL;
    }

    public void setRepositoryURL(String repositoryURL) {
        this.repositoryURL = repositoryURL;
    }

    public Properties getLabels() {
        return labels;
    }

    public void setLabels(Properties labels) {
        this.labels = labels;
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
        if (!(obj instanceof ArchetypeVersionImpl)) {
            return false;
        }
        ArchetypeVersionImpl other = (ArchetypeVersionImpl) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ArchetypeVersion [id=" + id + "]";
    }

}
