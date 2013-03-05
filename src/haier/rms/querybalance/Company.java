package haier.rms.querybalance;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-3-9
 * Time: 17:36:25
 * To change this template use File | Settings | File Templates.
 */
public class Company {
    int id;
    String name;
    String acct;
    Double sav;
    Double fix;
    Double assu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public Double getSav() {
        return sav;
    }

    public void setSav(Double sav) {
        this.sav = sav;
    }

    public Double getFix() {
        return fix;
    }

    public void setFix(Double fix) {
        this.fix = fix;
    }

    public Double getAssu() {
        return assu;
    }

    public void setAssu(Double assu) {
        this.assu = assu;
    }
}
