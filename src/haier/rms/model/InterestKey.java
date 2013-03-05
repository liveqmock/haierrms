package haier.rms.model;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-6
 * Time: 20:13:43
 * To change this template use File | Settings | File Templates.
 */
public class InterestKey {
    String startDate;
    String ywzl;
    String fkid;
    String dataDate;
    String bussItem;
    String NeiWaiFlag;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getYwzl() {
        return ywzl;
    }

    public void setYwzl(String ywzl) {
        this.ywzl = ywzl;
    }

    public String getFkid() {
        return fkid;
    }

    public void setFkid(String fkid) {
        this.fkid = fkid;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getBussItem() {
        return bussItem;
    }

    public void setBussItem(String bussItem) {
        this.bussItem = bussItem;
    }

    public String getNeiWaiFlag() {
        return NeiWaiFlag;
    }

    public void setNeiWaiFlag(String neiWaiFlag) {
        NeiWaiFlag = neiWaiFlag;
    }
}
