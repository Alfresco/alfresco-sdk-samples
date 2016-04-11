<#-- JavaScript Dependencies
<@markup id="js">
</@>
-->

<#-- Stylesheet Dependencies -->
<@markup id="css">
    <@script type="text/css" src="${url.context}/res/resources/add-surf-dashlet-and-page-share/components/dashlets/member-directory.css" group="dashlets"/>
</@>

<#-- Widget creation
<@markup id="widgets">
    <@createWidgets group="dashlets"/>
</@>
-->

<@markup id="html">
    <@uniqueIdDiv>
        <#assign id = args.htmlid?html>
        <#assign dashboardconfig=config.scoped['Dashboard']['dashboard']>

        <div class="dashlet">
            <div class="title">${msg("member.directory.dashletName")}</div>
            <div id="${id}-memberdir" class="body">

                <div class="toolbar">
                    <div class="actions">
                        <form name="input"
                              action="${url.context}/page/user/${context.user.id}/dashboard"
                              method="get">
                        ${msg("member.directory.searchFilter")}: <input type="text" name="filter" />
                            <input type="submit" value="Search" />
                        </form>
                    </div>
                </div>

                <p valign="top">
                    <span class="profile-label">${msg("member.directory.searchResult")}</span>
                    <#list people as p>
                        <br/>
                        <a class="profile-name" href="${url.context}/page/user/${p.userName}/profile">
                        ${p.firstName} ${p.lastName} (${p.email})
                        </a>
                    </#list>
                </p>

            </div>
        </div>
    </@>
</@>


