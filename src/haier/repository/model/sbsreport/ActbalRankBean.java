package haier.repository.model.sbsreport;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-10
 * Time: ÏÂÎç2:10
 * To change this template use File | Settings | File Templates.
 */
public class ActbalRankBean {
    String seqno;
    String actno;
    String actname;
    String actattr;
    long balamt;
    long lastdayamt;
    long firstdayamt;
    int actnum;

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public long getBalamt() {
        return balamt;
    }

    public void setBalamt(long balamt) {
        this.balamt = balamt;
    }

    public long getLastdayamt() {
        return lastdayamt;
    }

    public void setLastdayamt(long lastdayamt) {
        this.lastdayamt = lastdayamt;
    }

    public long getFirstdayamt() {
        return firstdayamt;
    }

    public void setFirstdayamt(long firstdayamt) {
        this.firstdayamt = firstdayamt;
    }

    public String getActattr() {
        return actattr;
    }

    public void setActattr(String actattr) {
        this.actattr = actattr;
    }

    public int getActnum() {
        return actnum;
    }

    public void setActnum(int actnum) {
        this.actnum = actnum;
    }
}
