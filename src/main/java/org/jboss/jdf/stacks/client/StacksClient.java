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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.jdf.stacks.client.messages.JBossLoggingMessages;
import org.jboss.jdf.stacks.client.messages.StacksMessages;
import org.jboss.jdf.stacks.model.Stacks;
import org.jboss.jdf.stacks.parser.Parser;

/**
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 * 
 */
public class StacksClient {

    private StacksClientConfiguration actualConfiguration;

    private StacksMessages msg;

    public StacksClient() {
        this(new DefaultStacksClientConfiguration());
    }

    public StacksClient(StacksClientConfiguration configuration) {
        this(configuration, new JBossLoggingMessages());
    }

    public StacksClient(StacksClientConfiguration configuration, StacksMessages stacksMessagesImpl) {
        this.msg = stacksMessagesImpl;
        this.actualConfiguration = configuration;
    }

    private Stacks initializeStacks() {
        InputStream inputStream = null;
        try {
            // Retrieve inputStream (local cache or remote)
            inputStream = getStacksInputStream();
            Stacks stacks = null;
            if (inputStream != null) {
                stacks = new Parser().parse(inputStream);
            }
            return stacks;
        } catch (FileNotFoundException e) {
            msg.showErrorMessageWithCause("FileNotFoundException", e);
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    msg.showErrorMessageWithCause("Something bad happened when closing the inputstream", e);
                }
            }
        }
    }

    /**
     * @return the stacks
     */
    public Stacks getStacks() {
        return initializeStacks();
    }

    /**
     * @return the actualConfiguration
     */
    public StacksClientConfiguration getActualConfiguration() {
        return actualConfiguration;
    }

    /**
     * @return
     * @throws FileNotFoundException
     * 
     */
    private InputStream getStacksInputStream() throws FileNotFoundException {
        InputStream repoStream = getCachedRepoStream(false);
        URL url = actualConfiguration.getUrl();
        // if cache expired
        if (repoStream == null && actualConfiguration.isOnline()) {
            msg.showDebugMessage("Local cache file " + getLocalCacheFile() + " doesn't exist or cache has been expired");
            try {
                msg.showDebugMessage("Retrieving Stacks from Remote repository " + url);
                repoStream = retrieveStacksFromRemoteRepository(url);
                setCachedRepoStream(repoStream);
                msg.showDebugMessage("Forcing the use of local cache after download file without error from " + url);
                repoStream = getCachedRepoStream(true);
            } catch (Exception e) {
                msg.showWarnMessage("It was not possible to contact the repository at " + url + " . Cause " + e.getMessage());
                msg.showWarnMessage("Falling back to cache!");
                repoStream = getCachedRepoStream(true);
            }
        }
        // If the Repostream stills empty after falling back to cache
        if (repoStream == null) {
            msg.showWarnMessage("Cache empty. Falling back to embed file");
            return this.getClass().getResourceAsStream("/stacks.yaml");
        } else {
            return repoStream;
        }

    }

    private InputStream retrieveStacksFromRemoteRepository(final URL url) throws Exception {
        if (url.getProtocol().startsWith("http")) {
            HttpGet httpGet = new HttpGet(url.toURI());
            DefaultHttpClient client = new DefaultHttpClient();
            configureProxy(client);
            HttpResponse httpResponse = client.execute(httpGet);
            switch (httpResponse.getStatusLine().getStatusCode()) {
                case 200:
                    msg.showDebugMessage("Connected to repository! Getting available Stacks");
                    break;

                case 404:
                    msg.showErrorMessage("Failed! (Stacks file not found: " + url + ")");
                    return null;

                default:
                    msg.showErrorMessage("Failed! (server returned status code: "
                            + httpResponse.getStatusLine().getStatusCode());
                    return null;
            }
            return httpResponse.getEntity().getContent();
        } else if (url.getProtocol().startsWith("file")) {
            return new FileInputStream(new File(url.toURI()));
        }
        return null;
    }

    private void configureProxy(DefaultHttpClient client) {
        if (actualConfiguration.getProxyHost() != null && !actualConfiguration.getProxyHost().isEmpty()) {
            String proxyHost = actualConfiguration.getProxyHost();
            int proxyPort = actualConfiguration.getProxyPort();
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            String proxyUsername = actualConfiguration.getProxyUser();
            if (proxyUsername != null && !proxyUsername.isEmpty()) {
                String proxyPassword = actualConfiguration.getProxyPassword();
                AuthScope authScope = new AuthScope(proxyHost, proxyPort);
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(proxyUsername, proxyPassword);
                client.getCredentialsProvider().setCredentials(authScope, credentials);
            }
        }
    }

    /**
     * Get the local file cache for each repository url
     * 
     * @return
     */
    public File getLocalCacheFile() {
        // Remove no word character from the repo url
        String repo = actualConfiguration.getUrl().toString().replaceAll("[^a-zA-Z_0-9]", "");
        return new File(System.getProperty("java.io.tmpdir"), repo + "stacks.yaml");
    }

    private InputStream getCachedRepoStream(final boolean force) throws FileNotFoundException {
        final String logmessage = "Local file %1s %2s used! Reason: Force:[%3b] - Online:[%4b] - LastModification: %5d/%6d";
        File localCacheFile = getLocalCacheFile();
        if (localCacheFile.exists()) {
            int cacheSeconds = actualConfiguration.getCacheRefreshPeriodInSeconds();
            boolean online = actualConfiguration.isOnline();
            long cachedvalidity = 1000 * cacheSeconds;
            long lastModified = localCacheFile.lastModified();
            long timeSinceLastModification = System.currentTimeMillis() - lastModified;
            // if online, consider the cache valid until it expires
            if (force || !online || timeSinceLastModification <= cachedvalidity) {
                msg.showDebugMessage(String.format(logmessage, localCacheFile, "was", force, online, timeSinceLastModification,
                        cachedvalidity));
                return new FileInputStream(localCacheFile);
            }
            msg.showDebugMessage(String.format(logmessage, localCacheFile, "was not", force, online, timeSinceLastModification,
                    cachedvalidity));
        }
        return null;
    }

    private void setCachedRepoStream(final InputStream stream) throws IOException {
        File localCacheFile = getLocalCacheFile();
        msg.showDebugMessage("Content stored at " + localCacheFile);
        if (!localCacheFile.exists()) {
            localCacheFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(localCacheFile);

        int i = 0;
        while ((i = stream.read()) != -1) {
            fos.write(i);
        }
        fos.close();
    }

    /**
     * This will drop the local cache to force an online update.
     */
    public void eraseRepositoryCache() {
        File localFile = getLocalCacheFile();
        localFile.delete();
        msg.showDebugMessage("Cache erased");
    }
}
