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

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

public class CustomClassloaderConstructor extends Constructor {

    private ClassLoader loader = CustomClassLoaderConstructor.class.getClassLoader();

    public CustomClassloaderConstructor(ClassLoader cLoader) {
        this(Object.class, cLoader);
    }

    public CustomClassloaderConstructor(Class<? extends Object> theRoot, ClassLoader theLoader) {
        super(theRoot);
        if (theLoader == null) {
            throw new NullPointerException("Loader must be provided.");
        }
        this.loader = theLoader;
    }

    @Override
    protected Class<?> getClassForName(String name) throws ClassNotFoundException {
        return Class.forName(name, true, loader);
    }
}
