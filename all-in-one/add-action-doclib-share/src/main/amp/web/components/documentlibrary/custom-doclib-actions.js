(function () {
    YAHOO.Bubbling.fire("registerAction",
        {
            actionName: "onShowCustomMessage",
            fn: function org_alfresco_training_onShowCustomMessage(file) {
                Alfresco.util.PopupManager.displayMessage(
                    {
                        text: this.msg("alfresco.tutorials.doclib.action.showCustomMessage.text",
                            file.displayName, Alfresco.constants.USERNAME)
                    });
            }
        });

    YAHOO.Bubbling.fire("registerAction",
        {
            actionName: "onActionCallWebScript",
            fn: function org_alfresco_training_onActionCallWebScript(file) {
                this.modules.actions.genericAction(
                    {
                        success: {
                            message: this.msg("alfresco.tutorials.doclib.action.callWebScript.msg.success",
                                file.displayName, Alfresco.constants.USERNAME)
                        },
                        failure: {
                            message: this.msg("alfresco.tutorials.doclib.action.callWebScript.msg.failure",
                                file.displayName, Alfresco.constants.USERNAME)
                        },
                        webscript: {
                            name: "sample/fileinfo?nodeRef={nodeRef}",
                            stem: Alfresco.constants.PROXY_URI,
                            method: Alfresco.util.Ajax.GET,
                            params: {
                                nodeRef: file.nodeRef
                            }
                        },
                        config: {}
                    });
            }
        });
})();