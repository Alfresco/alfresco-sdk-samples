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
package org.alfresco.tutorial.publicapiaccess.webscript;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.tutorial.publicapiaccess.model.AcmeContract;
import org.alfresco.tutorial.publicapiaccess.service.AcmeContentService;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import java.io.IOException;

import static org.alfresco.tutorial.publicapiaccess.model.AcmeContentModel.*;

/**
 * A Web Script that is only used to test the ACME Content Service interface
 *
 * @author martin.bergljung@alfresco.com
 * @version 1.0
 */
public class TestFoundationServicesWebScript extends AbstractWebScript {
    private AcmeContentService acmeContentService;

    public void setAcmeContentService(AcmeContentService acmeContentService) {
        this.acmeContentService = acmeContentService;
    }

    @Override
    public void execute(WebScriptRequest webScriptRequest, WebScriptResponse webScriptResponse) throws IOException {
        AcmeContract contractMetadata = new AcmeContract("DOC001", SecurityClassificationLevel.COMPANY_CONFIDENTIAL,
                "C001", "Contract A");
        NodeRef contractNodeRef = acmeContentService.createContractFile(
                "ContractA.txt", "This is my first contract...", contractMetadata);

        acmeContentService.applyWebPublishedAspect(contractNodeRef);

        webScriptResponse.getWriter().write("Done with the ACME Content Service tests!");
    }
}
