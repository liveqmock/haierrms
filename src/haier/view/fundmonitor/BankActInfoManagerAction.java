package haier.view.fundmonitor;

import haier.repository.model.fundmonitor.MtActbalBean;
import haier.repository.model.fundmonitor.MtActtypeUIBean;
import haier.service.fundmonitor.ActInfoManagerService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 银行账户信息维护.
 * User: zhanrui
 * Date: 11-6-17
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
//@SessionScoped
@ViewScoped
public class BankActInfoManagerAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BankActInfoManagerAction.class);
    private static final long serialVersionUID = 7741440602037366151L;

    private static final String BANKCD_SBS = "999";
    private static final String BANKCD_CCB = "105";


    private String queryType;
    private String reportFileName;
    //UI表格标题
    private String title;


    @ManagedProperty(value = "#{actInfoManagerService}")
    private ActInfoManagerService actInfoManagerService;
    private String bankcd;

    private String[] categorys = {"A", "H", "F", "D", "W"};
    private SelectItem[] categoryOptions;

    private MtActbalBean selectedActbalBean;
    private List<MtActtypeUIBean> mtActtypeUIBeanList = new ArrayList<MtActtypeUIBean>();

    private DataTable dataTableWidget;

    //==============================
    @PostConstruct
    public void postConstruct() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        //    取参数列表
        this.bankcd = request.getQueryString();
        if (StringUtils.isEmpty(this.bankcd)) {
            this.bankcd = "105";
        }

        //==
        this.categoryOptions = createFilterOptions(categorys);

        //===========
        //this.mtActtypeList = actInfoManagerService.selectActtypeList(BANKCD_CCB);
        this.mtActtypeUIBeanList = actInfoManagerService.selectActtypeUIList(BANKCD_CCB);

    }

    @PreDestroy
    public void saveData() {
        logger.info("aaa");
    }

    public String onSaveData(RowEditEvent event) {
        MtActtypeUIBean mtActtype = (MtActtypeUIBean) event.getObject();
        if (StringUtils.isEmpty(mtActtype.getCategory())) {
            MessageUtil.addError("请选定类别。");
            return null;
        }
        try {
            actInfoManagerService.insertActtypeRecord(mtActtype, BANKCD_CCB);
            MessageUtil.addInfo("记录处理成功");
        } catch (Exception e) {
            logger.error("增加记录时出现错误",e);
            MessageUtil.addError("增加记录时出现错误,可能帐号重复。");
        }
        return null;
    }



    public String onAddRecord() {
        MtActtypeUIBean mtActtypeUIBean = new MtActtypeUIBean();
//        mtActtypeUIBean.setActno("999999");
        this.mtActtypeUIBeanList.add(mtActtypeUIBean);

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

    //============================================getter/setter=================================

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


    public void setActInfoManagerService(ActInfoManagerService actInfoManagerService) {
        this.actInfoManagerService = actInfoManagerService;
    }

    public DataTable getDataTableWidget() {
        return dataTableWidget;
    }

    public void setDataTableWidget(DataTable dataTableWidget) {
        this.dataTableWidget = dataTableWidget;
    }

    public List<MtActtypeUIBean> getMtActtypeUIBeanList() {
        return mtActtypeUIBeanList;
    }

    public void setMtActtypeUIBeanList(List<MtActtypeUIBean> mtActtypeUIBeanList) {
        this.mtActtypeUIBeanList = mtActtypeUIBeanList;
    }
}
