<@markup id="css" >
   <#-- CSS Dependencies -->
</@>

<@markup id="js">
   <#-- JavaScript Dependencies -->
</@>

<@markup id="widgets">
   <@createWidgets group="dashlets"/>
</@>

<@markup id="post">
</@>

<@markup id="html">
   <@uniqueIdDiv>
      <div class="dashlet">
         <div class="title">${msg("header.label")}</div>
         <div class="body dashlet-padding">
           Hello world!
         </div>
      </div>
   </@>
</@>
