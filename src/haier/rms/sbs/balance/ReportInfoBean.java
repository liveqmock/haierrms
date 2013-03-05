package haier.rms.sbs.balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-2-15
 * Time: ÉÏÎç11:13
 * To change this template use File | Settings | File Templates.
 */
public class ReportInfoBean {
    String currcode;
    String currname;
    BigDecimal rate;

    List<BalanceBean> balanceBeanList = new ArrayList<BalanceBean>();

    public String getCurrcode() {
        return currcode;
    }

    public void setCurrcode(String currcode) {
        this.currcode = currcode;
    }

    public String getCurrname() {
        return currname;
    }

    public void setCurrname(String currname) {
        this.currname = currname;
    }

    public List<BalanceBean> getBalanceBeanList() {
        return balanceBeanList;
    }

    public void setBalanceBeanList(List<BalanceBean> balanceBeanList) {
        this.balanceBeanList = balanceBeanList;
    }
    public void addBalanceBean(BalanceBean bean) {
        this.balanceBeanList.add(bean);
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
