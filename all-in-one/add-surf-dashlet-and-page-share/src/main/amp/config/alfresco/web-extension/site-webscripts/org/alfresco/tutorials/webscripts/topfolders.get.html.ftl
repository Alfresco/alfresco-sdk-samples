<#if topContent?? && (topContent?size > 0)>
<h1>Top Folders:</h1>
    Found ${topContent?size} content items in the top folder (/Company Home).
    <#list topContent as tc>
        <br/>
        ${tc.object.properties["cmis:name"].value}
    </#list>
<#else>
    Did not find any content items in the top folder (root).
</#if>

<#if docs?? && (docs?size > 0)>
<h1>Search Result:</h1>
    Found ${docs?size} documents.
    <#list docs as d>
        <br/>
        ${d.properties["cmis:name"].value}
    </#list>
<#else>
    Did not find any documents.
</#if>

