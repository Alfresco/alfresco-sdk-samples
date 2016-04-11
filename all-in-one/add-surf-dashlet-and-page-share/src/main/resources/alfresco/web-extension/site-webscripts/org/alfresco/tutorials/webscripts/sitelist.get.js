var siteJSON = {}
var connector = remote.connect("alfresco-api");
var result = connector.get("/-default-/public/alfresco/versions/1/sites");
if (result.status.code == status.STATUS_OK) {
    // Don't use this: var peopleJSON = eval('(' + result + ')');, eval is not secure
    var siteJSON = jsonUtils.toObject(result);
}

model.sites = siteJSON["list"]["entries"];

/*
A call such as :

 http://localhost:8080/alfresco/api/-default-/public/alfresco/versions/1/sites

 Gives back a JSON result looking something like:

 {
 list: {
     pagination: {
         count: 2,
         hasMoreItems: false,
         totalItems: 2,
         skipCount: 0,
         maxItems: 100
     },
     entries: [
         {
             entry: {
                 role: "SiteManager",
                 visibility: "PUBLIC",
                 guid: "dd55bb54-ea21-4a2e-ab61-f609be277d5c",
                 description: "Alfresco knowledge base with manuals, presentations, videos etc",
                 id: "alfresco-kb",
                 title: "Alfresco Knowledge Base"
             }
         },
         {
             entry: {
                 role: "SiteManager",
                 visibility: "PUBLIC",
                 guid: "b4cff62a-664d-4d45-9302-98723eac1319",
                 description: "This is a Sample Alfresco Team site.",
                 id: "swsdp",
                 title: "Sample: Web Site Design Project"
             }
         }
       ]
     }
 }

 */
