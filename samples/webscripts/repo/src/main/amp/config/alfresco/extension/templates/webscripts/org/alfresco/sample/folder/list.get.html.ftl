<html>
<head>
    <title>Folder ${folder.displayPath}/${folder.name}</title>
</head>
<body>
Alfresco ${server.edition} Edition v${server.version} : dir
<p>
    Contents of folder ${folder.displayPath}/${folder.name}
<p>
<table>
<#list folder.children as child>
    <tr>
        <td><#if child.isContainer>d</#if></td>
        <td>${child.name}</td>
    </tr>
</#list>
</table>
</body>
</html>