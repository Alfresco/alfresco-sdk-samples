var nodeRef = url.templateArgs.store_type + "://" + url.templateArgs.store_id + "/" + url.templateArgs.id;

var node = search.findNode(nodeRef);
if (node) {
  node.remove();
  model.success = true;
  model.message = "OK";
} else {
  model.success = false;
  model.message = "Node not found";
}

