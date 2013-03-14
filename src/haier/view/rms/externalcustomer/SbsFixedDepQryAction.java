package haier.view.rms.externalcustomer;

import haier.repository.model.Ptoplog;
import haier.repository.model.infoqry.FixedDepositBean;
import haier.service.common.PlatformService;
import haier.service.common.ToolsService;
import haier.service.infoqry.FixedDepositQryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * SBS定期存款查询.
 * User: zhanrui
 * Date: 13-3-6
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class SbsFixedDepQryAction implements Serializable {
    private static final long serialVersionUID = 8064944968186579065L;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String corpName;
    private BigDecimal total;
    private List<FixedDepositBean> detlList;

    @ManagedProperty(value = "#{fixedDepositQryService}")
    private FixedDepositQryService fixedDepositQryService;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;


    public String onQuery() {
        try {
            detlList = fixedDepositQryService.getFixedDepositRecord(corpName);

            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("SbsFixedDepQryAction_onQuery");
            oplog.setActionName("定期存款信息查询（SBS）:查询");
            platformService.insertNewOperationLog(oplog);

        } catch (Exception e) {
            logger.error("查询定期余额错误", e);
            MessageUtil.addError("查询定期余额错误," + e.getMessage());
        }
        return null;
    }

    //====================

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public List<FixedDepositBean> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<FixedDepositBean> detlList) {
        this.detlList = detlList;
    }

    public FixedDepositQryService getFixedDepositQryService() {
        return fixedDepositQryService;
    }

    public void setFixedDepositQryService(FixedDepositQryService fixedDepositQryService) {
        this.fixedDepositQryService = fixedDepositQryService;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }
}
