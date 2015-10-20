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

package org.alfresco.tutorial.repoaction;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Alfresco Repository Action that can send emails with file attachments.
 *
 * @author martin.bergljung@alfresco.com
 */
public class SendAsEmailActionExecuter extends ActionExecuterAbstractBase {
    private static Log logger = LogFactory.getLog(SendAsEmailActionExecuter.class);

    public static final String PARAM_EMAIL_TO_NAME = "to";
    public static final String PARAM_EMAIL_SUBJECT_NAME = "subject";
    public static final String PARAM_EMAIL_BODY_NAME = "body_text";

    /**
     * The Alfresco Service Registry that gives access to all public content services in Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
        for (String s : new String[]{PARAM_EMAIL_TO_NAME, PARAM_EMAIL_SUBJECT_NAME, PARAM_EMAIL_BODY_NAME}) {
            paramList.add(new ParameterDefinitionImpl(s, DataTypeDefinition.TEXT, true, getParamDisplayLabel(s)));
        }
    }

    @Override
    protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
        if (serviceRegistry.getNodeService().exists(actionedUponNodeRef) == true) {
            // Get the email properties entered via Share Form
            String to = (String) action.getParameterValue(PARAM_EMAIL_TO_NAME);
            String subject = (String) action.getParameterValue(PARAM_EMAIL_SUBJECT_NAME);
            String body = (String) action.getParameterValue(PARAM_EMAIL_BODY_NAME);

            // Get document filename
            Serializable filename = serviceRegistry.getNodeService().getProperty(
                    actionedUponNodeRef, ContentModel.PROP_NAME);
            if (filename == null) {
                throw new AlfrescoRuntimeException("Document filename is null");
            }
            String documentName = (String) filename;

            try {
                // Create mail session
                Properties mailServerProperties = new Properties();
                mailServerProperties = System.getProperties();
                mailServerProperties.put("mail.smtp.host", "localhost");
                mailServerProperties.put("mail.smtp.port", "2525");
                Session session = Session.getDefaultInstance(mailServerProperties, null);
                session.setDebug(false);

                // Define message
                Message message = new MimeMessage(session);
                String fromAddress = "training@alfresco.com";
                message.setFrom(new InternetAddress(fromAddress));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);

                // Create the message part with body text
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(body);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // Create the Attachment part
                //
                //  Get the document content bytes
                byte[] documentData = getDocumentContentBytes(actionedUponNodeRef, documentName);
                if (documentData == null) {
                    throw new AlfrescoRuntimeException("Document content is null");
                }
                //  Attach document
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(
                        documentData, new MimetypesFileTypeMap().getContentType(documentName))));
                messageBodyPart.setFileName(documentName);
                multipart.addBodyPart(messageBodyPart);

                // Put parts in message
                message.setContent(multipart);

                // Send mail
                Transport.send(message);

                // Set status on node as "sent via email"
                Map<QName, Serializable> properties = new HashMap<QName, Serializable>();
                properties.put(ContentModel.PROP_ORIGINATOR, fromAddress);
                properties.put(ContentModel.PROP_ADDRESSEE, to);
                properties.put(ContentModel.PROP_SUBJECT, subject);
                properties.put(ContentModel.PROP_SENTDATE, new Date());
                serviceRegistry.getNodeService().addAspect(actionedUponNodeRef, ContentModel.ASPECT_EMAILED, properties);
            } catch (MessagingException me) {
                me.printStackTrace();
                throw new AlfrescoRuntimeException("Could not send email: " + me.getMessage());
            }
        }
    }

    /**
     * Get the content bytes for the document with passed in node reference.
     *
     * @param documentRef      the node reference for the document we want the content bytes for
     * @param documentFilename document filename for logging
     * @return a byte array containing the document content or null if not found
     */
    private byte[] getDocumentContentBytes(NodeRef documentRef, String documentFilename) {
        // Get a content reader
        ContentReader contentReader = serviceRegistry.getContentService().getReader(
                documentRef, ContentModel.PROP_CONTENT);
        if (contentReader == null) {
            logger.error("Content reader was null [filename=" + documentFilename + "][docNodeRef=" + documentRef + "]");

            return null;
        }

        // Get the document content bytes
        InputStream is = contentReader.getContentInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] documentData = null;

        try {
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = is.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            documentData = bos.toByteArray();
        } catch (IOException ioe) {
            logger.error("Content could not be read: " + ioe.getMessage() +
                    " [filename=" + documentFilename + "][docNodeRef=" + documentRef + "]");
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable e) {
                    logger.error("Could not close doc content input stream: " + e.getMessage() +
                            " [filename=" + documentFilename + "][docNodeRef=" + documentRef + "]");
                }
            }
        }

        return documentData;
    }
}
