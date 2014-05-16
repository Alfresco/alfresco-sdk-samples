<#escape x as jsonUtils.encodeJSONString(x)>
{
  "success" : "${success?string("true", "false")}",
  "message" : "${message}"
}
</#escape>
