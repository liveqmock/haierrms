<!--
/*********************************************************************
* ��������: SBU
* ����:
* ��������: 2010/03/02
* �޸���:
* �޸�����:
* ��Ȩ:
***********************************************************************/
-->
<%@page contentType="text/html; charset=GBK" %>
<%@ page import="haier.rms.sbureport.*" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="jxl.WorkbookSettings" %>
<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="net.sf.jxls.report.ReportManager" %>
<%@ page import="net.sf.jxls.report.ReportManagerImpl" %>
<%@ page import="net.sf.jxls.transformer.XLSTransformer" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="pub.platform.advance.utils.PropertyManager" %>
<%@ page import="pub.platform.db.ConnectionManager" %>
<%@ page import="pub.platform.db.DatabaseConnection" %>
<%@ page import="pub.platform.form.config.SystemAttributeNames" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%
    Log logger = LogFactory.getLog("balanceRpt.jsp");
    OperatorManager omgr = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);

    try {
        do {
            String startDate = request.getParameter("startDate");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if (startDate == null) {

                Date date = new Date();
                startDate = df.format(date);
            }

            // 20100512 zhan ����2010��5���Ժ��������4��30��֮ǰ�ֹ�����Ϊ����
            // 20100830 zhan ����2010��8��28���Ժ��������8��27��֮ǰ�ֹ�����Ϊ����
            String baseStartDate = "2010-08-28";
            if (startDate.substring(0, 4).equals("2010")) {
                startDate = baseStartDate;
            }

            String endDate = request.getParameter("endDate");
            if (endDate == null) {
                Date date = new Date();
                endDate = df.format(date);
            }

            if (endDate.compareTo(baseStartDate) < 0) {
                // �������
                response.reset();
                response.setContentType("application/vnd.ms-excel");
                String strfile = "sbu201008.xls";
                response.addHeader("Content-Disposition", "attachment; filename=" + strfile);


                String rptModelPath = PropertyManager.getProperty("REPORT_ROOTPATH");
                String fileName = rptModelPath + strfile;
//                String path = application.getRealPath(request.getRequestURI());
//                String templateFileName = dir + "\\Haierrms_Web\\report\\" + "sbutoplevel.xls";
                File file = new File(fileName);
                // �ж�ģ���Ƿ����,���������˳�
                if (!file.exists()) {
                    out.println(fileName + PropertyManager.getProperty("304"));
                    break;
                }

                WorkbookSettings setting = new WorkbookSettings();
                Locale locale = new Locale("zh", "CN");
                setting.setLocale(locale);
                setting.setEncoding("ISO-8859-1");
                // �õ�excel��sheet
                Workbook rw = Workbook.getWorkbook(file, setting);
                WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream(), rw, setting);
                wwb.write();
                wwb.close();
                rw.close();
                break; //�˳�
            }

            String startDate_p = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
            String endDate_p = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

            /*
            1������SBURPT���б��������ɵı�������

            2�������˳��Ŵ�ϵͳ����
            3�����������Ŵ�ϵͳ����
            4�������ֹ�̨������

            5��ֱ�Ӵ���һ����������
             */

            IMainETL langchao_loan = new LangChaoLoanDataETL();
            //ɾ����������
            ISbuRptUtil sbuutil = new SbuRptUtil();
            sbuutil.deleteCurrDateData(endDate_p);

            //���»�ȡ��������
            //�����ڴ��� 5013
            //�����ڷ��ز�����  5015
            //���ڷ��ز������������⣩  5240
            //���Ŵ�-��ͻ�ҵ�����루�����⣩ 1501
            //���Ŵ�-ר����ҵ�����루�����⣩ 1502
            //��ҵ������ҵ�����루�����⣩ ����5211 ����5212

            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5013");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5015");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "1501");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "1502");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5211");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5240");

            //����ͬ������
            double lastyear_5013 = langchao_loan.getLastYearTopLevelData(endDate_p, "5013");
            double lastyear_5015 = langchao_loan.getLastYearTopLevelData(endDate_p, "5015");
            double lastyear_5240 = langchao_loan.getLastYearTopLevelData(endDate_p, "5240");
            double lastyear_1501 = langchao_loan.getLastYearTopLevelData(endDate_p, "1501");
            double lastyear_1502 = langchao_loan.getLastYearTopLevelData(endDate_p, "1502");
            double lastyear_5211 = langchao_loan.getLastYearTopLevelData(endDate_p, "5211");

            //���»�ȡ��������
            IMainETL langchao_txtz = new LangChaoTxtzDataETL();
            //���� �����ڲ�
            langchao_txtz.processCurrYearData(startDate_p, endDate_p, "5011");
            //���� �����ⲿ
            langchao_txtz.processCurrYearData(startDate_p, endDate_p, "5212");
            //����ͬ������
            double lastyear_5011 = langchao_txtz.getLastYearTopLevelData(endDate_p, "5011");
            double lastyear_5212 = langchao_txtz.getLastYearTopLevelData(endDate_p, "5212");

            /*
            ���������Ŵ�ϵͳ����
            */
            IMainETL xfxd = new XFXDDataETL();
            xfxd.processCurrYearData(startDate_p, endDate_p, "5014");
            xfxd.processCurrYearData(startDate_p, endDate_p, "5037");


            /*
            �����ֹ�̨������
             */
            //����Ͷ������ҵ������ 5050
            IMainETL excel_invest = new ExcelInvestDataETL();
            excel_invest.processCurrYearData(startDate_p, endDate_p, "5050");
            double lastyear_5050 = excel_invest.getLastYearTopLevelData(endDate_p, "5050");

            //�������ҵ������  5230
            IMainETL excel_int = new ExcelIntDataETL();
            excel_int.processCurrYearData(startDate_p, endDate_p, "5230");
            double lastyear_5230 = excel_int.getLastYearTopLevelData(endDate_p, "5230");

            //�������ҵ������֮����ҵ������ 5231
            excel_int.processCurrYearData(startDate_p, endDate_p, "5231");
            double lastyear_5231 = excel_int.getLastYearTopLevelData(endDate_p, "5231");

            //��������ҵ������  5220
            IMainETL excel_other = new ExcelOtherDataETL();
            excel_other.processCurrYearData(startDate_p, endDate_p, "5220");
            double lastyear_5220 = excel_other.getLastYearTopLevelData(endDate_p, "5220");


            /*
           ֱ�Ӽ���һ�����е�Ӫҵ֧�����ݣ�6001��6002��
            */
            TopLevelDataETL etl_top = new TopLevelDataETL();
            double top6001_year = etl_top.getTopLevelData(endDate_p, "6001");
            double top6002_year = etl_top.getTopLevelData(endDate_p, "6002");


            //========================================================================
            DatabaseConnection conn = ConnectionManager.getInstance().get();

            Map beans = new HashMap();
            ReportManager reportManager = new ReportManagerImpl(conn.getConnection(), beans);

            beans.put("rm", reportManager);

            beans.put("date", endDate.substring(0, 4) + "/" + endDate.substring(5, 7) + "/" + endDate.substring(8, 10));

            Calendar cal = Calendar.getInstance();
            cal.clear();
            Date currentdate = df.parse(endDate);
            cal.setTime(currentdate);
            beans.put("totaldays", cal.get(cal.DAY_OF_YEAR));
            beans.put("yeardays", cal.getActualMaximum(cal.DAY_OF_YEAR));
            beans.put("monthdays", cal.getActualMaximum(cal.DAY_OF_MONTH));

            // ��ý�����һ�ܵĵڼ��죬�������ǵ�һ�죬���ڶ��ǵڶ���......
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;   //���һ��Ϊ��һ�� �����1
            if (dayOfWeek == 0) {
                dayOfWeek = 7;
            }
            beans.put("weekdays", dayOfWeek);

            //�����ڴ���
            beans.put("lastyear_5013", lastyear_5013);
            //������Ϣ���루�����ڣ�
            beans.put("lastyear_5011", lastyear_5011);
            //�����ڷ��ز�����
            beans.put("lastyear_5015", lastyear_5015);
            //���ڷ��ز������������⣩
            beans.put("lastyear_5240", lastyear_5240);
            //���Ŵ�-��ͻ�ҵ�����루�����⣩
            beans.put("lastyear_1501", lastyear_1501);
            //���Ŵ�-ר����ҵ�����루�����⣩
            beans.put("lastyear_1502", lastyear_1502);
            //��ҵ������ҵ�����루�����⣩
            beans.put("lastyear_52115212", lastyear_5211  + lastyear_5212);

            //TODO �����Ŵ�ҵ�����루�����⣩
            beans.put("lastyear_50145037", 0.00);

            //�ֹ�̨�����ݴ���
            beans.put("lastyear_5050", lastyear_5050);
            beans.put("lastyear_5230", lastyear_5230);
            beans.put("lastyear_5231", lastyear_5231);
            beans.put("lastyear_5220", lastyear_5220);

            //һ���� Ӫҵ֧�� 6001 6002  ����ʵ��
            beans.put("top6001_year", top6001_year);
            beans.put("top6002_year", top6002_year);

            //����SBURPT��ʱʹ�õ����ڲ���
            beans.put("sburptdate", endDate_p);
            //==============================================
            //�õ�����ģ��
//            String rptModelPath = PropertyManager.getProperty("REPORT_ROOTPATH");
//            String templateFileName = rptModelPath + "sbutoplevel.xls";
//             String path=application.getRealPath(request.getRequestURI());
            String path = application.getRealPath("/");
            String dir = new File(path).getParent();

            String templateFileName = dir + "\\Haierrms_Web\\report\\" + "sbutoplevel.xls";
            File file = new File(templateFileName);
            // �ж�ģ���Ƿ����,���������˳�
            if (!file.exists()) {
                out.println(templateFileName + PropertyManager.getProperty("304"));
                break;
            }

            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=" + "report.xls");

            XLSTransformer transformer = new XLSTransformer();
            InputStream is = new BufferedInputStream(new FileInputStream(templateFileName));
            HSSFWorkbook workbook = transformer.transformXLS(is, beans);
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            is.close();
            os.flush();
            os.close();


            //--------------------------------�������-----------------------------------------------------------------------------
            // �������
            out.flush();
            out.close();
        } while (false);
    } catch (Exception e) {
        e.printStackTrace();
        out.println("����������������ϵϵͳ������Ա��");
        out.flush();
        out.close();
    }
%>
