// Surf Dashlet widgets
var widgets = [];

// Main component
widgets.push({
    id: "HelloWorld",
    name: "MyCompany.dashlet.HelloWorld",
    options: {
        componentId: instance.object.id
    }
});

// Resizer
widgets.push({
    id : "DashletResizer",
    name : "Alfresco.widget.DashletResizer",
    initArgs : ["\"" + args.htmlid + "\"","\"" + instance.object.id + "\""],
    useMessages: false
});

// Title bar actions
var actions = [];
actions.push({
    cssClass: "help",
    bubbleOnClick:
    {
        message: msg.get("dashlet.help")
    },
    tooltip: msg.get("dashlet.help.tooltip")
});
widgets.push({
    id : "DashletTitleBarActions",
    name : "Alfresco.widget.DashletTitleBarActions",
    useMessages : false,
    options : {
        actions: actions
    }
});

model.widgets = widgets;




