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
    divfd_ActionTable.style.height = document.body.clientHeight - 180 + "px";
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
 
    var retxml = createExecuteform(queryForm, "insert", "qbal02", "queryEAI_YY");

    if (analyzeReturnXML(retxml) != "false") {
        //        alert("��ȡSBS��¼�ɹ�...");
    } else {
        alert("��ȡEAI��¼ʧ��...");
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
 *          ��������
 */
function ActionTable_RptExcel_click() {


    document.getElementById("queryForm").target = "_blank";
    document.getElementById("queryForm").action = "balanceRpt.jsp";
//    document.getElementById("queryForm").action = "/UI/ccb/loan/mortBefore/beforeExpressRpt.jsp?expressType=mortAfter";

    document.getElementById("queryForm").submit();
}