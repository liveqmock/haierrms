package haier.rms.sbs.balance;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T8123.T8123Handler;
import haier.activemq.service.sbs.txn.T8123.T8123SOFDataDetail;
import haier.repository.model.MtActtype;
import haier.repository.model.RmsSbsactattr;
import haier.rms.sbs.balance.sbs5834.T5834Service;
import haier.rms.sbs.balance.sbs8121.T8121ResponseRecord;
import haier.rms.sbs.balance.sbs8121.T8121Service;
import haier.service.rms.sbsbatch.ActbalService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;
import pub.platform.db.RecordSet;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-1-17
 * Time: ����3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class BalanceQryAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BalanceQryAction.class);
    private static final long serialVersionUID = -2848018355778876614L;

    private List<BalanceBean> detlList = new ArrayList<BalanceBean>();
    private List<RmsSbsactattr> actattrList = new ArrayList<RmsSbsactattr>();
    private List<RoeBean> roeList = new ArrayList<RoeBean>();
    private int totalcount = 0;
    private BigDecimal totalamt = new BigDecimal(0);

    private String actnum;
    private String startdate;
    private String enddate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private Map<String, RoeBean> roeMap = new HashMap<String, RoeBean>();

    private String[] acttypes = {"����", "����", "��֤��"};
    private String[] currencys = {"�����", "��Ԫ", "Ӣ��"};
    private SelectItem[] acttypeOptions;
    private SelectItem[] currencyOptions;
    private String queryType;
    private String reportFileName;
    //UI������
    private String title;

    @ManagedProperty(value = "#{actbalService}")
    private ActbalService actbalService;


    public List<BalanceBean> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<BalanceBean> detlList) {
        this.detlList = detlList;
    }

    public int getTotalcount() {
        return this.detlList.size();
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public BigDecimal getTotalamt() {
        this.totalamt = new BigDecimal("0");
        for (BalanceBean record : this.detlList) {
            totalamt = totalamt.add(record.rmbbal);
        }
        return totalamt;

    }

    public void setTotalamt(BigDecimal totalamt) {
        this.totalamt = totalamt;
    }

    public String getActnum() {
        return actnum;
    }

    public void setActnum(String actnum) {
        this.actnum = actnum;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public SelectItem[] getActtypeOptions() {
        return acttypeOptions;
    }

    public SelectItem[] getCurrencyOptions() {
        return currencyOptions;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActbalService(ActbalService actbalService) {
        this.actbalService = actbalService;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    //==============================
    @PostConstruct
    public void postConstruct() {

        Date date = new Date();
        String currDate = sdf.format(date);
        this.startdate = currDate.substring(0, 6) + "01";
        this.enddate = currDate;
        //query();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        //    ȡ�����б�
        this.queryType = request.getQueryString();
        if (StringUtils.isEmpty(this.queryType)) {
            this.queryType = "C";
        }
        if (this.queryType.equals("A")) {
            this.title = "A����ҵ���ʵʱ��ѯ�б� ";
        }
        if (this.queryType.equals("H")) {
            this.title = "H����ҵ���ʵʱ��ѯ�б� ";
        }
        if (this.queryType.equals("C")) {
            this.title = "ȫ����ҵ���ʵʱ��ѯ�б� ";
        }

        this.acttypeOptions = createFilterOptions(acttypes);
        this.currencyOptions = createFilterOptions(currencys);

        this.actattrList = actbalService.selectActattrList();

    }

    public String exportExcel() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (this.detlList.size() == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "���Ȳ�ѯ���ݡ�", ""));
            return null;
        }
        //����excel�ļ�
        this.reportFileName = createExcelTempFile();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ExcelFileName", this.reportFileName);
        return null;
    }

    public String query() {
        try {
            roeMap = new HashMap<String, RoeBean>();

/*
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:pdt");
            dataTable.setFirst(0);
            dataTable.setPage(1);
*/
            queryRecordsFromSBS_new();

            this.currencyOptions = createFilterOptions(createCurrencyArray());

        } catch (Exception e) {
            logger.error("��ѯʱ���ִ���", e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "��ѯʱ���ִ���", "�������ݿ�������⡣"));
        }
        return null;
    }

    public String reset() {
        //this.detlRecord = new T8121ResponseRecord();
        return null;
    }


    private void queryRecordsFromSBS() {

        T8121Service t8121service = new T8121Service();

        List<String> requestList = new ArrayList<String>();

        //�������̶�ֵ
        requestList.add(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        requestList.add(this.queryType);

        t8121service.setRequestParam(requestList);

        try {
            t8121service.querySBS();
            List<T8121ResponseRecord> responseList = t8121service.getReponseRecords();
            detlList = transformResponseRecords(responseList);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            List<String> msgs = t8121service.getMsgs();
            if (msgs.size() > 0) {
                for (String msg : msgs) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.toString(), null));
            }
            logger.error("SBS��ѯ����쳣", e);
        }


    }

    private List<BalanceBean> transformResponseRecords(List<T8121ResponseRecord> responseList) {
        List<BalanceBean> balList = new ArrayList<BalanceBean>();
        BigDecimal bd100 = new BigDecimal("100");
        for (T8121ResponseRecord respRecord : responseList) {
            BalanceBean balRecord = new BalanceBean();

            String cusidt = respRecord.getCusidt();
            String actno = cusidt.substring(0, 4) + "-" + cusidt.substring(4, 11) + "-" + cusidt.substring(11, 15) + "-" + cusidt.substring(15, 18);
            balRecord.setActno(actno);
            String actname = respRecord.getApcode();
            balRecord.setActname(actname);

            BigDecimal homecurbal = new BigDecimal(respRecord.getLasbal());
            balRecord.setHomecurbal(homecurbal);
            balRecord.setActtype(respRecord.getCurcde().trim());
            int acttype = Integer.parseInt(respRecord.getCurcde().trim());
            if (acttype >= 1 && acttype <= 3) {
                balRecord.setActtypename(this.acttypes[acttype - 1]);
            }

            String currcode = cusidt.substring(15, 18);
            balRecord.setCurrcode(currcode);

            processRoeMap(currcode);
            RoeBean roebean = this.roeMap.get(currcode);
            balRecord.setCurrname(roebean.getCurrname());
            balRecord.setRoe(roebean.getRate());

            //�����Ӧ����
            if (currcode.equals("001")) {
                balRecord.setRmbbal(homecurbal);
            } else {
                balRecord.setRmbbal(homecurbal.divide(bd100).multiply(roebean.getRate()));
            }

            //���ּ��������ʻ�
            for (RmsSbsactattr rmsSbsactattr : this.actattrList) {
                if (rmsSbsactattr.getActname().equals(actname.trim())) {
                    balRecord.setActattr(rmsSbsactattr.getActattr());
                    break;
                }
            }
            balList.add(balRecord);
        }
        return balList;
    }

    private void queryRecordsFromSBS_new() {
        try {
            SBSResponse4MultiRecord response = processSbsTxn_8123("1");
            List<SOFDataDetail> sofDataDetailList = response.getSofDataDetailList();
            response = processSbsTxn_8123("3");
            sofDataDetailList.addAll(response.getSofDataDetailList());

            List<SOFDataDetail> responseList = new ArrayList<SOFDataDetail>();

            if (this.queryType.equals("A") || this.queryType.equals("H")) {
                List<MtActtype> mtActtypeList = actbalService.selectActtype4SbsActList(this.queryType);
                for (SOFDataDetail sofDataDetail : sofDataDetailList) {
                    String actno = "8010" + ((T8123SOFDataDetail) sofDataDetail).getActnum();
                    for (MtActtype mtActtype : mtActtypeList) {
                        if (actno.equals(mtActtype.getActno())) {
                            responseList.add(sofDataDetail);
                            break;
                        }
                    }
                }
            }
            if (this.queryType.equals("C")) {
                responseList = sofDataDetailList;
            }
            detlList = transformResponseRecords_New(responseList);
        } catch (Exception e) {
            logger.error("SBS��ѯ����쳣", e);
            MessageUtil.addError("SBS��ѯ����쳣");
        }
    }

    private List<BalanceBean> transformResponseRecords_New(List<SOFDataDetail> responseList) {
        //��ȡSBS�˺����Զ����嵥
        List<MtActtype> acttypeList =  actbalService.selectActtype4SbsActList();

        List<BalanceBean> balList = new ArrayList<BalanceBean>();
        BigDecimal bd100 = new BigDecimal("100");
        for (SOFDataDetail respRecord : responseList) {
            BalanceBean balRecord = new BalanceBean();

            String cusidt = "8010" + ((T8123SOFDataDetail) respRecord).getActnum();
            String actno = cusidt.substring(0, 4) + "-" + cusidt.substring(4, 11) + "-" + cusidt.substring(11, 15) + "-" + cusidt.substring(15, 18);
            balRecord.setActno(actno);
            String actname = ((T8123SOFDataDetail) respRecord).getActnam();
            balRecord.setActname(actname);

            BigDecimal homecurbal = new BigDecimal(((T8123SOFDataDetail) respRecord).getActbal().replaceAll("(\\,|\\s+)", ""));
            balRecord.setHomecurbal(homecurbal);

            //1-����,2-����,3-���,9-����
            String sbsActtype = ((T8123SOFDataDetail) respRecord).getActtyp().trim();
            //1-��֤�� 0-�Ǳ�֤��
            String sbsAsstype = ((T8123SOFDataDetail) respRecord).getAsstyp().trim();

            if (sbsAsstype.equals("1")) {
                balRecord.setActtype("3");//��֤��
                balRecord.setActtypename(this.acttypes[2]);
            } else {
                if (sbsActtype.equals("1")) {
                    balRecord.setActtype("2");//����
                    balRecord.setActtypename(this.acttypes[1]);
                } else if (sbsActtype.equals("3")) {
                    balRecord.setActtype("1"); //����
                    balRecord.setActtypename(this.acttypes[0]);
                }
            }

            String currcode = cusidt.substring(15, 18);
            balRecord.setCurrcode(currcode);

            processRoeMap(currcode);
            RoeBean roebean = this.roeMap.get(currcode);
            balRecord.setCurrname(roebean.getCurrname());
            balRecord.setRoe(roebean.getRate());

            //�����Ӧ����
            if (currcode.equals("001")) {
                balRecord.setRmbbal(homecurbal);
            } else {
                balRecord.setRmbbal(homecurbal.divide(bd100).multiply(roebean.getRate()));
            }

            //���ּ��������ʻ�
            //for (RmsSbsactattr rmsSbsactattr : this.actattrList) {
            //    if (rmsSbsactattr.getActname().equals(actname.trim())) {
            //        balRecord.setActattr(rmsSbsactattr.getActattr());
            //        break;
            //   }
            //}

            for (MtActtype mtActtype : acttypeList) {
                if (mtActtype.getActno().equals(cusidt.trim())) {
                    balRecord.setActattr(mtActtype.getCategory());
                    break;
                }
            }


            balList.add(balRecord);
        }
        return balList;
    }


