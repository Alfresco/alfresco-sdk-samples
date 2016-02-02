<div>
<#if weather?exists>
    <h1>London Weather Today</h1>
    <h2>${weather[0].description}</h2>
<#else>
    Could not access weather information.
</#if>
</div>   