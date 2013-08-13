function selectTable(tableId)
{
	selectElementContents(document.getElementById(tableId));
}


function selectElementContents(el) {
    var body = document.body, range, sel;
    if (body.createTextRange) {
        range = body.createTextRange();
        range.moveToElementText(el);
        range.select();
    } else if (document.createRange && window.getSelection) {
        range = document.createRange();
        range.selectNodeContents(el);
        //range.execCommand("Copy");
        sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(range);
    }    
}


function submitForm(formId, hidenActionId, actionValue)
{
	document.getElementById(hidenActionId).value = actionValue;
	document.getElementById(formId).submit();
}


function selectTplItemDataType(selItemId, unSelItemId1, unSelItemId2)
{
	document.getElementById(selItemId).value = "true";
	document.getElementById(unSelItemId1).value = "false";
	document.getElementById(unSelItemId2).value = "false";
}