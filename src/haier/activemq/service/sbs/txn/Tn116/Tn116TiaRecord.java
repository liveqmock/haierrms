package haier.activemq.service.sbs.txn.Tn116;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-14
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 */
public class Tn116TiaRecord {
    /*
    CTF-ORDDAT	交易日期	X(8)	YYYYMMDD
     */
    private String  orddat;

    public String getOrddat() {
        return orddat;
    }

    public void setOrddat(String orddat) {
        this.orddat = orddat;
    }
}
