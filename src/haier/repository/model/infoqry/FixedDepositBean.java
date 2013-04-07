package haier.repository.model.infoqry;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-3-7
 * Time: ÏÂÎç2:55
 * To change this template use File | Settings | File Templates.
 */
public class FixedDepositBean {
    private String corpName;
    private String corpCode;
    private String bankItem;
    private String bankName;
    private String actNum;
    private String voucherNum;
    private BigDecimal balamt;
    private BigDecimal intRate;
    private String startDate;
    private String endDate;
    private int period;
    private String currCode;
    private String currName;
    private String csmOtherName;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getBankItem() {
        return bankItem;
    }

    public void setBankItem(String bankItem) {
        this.bankItem = bankItem;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getVoucherNum() {
        return voucherNum;
    }

    public void setVoucherNum(String voucherNum) {
        this.voucherNum = voucherNum;
    }

    public BigDecimal getBalamt() {
        return balamt;
    }

    public void setBalamt(BigDecimal balamt) {
        this.balamt = balamt;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getActNum() {
        return actNum;
    }

    public void setActNum(String actNum) {
        this.actNum = actNum;
    }

    public BigDecimal getIntRate() {
        return intRate;
    }

    public void setIntRate(BigDecimal intRate) {
        this.intRate = intRate;
    }

    public String getCurrName() {
        return currName;
    }

    public void setCurrName(String currName) {
        this.currName = currName;
    }

    public String getCurrCode() {
        return currCode;
    }

    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    public String getCsmOtherName() {
        return csmOtherName;
    }

    public void setCsmOtherName(String csmOtherName) {
        this.csmOtherName = csmOtherName;
    }
}
