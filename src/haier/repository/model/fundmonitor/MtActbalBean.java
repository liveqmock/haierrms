package haier.repository.model.fundmonitor;

import haier.repository.model.MtActbal;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-20
 * Time: ÏÂÎç4:54
 * To change this template use File | Settings | File Templates.
 */
public class MtActbalBean extends MtActbal implements Serializable{
    private static final long serialVersionUID = -4435637226638974584L;
    private String actname;
    private String bankname;
    private String category;
    private String openingbank;
    private String actattr;

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpeningbank() {
        return openingbank;
    }

    public void setOpeningbank(String openingbank) {
        this.openingbank = openingbank;
    }

    public String getActattr() {
        return actattr;
    }

    public void setActattr(String actattr) {
        this.actattr = actattr;
    }
}
