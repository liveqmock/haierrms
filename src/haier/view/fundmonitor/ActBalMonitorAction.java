package haier.view.fundmonitor;

import haier.repository.model.fundmonitor.MtActbalBean;
import haier.repository.model.fundmonitor.MtActbalChartBean;
import haier.repository.model.fundmonitor.MtActbalSumBean;
import haier.rms.sbs.balance.RoeBean;
import haier.service.fundmonitor.ActBalOnlineService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 实时查询SBS和商业银行企业余额.
 * User: zhanrui
 * Date: 11-6-17
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class ActBalMonitorAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ActBalMonitorAction.class);

//    private List<BalanceBean> detlList = new ArrayList<BalanceBean>();
//    private List<RmsSbsactattr> actattrList = new ArrayList<RmsSbsactattr>();
//    private List<RoeBean> roeList = new ArrayList<RoeBean>();
//    private int totalcount = 0;
//    private BigDecimal totalamt = new BigDecimal(0);

    private String actnum;
    private String startdate;
    private String enddate;
//    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private Map<String, RoeBean> roeMap = new HashMap<String, RoeBean>();

    private String[] acttypes = {"活期", "定期", "保证金"};
    private SelectItem[] currencyOptions;
    private String queryType;
    private String reportFileName;
    //UI表格标题
    private String title;


    @ManagedProperty(value = "#{actBalOnlineService}")
    private ActBalOnlineService actBalOnlineService;


    private PieChartModel pieChartModel;
    private CartesianChartModel cartesianModel;

    private List<MtActbalChartBean> mtActbalChartBeanList = new ArrayList<MtActbalChartBean>();
    private MtActbalChartBean mtActbalChartBean;

    private List<MtActbalBean> mtActbalBeanList = new ArrayList<MtActbalBean>();

    //保存最后发起查询的时间点。
    private Map<String, String> lastQrytimeMap = new HashMap<String, String>();

    private String bankcd;
    private String txndate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private String qrytime;

    private String[] categorys = {"A", "H", "F", "D", "W"};
    private SelectItem[] categoryOptions;

    private MtActbalBean selectedActbalBean;

    private DataModel dataModel;
    private UIData uiData;

    public UIData getUiData() {
        return uiData;
    }

    public void setUiData(UIData uiData) {
        this.uiData = uiData;
    }

    public DataModel getDataModel() {
        this.dataModel = new ListDataModel(this.mtActbalBeanList);
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
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

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public void setActBalOnlineService(ActBalOnlineService actBalOnlineService) {
        this.actBalOnlineService = actBalOnlineService;
    }

    public List<MtActbalChartBean> getMtActbalChartBeanList() {
        return mtActbalChartBeanList;
    }

    public void setMtActbalChartBeanList(List<MtActbalChartBean> mtActbalChartBeanList) {
        this.mtActbalChartBeanList = mtActbalChartBeanList;
    }

    public MtActbalChartBean getMtActbalChartBean() {
        return mtActbalChartBean;
    }

    public void setMtActbalChartBean(MtActbalChartBean mtActbalChartBean) {
        this.mtActbalChartBean = mtActbalChartBean;
    }

    public CartesianChartModel getCartesianModel() {
        return cartesianModel;
    }

    public void setCartesianModel(CartesianChartModel cartesianModel) {
        this.cartesianModel = cartesianModel;
    }

    public List<MtActbalBean> getMtActbalBeanList() {
        return mtActbalBeanList;
    }

    public void setMtActbalBeanList(List<MtActbalBean> mtActbalBeanList) {
        this.mtActbalBeanList = mtActbalBeanList;
    }

    public String getQrytime() {
        return qrytime;
    }

    public SelectItem[] getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(SelectItem[] categoryOptions) {
        this.categoryOptions = categoryOptions;
    }

    public MtActbalBean getSelectedActbalBean() {
        return selectedActbalBean;
    }

    public void setSelectedActbalBean(MtActbalBean selectedActbalBean) {
        this.selectedActbalBean = selectedActbalBean;
    }

    public String getBankcd() {
        return bankcd;
    }

    public PieChartModel getPieChartModel() {
        return pieChartModel;
    }

    public void setPieChartModel(PieChartModel pieChartModel) {
        this.pieChartModel = pieChartModel;
    }

    //==============================
    @PostConstruct
    public void postConstruct() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        //    取参数列表
        this.bankcd = request.getQueryString();
        if (StringUtils.isEmpty(this.bankcd)) {
            this.bankcd = "999";
        }

        //==
        this.categoryOptions = createFilterOptions(categorys);


        //==
        createCartesianModel();
        assembleChartBeanList();
        createPieModel();

        this.mtActbalBeanList = actBalOnlineService.selectActBalList(bankcd, txndate, lastQrytimeMap.get(bankcd));
        this.qrytime = lastQrytimeMap.get(bankcd);

    }

    private void createCartesianModel() {
        cartesianModel = new CartesianChartModel();

        List<MtActbalSumBean> mtActbalSumBeanList = actBalOnlineService.selectSumDataList(this.bankcd, this.txndate);

/*
        ChartSeries classA = new ChartSeries();
        ChartSeries classD = new ChartSeries();
        ChartSeries classF = new ChartSeries();
        ChartSeries classH = new ChartSeries();
        ChartSeries classW = new ChartSeries();
        classA.setLabel("类别：A");
        classD.setLabel("类别：D");
        classF.setLabel("类别：F");
        classH.setLabel("类别：H");
        classW.setLabel("类别：W");
*/

/*
        for (MtActbalSumBean mtActbalSumBean : mtActbalSumBeanList) {
            //String category = mtActbalSumBean.getCategory();
            char category = mtActbalSumBean.getCategory().charAt(0);
            long rmbamt = mtActbalSumBean.getRmbamt().longValue();
            switch (category) {
                case 'A':
                    classA.set(mtActbalSumBean.getQrytime(), rmbamt);
                    break;
                case 'D':
                    classD.set(mtActbalSumBean.getQrytime(), rmbamt);
                    break;
                case 'F':
                    classF.set(mtActbalSumBean.getQrytime(), rmbamt);
                    break;
                case 'H':
                    classH.set(mtActbalSumBean.getQrytime(), rmbamt);
                    break;
                case 'W':
                    classW.set(mtActbalSumBean.getQrytime(), rmbamt);
                    break;
                default:
            }
        }

        cartesianModel.addSeries(classA);
        cartesianModel.addSeries(classD);
        cartesianModel.addSeries(classF);
        cartesianModel.addSeries(classH);
        cartesianModel.addSeries(classW);
*/
    }


    private void assembleChartBeanList() {
        List<MtActbalSumBean> mtActbalSumBeanList = actBalOnlineService.selectSumDataList(this.bankcd, this.txndate);
        MtActbalChartBean chartBean = new MtActbalChartBean();
        for (MtActbalSumBean mtActbalSumBean : mtActbalSumBeanList) {
            if (chartBean.getQrytime() != null && !mtActbalSumBean.getQrytime().equals(chartBean.getQrytime())) {
                mtActbalChartBeanList.add(chartBean);
                chartBean = new MtActbalChartBean();
            }
            chartBean.setQrytime(mtActbalSumBean.getQrytime());
            char category = mtActbalSumBean.getCategory().charAt(0);
            BigDecimal rmbamt = mtActbalSumBean.getRmbamt();
            switch (category) {
                case 'A':
                    chartBean.setCategory1(rmbamt);
                    break;
                case 'D':
                    chartBean.setCategory2(rmbamt);
                    break;
                case 'F':
                    chartBean.setCategory3(rmbamt);
                    break;
                case 'H':
                    chartBean.setCategory4(rmbamt);
                    break;
                case 'W':
                    chartBean.setCategory5(rmbamt);
                    break;
                default:
            }
        }
        mtActbalChartBeanList.add(chartBean);

        for (MtActbalChartBean actbalChartBean : mtActbalChartBeanList) {
            actbalChartBean.setCategorySum(actbalChartBean.getCategory1()
                    .add(actbalChartBean.getCategory2())
                    .add(actbalChartBean.getCategory3())
                    .add(actbalChartBean.getCategory4())
                    .add(actbalChartBean.getCategory5())
            );
        }

        lastQrytimeMap.put(this.bankcd, chartBean.getQrytime());
    }

    private void createPieModel() {
        this.pieChartModel = new PieChartModel();
        String qrytime = lastQrytimeMap.get(this.bankcd);
        for (MtActbalChartBean chartBean : this.mtActbalChartBeanList) {
            if (chartBean.getQrytime().equals(qrytime)) {
                this.pieChartModel.set("类别 A", chartBean.getCategory1().longValue());
                this.pieChartModel.set("类别 D", chartBean.getCategory2().longValue());
                this.pieChartModel.set("类别 F", chartBean.getCategory3().longValue());
                this.pieChartModel.set("类别 H", chartBean.getCategory4().longValue());
                this.pieChartModel.set("类别 W", chartBean.getCategory5().longValue());
            }
        }
    }


    public String onRowSelectNavigate(SelectEvent event) {
        //FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCar", event.getObject());
        //return "carDetail?faces-redirect=true";
        this.qrytime = ((MtActbalChartBean) event.getObject()).getQrytime();
        this.mtActbalBeanList = actBalOnlineService.selectActBalList(bankcd, txndate, this.qrytime);

        return null;
    }

    public String onExportExcel() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (this.mtActbalBeanList.size() == 0) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "请先查询数据。", ""));
                return null;
            }

            //excel
            Map beansMap = new HashMap();
            beansMap.put("balanceBeanList", this.mtActbalBeanList);

            String reportPath = PropertyManager.getProperty("REPORT_ROOTPATH");
            String templateFileName = reportPath + "fundmonitor/actbalance.xls";

            String excelFilename = "actbal_" + new SimpleDateFormat("yyyyMMdd_").format(new Date()) + this.lastQrytimeMap.get(this.bankcd) + ".xls";

            XLSTransformer transformer = new XLSTransformer();

            InputStream is = new BufferedInputStream(new FileInputStream(templateFileName));

            //HSSFWorkbook wb = transformer.transformXLS(is, beansMap);
            Workbook wb = transformer.transformXLS(is, beansMap);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + excelFilename);
            response.setContentType("application/msexcel");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
            is.close();


