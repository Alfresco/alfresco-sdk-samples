<#assign datetimeformat="EEE, dd MMM yyyy HH:mm:ss zzz">
{
    "acmeDocs" : [
        <#list acmeDocs as acmeDoc>
            {
                "name"          : "${acmeDoc.name}",
                "creator"       : "${acmeDoc.creator}",
                "createdDate"   : "${acmeDoc.createdDate?string(datetimeformat)}",
                "modifier"      : "${acmeDoc.modifier}",
                "modifiedDate"  : "${acmeDoc.modifiedDate?string(datetimeformat)}",
                "docId"         : "${acmeDoc.docId!"Unknown"}",
                "securityClass" : "${acmeDoc.securityClassification!"Unknown"}"
            }
            <#if acmeDoc_has_next>,</#if>
        </#list>
    ]
}