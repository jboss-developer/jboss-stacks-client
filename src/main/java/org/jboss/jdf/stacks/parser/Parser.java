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
package org.jboss.jdf.stacks.parser;

import java.io.InputStream;

import org.jboss.jdf.stacks.model.ArchetypeImpl;
import org.jboss.jdf.stacks.model.ArchetypeVersionImpl;
import org.jboss.jdf.stacks.model.BomImpl;
import org.jboss.jdf.stacks.model.BomVersionImpl;
import org.jboss.jdf.stacks.model.MajorReleaseImpl;
import org.jboss.jdf.stacks.model.MinorReleaseImpl;
import org.jboss.jdf.stacks.model.RuntimeImpl;
import org.jboss.jdf.stacks.model.Stacks;
import org.jboss.jdf.stacks.model.StacksImpl;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;

/**
 * This a Parser for the JBoss Stacks YAML version Beta2
 * 
 * @author benevides@redhat.com
 * 
 */
public class Parser {

    public Stacks parse(InputStream is) {
        CustomClassloaderConstructor constructor = new CustomClassloaderConstructor(StacksImpl.class, this.getClass()
                .getClassLoader());
        TypeDescription stackDescription = new TypeDescription(StacksImpl.class);
        stackDescription.putListPropertyType("availableBoms", BomImpl.class);
        stackDescription.putListPropertyType("availableBomVersions", BomVersionImpl.class);
        stackDescription.putListPropertyType("availableRuntimes", RuntimeImpl.class);
        stackDescription.putListPropertyType("availableArchetypes", ArchetypeImpl.class);
        stackDescription.putListPropertyType("availableArchetypeVersions", ArchetypeVersionImpl.class);
        stackDescription.putListPropertyType("minorReleases", MinorReleaseImpl.class);
        stackDescription.putListPropertyType("majorReleases", MajorReleaseImpl.class);
   
        constructor.addTypeDescription(stackDescription);
        Yaml yaml = new Yaml(constructor);
        Stacks data = (Stacks) yaml.load(is);
        return data;
    }


 
}
