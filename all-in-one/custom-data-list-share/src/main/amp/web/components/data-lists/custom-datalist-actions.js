/**
 * A sample of a custom Data List action
 *
 * @namespace Alfresco
 * @class Alfresco.service.DataListActions
 */
(function()
{
    /**
     * Sample datalist action that just displays a message when clicked
     *
     * @method onActionDisplayMsg
     * @param file {object} Object literal representing one or more file(s) or folder(s) to be actioned
     */
    Alfresco.service.DataListActions.prototype.onActionDisplayMsg = function DL_onActionDisplayMsg(file)
    {
        Alfresco.util.PopupManager.displayMessage({
            text:this.msg('alfresco.tutorials.datalist.action.showMsg.message')
        });
    };
})();