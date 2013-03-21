package haier.view.platform;

import haier.repository.model.Ptoplog;
import haier.service.common.PlatformService;
import haier.service.common.ToolsService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pub.platform.security.OperatorManager;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-2-4
 * Time: ����1:44
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PtoplogAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<SelectItem> branchList;
    private String branchId;
    private String operId;
    private String startDate;
    private String endDate;

    private List<Ptoplog> detlList;

    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{rmsJdbc}")
    private NamedParameterJdbcTemplate rmsJdbc;


    @PostConstruct
    public void init() {
        OperatorManager om = platformService.getOperatorManager();
        String operid = om.getOperatorId();
        String branchid = om.getOperator().getDeptid();

        this.branchList = toolsService.selectBranchList(branchid);

        this.startDate = new DateTime().dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        this.endDate = new DateTime().toString("yyyy-MM-dd");
        detlList = new ArrayList();
    }

    public String onQuery() {
        try {
            Ptoplog oplog = new Ptoplog();
            oplog.setActionId("Ptoplog_onQuery");
            oplog.setActionName("ϵͳ������־��ѯ");
            oplog.setOpDataBranchid(this.branchId);
            oplog.setOpDataStartdate(this.startDate);
            oplog.setOpDataEnddate(this.endDate);
            platformService.insertNewOperationLog(oplog);

            Map<String,Object> paramMap = new HashMap();
            List<String> branchStrList = platformService.selectBranchLevelList(branchId);
            paramMap.put("branchStrList", branchStrList);
            paramMap.put("startDate", this.startDate);
            paramMap.put("endDate", this.endDate);
/*
            String sql = "select * from ptoplog where oper_branchid in (:branchStrList)" +
                    " and to_char(op_date,'yyyy-mm-dd') >= :startDate and to_char(op_date,'yyyy-mm-dd') <= :endDate " +
                    " order by op_date desc, oper_branchid";
*/
            String sql = "select * from ptoplog where  " +
                    " to_char(op_date,'yyyy-mm-dd') >= :startDate and to_char(op_date,'yyyy-mm-dd') <= :endDate " +
                    " order by op_date desc, oper_branchid";
            detlList = rmsJdbc.query(sql, paramMap, new BeanPropertyRowMapper<Ptoplog>(Ptoplog.class));
        } catch (Exception e) {
            logger.error("��ѯ����ʱ���ִ���", e);
            MessageUtil.addWarn("��ѯ����ʱ���ִ���" + e.getMessage());
        }
        return null;
    }

    //=========================

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Ptoplog> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<Ptoplog> detlList) {
        this.detlList = detlList;
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

    public NamedParameterJdbcTemplate getRmsJdbc() {
        return rmsJdbc;
    }

    public void setRmsJdbc(NamedParameterJdbcTemplate rmsJdbc) {
        this.rmsJdbc = rmsJdbc;
    }
}
