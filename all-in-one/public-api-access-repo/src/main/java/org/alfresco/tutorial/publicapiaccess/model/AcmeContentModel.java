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

import org.alfresco.service.namespace.QName;

import java.util.Arrays;
import java.util.List;

/**
 * The ACME Content Model
 *
 * @author martin.bergljung@alfresco.com
 */
public class AcmeContentModel {
    public final static String NAMESPACE_URI = "http://www.acme.org/model/content/1.0";
    public final static String NAMESPACE_PREFIX = "acme";

    public static QName acme(final String qname) {
        return QName.createQName(NAMESPACE_URI, qname);
    }

    /**
     * Possible security classification levels
     * (i.e. acme:securityClassificationOptions constraint)
     */
    public static enum SecurityClassificationLevel {
        PUBLIC("Complete"),
        CLIENT_CONFIDENTIAL("Client Confidential"),
        COMPANY_CONFIDENTIAL("Company Confidential"),
        STRICTLY_CONFIDENTIAL("Strictly Confidential");

        private final String level;

        SecurityClassificationLevel(final String level) {
            this.level = level;
        }

        @Override
        public String toString() {
            return level;
        }
    }

    /**
     * ACME base document Type
     */
    public static final class DocumentType {
        public static final QName QNAME = acme("document");

        private DocumentType() {
        }

        public static final class Prop {
            private Prop() {
            }

            public static final QName DOCUMENT_ID = acme("documentId");
        }
    }

    /**
     * ACME Contract Type
     */
    public static final class ContractType {
        public static final QName QNAME = acme("contract");

        private ContractType() {
        }

        public static final class Prop {
            private Prop() {
            }

            public static final QName CONTRACT_NAME = acme("contractName");
            public static final QName CONTRACT_ID = acme("contractId");
        }
    }

    /**
     * Web Published aspect
     */
    public static final class WebPublishedAspect {
        public static final QName QNAME = acme("webPublished");

        private WebPublishedAspect() {
        }

        public static final class Prop {
            private Prop() {
            }

            public static final QName PUBLISHED_DATE = acme("publishedDate");
        }
    }

    /**
     * Security classified aspect
     */
    public static final class SecurityClassifiedAspect {
        public static final QName QNAME = acme("securityClassified");

        private SecurityClassifiedAspect() {
        }

        public static final class Prop {
            private Prop() {
            }

            public static final QName SECURITY_CLASSIFICATION = acme("securityClassification");
        }
    }
}
