var SQLStr;
var gWhereStr = "";

/**
 * ��ʼ��form����,
 * <p>
 * ����ʼ�����㶨λ�ڲ�ѯ������һ���ؼ���;
 * <p>
 * ��ÿ�β�ѯ��Ϻ󽹵��Զ���λ����һ���ؼ�����ȫѡ��
 *
 */

function body_resize() {
    divfd_ActionTable.style.height = document.body.clientHeight - 207 + "px";
    ActionTable.fdwidth = "100%";
    initDBGrid("ActionTable");
    // ��ʼ��ҳ�潹��
    body_init(queryForm, "queryClick");
}

function datatable_refresh() {
}


////////��ѯ����
function queryClick() {
    var whereStr = "";
    /*
     if (trimStr(document.all["txdate"].value) != "") {
     var retxml = createExecuteform(queryForm, "insert", "qbal01", "querySBS");

     if (analyzeReturnXML(retxml) != "false") {
     alert("��ȡSBS��¼�ɹ�...");
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
//                alert("��ȡSBS��¼�ɹ�...");
    } else {
        alert("��ȡSBS��¼ʧ��...");
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
 *          ��������
 */
function ActionTable_RptExcel_click() {

    // ����ϵͳ�����
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