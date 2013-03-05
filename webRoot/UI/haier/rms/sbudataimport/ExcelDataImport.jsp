<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2010-7-5
  Time: 17:45:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.jspsmart.upload.SmartUploadException" %>
<%@ page import="haier.rms.sbureport.excelimport.DataInsert" %>
<%
    SmartUpload su = new SmartUpload();
    su.setForcePhysicalPath(true);
    su.initialize(pageContext);
    su.setMaxFileSize(10000000);     //限制上传文件大小
    su.setTotalMaxFileSize(2000000000);
    su.setAllowedFilesList("xls");
    su.setDeniedFilesList("exe,bat,jsp,htm,html");
//
//    String fm = (String)su.getRequest().getParameter("aaa");
////    String fm = request.getParameter("_post");
//    if (fm != null){
    String savePath = "D:\\rms\\temp\\";

    try{
        su.upload();
    }catch(SmartUploadException ex){
        ex.getMessage();
    }
    com.jspsmart.upload.Request req = su.getRequest();
    String datadate = req.getParameter("txtDataDate");
    datadate = datadate.replace("-","");
    //save
    com.jspsmart.upload.File f = su.getFiles().getFile(0);
    try{
        f.saveAs(savePath + f.getFileName());
    }catch(SmartUploadException ex1){
        ex1.printStackTrace();
    }
    DataInsert di = new DataInsert();
    savePath = savePath + f.getFileName();
    String success = di.runDataInsert(savePath,datadate);
    if (success.equals("0")){
    %>
        <script type="text/javascript">alert("数据成功导入。");
            document.location.href = "ExcelDataImport.html";
        </script>
<%
    } else if (success.equals("-1")) {
%>
        <script type="text/javascript">alert("导入失败，请重新导入。");
            document.location.href = "ExcelDataImport.html";
        </script>
<%
    }
%>