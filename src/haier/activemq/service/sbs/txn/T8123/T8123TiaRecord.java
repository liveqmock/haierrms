package haier.activemq.service.sbs.txn.T8123;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-14
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 */
public class T8123TiaRecord {
    /*
    CTF-CUSIDT	客户号	X(7)	固定值
    CTF-APCODE	核算码	X(1)
    CTF-CURCDE	币别	X(3)	左对齐，右补空格
    CTF-ACTTYP	帐户类型	X(1)	左对齐，右补空格	1-定存,2-贷款,3-活存,9-其他
     */
    private String  cusidt;
    private String  apcode;
    private String  curcde;
    private String  acttyp;

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

    public String getActtyp() {
        return acttyp;
    }

    public void setActtyp(String acttyp) {
        this.acttyp = acttyp;
    }
}
