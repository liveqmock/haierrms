package haier.rms.sbs.balance;

import java.math.BigDecimal;

/**
 * ����.
 * User: zhanrui
 * Date: 11-2-14
 * Time: ����11:39
 * To change this template use File | Settings | File Templates.
 */
public class RoeBean {
    String currcode;
    String currname;
    BigDecimal rate;

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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
