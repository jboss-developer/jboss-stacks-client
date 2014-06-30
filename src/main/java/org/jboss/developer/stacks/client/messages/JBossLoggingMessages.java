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

package org.jboss.developer.stacks.client.messages;

import org.jboss.developer.stacks.client.StacksClient;
import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 *
 */
public class JBossLoggingMessages implements StacksMessages {
    
    private static final Logger log = Logger.getLogger(StacksClient.class.getName());

    /* (non-Javadoc)
     * @see org.jboss.jdf.stacks.client.messages.StacksMessages#showInfoMessage(java.lang.String)
     */
    @Override
    public void showInfoMessage(String infoMessage) {
        log.info(infoMessage);
    }

    /* (non-Javadoc)
     * @see org.jboss.jdf.stacks.client.messages.StacksMessages#showWarnMessage(java.lang.String)
     */
    @Override
    public void showWarnMessage(String warnMessage) {
        log.warn(warnMessage);
    }

    /* (non-Javadoc)
     * @see org.jboss.jdf.stacks.client.messages.StacksMessages#showDebugMessage(java.lang.String)
     */
    @Override
    public void showDebugMessage(String debugMessage) {
        log.debug(debugMessage);
    }

    /* (non-Javadoc)
     * @see org.jboss.jdf.stacks.client.messages.StacksMessages#showErrorMessage(java.lang.String)
     */
    @Override
    public void showErrorMessage(String errorMessage) {
        log.error(errorMessage);
    }

    /* (non-Javadoc)
     * @see org.jboss.jdf.stacks.client.messages.StacksMessages#showErrorMessageWithCause(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void showErrorMessageWithCause(String errorMessage, Throwable cause) {
        log.error(errorMessage, cause);
    }

}
