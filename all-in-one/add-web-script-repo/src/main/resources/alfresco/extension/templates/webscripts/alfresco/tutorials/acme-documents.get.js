/**
 * @constructor
 */
function AcmeDocumentInfo(doc) {
    this.name = doc.name;
    this.creator = doc.properties.creator;
    this.createdDate = doc.properties.created;
    this.modifier = doc.properties.modifier;
    this.modifiedDate = doc.properties.modified;
    this.docId = doc.properties["acme:documentId"];
    this.securityClassification = doc.properties["acme:securityClassification"];
}

function main() {
    // Do Free Text Search (FTS) if we got a keyword passed in
    var searchKeyword = args["q"];
    if (searchKeyword == null || searchKeyword.length == 0) {
        searchKeyword = "";
    } else {
        searchKeyword = " AND TEXT:\"" + searchKeyword + "\"";
    }

    // Example query: TYPE:'acme:document' TEXT:'sample'
    // By default TEXT matches after doing stemming, so the following would match our default bootstrapped Acme doc:
    // "samples. sampled, sampling, etc, it would not match for example samplex as that is not a word..."
    var acmeDocNodes = search.luceneSearch("TYPE:\"acme:document\"" + searchKeyword);
    if (acmeDocNodes == null || acmeDocNodes.length == 0) {
        status.code = 404;
        status.message = "No ACME documents found";
        status.redirect = true;
    } else {
        var acmeDocInfos = new Array();
        for (i = 0; i < acmeDocNodes.length; i++) {
            acmeDocInfos[i] = new AcmeDocumentInfo(acmeDocNodes[i]);
        }
        model.acmeDocs = acmeDocInfos;
        return model;
    }
}

main();
