package haier.rms.model;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-5-7
 * Time: 17:31:39
 * To change this template use File | Settings | File Templates.
 */
public class EaiCorpBal {
    String id;
    String corpname;
    String corpacct;
    String item;
    String bankname;
    double balance;
    String currcd;
    String querydate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getCorpacct() {
        return corpacct;
    }

    public void setCorpacct(String corpacct) {
        this.corpacct = corpacct;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrcd() {
        return currcd;
    }

    public void setCurrcd(String currcd) {
        this.currcd = currcd;
    }

    public String getQuerydate() {
        return querydate;
    }

    public void setQuerydate(String querydate) {
        this.querydate = querydate;
    }
}
