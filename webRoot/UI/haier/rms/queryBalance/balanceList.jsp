<!--
/*********************************************************************
* 功能描述: 股票帐户余额实时查询
* 作者:
* 开发日期: 2010/01/24
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

    <script language="javascript" src="balanceList.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/pub.js"></script>


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

    String stockcd = request.getParameter("stockcd");
    if ("A".equals(stockcd)) {
        stockcd = "1";
    }else if ("H".equals(stockcd)) {
        stockcd = "2";
    } else { //查询变动
        stockcd = "3";
    }
    String txdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    DBGrid dbGrid = new DBGrid();
    dbGrid.setGridID("ActionTable");
    String sql = "select cusidt,apcode,lasbal,curcde,txdate from rms_acctbal where stockcd = " + stockcd + " and 1=1 ";
    dbGrid.setfieldSQL(sql);

    dbGrid.setField("帐    号", "center", "15", "cusidt", "true", "0");
    dbGrid.setField("户    名", "text", "25", "apcode", "true", "0");
    dbGrid.setField("余    额", "money", "15", "lasbal", "true", "0");
    dbGrid.setField("帐户类型", "dropdown", "10", "curcde", "true", "accttype");
    dbGrid.setField("查询日期", "center", "10", "txdate", "true", "0");

    dbGrid.setWhereStr(" order by cusidt asc ");

    dbGrid.initSumfield("总金额: ", "lasbal");

    dbGrid.setpagesize(200);
    dbGrid.setdataPilotID("datapilot");
//    dbGrid.setbuttons("查看项目=query,添加项目=appendRecod,编辑项目=editRecord,删除项目=deleteRecord,moveFirst,prevPage,nextPage,moveLast");
    dbGrid.setbuttons("导出Excel=RptExcel,moveFirst,prevPage,nextPage,moveLast");
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
            <input type="hidden" id="stockcd" name="stockcd" value=<%=stockcd%>>
            <input type="hidden" id="txdate" name="txdate" value=<%=txdate%>>

            <tr>
                <td width="20%" class="lbl_right_padding">
                    帐 号:
                </td>
                <td width="30%" align="right" nowrap="nowrap" class="data_input">
                    <%--<input type="text" id="proj_name" size="40" class="ajax-suggestion url-getPull.jsp">--%>
                    <input type="text" id="cusidt" name="cusidt" style="width:90%">
                </td>
                <%--</tr>--%>

                <%--<tr>--%>
                <td width="20%" align="right" nowrap="nowrap" class="lbl_right_padding">
                    户 名:
                </td>
                <td width="30%" align="right" nowrap="nowrap" class="data_input">
                    <input type="text" id="apcode" name="apcode" style="width:90%">
                </td>

                <%--<td width="10%" align="Right" nowrap="nowrap">--%>
                <td align="Right" nowrap="nowrap">
                    <input name="cbRetrieve" type="button" class="buttonGrooveDisable" id="button"
                           onClick="queryClick()" onMouseOver="button_onmouseover()"
                           onMouseOut="button_onmouseout()" value="&nbsp;&nbsp;&nbsp;检 索&nbsp;&nbsp;&nbsp;">
                </td>
            </tr>

            <tr>
                <td width="20%" align="right" nowrap="nowrap" class="lbl_right_padding">
                    余 额:
                </td>
                <td width="30%" align="right" nowrap="nowrap" class="data_input">
                    <%--<input type="text" id="proj_name" size="40" class="ajax-suggestion url-getPull.jsp">--%>
                    <input type="text" id="lasbal" name="lasbal" style="width:90%">
                </td>
                <%--</tr>--%>

                <%--<tr height="20">--%>
                <td width="20%" align="right" nowrap="nowrap" class="lbl_right_padding">
                    帐户类型:
                </td>
                <td width="30%" align="right" nowrap="nowrap" class="data_input">
                    <%--<input type="text" id="proj_name" size="40" class="ajax-suggestion url-getPull.jsp">--%>
                    <%--<input type="text" id="curcde" size="60">--%>
                    <%
                        ZtSelect zs = new ZtSelect("curcde", "ACCTTYPE", "0");
                        zs.addAttr("style", "width: 90%");
                        zs.addAttr("fieldType", "text");
                        //zs.addAttr("isNull","false");
                        zs.addOption("", "");
                        out.print(zs);
                    %>
                </td>
                <td align="Right" nowrap="nowrap">
                    <input name="Input" class="buttonGrooveDisable" type="reset"
                           value="&nbsp;&nbsp;&nbsp;重 填&nbsp;&nbsp;&nbsp;"
                           onMouseOver="button_onmouseover()" onMouseOut="button_onmouseout()">
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
