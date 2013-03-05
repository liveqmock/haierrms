package haier.rms.sbs.relatedtxnreport;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-9
 * Time: ÉÏÎç7:49
 * To change this template use File | Settings | File Templates.
 */
public class RelatedTxnDataBean {
    String rptdate;
    //A¹É
    long rmb1;
    long foreign1;
    long total1;
    //H¹ÉÆ±
    long rmb2;
    long foreign2;
    long total2;

    long total;

    public String getRptdate() {
        return rptdate;
    }

    public void setRptdate(String rptdate) {
        this.rptdate = rptdate;
    }

    public long getRmb1() {
        return rmb1;
    }

    public void setRmb1(long rmb1) {
        this.rmb1 = rmb1;
    }

    public long getForeign1() {
        return foreign1;
    }

    public void setForeign1(long foreign1) {
        this.foreign1 = foreign1;
    }

    public long getTotal1() {
        return total1;
    }

    public void setTotal1(long total1) {
        this.total1 = total1;
    }

    public long getRmb2() {
        return rmb2;
    }

    public void setRmb2(long rmb2) {
        this.rmb2 = rmb2;
    }

    public long getForeign2() {
        return foreign2;
    }

    public void setForeign2(long foreign2) {
        this.foreign2 = foreign2;
    }

    public long getTotal2() {
        return total2;
    }

    public void setTotal2(long total2) {
        this.total2 = total2;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