//             FacesContext facesContext = FacesContext.getCurrentInstance();
//        VariableResolver vr = facesContext.getApplication().getVariableResolver();
//             ActBalMonitorAction actBalMonitorAction = (ActBalMonitorAction)vr.resolveVariable(facesContext, "actBalMonitorAction");
//            DataModel listDataModel = (DataModel)actBalMonitorAction.getUiData();
//            this.reportFileName = createExcelTempFile();

//            List<MtActbalBean> beans =  (List<MtActbalBean>)dataModel.getWrappedData();
//            for (MtActbalBean bean : beans) {
//                logger.info(bean.getActno());
//            }
//            logger.info("aaaaa" + beans.size());
//
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ExcelFileName", this.reportFileName);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        return ("balanceRpt.jsp?faces-redirect=true");
        return null;
    }

    public String query() {
        try {
            actBalOnlineService.queryAndInsertAllActBalFromSBS();

            actBalOnlineService.queryAndInsertAllActBalFromCCB();
            this.currencyOptions = createFilterOptions(createCurrencyArray());

        } catch (Exception e) {
            logger.error("查询时出现错误。", e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "查询时出现错误。" + e.getMessage(), ""));
        }
        return null;
    }

    public String reset() {
        //this.detlRecord = new T8121ResponseRecord();
        return null;
    }


    private SelectItem[] createFilterOptions(String[] acttypes) {
        SelectItem[] options = new SelectItem[acttypes.length + 1];

        options[0] = new SelectItem("", "请选择");
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


}
