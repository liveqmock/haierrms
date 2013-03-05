var SQLStr;
var gWhereStr = "";

/**
 * 初始化form函数,
 * <p>
 * ■初始化焦点定位在查询条件第一个控件上;
 * <p>
 * ■每次查询完毕后焦点自动定位到第一个控件，且全选；
 *
 * @return
 */
function body_resize() {
    divfd_ActionTable.style.height = document.body.clientHeight - 225 +"px";
    ActionTable.fdwidth="100%";

    initDBGrid("ActionTable");
    // 初始化页面焦点
    //    body_init(queryForm, "cbRetrieve");
    body_init(queryForm, "queryClick");
//    document.getElementById("cusidt").focus();
//    document.getElementById("cusidt").select();
}

function datatable_refresh() {
}


////////查询函数
function queryClick() {

    var whereStr = "";

    var retxml = createExecuteform(queryForm, "insert", "qcorp1", "querySBS");

    if (analyzeReturnXML(retxml) != "false") {
//        alert("获取SBS记录成功...");
    }else{
        alert("获取SBS记录失败...");
        return;
    }
    document.all["ActionTable"].RecordCount = "0";
    document.all["ActionTable"].AbsolutePage = "1";


    if (whereStr !=document.all["ActionTable"].whereStr){
        document.all["ActionTable"].whereStr=whereStr +" order by id asc ";
        document.all["ActionTable"].RecordCount="0";
        document.all["ActionTable"].AbsolutePage="1";
    }

    Table_Refresh("ActionTable", false, datatable_refresh);
//    		Table_Refresh("ActionTable");
}

