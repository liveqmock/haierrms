package haier.rms.sbs.detlbook.sbs8853;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-1-17
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
public class T8853ResponseRecord {

    private String actnum;
    private String actnam;

    private String cusidt;
    private String apcode;
    private String curcde;

    private String stmdat; //交易日期 YYYYMMDD
    private String tlrnum;
    private String vchset;
    private String setseq;
    private String txnamt;
    private String lasbal;
    private String furinf;

    private String formatedActnum;
    private BigDecimal formatedTxnamt;


    public String getActnum() {
        return actnum;
    }

    public void setActnum(String actnum) {
        this.actnum = actnum;
    }

    public String getActnam() {
        return actnam;
    }

    public void setActnam(String actnam) {
        this.actnam = actnam;
    }

    public String getCusidt() {
        return cusidt;
    }

    public void setCusidt(String cusidt) {
        this.cusidt = cusidt;
    }

    public String getApcode() {
        return apcode;
    }

    public void setApcode(String apcode) {
        this.apcode = apcode;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    public String getStmdat() {
        return stmdat;
    }

    public void setStmdat(String stmdat) {
        this.stmdat = stmdat;
    }

    public String getTlrnum() {
        return tlrnum;
    }

    public void setTlrnum(String tlrnum) {
        this.tlrnum = tlrnum;
    }

    public String getVchset() {
        return vchset;
    }

    public void setVchset(String vchset) {
        this.vchset = vchset;
    }

    public String getSetseq() {
        return setseq;
    }

    public void setSetseq(String setseq) {
        this.setseq = setseq;
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }

    public String getLasbal() {
        return lasbal;
    }

    public void setLasbal(String lasbal) {
        this.lasbal = lasbal;
    }

    public String getFurinf() {
        return furinf;
    }

    public void setFurinf(String furinf) {
        this.furinf = furinf;
    }

    public String getFormatedActnum() {
        return formatedActnum;
    }

    public void setFormatedActnum(String formatedActnum) {
        this.formatedActnum = formatedActnum;
    }

    public BigDecimal getFormatedTxnamt() {
        return formatedTxnamt;
    }

    public void setFormatedTxnamt(BigDecimal formatedTxnamt) {
        this.formatedTxnamt = formatedTxnamt;
    }
}
