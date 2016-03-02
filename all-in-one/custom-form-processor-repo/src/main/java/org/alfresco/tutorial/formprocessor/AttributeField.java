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

package org.alfresco.tutorial.formprocessor;

import org.alfresco.repo.forms.*;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.namespace.QName;

/**
 *  This class represents a {@link Field} for an attribute form.
 *
 * @author martin.bergljung@alfresco.com
 */
public class AttributeField implements Field {
    private FieldDefinition fieldDef;
    private String name;

    public AttributeField(String name, String description, String displayLabel, boolean mandatory) {
        this.name = name;

        // All attribute fields are text input
        QName type = DataTypeDefinition.TEXT;

        // Re-use the content model property field definition
        PropertyFieldDefinition propDef = new PropertyFieldDefinition(this.name, type.getLocalName());
        propDef.setMandatory(mandatory);
        this.fieldDef = propDef;

        // Set form field display label
        this.fieldDef.setDescription(description);
        this.fieldDef.setLabel(displayLabel);
        this.fieldDef.setDataKeyName(this.name);
    }

    @Override
    public FieldDefinition getFieldDefinition() {
        return this.fieldDef;
    }

    @Override
    public String getFieldName() {
        return this.name;
    }

    @Override
    public Object getValue() {
        // Setting attribute forms fields to null, no edit support
        return null;
    }
}
