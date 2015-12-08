/**
 * Copyright (C) 2015 Alfresco Software Limited.
 *
 * This file is part of the Alfresco SDK Samples project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Aikau Dashlet displaying an Alfresco logo and a Hello message in a vertical layout.
 *
 * @module tutorials/dashlets/HelloDashlet
 * @extends alfresco/dashlets/Dashlet
 * @author Martin Bergljung
 */
define(["dojo/_base/declare",
        "alfresco/core/Core",
        "alfresco/core/I18nUtils",
        "alfresco/dashlets/Dashlet"],
    function(declare, AlfCore, I18nUtils, Dashlet) {

        return declare([Dashlet], {
            /*
             * Add padding to the body.
             * smallpad (4px padding), mediumpad (10px padding - recommended) and largepad (16px padding)
             */
            additionalCssClasses: "mediumpad",

            /**
             * Explicit height in pixels of the Dashlet body.
             */
            bodyHeight: 200,

            /**
             * Id that will be used to store properties for this Dashlet.
             * i.e. the Dashlet height when using the resizer.
             */
            componentId: "component.hello-dashlet",

            /**
             * The i18n scope to use for this Dashlet.
             */
            i18nScope: "tutorials.dashlets.HelloDashlet",

            /**
             * An array of the i18n files to use with this Dashlet.
             */
            i18nRequirements: [{i18nFile: "./i18n/HelloDashlet.properties"}],

            /**
             * The widgets to be acting as title bar actions.
             */
            widgetsForTitleBarActions: [
                {
                    id: "HELLO_DASHLET_ACTIONS",
                    name: "alfresco/html/Label",
                    config: {
                        label: "Title-bar actions"
                    }
                }
            ],

            /**
             * The widgets to be placed in the top toolbar.
             */
            widgetsForToolbar: [
                {
                    id: "HELLO_DASHLET_TOOLBAR",
                    name: "alfresco/html/Label",
                    config: {
                        label: "Toolbar"
                    }
                }
            ],

            /**
             * The widgets to be placed in the second toolbar.
             */
            widgetsForToolbar2: [
                {
                    id: "HELLO_DASHLET_TOOLBAR2",
                    name: "alfresco/html/Label",
                    config: {
                        label: "Toolbar2"
                    }
                }
            ],

            /**
             * The widgets to be placed in the body of the dashlet.
             */
            widgetsForBody: [
                {
                    id: "HELLO_DASHLET_VERTICAL_LAYOUT",
                    name: "alfresco/layout/VerticalWidgets",
                    config: {
                        widgetWidth: 50,
                        widgets: [
                            {
                                id: "HELLO_DASHLET_ALFRESCO_LOGO_WIDGET",
                                name: "alfresco/logo/Logo",
                                config: {
                                    logoClasses: "alfresco-logo-only"
                                }
                            },
                            {
                                id: "HELLO_DASHLET_HELLO_WIDGET",
                                name: "acmewidgets/HelloDashletWidget"
                            }
                        ]
                    }
                }
            ]

        });
    });