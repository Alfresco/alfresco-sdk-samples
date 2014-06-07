<node>
    <#if node?exists>
    <nodeRef>${node.nodeRef}</nodeRef>
    <name>${node.name}</name>
    <path>${node.qnamePath}</path>
    <creator>${node.properties['creator']}</creator>
    </#if>
</node>

