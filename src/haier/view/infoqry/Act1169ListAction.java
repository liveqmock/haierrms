package haier.view.infoqry;

import haier.repository.model.Ptoplog;
import haier.repository.model.S1169Corplist;
import haier.service.common.PlatformService;
import haier.service.infoqry.Act1169ListService;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 1169企业清单维护.
 * User: zhanrui
 * Date: 2013-03-08
 * Time: 下午3:39
 */

@ManagedBean
@ViewScoped
public class Act1169ListAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Act1169ListAction.class);
    private static final long serialVersionUID = 7741440602037366151L;

    @ManagedProperty(value = "#{act1169ListService}")
    private Act1169ListService act1169ListService;

    private S1169Corplist selectedBean;
    private List<S1169Corplist> detlList;
    private DataTable dataTableWidget;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;


    //==============================
    @PostConstruct
    public void postConstruct() {
        this.detlList = act1169ListService.selectCorpList();
    }

    public String onSaveData(RowEditEvent event) {
        S1169Corplist corp = (S1169Corplist) event.getObject();
        try {
            act1169ListService.insertCorplistRecord(corp);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("Act1169ListAction_onSaveData");
            oplog.setActionName("1169企业清单维护:保存");
            oplog.setOpLog(this.selectedBean.getCordname());
            platformService.insertNewOperationLog(oplog);
            MessageUtil.addInfo("记录处理成功");
        } catch (Exception e) {
            logger.error("增加记录时出现错误",e);
            MessageUtil.addError("增加记录时出现错误,可能帐号重复。");
        }
        return null;
    }
    public String onDelete() {
        try {
            act1169ListService.deleteCorplistRecord(this.selectedBean);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("Act1169ListAction_onDelete");
            oplog.setActionName("1169企业清单维护:删除");
            oplog.setOpLog(this.selectedBean.getCordname());
            platformService.insertNewOperationLog(oplog);

            MessageUtil.addInfo("记录处理成功");
        } catch (Exception e) {
            logger.error("删除记录时出现错误",e);
            MessageUtil.addError("删除记录时出现错误。" + e.getMessage());
        }
        return null;
    }


    public String onAddRecord() {
        S1169Corplist corp = new S1169Corplist();
        corp.setPkid(UUID.randomUUID().toString());
        this.detlList.add(corp);

        dataTableWidget.setFirst(this.detlList.size());
        return null;
    }


    //============================================getter/setter=================================


    public Act1169ListService getAct1169ListService() {
        return act1169ListService;
    }

    public void setAct1169ListService(Act1169ListService act1169ListService) {
        this.act1169ListService = act1169ListService;
    }

    public S1169Corplist getSelectedBean() {
        return selectedBean;
    }

    public void setSelectedBean(S1169Corplist selectedBean) {
        this.selectedBean = selectedBean;
    }

    public List<S1169Corplist> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<S1169Corplist> detlList) {
        this.detlList = detlList;
    }

    public DataTable getDataTableWidget() {
        return dataTableWidget;
    }

    public void setDataTableWidget(DataTable dataTableWidget) {
        this.dataTableWidget = dataTableWidget;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }
}
