package gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * User: zhanrui
 * Date: 2009-7-6
 * Time: 15:35:54
 * To change this template use File | Settings | File Templates.
 */
public class BatchQueryResult {

    String floflg;          //后续包标志
    String totcnt;          //总记录数
    String curcnt;          //当前包笔数


    List<BalanceRecord> dtldat = new ArrayList();

    private static Log logger = LogFactory.getLog(CtgManager.class);


    public String getFloflg() {
        return floflg;
    }

    public void setFloflg(String floflg) {
        this.floflg = floflg;
    }

    public String getTotcnt() {
        return totcnt;
    }

    public void setTotcnt(String totcnt) {
        this.totcnt = totcnt;
    }

    public String getCurcnt() {
        return curcnt;
    }

    public void setCurcnt(String curcnt) {
        this.curcnt = curcnt;
    }

    public void add(BalanceRecord record) {
        this.dtldat.add(record);
    }

    public List<BalanceRecord> getAll() {
        return this.dtldat;
    }


}
