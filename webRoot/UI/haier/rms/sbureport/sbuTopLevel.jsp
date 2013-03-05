<%@ page import="java.text.SimpleDateFormat" %>
<%@page contentType="text/html; charset=GBK" %>
<%@include file="/global.jsp" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=GBK">
    <title></title>
    <script language="javascript" src="sbuTopLevel.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/pub.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/common.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/DataWindow.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/pub.js"></script>
    <LINK href="<%=contextPath%>/css/newccb.css" type="text/css" rel="stylesheet">

    <style type="text/css">
        .queryPanalDiv {
            width: 100%;
            margin: 5px auto;
            padding: 2px, 20px, 2px, 20px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .queryDiv {
            width: 90%;
            margin: 0px auto;
            padding: 2px, 1px, 1px, 1px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }

        .queryButtonDiv {
            width: 100%;
            margin: 5px auto;
            padding: 10px, 5px, 2px, 2px;
            text-align: center; /*border: 1px #1E7ACE solid;*/
        }
    </style>


</head>
<%

    java.util.Calendar   cal   =   java.util.Calendar.getInstance();
    cal.add(Calendar.DAY_OF_YEAR,-1);
    String endDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    cal.set(cal.get(Calendar.YEAR),0,1);
    String startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

%>

<%--<body onload="payBillInit()" bgcolor="#ffffff" class="Bodydefault">--%>
<body onload="formInit()" bgcolor="#ffffff" class="Bodydefault">
<%--     <div class="title">
       <br>
       按经办行、合作方、合作项目名称、未办抵押原因统计:<br>
       </div>--%>
<fieldset style="padding:40px 25px 0px 25px;margin:0px 20px 0px 20px">
    <legend>查询条件</legend>
    <br>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <form id="queryForm" name="queryForm">
            <!-- 组合查询统计类型一 -->
            <input type="hidden" value="miscRpt01" id="rptType" name="rptType"/>
            <tr>
                <td width="30%" nowrap="nowrap" class="lbl_right_padding">报表起始日期</td>
                <td width="70%" nowrap="nowrap" class="data_input"><input type="text" id="startDate"
                                                                          name="startDate"   readonly="readonly"   
                                                                          <%--onClick="WdatePicker()"--%>
                                                                          fieldType="date" size="40" value= <%=startDate%>></td>
            </tr>
            <tr>
                <td width="30%" nowrap="nowrap" class="lbl_right_padding">报表截至日期</td>
                <td width="70%" nowrap="nowrap" class="data_input"><input type="text" id="endDate"
                                                                          name="endDate"
                                                                          onClick="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
                                                                          fieldType="date" size="40" value= <%=endDate%>></td>
            </tr>
            <tr>
                <td colspan="4" nowrap="nowrap" align="center" style="padding:20px">
                    <input name="expExcel" class="buttonGrooveDisableExcel" type="button"
                           onClick="loanTab_expExcel_click()" value="导出excel">
                    <input type="reset" value="重填" class="buttonGrooveDisable">
                </td>
            </tr>
        </form>
    </table>
</fieldset>


<br/>
<br/>
<br/>

<div class="help-window">
    <DIV class=formSeparator>
        <H2>报表说明</H2>
    </DIV>                              
    <div class="help-info">
        <ul>
            <li>2010年8月27日(含)之前的报表数据为手工计算表格，2010年8月28日(含)之后的报表为自动生成的数据.</li>
            <li>报表默认开始日期为本年度第一天，不可以更改.</li>
            <li>报表默认截至日期为当前日期的前一天，可修改.</li>
            <li>生成报表后，若显示“#VALUE!”等错误信息(EXCEl版本原因)，请按CTRL+ALT+F9键进行表格数据重新计算.</li>
        </ul>
    </div>
</div>


</body>
</html>