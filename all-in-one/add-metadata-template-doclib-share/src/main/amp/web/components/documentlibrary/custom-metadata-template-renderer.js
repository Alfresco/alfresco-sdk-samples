(function () {
    YAHOO.Bubbling.fire("registerRenderer",
        {
            propertyName: "acmeDocumentIdCustomRendition",
            renderer: function acmeDocumentId_renderer(record, label) {
                var jsNode = record.jsNode,
                    properties = jsNode.properties,
                    html = "";
                var acmeDocId = properties["acme:documentId"] || "";
                html = '<span>' + label + '<h2>' + acmeDocId + '</h2></span>';

                return html;
            }
        });
})();