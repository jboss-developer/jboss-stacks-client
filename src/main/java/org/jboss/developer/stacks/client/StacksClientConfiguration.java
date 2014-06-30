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

import java.net.URL;

/**
 * A configuration for the stacks.
 * 
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 */
public interface StacksClientConfiguration {
    
    public static final String REPO_PROPERTY = "jboss-developer.stacks.client.repo";
    public static final String PRESTACKS_REPO_PROPERTY = "jboss-developer.prestacks.client.repo";


    /**
     * The URL to connect to.
     * 
     * @return the url
     */
    public URL getUrl();

    /**
     * Set the URL to connect to
     * 
     * @param url
     */
    public void setUrl(URL url);

    /**
     * The proxy host.
     * 
     * @return the proxy host or {@code null} if no proxy is specified
     */
    public String getProxyHost();

    /**
     * Set the proxy host
     * 
     * @param proxyHost
     */
    public void setProxyHost(String proxyHost);

    /**
     * The proxy port.
     * 
     * @return the proxy port or 0 if not specified
     */
    public int getProxyPort();

    /**
     * Set the proxy port
     * 
     * @param proxyPort
     */
    public void setProxyPort(int proxyPort);

    /**
     * The proxy connection user.
     * 
     * @return the user or {@code null} if not specified
     */
    public String getProxyUser();

    /**
     * Set the proxy connection user.
     * 
     * @param proxyUser
     */
    public void setProxyUser(String proxyUser);

    /**
     * The proxy connection password.
     * 
     * @return the password or {@code null} if not specified
     */
    public String getProxyPassword();

    /**
     * Set the proxy connection password
     * 
     * @param proxyPassword
     */
    public void setProxyPassword(String proxyPassword);

    /**
     * If this client is using in a online/offline environment
     * 
     * @return
     */
    public boolean isOnline();

    /**
     * Set online/offline environment
     * 
     * @param online
     */
    public void setOnline(boolean online);

    /**
     * Refresh period of local cache. Default to 86400 seconds (24 hours). Use -1 to disable cache.
     * 
     * @return
     */
    public int getCacheRefreshPeriodInSeconds();

    /**
     * Set the refresh period of local cache in seconds
     * 
     * @param seconds
     */
    public void setCacheRefreshPeriodInSeconds(int seconds);

}
