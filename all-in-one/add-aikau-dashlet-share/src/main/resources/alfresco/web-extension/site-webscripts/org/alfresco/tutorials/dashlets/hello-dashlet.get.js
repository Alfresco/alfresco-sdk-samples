model.jsonModel = {
    rootNodeId: args.htmlid,
    pubSubScope: instance.object.id,
    /* Include Aikau services here, for example :
    services: [
        { name: "alfresco/services/ReportService" },
        { name: "alfresco/services/NavigationService" }
    ],*/
    widgets: [
        {
            id: "HELLO_DASHLET",
            name: "acmedashlets/HelloDashlet"
        }
    ]
};

