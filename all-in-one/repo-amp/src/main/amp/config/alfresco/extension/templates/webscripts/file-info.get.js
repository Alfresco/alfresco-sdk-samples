var nodeRef = args["nodeRef"];
var fileNode = search.findNode(nodeRef);

model["name"] = fileNode.name;
model["creator"] = fileNode.properties.creator;
model["createdDate"] = fileNode.properties.created;
model["modifier"] = fileNode.properties.modifier;
model["modifiedDate"] = fileNode.properties.modified;
