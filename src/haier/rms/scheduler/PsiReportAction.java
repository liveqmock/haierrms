package haier.rms.scheduler;

import haier.scheduler.PsiReportHandler;
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
 * 手工发起共享中心（PSI）自动报表程序
 * User: zhanrui
 * Date: 11-4-8
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class PsiReportAction {
    private Logger logger = Logger.getLogger(this.getClass());

    private Date startdate;
    private Date enddate;

    @ManagedProperty(value = "#{sbsactbal}")
    private SBSAccountBalanceHandler sbsScheduler;

    @ManagedProperty(value = "#{psiReport}")
    private PsiReportHandler psiScheduler;

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

    public SBSAccountBalanceHandler getSbsScheduler() {
        return sbsScheduler;
    }

    public void setSbsScheduler(SBSAccountBalanceHandler sbsScheduler) {
        this.sbsScheduler = sbsScheduler;
    }

    public PsiReportHandler getPsiScheduler() {
        return psiScheduler;
    }

    public void setPsiScheduler(PsiReportHandler psiScheduler) {
        this.psiScheduler = psiScheduler;
    }

    //=======================================================
    @PostConstruct
    public void init() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusDays(1).toDate();

        this.enddate = new Date();
    }

/*
    public String doStart() {
        try {
            haier.rms.psireport.Scheduler psi = new Scheduler();
            psi.start(this.startdate);
        } catch (Exception e) {
            logger.error(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "调度任务已完成。", null));
        return null;
    }
*/
    public String doStart() {
        try {
            sbsScheduler.run(startdate);
            psiScheduler.run(startdate);
        } catch (Exception e) {
            logger.error(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "调度任务已完成。", null));
        return null;
    }

}
