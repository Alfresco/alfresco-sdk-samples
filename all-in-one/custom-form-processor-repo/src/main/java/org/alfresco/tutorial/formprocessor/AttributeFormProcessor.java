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
import org.alfresco.repo.forms.processor.FilteredFormProcessor;
import org.alfresco.repo.forms.processor.FormCreationData;
import org.alfresco.repo.forms.processor.node.FormFieldConstants;
import org.alfresco.service.ServiceRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.*;

/**
 * Attribute Form Processor that can handle global keys->value attributes.
 * These attributes are not associated with any content model neither
 * stored as nodes in the repository.
 *
 * @author martin.bergljung@alfresco.com
 */
public class AttributeFormProcessor extends FilteredFormProcessor<AttributeItem, AttributeItem> {
    private static final Log logger = LogFactory.getLog(AttributeFormProcessor.class);

    /**
     * Form field names
     */
    public static final String KEY1_FORM_FIELD = "attribute_key1";
    public static final String KEY2_FORM_FIELD = "attribute_key2";
    public static final String KEY3_FORM_FIELD = "attribute_key3";
    public static final String VALUE_FORM_FIELD = "attribute_value";

    /**
     * Access to all public Alfresco services
     */
    ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    /*
     * @see org.alfresco.repo.forms.processor.node.FilteredFormProcessor#getLogger()
     */
    @Override
    protected Log getLogger() {
        return logger;
    }

    /**
     * @param item the item.getId() will be the attribute keys as a comma separated string for the edit and view
     *             form modes, and "create_attribute" for create form mode.
     * @return the AttributeItem with the separate keys
     * @see org.alfresco.repo.forms.processor.FilteredFormProcessor#getTypedItem(org.alfresco.repo.forms.Item)
     */
    @Override
    protected AttributeItem getTypedItem(Item item) {
        AttributeItem attributeItem = new AttributeItem();

        final String attributeKeys = item.getId();

        if (StringUtils.isNotBlank(attributeKeys) && !attributeKeys.equals("create_attribute")) {
            String[] keys = attributeKeys.split(",");
            attributeItem.setKeys(keys);
        }

        return attributeItem;
    }

    /*
     * Return item type as "attribute"
     *
     * @see org.alfresco.repo.forms.processor.FilteredFormProcessor#getItemType(java.lang.Object)
     */
    @Override
    protected String getItemType(AttributeItem item) {
        return AttributeItem.TYPE;
    }

    /*
     * An attribute is not accessible via a URL so this method is not applicable.
     *
     * @see org.alfresco.repo.forms.processor.FilteredFormProcessor#getItemURI(java.lang.Object)
     */
    @Override
    protected String getItemURI(AttributeItem item) {
        return "";
    }

    /**
     * Creates a data object used by the FormProcessor and FieldProcessors to create {@link Field Fields}
     *
     * @return
     */
    @Override
    protected Object makeItemData(AttributeItem item) {
        if (item.getKeys() != null && item.getKeys().length > 0) {
            // Get the attribute value for key(s)
            Serializable attributeValue = serviceRegistry.getAttributeService().getAttribute(item.getKeys());
            item.setValue(attributeValue);
        }

        // Return the attribute item with optionally the value set
        return item;
    }

