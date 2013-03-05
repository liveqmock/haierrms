package haier.rms.scheduler;

import haier.scheduler.DisReportHandler;
import haier.scheduler.SBSAccountBalanceHandler;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-8
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class DisReportAction {
    private Logger logger = Logger.getLogger(this.getClass());

    private Date startdate;
    private Date enddate;

    @ManagedProperty(value = "#{sbsactbal}")
    private SBSAccountBalanceHandler sbsScheduler;

    @ManagedProperty(value = "#{disactbal}")
    private DisReportHandler disScheduler;

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public void setSbsScheduler(SBSAccountBalanceHandler sbsScheduler) {
        this.sbsScheduler = sbsScheduler;
    }

    public void setDisScheduler(DisReportHandler disScheduler) {
        this.disScheduler = disScheduler;
    }

    //=======================================================
    @PostConstruct
    public void init() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusDays(1).toDate();

        this.enddate = new Date();
    }

    public String doStart() {
        try {
            sbsScheduler.run(startdate);
            disScheduler.run(startdate);
        } catch (Exception e) {
            logger.error(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "调度任务已完成。", null));
        return null;
    }
}
