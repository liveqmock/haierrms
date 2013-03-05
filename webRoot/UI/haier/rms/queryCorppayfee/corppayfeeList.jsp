<!--
/*********************************************************************
* 功能描述: 对公支付手续费查询
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

    <script language="javascript" src="corppayfeeList.js"></script>
    <script language="javascript" src="/UI/support/pub.js"></script>

    <style type="text/css">
        .queryPanalDiv {
            width: 100%;
            margin: 5px auto;
            padding: 1px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .queryDiv {
            width: 90%;
            margin: 1px auto;
            padding: 1px, 1px, 1px, 1px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .queryButtonDiv {
            width: 100%;
            margin: 5px auto;
            padding: 5px, 10px, 5px, 5px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .listPanalDiv {
            width: 100%;
            margin: 5px auto;
            padding: 0px, 0px, 10px, 0px;
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

    String txdate = new SimpleDateFormat("yyyy-MM").format(new Date());

    DBGrid dbGrid = new DBGrid();
    dbGrid.setGridID("ActionTable");
//    String sql = "select id,corpacct,corpname,bankname,countnum,TO_CHAR(fee,'FM999,999,999,990.00') from rms_corppayfee where  1=1 ";
    String sql = "select id,corpacct,corpname,bankname,countnum,fee from rms_corppayfee where  1=1 ";
    dbGrid.setfieldSQL(sql);

    dbGrid.setField("序号", "center", "5", "id", "true", "0");
    dbGrid.setField("企业帐号", "center", "15", "corpacct", "true", "0");
    dbGrid.setField("企业名称", "text", "25", "corpname", "true", "0");
    dbGrid.setField("付款银行", "center", "10", "bankname", "true", "0");
    dbGrid.setField("笔数", "number", "5", "countnum", "true", "0");
    dbGrid.setField("金额", "money", "10", "fee", "true", "0");
    dbGrid.setWhereStr(" order by id asc ");

    dbGrid.initSumfield("总金额: ", "fee");


    dbGrid.setpagesize(100);
    dbGrid.setdataPilotID("datapilot");
//    dbGrid.setbuttons("查看项目=query,添加项目=appendRecod,编辑项目=editRecord,删除项目=deleteRecord,moveFirst,prevPage,nextPage,moveLast");
    dbGrid.setbuttons("导出Excel=excel,moveFirst,prevPage,nextPage,moveLast");
%>
<body bgcolor="#ffffff" onload="body_resize()" onresize="body_resize();" class="Bodydefault">

<%--<form id="queryForm" name="queryForm">--%>
<!-- 编号  该隐藏字段是为了删除之用 -->
<%--<input type="hidden" id="stockcd" value=<%=stockcd%> >--%>
<%--<input type="hidden" id="txdate" value=<%=txdate%> >--%>
<br>

<fieldset>
    <legend>
        查询条件
    </legend>
    <div class="queryPanalDiv">
        <div class="queryDiv">

            <table border="0" cellspacing="0" cellpadding="0" width="100%">

                <form id="queryForm" name="queryForm">

                    <tr>
                        <td width="20%" nowrap="nowrap" class="lbl_right_padding">日期:</td>
                        <td width="30%" nowrap="nowrap" class="data_input">
                            <input type="text" id="txdate"
                                   name="CUST_OPEN_DT"
                                   onClick="WdatePicker({startDate:'%y-%M',dateFmt:'yyyy-MM',alwaysUseStartDate:true})"
                                   <%--fieldType="date" size="20">--%>
                            fieldType="date" size="20" value= <%=txdate%>>

                        </td>
                        <td width="20%" align="right" nowrap="nowrap" class="lbl_right_padding">
                            缴费类型:
                        </td>
                        <td width="30%" align="right" nowrap="nowrap" class="data_input">
                            <%
                                ZtSelect zs = new ZtSelect("type", "CORPPAYFEETYPE", "0");
                                zs.addAttr("style", "width: 50%");
                                zs.addAttr("fieldType", "text");
                                zs.addAttr("isNull", "false");
                                zs.addOption("", "");
                                out.print(zs);
                            %>

                        </td>
                        <td colspan="8" nowrap="nowrap" align="right"><input class="buttonGrooveDisable"
                                                                             name="cbRetrieve" type="button" id="button"
                                                                             onClick="queryClick()"
                                                                             value="检索">
                            <input class="buttonGrooveDisable" name="Input" type="reset" value="重填">
                        </td>
                    </tr>


                </form>
            </table>

        </div>
    </div>


</fieldset>



<div class="listPanalDiv">
    <fieldset>
        <legend>
            缴费信息
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

</div>

<div id="search-result-suggestions">
    <div id="search-results">
    </div>
</div>

</body>
</html>
