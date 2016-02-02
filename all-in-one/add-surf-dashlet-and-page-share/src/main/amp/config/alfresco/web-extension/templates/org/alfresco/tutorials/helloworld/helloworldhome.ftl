<#include "/org/alfresco/include/alfresco-template.ftl" />
<#-- From 4.2 and onwards configuration -->


<@templateHeader>
</@>

<@templateBody>
   <@markup id="alf-hd">
   <div id="alf-hd">
      <@region scope="global" id="share-header" chromeless="true"/>
   </div>
   </@>
   <@markup id="bd">
    <div id="bd">
        <@region id="people" scope="page" />
        <@region id="sites" scope="page" />
        <@region id="topfolders" scope="page" />
    </div>
   </@>
</@>

<@templateFooter>
   <@markup id="alf-ft">
   <div id="alf-ft">
      <@region id="footer" scope="global" />
   </div>
   </@>
</@>
