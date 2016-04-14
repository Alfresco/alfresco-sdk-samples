// Setup Alfresco's home page to be loaded in the Web View Dashlet
// This controller is run after the out-of-the-box controller
//
if (model.isDefault == true)
{
    model.widgets[0].options.webviewTitle = "Alfresco!";
    model.widgets[0].options.webviewURI = "http://www.alfresco.com";
    model.widgets[0].options.isDefault = false;
}

