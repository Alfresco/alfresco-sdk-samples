<#-- JavaScript Dependencies -->
<@markup id="js">
    <@script type="text/javascript" src="${url.context}/res/resources/add-surf-dashlet-and-page-share/components/dashlets/helloworld.js" group="dashlets"/>
</@>

<#-- Stylesheet Dependencies
<@markup id="css">
    <@script type="text/css" src="${url.context}/res/resources/add-surf-dashlet-and-page-share/components/dashlets/helloworld.css" group="dashlets"/>
</@>
-->

<#-- Surf Widget creation -->
<@markup id="widgets">
    <@createWidgets group="dashlets"/>
</@>

<@markup id="html">
    <@uniqueIdDiv>
        <#assign el = args.htmlid?html>
        <#assign dashboardconfig=config.scoped['Dashboard']['dashboard']>

        <div class="dashlet">
            <div class="title">
            ${msg("hello.world.dashletTitle")}
            </div>
            <div class="body">
                <button id="${el}-testButton">${msg('hello.world.buttonLabel')}</button>
            </div>
        </div>
    </@>
</@>





