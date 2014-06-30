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
package org.jboss.developer.stacks.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import junit.framework.Assert;

import org.jboss.developer.stacks.client.DefaultStacksClientConfiguration;
import org.jboss.developer.stacks.client.StacksClientConfiguration;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 * 
 */
public class DefaultStacksClientConfigurationTest {

    @Before
    public void cleanSystemProperty() {
        System.clearProperty(DefaultStacksClientConfiguration.REPO_PROPERTY);
    }

    @Test
    public void testSystemProperty() {
        try {
            System.setProperty(DefaultStacksClientConfiguration.REPO_PROPERTY, "someValue");
            new DefaultStacksClientConfiguration().getUrl();
            Assert.fail("Should fail with a not valid URL from system Property");
        } catch (IllegalStateException e) {
            Assert.assertNotNull("Should fail with a not valid URL from system Property", e);
        }finally{
            //Reset System property after this test
            System.getProperties().remove(DefaultStacksClientConfiguration.REPO_PROPERTY);
        }
    }

    @Test
    public void testFileConfig() throws IOException {
        URL url = new DefaultStacksClientConfiguration().getUrl();
        Assert.assertEquals("URL Should be used from internal config file", url.toString(), getPropertyFromConfig());
    }

    @Test
    public void testProperty() throws IOException {
        URL url = new URL("http://www.jboss.org/jdf");
        StacksClientConfiguration clientConfiguration = new DefaultStacksClientConfiguration();
        clientConfiguration.setUrl(url);
        Assert.assertEquals("URL should be used from programatic config", url, clientConfiguration.getUrl());
    }

    /**
     * @return
     * @throws IOException
     */
    private String getPropertyFromConfig() throws IOException {
        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream("config.properties");
            Properties p = new Properties();
            p.load(is);
            return p.getProperty(DefaultStacksClientConfiguration.REPO_PROPERTY);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
