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
package org.alfresco.tutorial.publicapiaccess.model;

/**
 * ACME document metadata
 *
 * @author martin.bergljung@alfresco.com
 */
public class AcmeDocument {
    protected String documentId;
    protected AcmeContentModel.SecurityClassificationLevel securityClassificationLevel;

    public AcmeDocument(String documentId, AcmeContentModel.SecurityClassificationLevel securityClassificationLevel) {
        this.documentId = documentId;
        this.securityClassificationLevel = securityClassificationLevel;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public AcmeContentModel.SecurityClassificationLevel getSecurityClassificationLevel() {
        return securityClassificationLevel;
    }

    public void setSecurityClassificationLevel(AcmeContentModel.SecurityClassificationLevel securityClassificationLevel) {
        this.securityClassificationLevel = securityClassificationLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcmeDocument)) return false;

        AcmeDocument that = (AcmeDocument) o;

        if (documentId != null ? !documentId.equals(that.documentId) : that.documentId != null) return false;
        return securityClassificationLevel == that.securityClassificationLevel;

    }

    @Override
    public int hashCode() {
        int result = documentId != null ? documentId.hashCode() : 0;
        result = 31 * result + (securityClassificationLevel != null ? securityClassificationLevel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AcmeDocument{" +
                "documentId='" + documentId + '\'' +
                ", securityClassificationLevel=" + securityClassificationLevel +
                '}';
    }
}
