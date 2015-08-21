<#assign datetimeformat="EEE, dd MMM yyyy HH:mm:ss zzz">
{
    "name"          : "${name}",
    "creator"       : "${creator}",
    "createdDate"   : "${createdDate?string(datetimeformat)}",
    "modifier"      : "${modifier}",
    "modifiedDate"  : "${modifiedDate?string(datetimeformat)}"
}