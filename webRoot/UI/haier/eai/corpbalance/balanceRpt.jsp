<!--
/*********************************************************************
* 功能描述: 股票帐户余额实时查询 报表
* 作者:
* 开发日期: 2010/03/02
* 修改人:
* 修改日期:
* 版权:
***********************************************************************/
-->
<%@page contentType="text/html; charset=GBK" %>
<%@page import="pub.platform.advance.utils.PropertyManager" %>
<%@page import="pub.platform.security.OperatorManager" %>
<%@page import="pub.platform.form.config.SystemAttributeNames" %>
<%@ page import="haier.rms.dao.RMSACCTBAL" %>
<%@ page import="java.util.*" %>
<%@ page import="haier.rms.querybalance.Company" %>
<%@ page import="net.sf.jxls.transformer.XLSTransformer" %>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%
    Log logger = LogFactory.getLog("balanceRpt.jsp");
    OperatorManager omgr = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);

    try {
        do {
            String stockcd = request.getParameter("stockcd");
            if (stockcd == null) {
                logger.error("stockcd 股票类型未定义。");
                break;
            }

            List<RMSACCTBAL> rmsacctbals = new ArrayList<RMSACCTBAL>();
            List<Company> companys = new ArrayList<Company>();

            RMSACCTBAL rmsacctbal = new RMSACCTBAL();
            rmsacctbals = rmsacctbal.findByWhere(" where stockcd = '" + stockcd + "' order by apcode,cusidt");
            for (int i = 0; i < rmsacctbals.size(); i++) {
                Company company = new Company();
                company.setId(i+1);
                company.setName(rmsacctbals.get(i).getApcode());
                company.setAcct(rmsacctbals.get(i).getCusidt());
                String type = rmsacctbals.get(i).getCurcde();
                Double amt = rmsacctbals.get(i).getLasbal();
                if ("1".equals(type)) {
                    company.setSav(amt);
                    company.setFix(0.00);
                    company.setAssu(0.00);
                } else if ("2".equals(type)) {
                    company.setSav(0.00);
                    company.setFix(amt);
                    company.setAssu(0.00);
                } else {
                    company.setSav(0.00);
                    company.setFix(0.00);
                    company.setAssu(amt);
                }
                companys.add(company);
            }


            //得到报表模板
            String rptModelPath = PropertyManager.getProperty("REPORT_ROOTPATH");
            String templateFileName = rptModelPath + "stockbalance.xls";
            File file = new File(templateFileName);
            // 判断模板是否存在,不存在则退出
            if (!file.exists()) {
                out.println(templateFileName + PropertyManager.getProperty("304"));
                break;
            }

            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=" + "report.xls");

            Map beans = new HashMap();
            beans.put("companys", companys);

            XLSTransformer transformer = new XLSTransformer();
            InputStream is = new BufferedInputStream(new FileInputStream(templateFileName));
            HSSFWorkbook workbook = transformer.transformXLS(is, beans);
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            is.close();
            os.flush();
            os.close();


            // 关闭excel
//            wwb.write();
//            wwb.close();
//            rw.close();

            //--------------------------------输出报表-----------------------------------------------------------------------------
            // 输出报表
            out.flush();
            out.close();
        } while (false);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
