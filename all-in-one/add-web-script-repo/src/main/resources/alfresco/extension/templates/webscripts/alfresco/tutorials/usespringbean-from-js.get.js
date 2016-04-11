importClass(Packages.org.springframework.web.context.ContextLoader);
importClass(Packages.org.alfresco.service.cmr.repository.NodeService);
var ctx = ContextLoader.getCurrentWebApplicationContext();
var s = ctx.getBean("NodeService", org.alfresco.service.cmr.repository.NodeService);
var stores = s.getStores().toArray();
var storesNames = [];
for (i = 0; i < stores.length; i++) {
    storesNames[i] = stores[i].protocol;
}
model.stores = storesNames;