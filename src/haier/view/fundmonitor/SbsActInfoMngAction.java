package haier.view.fundmonitor;

import haier.repository.model.MtActtype;
import haier.service.fundmonitor.ActInfoManagerService;
import org.apache.commons.lang.StringUtils;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SBS�˻���Ϣά��.
 * User: zhanrui
 * Date: 11-6-17
 * Time: ����3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class SbsActInfoMngAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(SbsActInfoMngAction.class);

    private static final String BANKCD_SBS = "999";
    private static final String BANKCD_CCB = "105";
    private static final long serialVersionUID = 4309033869307406603L;

    private String queryType;
    private String reportFileName;
    //UI������
    private String title = "�ʻ���Ϣ�嵥";

    @ManagedProperty(value = "#{actInfoManagerService}")
    private ActInfoManagerService actInfoManagerService;

    private String[] categorys = {"A", "H", "F", "D", "W"};
    private SelectItem[] categoryOptions;

    private List<MtActtype> mtActtypeList = new ArrayList<MtActtype>();
    private MtActtype[] selectedActtypes;

    //==============================
    @PostConstruct
    public void postConstruct() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        //    ȡ�����б�
        String action = request.getParameter("action");
        if (StringUtils.isEmpty(action)) {
            try {
                context.getExternalContext().dispatch("/UI/common/error.xhtml");
                return;
            } catch (IOException e) {
                logger.error("ҳ�����δ����");
            }
        }

        //==

        this.categoryOptions = createFilterOptions(categorys);

        if (action.equals("update")) {
            this.mtActtypeList = actInfoManagerService.selectMtActtypeList();
            this.title = "�ʻ���Ϣ�嵥(��" + this.mtActtypeList.size() + "��)";
        }
    }


    public String onSaveData(RowEditEvent event) {
        MtActtype mtActtype = (MtActtype) event.getObject();
        if (StringUtils.isEmpty(mtActtype.getCategory())) {
            MessageUtil.addError("��ѡ�����");
            return null;
        }
        try {
            actInfoManagerService.updateSbsActtypeOneRecord(mtActtype);
            MessageUtil.addInfo("��¼����ɹ�");
        } catch (Exception e) {
            logger.error("���Ӽ�¼ʱ���ִ���", e);
            MessageUtil.addError("���Ӽ�¼ʱ���ִ���,�����ʺ��ظ���");
        }
        return null;
    }


    public String onSyncSbsActInfo() {

        this.mtActtypeList = actInfoManagerService.getSbsActInfoFromInterface();
        this.title = "�ʻ���Ϣ�嵥(��" + this.mtActtypeList.size() + "��)";
        return null;
    }

    public String onSetCategoryA() {
        return batchsetCategory("A");
    }

    public String onSetCategoryH() {
        return batchsetCategory("H");
    }

    public String onSetCategoryF() {
        return batchsetCategory("F");
    }

    public String onSetCategoryD() {
        return batchsetCategory("D");
    }

    public String onSetCategoryW() {
        return batchsetCategory("W");
    }

    private String batchsetCategory(String category) {
        if (this.selectedActtypes.length == 0) {
            MessageUtil.addError("��ѡ�����޸ĵ��ʻ�");
            return null;
        }
        actInfoManagerService.batchsetActInfoCategory(this.selectedActtypes, category);
        MessageUtil.addInfo("���" + category + "�趨��ɣ����趨" + this.selectedActtypes.length + "����¼��");
        return null;
    }

    private SelectItem[] createFilterOptions(String[] acttypes) {
        SelectItem[] options = new SelectItem[acttypes.length + 1];

        options[0] = new SelectItem("", "��ѡ��");
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


    public void setActInfoManagerService(ActInfoManagerService actInfoManagerService) {
        this.actInfoManagerService = actInfoManagerService;
    }

    public String[] getCategorys() {
        return categorys;
    }

    public void setCategorys(String[] categorys) {
        this.categorys = categorys;
    }

    public MtActtype[] getSelectedActtypes() {
        return selectedActtypes;
    }

    public void setSelectedActtypes(MtActtype[] selectedActtypes) {
        this.selectedActtypes = selectedActtypes;
    }

    public List<MtActtype> getMtActtypeList() {
        return mtActtypeList;
    }

    public void setMtActtypeList(List<MtActtype> mtActtypeList) {
        this.mtActtypeList = mtActtypeList;
    }
}