//=======================sbs interface  begin==============================================

    /**
     * SBS �ӿ�
     *
     * @param type 1-����,2-����,3-���,9-����
     * @return
     */
    private SBSResponse4MultiRecord processSbsTxn_8123(String type) {
        T8123Handler handler = new T8123Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("       ");//7λ�ͻ���
        paramList.add("    ");//4λ������
        paramList.add("   ");//3λ�ұ�
        paramList.add(type);//1λ�ʻ�����
        paramList.add("1");//1����λ 2������

        SBSRequest request = new SBSRequest("8123", paramList);
        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();

        SOFDataDetail sofDataDetail = new T8123SOFDataDetail();
        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);

        String formcode = response.getFormcode();
        if (!formcode.substring(0, 1).equals("T")) {
            String forminfo = response.getForminfo();
            throw new RuntimeException("�����쳣��" + formcode + (forminfo == null ? " " : forminfo) + PropertyManager.getProperty(formcode));
        }
        return response;
    }
//=======================sbs interface  end==============================================


    /**
     * ��SBS�л�ȡ��ػ�����Ϣ �ӱ��ر��л�ȡ��������
     *
     * @param currcode
     */
    private void processRoeMap(String currcode) {
        if (!this.roeMap.containsKey(currcode)) {
            RoeBean roebean = new RoeBean();
            roebean.setCurrcode(currcode);
            roebean.setCurrname(getCurrNameFromDB(currcode));
            if (currcode.equals("001")) {
                roebean.setRate(new BigDecimal("100.00"));
            } else {
                roebean.setRate(getRoeFromSBS(currcode));
            }
            this.roeMap.put(currcode, roebean);
        }
    }

    private String getCurrNameFromDB(String currcode) {
        if (currcode == null) {
            return currcode;
        }
        String currname = "";
        ConnectionManager cm = ConnectionManager.getInstance();
        try {
            DatabaseConnection conn = cm.getConnection();
            RecordSet rs = conn.executeQuery("select curnmc from sbs_actccy where curcde = '" + currcode + "'");
            while (rs.next()) {
                currname = rs.getString("curnmc");
            }
            return currname;
        } finally {
            cm.release();
        }
    }

    private BigDecimal getRoeFromSBS(String currcode) {
        String curdate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        T5834Service sbs = new T5834Service();
        String strRoe = "";
        try {
            strRoe = sbs.querySBS(curdate, currcode, "001");
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            List<String> msgs = sbs.getMsgs();
            if (msgs.size() > 0) {
                for (String msg : msgs) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
        }

        if (StringUtils.isEmpty(strRoe.trim())) {
            return new BigDecimal("0");
        } else
            return new BigDecimal(deleteStr(strRoe.trim(), ","));
    }

    /**
     * ��Դ����ɾ��ָ���ַ������������滻��Ľ���ַ���
     *
     * @param source    Դ�ַ���
     * @param subString Ҫɾ�����ַ�
     * @return �滻����ַ���
     */
    public String deleteStr(String source, String subString) {
        StringBuffer output = new StringBuffer();
        //Դ�ַ�������
        int lengthOfSource = source.length();
        //��ʼ����λ��
        int posStart = 0;
        //������Դ�ַ�����λ��
        int pos;
        while ((pos = source.indexOf(subString, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));
            posStart = pos + 1;
        }
        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }
        return output.toString();
    }

    private SelectItem[] createFilterOptions(String[] acttypes) {
        SelectItem[] options = new SelectItem[acttypes.length + 1];

        options[0] = new SelectItem("", "��ѡ��");
        for (int i = 0; i < acttypes.length; i++) {
            options[i + 1] = new SelectItem(acttypes[i], acttypes[i]);
        }
        return options;
    }

    private String[] createCurrencyArray() {
        int i = 0;
        String[] currencys = new String[this.roeMap.size()];
        for (Map.Entry<String, RoeBean> entry : this.roeMap.entrySet()) {
            currencys[i] = entry.getValue().getCurrname();
            i++;
        }
        return currencys;
    }

    /**
     * �ѷ��� �����ο�
     *
     * @param strfileName
     */
    private static void downloadFile(String strfileName) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            //�ļ�����·��
            String excelName = PropertyManager.getProperty("REPORT_ROOTPATH") + "temp/" + strfileName;
            //String excelName = servletContext.getRealPath("/report/temp") + "/" + strfileName;
            File exportFile = new File(excelName);
            //�ж��ļ��Ƿ����
            if (!exportFile.exists()) {
                logger.error("EXCEL��ȡ����");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EXCEL�ļ������ڡ�", null));
            }
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + strfileName);
            httpServletResponse.setContentLength((int) exportFile.length());
            httpServletResponse.setContentType("application/x-download");
            // httpServletResponse.setContentType("application/vnd.ms-excel");

            byte[] b = new byte[1024];
            int i = 0;
            FileInputStream fis = new java.io.FileInputStream(exportFile);
            while ((i = fis.read(b)) > 0) {
                servletOutputStream.write(b, 0, i);
            }
        } catch (IOException e) {
            logger.error("EXCEL��ȡ����", e);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EXCEL��ȡ����", null));
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    private String createExcelTempFile() {
        List<ReportInfoBean> reportInfoBeanList = new ArrayList<ReportInfoBean>();

        for (Map.Entry<String, RoeBean> entry : this.roeMap.entrySet()) {
            ReportInfoBean reportInfoBean = new ReportInfoBean();
            String currcode = entry.getValue().getCurrcode();
            reportInfoBean.setCurrcode(currcode);
            reportInfoBean.setCurrname(entry.getValue().getCurrname());
            reportInfoBean.setRate(entry.getValue().getRate());
            int seqno = 0;
            for (BalanceBean balanceBean : this.detlList) {
                if (balanceBean.getCurrcode().equals(currcode)) {
                    BalanceBean newbean = new BalanceBean();
                    BeanUtils.copyProperties(balanceBean, newbean);
                    seqno++;
                    newbean.setSeqno(seqno);
                    setBalanceBeanReportFieldValue(balanceBean, newbean);
                    reportInfoBean.addBalanceBean(newbean);
                }
            }
            reportInfoBeanList.add(reportInfoBean);
        }
        //excel
        Map beansMap = new HashMap();

        List<ReportInfoBean> reportHomeCurrencyList = new ArrayList<ReportInfoBean>();
        List<ReportInfoBean> reportForeignCurrencyList = new ArrayList<ReportInfoBean>();
        for (ReportInfoBean reportInfoBean : reportInfoBeanList) {
            if (reportInfoBean.getCurrcode().equals("001")) {
                reportHomeCurrencyList.add(reportInfoBean);
            } else {
                reportForeignCurrencyList.add(reportInfoBean);
            }
        }


        beansMap.put("renminbilist", reportHomeCurrencyList);
        beansMap.put("forecurrlist", reportForeignCurrencyList);

        XLSTransformer transformer = new XLSTransformer();
        String reportPath = PropertyManager.getProperty("REPORT_ROOTPATH");
        String templateFileName = reportPath + "sbs/sbsallactbalance.xls";

        String excelFilename = "sbsbal_" + new SimpleDateFormat("yyyyMMdd_HHmm_").format(new Date()) + this.queryType + ".xls";
        String destFileName = reportPath + "temp/" + excelFilename;
        try {
            transformer.transformXLS(templateFileName, beansMap, destFileName);
        } catch (Exception e) {
            logger.error("execl���ɴ���", e);
            throw new RuntimeException("execl���ɴ���", e);
        }
        return excelFilename;
    }

    private void setBalanceBeanReportFieldValue(BalanceBean srcBalanceBean, BalanceBean desBalnceBean) {
        String acttype = srcBalanceBean.getActtype().trim();
        if ("1".equals(acttype)) {
            desBalnceBean.setActtype1bal(desBalnceBean.getActtype1bal().add(srcBalanceBean.getHomecurbal()));
        } else if ("2".equals(acttype)) {
            desBalnceBean.setActtype2bal(desBalnceBean.getActtype2bal().add(srcBalanceBean.getHomecurbal()));
        } else if ("3".equals(acttype)) {
            desBalnceBean.setActtype3bal(desBalnceBean.getActtype3bal().add(srcBalanceBean.getHomecurbal()));
        }
    }
}
