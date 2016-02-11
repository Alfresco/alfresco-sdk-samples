/**
 * Copyright (C) 2015 Alfresco Software Limited.
 * <p/>
 * This file is part of the Alfresco SDK Samples project.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.alfresco.tutorial.repo.security.authentication;

import net.sf.acegisecurity.Authentication;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.security.authentication.AbstractAuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A custom authentication mechanism implementation.
 *
 * @author martin.bergljung@alfresco.com
 */
public class CustomAuthenticationComponentImpl extends AbstractAuthenticationComponent {
    private static final Log LOG = LogFactory.getLog(CustomAuthenticationComponentImpl.class);

    /**
     * Some custom properties that could be used to inject a remote login server hostname and port.
     * Not used at the moment but demonstrates property injection in custom authentication component.
     */
    private String remoteAuthenticatorHostname;
    private String remoteAuthenticatorPort;

    public void setRemoteAuthenticatorHostname(String remoteAuthenticatorHostname) {
        this.remoteAuthenticatorHostname = remoteAuthenticatorHostname;
    }

    public void setRemoteAuthenticatorPort(String remoteAuthenticatorPort) {
        this.remoteAuthenticatorPort = remoteAuthenticatorPort;
    }

    public void authenticateImpl(String userName, char[] password) throws AuthenticationException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Login request(" + remoteAuthenticatorHostname + ":" + remoteAuthenticatorPort +
                    ") : [userName=" + userName + "][password=" + String.valueOf(password) + "]");
        }

        // Do your custom authentication here, and then set the current user (in this example we are only allowing
        // john to authenticate successfully, and we don't check pwd)
        // You would typically connect to the remote authentication mechanism to verify username/pwd...
        if (StringUtils.equals(userName, "john") || isGuestUserName(userName) ||
                getDefaultAdministratorUserNames().contains(userName)) {
            setCurrentUser(userName);
        } else {
            String msg = "Login request: username not recognized [userName=" + userName + "]";
            LOG.error(msg);
            throw new AuthenticationException(msg);
        }
    }

    /**
     * The default is not to support token base authentication
     */
    public Authentication authenticate(Authentication token) throws AuthenticationException {
        throw new AlfrescoRuntimeException("Authentication via token not supported");
    }

    /**
     * This authentication component implementation allows guest login
     * @return
     */
    @Override
    protected boolean implementationAllowsGuestLogin() {
        return true;
    }
}
