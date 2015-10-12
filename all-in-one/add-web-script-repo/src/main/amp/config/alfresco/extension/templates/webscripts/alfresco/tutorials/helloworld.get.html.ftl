<#assign datetimeformat="yyyy-MM-dd HH:mm:ss">
<h2>${msg("hello.world")} - ${personName}</h2>
<#if greetingActive == "true">
    <p>
        <i>${greetingText}</i>
    </p>
</#if>
<p>The time is now: "${currentDateTime?string(datetimeformat)}</p>