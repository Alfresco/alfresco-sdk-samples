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
 *
 * ACME Contract metadata
 *
 * @author martin.bergljung@alfresco.com
 */
public class AcmeContract extends AcmeDocument {
    protected String contractId;
    protected String contractName;

    public AcmeContract(String documentId, AcmeContentModel.SecurityClassificationLevel securityClassificationLevel,
                        String contractId, String contractName) {
        super(documentId, securityClassificationLevel);

        this.contractId = contractId;
        this.contractName = contractName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcmeContract)) return false;
        if (!super.equals(o)) return false;

        AcmeContract that = (AcmeContract) o;

        if (contractId != null ? !contractId.equals(that.contractId) : that.contractId != null) return false;
        return !(contractName != null ? !contractName.equals(that.contractName) : that.contractName != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (contractId != null ? contractId.hashCode() : 0);
        result = 31 * result + (contractName != null ? contractName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AcmeContract{" +
                "documentId='" + documentId + '\'' +
                ", securityClassificationLevel=" + securityClassificationLevel +
                ", contractId='" + contractId + '\'' +
                ", contractName='" + contractName + '\'' +
                '}';
    }
}

