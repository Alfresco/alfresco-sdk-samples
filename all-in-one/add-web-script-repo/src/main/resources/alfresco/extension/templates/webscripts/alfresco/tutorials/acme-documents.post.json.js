// Get the POSTed JSON data
var name = json.get("name");
var docId = json.get("docId");
var securityClass = json.get("securityClass");
var content = json.get("content");

// Create the new ACME Text Document
var acmeTextDocFileName = name;
var guestHomeFolder = companyhome.childByNamePath("Guest Home");
var acmeTextDocFile = guestHomeFolder.childByNamePath(acmeTextDocFileName);
if (acmeTextDocFile == null) {
    var contentType = "acme:document";
    var properties = new Array();
    properties['acme:documentId'] = docId;
    properties['acme:securityClassification'] = securityClass;
    acmeTextDocFile = guestHomeFolder.createNode(acmeTextDocFileName, contentType, properties);
    acmeTextDocFile.content = content;
    acmeTextDocFile.mimetype = "text/plain";

    // Send back the NodeRef so it can be further used if necessary
    model.nodeRef = acmeTextDocFile.nodeRef;
} else {
    status.code = 404;
    status.message = "ACME Text Document with name: '" + acmeTextDocFileName + "' already exist!";
    status.redirect = true;
}




