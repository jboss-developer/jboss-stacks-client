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
package org.jboss.jdf.stacks.client;

import static org.jboss.jdf.stacks.client.StacksClientConfiguration.PRESTACKS_REPO_PROPERTY;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import junit.framework.Assert;

import org.jboss.jdf.stacks.model.Stacks;
import org.jboss.jdf.stacks.parser.Parser;
import org.junit.Test;

public class ParserTest {

    @Test
    public void test() throws FileNotFoundException {
        InputStream is = this.getClass().getResourceAsStream("/stacks.yaml");
        Parser p = new Parser();
        Stacks stacks = p.parse(is);
        Assert.assertTrue(stacks.getAvailableBoms().size() > 10);
    }

    @Test
    public void testParsePreStacks() throws IOException {
        StacksClient sc = new StacksClient();
        sc.getActualConfiguration().setUrl(new URL(getPropertyFromConfig(PRESTACKS_REPO_PROPERTY)));
        Stacks preStacks = sc.getStacks();
        Assert.assertEquals(preStacks.getAvailableRuntimes().size(), 11);
    }

    private String getPropertyFromConfig(String property) throws IOException {
        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream("/org/jboss/jdf/stacks/client/config.properties");
            Properties p = new Properties();
            p.load(is);
            return p.getProperty(property);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
