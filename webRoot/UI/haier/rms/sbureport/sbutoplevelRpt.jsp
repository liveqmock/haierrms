<!--
/*********************************************************************
* 功能描述: SBU
* 作者:
* 开发日期: 2010/03/02
* 修改人:
* 修改日期:
* 版权:
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

            // 20100512 zhan 对于2010年5月以后的数据以4月30日之前手工数据为基础
            // 20100830 zhan 对于2010年8月28日以后的数据以8月27日之前手工数据为基础
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
                // 输出报表
                response.reset();
                response.setContentType("application/vnd.ms-excel");
                String strfile = "sbu201008.xls";
                response.addHeader("Content-Disposition", "attachment; filename=" + strfile);


                String rptModelPath = PropertyManager.getProperty("REPORT_ROOTPATH");
                String fileName = rptModelPath + strfile;
//                String path = application.getRealPath(request.getRequestURI());
//                String templateFileName = dir + "\\Haierrms_Web\\report\\" + "sbutoplevel.xls";
                File file = new File(fileName);
                // 判断模板是否存在,不存在则退出
                if (!file.exists()) {
                    out.println(fileName + PropertyManager.getProperty("304"));
                    break;
                }

                WorkbookSettings setting = new WorkbookSettings();
                Locale locale = new Locale("zh", "CN");
                setting.setLocale(locale);
                setting.setEncoding("ISO-8859-1");
                // 得到excel的sheet
                Workbook rw = Workbook.getWorkbook(file, setting);
                WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream(), rw, setting);
                wwb.write();
                wwb.close();
                rw.close();
                break; //退出
            }

            String startDate_p = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
            String endDate_p = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

            /*
            1、清理SBURPT表中本日已生成的报表数据

            2、处理浪潮信贷系统数据
            3、处理消费信贷系统数据
            4、处理手工台帐数据

            5、直接处理一级表报表数据
             */

            IMainETL langchao_loan = new LangChaoLoanDataETL();
            //删除当日数据
            ISbuRptUtil sbuutil = new SbuRptUtil();
            sbuutil.deleteCurrDateData(endDate_p);

            //重新获取贷款数据
            //集团内贷款 5013
            //集团内房地产开发  5015
            //金融房地产开发（集团外）  5240
            //买方信贷-大客户业务收入（集团外） 1501
            //买方信贷-专卖店业务收入（集团外） 1502
            //产业链融资业务收入（集团外） 贷款5211 贴现5212

            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5013");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5015");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "1501");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "1502");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5211");
            langchao_loan.processCurrYearData(startDate_p, endDate_p, "5240");

            //上年同期数据
            double lastyear_5013 = langchao_loan.getLastYearTopLevelData(endDate_p, "5013");
            double lastyear_5015 = langchao_loan.getLastYearTopLevelData(endDate_p, "5015");
            double lastyear_5240 = langchao_loan.getLastYearTopLevelData(endDate_p, "5240");
            double lastyear_1501 = langchao_loan.getLastYearTopLevelData(endDate_p, "1501");
            double lastyear_1502 = langchao_loan.getLastYearTopLevelData(endDate_p, "1502");
            double lastyear_5211 = langchao_loan.getLastYearTopLevelData(endDate_p, "5211");

            //重新获取贴现数据
            IMainETL langchao_txtz = new LangChaoTxtzDataETL();
            //贴现 集团内部
            langchao_txtz.processCurrYearData(startDate_p, endDate_p, "5011");
            //贴现 集团外部
            langchao_txtz.processCurrYearData(startDate_p, endDate_p, "5212");
            //上年同期数据
            double lastyear_5011 = langchao_txtz.getLastYearTopLevelData(endDate_p, "5011");
            double lastyear_5212 = langchao_txtz.getLastYearTopLevelData(endDate_p, "5212");

            /*
            处理消费信贷系统数据
            */
            IMainETL xfxd = new XFXDDataETL();
            xfxd.processCurrYearData(startDate_p, endDate_p, "5014");
            xfxd.processCurrYearData(startDate_p, endDate_p, "5037");


            /*
            处理手工台帐数据
             */
            //处理投资银行业务数据 5050
            IMainETL excel_invest = new ExcelInvestDataETL();
            excel_invest.processCurrYearData(startDate_p, endDate_p, "5050");
            double lastyear_5050 = excel_invest.getLastYearTopLevelData(endDate_p, "5050");

            //处理国际业务数据  5230
            IMainETL excel_int = new ExcelIntDataETL();
            excel_int.processCurrYearData(startDate_p, endDate_p, "5230");
            double lastyear_5230 = excel_int.getLastYearTopLevelData(endDate_p, "5230");

            //处理国际业务数据之境外业务收入 5231
            excel_int.processCurrYearData(startDate_p, endDate_p, "5231");
            double lastyear_5231 = excel_int.getLastYearTopLevelData(endDate_p, "5231");

            //处理其他业务数据  5220
            IMainETL excel_other = new ExcelOtherDataETL();
            excel_other.processCurrYearData(startDate_p, endDate_p, "5220");
            double lastyear_5220 = excel_other.getLastYearTopLevelData(endDate_p, "5220");


            /*
           直接计算一级表中的营业支出数据（6001、6002）
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

            // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;   //礼拜一作为第一天 这里减1
            if (dayOfWeek == 0) {
                dayOfWeek = 7;
            }
            beans.put("weekdays", dayOfWeek);

            //集团内贷款
            beans.put("lastyear_5013", lastyear_5013);
            //贴现利息收入（集团内）
            beans.put("lastyear_5011", lastyear_5011);
            //集团内房地产开发
            beans.put("lastyear_5015", lastyear_5015);
            //金融房地产开发（集团外）
            beans.put("lastyear_5240", lastyear_5240);
            //买方信贷-大客户业务收入（集团外）
            beans.put("lastyear_1501", lastyear_1501);
            //买方信贷-专卖店业务收入（集团外）
            beans.put("lastyear_1502", lastyear_1502);
            //产业链融资业务收入（集团外）
            beans.put("lastyear_52115212", lastyear_5211  + lastyear_5212);

            //TODO 消费信贷业务收入（集团外）
            beans.put("lastyear_50145037", 0.00);

            //手工台帐数据处理
            beans.put("lastyear_5050", lastyear_5050);
            beans.put("lastyear_5230", lastyear_5230);
            beans.put("lastyear_5231", lastyear_5231);
            beans.put("lastyear_5220", lastyear_5220);

            //一级表 营业支出 6001 6002  本年实际
            beans.put("top6001_year", top6001_year);
            beans.put("top6002_year", top6002_year);

            //处理SBURPT表时使用的日期参数
            beans.put("sburptdate", endDate_p);
            //==============================================
            //得到报表模板
//            String rptModelPath = PropertyManager.getProperty("REPORT_ROOTPATH");
//            String templateFileName = rptModelPath + "sbutoplevel.xls";
//             String path=application.getRealPath(request.getRequestURI());
            String path = application.getRealPath("/");
            String dir = new File(path).getParent();

            String templateFileName = dir + "\\Haierrms_Web\\report\\" + "sbutoplevel.xls";
            File file = new File(templateFileName);
            // 判断模板是否存在,不存在则退出
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


            //--------------------------------输出报表-----------------------------------------------------------------------------
            // 输出报表
            out.flush();
            out.close();
        } while (false);
    } catch (Exception e) {
        e.printStackTrace();
        out.println("报表生成有误，请联系系统管理人员。");
        out.flush();
        out.close();
    }
%>
