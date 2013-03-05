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
    divfd_ActionTable.style.height = document.body.clientHeight - 207 + "px";
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
    /*
     if (trimStr(document.all["txdate"].value) != "") {
     var retxml = createExecuteform(queryForm, "insert", "qbal01", "querySBS");

     if (analyzeReturnXML(retxml) != "false") {
     alert("获取SBS记录成功...");
     }else
     return;

     whereStr += " and ( txdate = '" + document.all.txdate.value + "')";

     } else {
     alert("date null");
     return;
     }
     */

    var retxml = createExecuteform(queryForm, "insert", "qbal01", "querySBS");

    if (analyzeReturnXML(retxml) != "false") {
//                alert("获取SBS记录成功...");
    } else {
        alert("获取SBS记录失败...");
        return;
    }


    if (trimStr(document.all["cusidt"].value) != "")
        whereStr += " and ( cusidt like '%" + document.all.cusidt.value + "%')";

    if (trimStr(document.all["apcode"].value) != "")
        whereStr += " and ( apcode like '%" + document.all.apcode.value + "%')";

    if (trimStr(document.all["lasbal"].value) != "")
        whereStr += " and ( lasbal >= " + document.all.lasbal.value + ")";

    if (trimStr(document.all["curcde"].value) != "")
        whereStr += " and ( curcde = '" + document.all.curcde.value + "')";


    //    whereStr += " order by apcode asc ";
    document.all["ActionTable"].RecordCount = "0";
    document.all["ActionTable"].AbsolutePage = "1";

    //    alert(whereStr);

    if (whereStr != document.all["ActionTable"].whereStr) {
        document.all["ActionTable"].whereStr = whereStr + " order by apcode asc ";
        document.all["ActionTable"].RecordCount = "0";
        document.all["ActionTable"].AbsolutePage = "1";
    }

    Table_Refresh("ActionTable", false, body_resize);

    //    Table_Refresh("ActionTable", false, datatable_refresh);
    //    Table_Refresh("ActionTable", false, datatable_refresh);
    //    		Table_Refresh("ActionTable");
}

/*

 function querySBSData() {
 var retxml = createExecuteform(queryForm, "insert", "qbal01", "querySBS");

 if (analyzeReturnXML(retxml) != "false") {
 alert(MSG_DEL_SUCCESS);
 //        document.getElementById("ActionTable").RecordCount = "0";
 //        Table_Refresh("ActionTable", false, body_resize);
 }

 }
 */


/**
 * report
 *
 * @param doType:select
 *          操作类型
 */
function ActionTable_RptExcel_click() {

    // 增加系统锁检查
    /*
     if (getSysLockStatus() == "1") {
     alert(MSG_SYSLOCK);
     return;
     }
     */
    //  if (dw_form.validate() != null)
    //    return;

    document.getElementById("queryForm").target = "_blank";
    document.getElementById("queryForm").action = "balanceRpt.jsp";
//    document.getElementById("queryForm").action = "/UI/ccb/loan/mortBefore/beforeExpressRpt.jsp?expressType=mortAfter";

    document.getElementById("queryForm").submit();
}