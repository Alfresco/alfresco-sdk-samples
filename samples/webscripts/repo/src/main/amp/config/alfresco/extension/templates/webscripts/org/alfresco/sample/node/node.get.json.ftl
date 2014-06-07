<#escape x as jsonUtils.encodeJSONString(x)>
{
  "node" : {
    <#if node?exists>
      "nodeRef" : "${node.nodeRef}",
      "name"    : "${node.name}",
      "path"    : "${node.qnamePath}",
      "creator" : "${node.properties['creator']}"
    </#if>
  }
}
</#escape>
