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
       �������С���������������Ŀ���ơ�δ���Ѻԭ��ͳ��:<br>
       </div>--%>
<fieldset style="padding:40px 25px 0px 25px;margin:0px 20px 0px 20px">
    <legend>��ѯ����</legend>
    <br>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <form id="queryForm" name="queryForm">
            <!-- ��ϲ�ѯͳ������һ -->
            <input type="hidden" value="miscRpt01" id="rptType" name="rptType"/>
            <tr>
                <td width="30%" nowrap="nowrap" class="lbl_right_padding">������ʼ����</td>
                <td width="70%" nowrap="nowrap" class="data_input"><input type="text" id="startDate"
                                                                          name="startDate"   readonly="readonly"   
                                                                          <%--onClick="WdatePicker()"--%>
                                                                          fieldType="date" size="40" value= <%=startDate%>></td>
            </tr>
            <tr>
                <td width="30%" nowrap="nowrap" class="lbl_right_padding">�����������</td>
                <td width="70%" nowrap="nowrap" class="data_input"><input type="text" id="endDate"
                                                                          name="endDate"
                                                                          onClick="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
                                                                          fieldType="date" size="40" value= <%=endDate%>></td>
            </tr>
            <tr>
                <td colspan="4" nowrap="nowrap" align="center" style="padding:20px">
                    <input name="expExcel" class="buttonGrooveDisableExcel" type="button"
                           onClick="loanTab_expExcel_click()" value="����excel">
                    <input type="reset" value="����" class="buttonGrooveDisable">
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
        <H2>����˵��</H2>
    </DIV>                              
    <div class="help-info">
        <ul>
            <li>2010��8��27��(��)֮ǰ�ı�������Ϊ�ֹ�������2010��8��28��(��)֮��ı���Ϊ�Զ����ɵ�����.</li>
            <li>����Ĭ�Ͽ�ʼ����Ϊ����ȵ�һ�죬�����Ը���.</li>
            <li>����Ĭ�Ͻ�������Ϊ��ǰ���ڵ�ǰһ�죬���޸�.</li>
            <li>���ɱ��������ʾ��#VALUE!���ȴ�����Ϣ(EXCEl�汾ԭ��)���밴CTRL+ALT+F9�����б���������¼���.</li>
        </ul>
    </div>
</div>


</body>
</html>