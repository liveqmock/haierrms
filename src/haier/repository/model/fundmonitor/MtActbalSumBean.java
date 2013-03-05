package haier.repository.model.fundmonitor;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-24
 * Time: ÏÂÎç3:34
 * To change this template use File | Settings | File Templates.
 */
public class MtActbalSumBean {
    private String qrytime;
    private String cnt;
    private BigDecimal rmbamt;
    private String category;


    public String getQrytime() {
        return qrytime;
    }

    public void setQrytime(String qrytime) {
        this.qrytime = qrytime;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public BigDecimal getRmbamt() {
        return rmbamt;
    }

    public void setRmbamt(BigDecimal rmbamt) {
        this.rmbamt = rmbamt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
