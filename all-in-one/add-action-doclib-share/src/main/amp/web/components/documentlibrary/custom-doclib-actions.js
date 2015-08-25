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
                            callback: {
                                fn: function org_alfresco_training_onActionCallWebScriptSuccess(response) {
                                    Alfresco.util.PopupManager.displayPrompt(
                                        {
                                            title: this.msg("alfresco.tutorials.doclib.action.callWebScript.msg.success"),
                                            text: JSON.stringify(response.json),
                                            buttons: [
                                                {
                                                    text: this.msg("button.ok"),
                                                    handler: function org_alfresco_training_onActionCallWebScriptSuccess_success_ok() {
                                                        this.destroy();
                                                    },
                                                    isDefault: true
                                                },
                                                {
                                                    text: this.msg("button.cancel"),
                                                    handler: function org_alfresco_training_onActionCallWebScriptSuccess_cancel() {
                                                        this.destroy();
                                                    }
                                                }]
                                        });

                                },
                                scope: this
                            }
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