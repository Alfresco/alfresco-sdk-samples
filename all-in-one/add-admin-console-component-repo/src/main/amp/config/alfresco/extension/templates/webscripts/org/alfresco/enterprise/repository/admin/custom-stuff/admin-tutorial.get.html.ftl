<#include "/org/alfresco/enterprise/repository/admin/admin-template.ftl" />

<@page title=msg("tutorial.title")>
   
   <div class="column-full">
      <@section label=msg("tutorial.column") />
      <#-- tutorial - Retrieve keys - which are attribute names - use to index into attribute hash -->
      <#-- You can index directly by attribute name e.g. <@control attribute=attributes["Subject"] /> -->
      <#list attributes?keys as a>
         <@control attribute=attributes[a] />
      </#list>
   </div>
   
   <div class="column-left">
      <@section label=msg("tutorial.leftcolumn") />
      <#-- tutorial - Retrieve values - which are attributes -->
      <#list attributes?values as a>
         <@control attribute=a />
      </#list>
   </div>
   <div class="column-right">
      <@section label=msg("tutorial.rightcolumn") />
      <#-- tutorial - Retrieve values - which are attributes -->
      <#list attributes?values as a>
         <@control attribute=a />
      </#list>
   </div>
   
</@page>