var connector = remote.connect("alfresco-api");

// Get some stuff via CMIS REST API
var topFolderContentAsJSONString = connector.get("/-default-/public/cmis/versions/1.1/browser/root");

// Query via CMIS REST API
var queryStatement = encodeURIComponent("select * from cmis:document where cmis:name like 'Project%'");
var cmisQuery = "cmisselector=query&q=" + queryStatement + "&searchAllVersions=false&includeAllowableActions=false&includeRelationships=none&skipCount=0&maxItems=10";
var queryResult = connector.get("/-default-/public/cmis/versions/1.1/browser?" + cmisQuery);

var topFolderContentJSON = jsonUtils.toObject(topFolderContentAsJSONString);
var docs = jsonUtils.toObject(queryResult);

model.topContent = topFolderContentJSON["objects"];
model.docs = docs["results"];


/*
A call to /browser/root to get top content will result in a JSON response such as:

{
    "objects": [
    {
        "object": {
            "properties": {
                "cmis:objectId": {
                    "id": "cmis:objectId",
                    "localName": "objectId",
                    "displayName": "Object Id",
                    "queryName": "cmis:objectId",
                    "type": "id",
                    "cardinality": "single",
                    "value": "282c5a61-b6ae-4b53-9547-8d980f993645"
                },
                "alfcmis:nodeRef": {
                    "id": "alfcmis:nodeRef",
                    "localName": "nodeRef",
                    "displayName": "Alfresco Node Ref",
                    "queryName": "alfcmis:nodeRef",
                    "type": "id",
                    "cardinality": "single",
                    "value": "workspace:\/\/SpacesStore\/282c5a61-b6ae-4b53-9547-8d980f993645"
                },
                "cmis:path": {
                    "id": "cmis:path",
                    "localName": "path",
                    "displayName": "Path",
                    "queryName": "cmis:path",
                    "type": "string",
                    "cardinality": "single",
                    "value": "\/Data Dictionary"
                },
                "cmis:allowedChildObjectTypeIds": {
                    "id": "cmis:allowedChildObjectTypeIds",
                    "localName": "allowedChildObjectTypeIds",
                    "displayName": "Allowed Child Object Types Ids",
                    "queryName": "cmis:allowedChildObjectTypeIds",
                    "type": "id",
                    "cardinality": "multi",
                    "value": null
                },
                "cmis:lastModifiedBy": {
                    "id": "cmis:lastModifiedBy",
                    "localName": "lastModifiedBy",
                    "displayName": "Last Modified By",
                    "queryName": "cmis:lastModifiedBy",
                    "type": "string",
                    "cardinality": "single",
                    "value": "System"
                },
                "cmis:secondaryObjectTypeIds": {
                    "id": "cmis:secondaryObjectTypeIds",
                    "localName": "secondaryObjectTypeIds",
                    "displayName": "Secondary Object Type Ids",
                    "queryName": "cmis:secondaryObjectTypeIds",
                    "type": "id",
                    "cardinality": "multi",
                    "value": [
                        "P:cm:titled",
                        "P:sys:localized",
                        "P:app:uifacets"
                    ]
                },
                "cmis:objectTypeId": {
                    "id": "cmis:objectTypeId",
                    "localName": "objectTypeId",
                    "displayName": "Object Type Id",
                    "queryName": "cmis:objectTypeId",
                    "type": "id",
                    "cardinality": "single",
                    "value": "cmis:folder"
                },
                "cmis:description": {
                    "id": "cmis:description",
                    "localName": "description",
                    "displayName": "Description",
                    "queryName": "cmis:description",
                    "type": "string",
                    "cardinality": "single",
                    "value": "User managed definitions"
                },
                "cmis:createdBy": {
                    "id": "cmis:createdBy",
                    "localName": "createdBy",
                    "displayName": "Created by",
                    "queryName": "cmis:createdBy",
                    "type": "string",
                    "cardinality": "single",
                    "value": "System"
                },
                "cmis:baseTypeId": {
                    "id": "cmis:baseTypeId",
                    "localName": "baseTypeId",
                    "displayName": "Base Type Id",
                    "queryName": "cmis:baseTypeId",
                    "type": "id",
                    "cardinality": "single",
                    "value": "cmis:folder"
                },
                "cmis:parentId": {
                    "id": "cmis:parentId",
                    "localName": "parentId",
                    "displayName": "Parent Id",
                    "queryName": "cmis:parentId",
                    "type": "id",
                    "cardinality": "single",
                    "value": "b3eb84f7-3e10-4ff9-922d-14ecb0a11420"
                },
                "cmis:creationDate": {
                    "id": "cmis:creationDate",
                    "localName": "creationDate",
                    "displayName": "Creation Date",
                    "queryName": "cmis:creationDate",
                    "type": "datetime",
                    "cardinality": "single",
                    "value": 1454066243859
                },
                "cmis:changeToken": {
                    "id": "cmis:changeToken",
                    "localName": "changeToken",
                    "displayName": "Change token",
                    "queryName": "cmis:changeToken",
                    "type": "string",
                    "cardinality": "single",
                    "value": null
                },
                "cmis:name": {
                    "id": "cmis:name",
                    "localName": "name",
                    "displayName": "Name",
                    "queryName": "cmis:name",
                    "type": "string",
                    "cardinality": "single",
                    "value": "Data Dictionary"
                },
                "cmis:lastModificationDate": {
                    "id": "cmis:lastModificationDate",
                    "localName": "lastModificationDate",
                    "displayName": "Last Modified Date",
                    "queryName": "cmis:lastModificationDate",
                    "type": "datetime",
                    "cardinality": "single",
                    "value": 1454066257612
                },
                "cm:title": {
                    "id": "cm:title",
                    "localName": "title",
                    "displayName": "Title",
                    "queryName": "cm:title",
                    "type": "string",
                    "cardinality": "single",
                    "value": "Data Dictionary"
                },
                "cm:description": {
                    "id": "cm:description",
                    "localName": "description",
                    "displayName": "Description",
                    "queryName": "cm:description",
                    "type": "string",
                    "cardinality": "single",
                    "value": "User managed definitions"
                },
                "app:icon": {
                    "id": "app:icon",
                    "localName": "icon",
                    "displayName": "Icon",
                    "queryName": "app:icon",
                    "type": "string",
                    "cardinality": "single",
                    "value": "space-icon-default"
                }
            },
            "propertiesExtension": {
                "aspects": {
                    "appliedAspects": [
                        "P:cm:titled",
                        "P:sys:localized",
                        "P:app:uifacets"
                    ],
                    "properties": {
                        "propertyString": [
                            {
                                "value": "Data Dictionary"
                            },
                            {
                                "value": "User managed definitions"
                            },
                            {
                                "value": "Data Dictionary"
                            },
                            {
                                "value": "User managed definitions"
                            },
                            {
                                "value": "space-icon-default"
                            }
                        ]
                    }
                }
            }
        }
    },
    {
        "object": {    },
        "object": {    },
],
    "hasMoreItems": false,
    "numItems": 8
}


The search will respond with JSON such as:

{
    "results": [
    {
        "properties": {
            "alfcmis:nodeRef": {
                "id": "alfcmis:nodeRef",
                "localName": "nodeRef",
                "queryName": "alfcmis:nodeRef",
                "type": "id",
                "cardinality": "single",
                "value": "workspace:\/\/SpacesStore\/1a0b110f-1e09-4ca2-b367-fe25e4964a4e"
            },
            "cmis:isImmutable": {
                "id": "cmis:isImmutable",
                "localName": "isImmutable",
                "displayName": "Is Immutable",
                "queryName": "cmis:isImmutable",
                "type": "boolean",
                "cardinality": "single",
                "value": false
            },
            "cmis:versionLabel": {
                "id": "cmis:versionLabel",
                "localName": "versionLabel",
                "displayName": "Version Label",
                "queryName": "cmis:versionLabel",
                "type": "string",
                "cardinality": "single",
                "value": "1.1"
            },
            "cmis:objectTypeId": {
                "id": "cmis:objectTypeId",
                "localName": "objectTypeId",
                "queryName": "cmis:objectTypeId",
                "type": "id",
                "cardinality": "single",
                "value": "cmis:document"
            },
            "cmis:description": {
                "id": "cmis:description",
                "localName": "description",
                "queryName": "cmis:description",
                "type": "string",
                "cardinality": "single",
                "value": "Conract for the Green Energy project"
            },
            "cmis:createdBy": {
                "id": "cmis:createdBy",
                "localName": "createdBy",
                "queryName": "cmis:createdBy",
                "type": "string",
                "cardinality": "single",
                "value": "abeecher"
            },
            "cmis:checkinComment": {
                "id": "cmis:checkinComment",
                "localName": "checkinComment",
                "displayName": "Checkin Comment",
                "queryName": "cmis:checkinComment",
                "type": "string",
                "cardinality": "single",
                "value": null
            },
            "cmis:creationDate": {
                "id": "cmis:creationDate",
                "localName": "creationDate",
                "queryName": "cmis:creationDate",
                "type": "datetime",
                "cardinality": "single",
                "value": 1297805214600
            },
            "cmis:isMajorVersion": {
                "id": "cmis:isMajorVersion",
                "localName": "isMajorVersion",
                "displayName": "Is Major Version",
                "queryName": "cmis:isMajorVersion",
                "type": "boolean",
                "cardinality": "single",
                "value": false
            },
            "cmis:contentStreamFileName": {
                "id": "cmis:contentStreamFileName",
                "localName": "contentStreamFileName",
                "displayName": "Content Stream Filename",
                "queryName": "cmis:contentStreamFileName",
                "type": "string",
                "cardinality": "single",
                "value": "Project Contract.pdf"
            },
            "cmis:name": {
                "id": "cmis:name",
                "localName": "name",
                "queryName": "cmis:name",
                "type": "string",
                "cardinality": "single",
                "value": "Project Contract.pdf"
            },
            "cmis:isLatestVersion": {
                "id": "cmis:isLatestVersion",
                "localName": "isLatestVersion",
                "displayName": "Is Latest Version",
                "queryName": "cmis:isLatestVersion",
                "type": "boolean",
                "cardinality": "single",
                "value": true
            },
            "cmis:lastModificationDate": {
                "id": "cmis:lastModificationDate",
                "localName": "lastModificationDate",
                "queryName": "cmis:lastModificationDate",
                "type": "datetime",
                "cardinality": "single",
                "value": 1308047334714
            },
            "cmis:contentStreamLength": {
                "id": "cmis:contentStreamLength",
                "localName": "contentStreamLength",
                "displayName": "Content Stream Length",
                "queryName": "cmis:contentStreamLength",
                "type": "integer",
                "cardinality": "single",
                "value": 381778
            },
            "cmis:objectId": {
                "id": "cmis:objectId",
                "localName": "objectId",
                "queryName": "cmis:objectId",
                "type": "id",
                "cardinality": "single",
                "value": "1a0b110f-1e09-4ca2-b367-fe25e4964a4e;1.1"
            },
            "cmis:lastModifiedBy": {
                "id": "cmis:lastModifiedBy",
                "localName": "lastModifiedBy",
                "queryName": "cmis:lastModifiedBy",
                "type": "string",
                "cardinality": "single",
                "value": "admin"
            },
            "cmis:secondaryObjectTypeIds": {
                "id": "cmis:secondaryObjectTypeIds",
                "localName": "secondaryObjectTypeIds",
                "queryName": "cmis:secondaryObjectTypeIds",
                "type": "id",
                "cardinality": "multi",
                "value": [
                    "P:rn:renditioned",
                    "P:cm:titled",
                    "P:cm:ownable",
                    "P:cm:likesRatingSchemeRollups",
                    "P:cm:rateable",
                    "P:sys:localized",
                    "P:cm:author"
                ]
            },
            "cmis:contentStreamId": {
                "id": "cmis:contentStreamId",
                "localName": "contentStreamId",
                "displayName": "Content Stream Id",
                "queryName": "cmis:contentStreamId",
                "type": "id",
                "cardinality": "single",
                "value": "store:\/\/2016\/1\/29\/11\/17\/4f5cbd65-2032-4561-9a61-8bd8b1d0b854.bin"
            },
            "cmis:contentStreamMimeType": {
                "id": "cmis:contentStreamMimeType",
                "localName": "contentStreamMimeType",
                "displayName": "Content Stream MIME Type",
                "queryName": "cmis:contentStreamMimeType",
                "type": "string",
                "cardinality": "single",
                "value": "application\/pdf"
            },
            "cmis:baseTypeId": {
                "id": "cmis:baseTypeId",
                "localName": "baseTypeId",
                "queryName": "cmis:baseTypeId",
                "type": "id",
                "cardinality": "single",
                "value": "cmis:document"
            },
            "cmis:changeToken": {
                "id": "cmis:changeToken",
                "localName": "changeToken",
                "queryName": "cmis:changeToken",
                "type": "string",
                "cardinality": "single",
                "value": null
            },
            "cmis:isPrivateWorkingCopy": {
                "id": "cmis:isPrivateWorkingCopy",
                "localName": "isPrivateWorkingCopy",
                "displayName": "Is private working copy",
                "queryName": "cmis:isPrivateWorkingCopy",
                "type": "boolean",
                "cardinality": "single",
                "value": false
            },
            "cmis:versionSeriesCheckedOutBy": {
                "id": "cmis:versionSeriesCheckedOutBy",
                "localName": "versionSeriesCheckedOutBy",
                "displayName": "Version Series Checked Out By",
                "queryName": "cmis:versionSeriesCheckedOutBy",
                "type": "string",
                "cardinality": "single",
                "value": null
            },
            "cmis:isVersionSeriesCheckedOut": {
                "id": "cmis:isVersionSeriesCheckedOut",
                "localName": "isVersionSeriesCheckedOut",
                "displayName": "Is Version Series Checked Out",
                "queryName": "cmis:isVersionSeriesCheckedOut",
                "type": "boolean",
                "cardinality": "single",
                "value": false
            },
            "cmis:versionSeriesId": {
                "id": "cmis:versionSeriesId",
                "localName": "versionSeriesId",
                "displayName": "Version series id",
                "queryName": "cmis:versionSeriesId",
                "type": "id",
                "cardinality": "single",
                "value": "1a0b110f-1e09-4ca2-b367-fe25e4964a4e"
            },
            "cmis:isLatestMajorVersion": {
                "id": "cmis:isLatestMajorVersion",
                "localName": "isLatestMajorVersion",
                "displayName": "Is Latest Major Version",
                "queryName": "cmis:isLatestMajorVersion",
                "type": "boolean",
                "cardinality": "single",
                "value": false
            },
            "cmis:versionSeriesCheckedOutId": {
                "id": "cmis:versionSeriesCheckedOutId",
                "localName": "versionSeriesCheckedOutId",
                "displayName": "Version Series Checked Out Id",
                "queryName": "cmis:versionSeriesCheckedOutId",
                "type": "id",
                "cardinality": "single",
                "value": null
            }
        }
    },
    {
        "properties": {        }
    },
    {
        "properties": {       }
    }
],
    "hasMoreItems": false,
    "numItems": 3
}


 */
