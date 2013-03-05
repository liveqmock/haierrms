package haier.rms.sbs.statistics;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-18
 * Time: ????11:20
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class ChartDateAction implements Serializable {

    private static final long serialVersionUID = -2723620773143915974L;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String startdate;
//    private String path;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//====================================================

    @PostConstruct
    public void init() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusMonths(1).toString("yyyy年MM月");
    }

    public String onNext() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map params = context.getExternalContext().getSessionMap();
        String path = (String) context.getExternalContext().getRequestParameterMap().get("path");
        params.remove("path");
        if (StringUtils.isEmpty(path)) {
            logger.error("输入模式参数错误！");
            MessageUtil.addError("输入模式参数错误！");
            return null;

        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("path", path);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", this.startdate);

        return "actbalRankChart.xhtml";
    }

}



