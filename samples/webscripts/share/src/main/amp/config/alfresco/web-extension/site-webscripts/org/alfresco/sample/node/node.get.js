var path = url.templateArgs.store_type + "/" + url.templateArgs.store_id + "/" + url.templateArgs.id;

logger.warn("/example-webscripts/node/" + path);
logger.log("FISK?\n\n");

var connector = remote.connect("alfresco");
var result = connector.get("/example-webscripts/node/" + path);
result = eval('(' + result + ')');
model.node = result.node;

