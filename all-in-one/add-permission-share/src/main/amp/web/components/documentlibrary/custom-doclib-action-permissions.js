(function () {
    YAHOO.Bubbling.fire("registerAction",
        {
            actionName: "onPublishToWeb",
            fn: function org_alfresco_training_onPublishToWeb(file) {
                Alfresco.util.PopupManager.displayMessage(
                    {
                        text: this.msg("alfresco.tutorials.permissions.publishToWeb.text", file.displayName)
                    });
            }
        });
})();