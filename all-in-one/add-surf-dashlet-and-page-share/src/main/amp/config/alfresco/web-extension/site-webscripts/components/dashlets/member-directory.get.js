// Get args from the Share page URL
var filterValue = page.url.args["filter"];
var connector = remote.connect("alfresco");
var peopleJSONString = connector.get("/api/people?filter=" + filterValue);

// create json object from data
// Don't use this: var peopleJSON = eval('(' + peopleJSONString + ')');, eval is not secure
var peopleJSON = jsonUtils.toObject(peopleJSONString);
model.people = peopleJSON["people"];