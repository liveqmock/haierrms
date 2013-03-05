var SQLStr;
var gWhereStr = "";

/**
 * 初始化form函数,
 * <p>
 * ■初始化焦点定位在查询条件第一个控件上;
 * <p>
 * ■每次查询完毕后焦点自动定位到第一个控件，且全选；
 *
 */

function body_resize() {
    divfd_ActionTable.style.height = document.body.clientHeight - 180 + "px";
    ActionTable.fdwidth = "100%";
    initDBGrid("ActionTable");
    // 初始化页面焦点
    body_init(queryForm, "queryClick");
}

function datatable_refresh() {
}


////////查询函数
function queryClick() {
    var whereStr = "";
 
    var retxml = createExecuteform(queryForm, "insert", "qbal02", "queryEAI_YY");

    if (analyzeReturnXML(retxml) != "false") {
        //        alert("获取SBS记录成功...");
    } else {
        alert("获取EAI记录失败...");
        return;
    }

/*

    if (trimStr(document.all["corpname"].value) != "")
        whereStr += " and ( corpname like '%" + document.all.corpname.value + "%')";

    if (trimStr(document.all["corpacct"].value) != "")
        whereStr += " and ( corpacct like '%" + document.all.corpacct.value + "%')";

    if (trimStr(document.all["balance"].value) != "")
        whereStr += " and ( balance >= " + document.all.balance.value + ")";

    if (trimStr(document.all["item"].value) != "")
        whereStr += " and ( item like '%" + document.all.itemname.value + "%')";


    //    whereStr += " order by apcode asc ";
    document.all["ActionTable"].RecordCount = "0";
    document.all["ActionTable"].AbsolutePage = "1";

    //    alert(whereStr);

    if (whereStr != document.all["ActionTable"].whereStr) {
        document.all["ActionTable"].whereStr = whereStr + " order by corpname ";
        document.all["ActionTable"].RecordCount = "0";
        document.all["ActionTable"].AbsolutePage = "1";
    }

*/
    Table_Refresh("ActionTable", false, body_resize);

}

/**
 * report
 *
 * @param doType:select
 *          操作类型
 */
function ActionTable_RptExcel_click() {


    document.getElementById("queryForm").target = "_blank";
    document.getElementById("queryForm").action = "balanceRpt.jsp";
//    document.getElementById("queryForm").action = "/UI/ccb/loan/mortBefore/beforeExpressRpt.jsp?expressType=mortAfter";

    document.getElementById("queryForm").submit();
}