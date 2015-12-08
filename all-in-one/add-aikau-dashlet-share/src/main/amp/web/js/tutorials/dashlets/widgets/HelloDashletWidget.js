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
 * Aikau Dashlet displaying documents classified with the
 * 'acme:document' type.
 *
 * @module tutorials/dashlets/widgets/HelloDashletWidget
 * @extends dijit/_WidgetBase
 * @author Martin Bergljung
 */
define(["dojo/_base/declare",
        "dijit/_WidgetBase",
        "alfresco/core/Core",
        "dijit/_TemplatedMixin",
        "dojo/text!./templates/HelloDashletWidget.html"
    ],
    function(declare, _Widget, Core, _Templated, template) {
        return declare([_Widget, Core, _Templated], {
            templateString: template,
            i18nRequirements: [ {i18nFile: "./i18n/HelloDashletWidget.properties"} ],
            cssRequirements: [{cssFile:"./css/HelloDashletWidget.css"}],

            buildRendering: function org_tutorials_dashlets_HelloDashletWidget__buildRendering() {
                this.greeting = this.message('hello-label');

                this.inherited(arguments);

            }
        });
    });