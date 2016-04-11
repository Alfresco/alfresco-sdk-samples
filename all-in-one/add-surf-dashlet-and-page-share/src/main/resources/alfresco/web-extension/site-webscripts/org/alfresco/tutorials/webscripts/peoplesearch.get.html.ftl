<#if filterValue??>
    Search filter = ${filterValue}
    <br/>
<#else>
    Search filter is not set.
</#if>

<#if people?? && (people?size > 0)>
    <h1>People Search Result:</h1>
    Matched ${people?size} users.
    <#list people as p>
        <br/>
        <a href="${url.context}/page/user/${p.userName}/profile">
            ${p.firstName} ${p.lastName} (${p.email})
        </a>
    </#list>
<#else>
    Search did not match anything.
</#if>


