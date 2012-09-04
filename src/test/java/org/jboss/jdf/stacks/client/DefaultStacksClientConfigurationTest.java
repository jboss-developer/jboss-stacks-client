/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.jdf.stacks.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import junit.framework.Assert;

import org.jboss.jdf.stacks.client.DefaultStacksClientConfiguration;
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
