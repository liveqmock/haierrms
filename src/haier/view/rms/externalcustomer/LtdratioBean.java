package haier.view.rms.externalcustomer;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-20
 * Time: ÉÏÎç10:03
 * To change this template use File | Settings | File Templates.
 */
public class LtdratioBean {
    private String apcode;

    private String actname;

    private BigDecimal balamt;

    private String deptname;

    private String days;

    public String getApcode() {
        return apcode;
    }

    public void setApcode(String apcode) {
        this.apcode = apcode;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public BigDecimal getBalamt() {
        return balamt;
    }

    public void setBalamt(BigDecimal balamt) {
        this.balamt = balamt;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
