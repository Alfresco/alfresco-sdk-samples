/**
 * MyCompany root namespace.
 *
 * @namespace MyCompany
 */
if (typeof MyCompany == "undefined" || !MyCompany) {
    var MyCompany = {};
}

/**
 * MyCompany dashlet namespace.
 *
 * @namespace MyCompany.dashlet
 */
if (typeof MyCompany.dashlet == "undefined" || !MyCompany.dashlet) {
    MyCompany.dashlet = {};
}

/**
 * Sample Hello World dashboard Surf component.
 *
 * @namespace MyCompany.dashlet
 * @class MyCompany.dashlet.HelloWorld
 * @author
 */
(function () {
    /**
     * YUI Library aliases
     */
    var Dom = YAHOO.util.Dom,
        Event = YAHOO.util.Event;

    /**
     * Alfresco Slingshot aliases
     */
    var $html = Alfresco.util.encodeHTML,
        $combine = Alfresco.util.combinePaths;


    /**
     * Dashboard HelloWorld constructor.
     *
     * @param {String} htmlId The HTML id of the parent element
     * @return {MyCompany.dashlet.HelloWorld} The new component instance
     * @constructor
     */
    MyCompany.dashlet.HelloWorld = function HelloWorld_constructor(htmlId) {
        return MyCompany.dashlet.HelloWorld.superclass.constructor.call(this, "MyCompany.dashlet.HelloWorld", htmlId);
    };

    /**
     * Extend from Alfresco.component.Base and add class implementation
     */
    YAHOO.extend(MyCompany.dashlet.HelloWorld, Alfresco.component.Base,
        {
            /**
             * Object container for initialization options
             *
             * @property options
             * @type object
             */
            options: {},

            /**
             * Fired by YUI when parent element is available for scripting
             *
             * @method onReady
             */
            onReady: function HelloWorld_onReady() {
                this.widgets.testButton = Alfresco.util.createYUIButton(this, "testButton", this.onButtonClick);
            },

            /**
             * Button click event handler
             *
             * @method onButtonClick
             */
            onButtonClick: function HelloWorld_onButtonClick(e) {
                Alfresco.util.PopupManager.displayMessage(
                    {
                        text: "Button clicked in Hello World Surf Dashlet!"
                    });
            }
        });
})();