<!--
/*********************************************************************
* 功能描述: EAI-下属帐户余额实时查询
* 作者:
* 开发日期: 2010/05/01
* 修改人:
* 修改日期:
* 版权:
***********************************************************************/
-->
<%@page contentType="text/html; charset=GBK" %>
<%@include file="/global.jsp" %>
<%@page import="pub.platform.security.OperatorManager" %>
<%@page import="pub.platform.form.config.SystemAttributeNames" %>
<%@page import="pub.platform.db.DBGrid" %>
<%@page import="pub.platform.html.ZtSelect" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=GBK">
    <title></title>

    <script language="javascript" src="yybalanceList.js"></script>
    <script language="javascript" src="/UI/support/pub.js"></script>


    <style type="text/css">
        .queryPanalDiv {
            width: 100%;
            margin: 1px auto;
            padding: 1px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .queryDiv {
            width: 96%;
            margin: 1px auto;
            padding: 1px, 1px, 1px, 1px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .queryButtonDiv {
            width: 100%;
            margin: 1px auto;
            padding: 1px, 1px, 1px, 1px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .listPanalDiv {
            width: 100%;
            margin: 1px auto;
            padding: 0px, 0px, 5px, 0px;
            text-align: left; /*border: 1px #1E7ACE solid;*/
        }

        .queryListBootomDiv {
            width: 100%;
            margin: 5px auto;
            padding: 10px, 5px, 10px, 5px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .data_input {
            text-align: left;
            background-color: #F5F5F5;
            border: #C0C0C0 1px solid;
            font-weight: bold;
            letter-spacing: 1px;
            font-size: 9pt;
            padding: 1px, 1px, 1px, 2px;
        }

        a:hover {
            color: #ff0000;
        }

        tr {
            height: 20px;
        }

    </style>

</head>
<%
    OperatorManager omgr = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
    String deptId = omgr.getOperator().getPtDeptBean().getDeptid();

    String txdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    DBGrid dbGrid = new DBGrid();
    dbGrid.setGridID("ActionTable");
    String sql = "select corpname,item,balance,currcd,querydate from eai_yongyoucorpbal where  1=1 ";
    dbGrid.setfieldSQL(sql);

//    dbGrid.setField("序号", "center", "10", "id", "true", "0");
    dbGrid.setField("企业名称", "text", "40", "corpname", "true", "0");
    dbGrid.setField("账套", "center", "10", "item", "true", "0");
//    dbGrid.setField("帐号", "text", "30", "corpacct", "true", "0");
    dbGrid.setField("余额", "money", "20", "balance", "true", "0");
    dbGrid.setField("币别", "center", "10", "currcd", "true", "0");
    dbGrid.setField("查询日期", "center", "20", "querydate", "true", "0");

    dbGrid.setWhereStr(" order by corpname ");

    dbGrid.initSumfield("总金额: ", "balance");

    dbGrid.setpagesize(200);
    dbGrid.setdataPilotID("datapilot");
//    dbGrid.setbuttons("导出Excel=RptExcel,moveFirst,prevPage,nextPage,moveLast");
    dbGrid.setbuttons("moveFirst,prevPage,nextPage,moveLast");
%>
<body bgcolor="#ffffff" onload="body_resize()" onresize="body_resize();" class="Bodydefault">

<fieldset>
    <legend>
        查询条件
    </legend>
    <%--<div class="queryPanalDiv">--%>
    <%--<div class="queryDiv">--%>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <form id="queryForm" name="queryForm">
            <%--<input type="hidden" id="txdate" name="txdate" value=<%=txdate%>>--%>
            <tr>
                <td width="20%" nowrap="nowrap" class="lbl_right_padding">查询余额日期:</td>
                <td width="50%" nowrap="nowrap" class="data_input">
                    <input type="text" id="txdate"
                           name="txdate"
                           onClick="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
                    fieldType="date" size="20" value= <%=txdate%>>

                </td>
                <td width="30%" colspan="8" nowrap="nowrap" align="center"><input class="buttonGrooveDisable"
                                                                     name="cbRetrieve" type="button" id="button"
                                                                     onClick="queryClick()"
                                                                     value="检索">
                    <input class="buttonGrooveDisable" name="Input" type="reset" value="重填">
                </td>
            </tr>

        </form>

    </table>

</fieldset>


<%--<div class="listPanalDiv">--%>
<fieldset>
    <legend>
        帐户余额信息
    </legend>
    <table width="100%">
        <tr>
            <td>
                <%=dbGrid.getDBGrid()%>
            </td>
        </tr>
    </table>
</fieldset>
<FIELDSET>
    <LEGEND>
        操作
    </LEGEND>
    <table width="100%" class="title1">
        <tr>
            <td align="right">
                <%=dbGrid.getDataPilot()%>
            </td>
        </tr>
    </table>
</FIELDSET>

<%--</div>--%>
<%--

<div id="search-result-suggestions">
    <div id="search-results">
    </div>
</div>
--%>

</body>
</html>
