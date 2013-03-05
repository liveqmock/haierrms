package haier.service.rms.pbcreport.model;

import java.math.BigDecimal;

/**
 * SBS ¿ÆÄ¿Óà¶î£¨×ÜÕÊÂë ºËËãÂë£©
 * User: zhanrui
 * Date: 11-6-30
 * Time: ÉÏÎç9:58
 * To change this template use File | Settings | File Templates.
 */
public class SbsItemBalBean {
    private String type;
    private String code;
    private String codename;
    private BigDecimal balamt;
    private String currcd;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getBalamt() {
        return balamt;
    }

    public void setBalamt(BigDecimal balamt) {
        this.balamt = balamt;
    }

    public String getCurrcd() {
        return currcd;
    }

    public void setCurrcd(String currcd) {
        this.currcd = currcd;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }
}