    /* Store the attribute and return the stored attribute data.
     * There is no special unique ID generated when we store an attribute so just return it as we stored it.
     *
     * @see org.alfresco.repo.forms.processor.FilteredFormProcessor#internalPersist(java.lang.Object, org.alfresco.repo.forms.FormData)
     */
    @Override
    protected AttributeItem internalPersist(AttributeItem item, FormData data) {
        if (logger.isDebugEnabled()) {
            logger.debug("Persisting form data for: [item=" + item + "][data=" + data + "]");
        }

        // We need to find the form field data for the attribute keys and value.
        // However the form service may have added "prop_", "assoc_", "_added" and "_removed"
        // prefixes/suffixes, which we are not interested in.
        Serializable key1Object = null;
        FormData.FieldData key1FieldData = data.getFieldData(FormFieldConstants.PROP_DATA_PREFIX + KEY1_FORM_FIELD);
        if (key1FieldData == null) {
            throw new FormException("Missing attribute form data: " + KEY1_FORM_FIELD +
                    " [item=" + item + "][data=" + data + "]");
        } else {
            if (key1FieldData.getValue() instanceof Serializable) {
                key1Object = (Serializable)key1FieldData.getValue();
            } else {
                throw new FormException("Value for attribute key1 is not Serializable: " +
                        " [item=" + item + "][data=" + data + "]");
            }
        }
        Serializable key2Object = null;
        Serializable key3Object = null;
        FormData.FieldData key2FieldData = data.getFieldData(FormFieldConstants.PROP_DATA_PREFIX + KEY2_FORM_FIELD);
        if (key2FieldData != null) {
            if (key2FieldData.getValue() instanceof Serializable) {
                key2Object = (Serializable)key2FieldData.getValue();
            } else {
                throw new FormException("Value for attribute key2 is not Serializable: " +
                        " [item=" + item + "][data=" + data + "]");
            }
            FormData.FieldData key3FieldData = data.getFieldData(FormFieldConstants.PROP_DATA_PREFIX + KEY3_FORM_FIELD);
            if (key3FieldData != null) {
                if (key3FieldData.getValue() instanceof Serializable) {
                    key3Object = (Serializable)key3FieldData.getValue();
                } else {
                    throw new FormException("Value for attribute key3 is not Serializable: " +
                            " [item=" + item + "][data=" + data + "]");
                }
            } else {
                // OK, only one key is needed
            }
        } else {
            // OK, only one key is needed
        }
        Serializable valueObject = null;
        FormData.FieldData valuefieldData = data.getFieldData(FormFieldConstants.PROP_DATA_PREFIX + VALUE_FORM_FIELD);
        if (valuefieldData != null && valuefieldData.getValue() != null) {
            if (valuefieldData.getValue() instanceof Serializable) {
                valueObject = (Serializable) valuefieldData.getValue();
            } else {
                throw new FormException("Value for attribute value is not Serializable: " +
                        " [item=" + item + "][data=" + data + "]");
            }
        } else {
            // OK, value can be null
        }

        Serializable[] keys = { key1Object, key2Object, key3Object };

        if (serviceRegistry.getAttributeService().exists(keys)) {
            // Update the attribute
            serviceRegistry.getAttributeService().setAttribute(valueObject, keys);
        } else {
            // Create the attribute
            serviceRegistry.getAttributeService().createAttribute(valueObject, keys);
        }

        return item;
    }

    /**
     * Generate default fields if there were none specified via a form configuration.
     * We know what fields are necessary for creating an attribute (key,key2,key3, and value).
     *
     * @param data
     * @param fieldsToIgnore
     * @return
     * @see org.alfresco.repo.forms.processor.FilteredFormProcessor#generateDefaultFields(org.alfresco.repo.forms.processor.FormCreationData, java.util.List)
     */
    protected List<Field> generateDefaultFields(FormCreationData data, List<String> fieldsToIgnore) {
        List<Field> fields = new ArrayList<Field>();

        // Add the four attribute fields that we need
        fields.add(new AttributeField(KEY1_FORM_FIELD, "Attribute key1 - mandatory", "Key1", true));
        fields.add(new AttributeField(KEY2_FORM_FIELD, "Attribute key2 - can be null", "Key2", false));
        fields.add(new AttributeField(KEY3_FORM_FIELD, "Attribute key3 - can be null", "Key2", false));
        fields.add(new AttributeField(VALUE_FORM_FIELD, "Attribute value - can be null", "Value", false));

        return fields;
    }

    /**
     * Check field definitions coming in from a share-config-custom.xml form configuration.
     * Might be less than four... as only key1 is necessary.
     *
     * @param requestedFields fields requested via form definition
     * @param data
     * @return
     */
    @Override
    protected List<Field> generateSelectedFields(List<String> requestedFields, FormCreationData data) {
        List<String> fieldsToIgnore = Collections.emptyList();

        // First just generate the four required fields (i.e. keys plus value)
        List<Field> fields = this.generateDefaultFields(data, fieldsToIgnore);

        List<Field> results = new ArrayList<Field>();

        for (Field f : fields) {
            if (f.getFieldName().equals(KEY1_FORM_FIELD)) {
                // Mandatory
                results.add(f);
            } else if (requestedFields.contains(f.getFieldName())) {
                // It was selected via form
                results.add(f);
            }
        }

        return results;
    }

    @Override
    protected List<String> getDefaultIgnoredFields() {
        return Collections.emptyList();
    }

}
