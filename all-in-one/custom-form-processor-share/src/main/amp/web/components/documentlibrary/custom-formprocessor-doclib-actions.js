(function () {
    YAHOO.Bubbling.fire("registerAction",
        {
            actionName: "onCreateAttribute",
            fn: function org_alfresco_training_onCreateAttribute(file) {

                var elementId = this.id + "-createAttribute",
                    createAttributeDlg = new Alfresco.module.SimpleDialog(elementId);

                // This is a create scenario, attribute does not exist
                var keys = "create_attribute";

                // Create Attribute Dialog Form Template
                var templateUrl = YAHOO.lang.substitute(
                    Alfresco.constants.URL_SERVICECONTEXT +
                    "components/form?itemKind={itemKind}&itemId={itemId}&mode={mode}&submitType={submitType}&formId={formId}&showCancelButton=true",
                    {
                        itemKind: "attribute",
                        itemId: keys,
                        mode: "create",
                        submitType: "json",
                        formId: "attributeForm"
                    }
                );

                // Intercept before dialog show
                var doBeforeDialogShow = function org_alfresco_training_onCreateAttribute_doBeforeDialogShow(p_form, p_dialog) {
                    Dom.get(elementId + "-form-container_h").innerHTML =
                        this.msg("alfresco.tutorials.doclib.action.createAttribute.dialog.title");
                };

                // Set dialog options and show it
                createAttributeDlg.setOptions(
                    {
                        width: "33em",
                        templateUrl: templateUrl,
                        actionUrl: null,
                        destroyOnHide: true,
                        doBeforeDialogShow: {
                            fn: doBeforeDialogShow,
                            scope: this
                        },
                        onSuccess: {
                            fn: function org_alfresco_training_onCreateAttribute_success(response) {
                                Alfresco.util.PopupManager.displayMessage(
                                    {
                                        text: this.msg("alfresco.tutorials.doclib.action.createAttribute.msg.success")
                                    });
                            },
                            scope: this
                        },
                        onFailure: {
                            fn: function org_alfresco_training_onCreateAttribute_failure(response) {
                                Alfresco.util.PopupManager.displayMessage(
                                    {
                                        text: this.msg("alfresco.tutorials.doclib.action.createAttribute.msg.failure")
                                    });
                            },
                            scope: this
                        }
                    }
                ).show();
            }
        })
})();