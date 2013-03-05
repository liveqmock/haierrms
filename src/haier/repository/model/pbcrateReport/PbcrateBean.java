package haier.repository.model.pbcrateReport;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-10-11
 * Time: ÏÂÎç1:53
 * To change this template use File | Settings | File Templates.
 */
public class PbcrateBean {
    private String productstypecode;
    private BigDecimal cnt;
    private BigDecimal lbal;
    private String currencycode;

    public String getProductstypecode() {
        return productstypecode;
    }

    public void setProductstypecode(String productstypecode) {
        this.productstypecode = productstypecode;
    }

    public BigDecimal getCnt() {
        return cnt;
    }

    public void setCnt(BigDecimal cnt) {
        this.cnt = cnt;
    }

    public BigDecimal getLbal() {
        return lbal;
    }

    public void setLbal(BigDecimal lbal) {
        this.lbal = lbal;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }
}
