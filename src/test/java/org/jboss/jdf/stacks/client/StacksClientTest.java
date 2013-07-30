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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.jboss.jdf.stacks.client.StacksClient;
import org.jboss.jdf.stacks.client.StacksClientConfiguration;
import org.jboss.jdf.stacks.model.Stacks;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 * 
 */
public class StacksClientTest {

    /**
     * Setup to print debug messages on Console
     */
    @BeforeClass
    public static void setup() {
        Logger jdfLogger = Logger.getLogger("org.jboss");
        Logger rootLogger = Logger.getLogger("");
        jdfLogger.setLevel(Level.ALL);
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.ALL);
            }
        }
        Assert.assertTrue(jdfLogger.isLoggable(Level.FINEST));
    }

    @Test
    public void testGetStacks() {
        StacksClient stacksClient = new StacksClient();
        Stacks stacks = stacksClient.getStacks();
        Assert.assertNotNull("Stacks should be available", stacks);
    }

    @Test
    public void testGetStacksAfterCacheErased() {
        StacksClient stacksClient = new StacksClient();
        stacksClient.eraseRepositoryCache();
        Stacks stacks = stacksClient.getStacks();
        Assert.assertNotNull("Stacks should be working after cache erased", stacks);
    }

    @Test
    public void testGetLocalCacheFile() {
        StacksClient stacksClient = new StacksClient();
        stacksClient.eraseRepositoryCache();
        File localCacheFile = stacksClient.getLocalCacheFile();
        // Local cached should be erased
        Assert.assertFalse("Cache file should be erased", localCacheFile.exists());
        // Force initilization
        stacksClient.getStacks();
        Assert.assertTrue("Cache should be created after new initialization", localCacheFile.exists());
    }

    @Test
    public void testCacheUse() {
        StacksClient stacksClient = new StacksClient();
        // Get Stacks - Force cache creation
        stacksClient.getStacks();
        long lastChangedCache = stacksClient.getLocalCacheFile().lastModified();
        stacksClient.getStacks();
        long lastChangedCache2 = stacksClient.getLocalCacheFile().lastModified();

        Assert.assertEquals("Cache should be the same", lastChangedCache, lastChangedCache2);
    }

    @Test
    public void testCacheInvalidation() {
        StacksClient stacksClient = new StacksClient();
        stacksClient.getActualConfiguration().setCacheRefreshPeriodInSeconds(-1);
        stacksClient.getStacks();
        long lastChangedCache = stacksClient.getLocalCacheFile().lastModified();
        stacksClient.getStacks();
        long lastChangedCache2 = stacksClient.getLocalCacheFile().lastModified();

        Assert.assertTrue("Cache should be recreated", lastChangedCache != lastChangedCache2);
    }

    @Test
    public void testStacksWrongURL() throws MalformedURLException {
        StacksClient stacksClient = new StacksClient();
        stacksClient.getActualConfiguration().setUrl(new URL("file:///nonexistingfile.yaml"));
        try {
            stacksClient.getStacks();
        } catch (Exception e) {
            Assert.fail("Should fall back to Classpath");
        }
    }

    @Test
    public void testCacheFallbackOfflineUse() throws MalformedURLException {
        StacksClient stacksClient = new StacksClient();
        stacksClient.eraseRepositoryCache();
        stacksClient.getStacks();
        StacksClientConfiguration config = stacksClient.getActualConfiguration();
        config.setCacheRefreshPeriodInSeconds(0);
        config.setOnline(false);
        Stacks stacks = stacksClient.getStacks();
        Assert.assertNotNull("Stacks should be available in forced offline use", stacks);
    }

    @Test
    public void testCacheFallbackFailNoCache() throws MalformedURLException {
        StacksClient stacksClient = new StacksClient();
        stacksClient.eraseRepositoryCache();
        stacksClient.getStacks();
        StacksClientConfiguration config = stacksClient.getActualConfiguration();
        config.setCacheRefreshPeriodInSeconds(0);
        config.setOnline(false);
        stacksClient.eraseRepositoryCache();
        try {
            stacksClient.getStacks();
        } catch (Exception e) {
            Assert.fail("Should fall back to Classpath");
        }
    }

}
