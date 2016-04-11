<h2>Repo Stores</h2>
<#list stores as store>
${store}
    <#if store_has_next>,</#if>
</#list>