package haier.repository.model.sbsreport;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-12
 * Time: ÉÏÎç10:05
 * To change this template use File | Settings | File Templates.
 */
public class CorpBalanceBean {
    String txndate;
    String corpname;
    long balamt;
    int actnum; //ÕË»§Êý

    public String getTxndate() {
        return txndate;
    }

    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public long getBalamt() {
        return balamt;
    }

    public void setBalamt(long balamt) {
        this.balamt = balamt;
    }

    public int getActnum() {
        return actnum;
    }

    public void setActnum(int actnum) {
        this.actnum = actnum;
    }
}
