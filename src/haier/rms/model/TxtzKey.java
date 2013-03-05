package haier.rms.model;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-9
 * Time: 15:13:46
 * To change this template use File | Settings | File Templates.
 */
public class TxtzKey {
    String  startDate;
    String  NeiWaiFlag;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNeiWaiFlag() {
        return NeiWaiFlag;
    }

    public void setNeiWaiFlag(String neiWaiFlag) {
        NeiWaiFlag = neiWaiFlag;
    }
}
