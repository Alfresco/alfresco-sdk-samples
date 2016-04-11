<import resource="classpath:alfresco/enterprise/webscripts/org/alfresco/enterprise/repository/admin/admin-common.lib.js">

/**
 * Repository Admin Console
 * 
 * Tutorial GET method
 */

/* Example to retrieve specific bean attributes and populate the model manually:
function main(bean)
{
   // NOTE: populate the model to be passed to the template
   //       list the attributes you want to retrieve
   model.attributes = Admin.getMBeanAttributes(
      bean,
      ["Subject", "LicenseMode", "Issued", "RemainingDays", "HeartBeatDisabled"]
   );
   
   // Always supply the console tools to the template - pass in current WebScript tool ID
   // NOTE: remember to change this to your webscript ID e.g. "admin-example"
   // NOTE: ensure your webscript URL is of the form: /enterprise/admin/admin-example
   model.tools = Admin.getConsoleTools("admin-tutorial");
}

// NOTE: pass in the name of the MBean
main("Alfresco:Name=License");
*/

/* Tutorial to retrieve bean attributes and automatically populate the model: */
Admin.initModel(
   "Alfresco:Name=License",
   ["Subject", "LicenseMode", "Issued", "RemainingDays"],
   "admin-tutorial"
);
