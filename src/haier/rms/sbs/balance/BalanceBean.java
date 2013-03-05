package haier.rms.sbs.balance;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-2-14
 * Time: 上午9:50
 * To change this template use File | Settings | File Templates.
 */
public class BalanceBean implements Serializable{
    /*
    T839-CUSIDT	账号	  X(18)
    T839-APCODE	户名	  X(60)
    T839-LASBAL	余额	  X(18)
    T839-CURCDE	账户类别	X(1)
    */
    String actno;      //账号
    String actname;    //户名
    BigDecimal homecurbal; //本币余额
    BigDecimal rmbbal;     //人民币余额
    String acttype;    //帐户类别
    String acttypename;    //帐户类别名称
    BigDecimal roe;        //汇率  rate of exchange
    String currcode;   //币种
    String currname;   //币种名称
    String actattr;    //帐户属性   （集团内外帐户 内 外）

    //EXCEL输出用字段
    int seqno;   //序号
    BigDecimal acttype1bal = new BigDecimal("0");
    BigDecimal acttype2bal = new BigDecimal("0");
    BigDecimal acttype3bal = new BigDecimal("0");

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

    public BigDecimal getHomecurbal() {
        return homecurbal;
    }

    public void setHomecurbal(BigDecimal homecurbal) {
        this.homecurbal = homecurbal;
    }

    public BigDecimal getRmbbal() {
        return rmbbal;
    }

    public void setRmbbal(BigDecimal rmbbal) {
        this.rmbbal = rmbbal;
    }

    public String getActtype() {
        return acttype;
    }

    public void setActtype(String acttype) {
        this.acttype = acttype;
    }

    public BigDecimal getRoe() {
        return roe;
    }

    public void setRoe(BigDecimal roe) {
        this.roe = roe;
    }

    public String getCurrcode() {
        return currcode;
    }

    public void setCurrcode(String currcode) {
        this.currcode = currcode;
    }

    public String getCurrname() {
        return currname;
    }

    public void setCurrname(String currname) {
        this.currname = currname;
    }

    public String getActtypename() {
        return acttypename;
    }

    public void setActtypename(String acttypename) {
        this.acttypename = acttypename;
    }

    public BigDecimal getActtype1bal() {
        return acttype1bal;
    }

    public void setActtype1bal(BigDecimal acttype1bal) {
        this.acttype1bal = acttype1bal;
    }

    public BigDecimal getActtype2bal() {
        return acttype2bal;
    }

    public void setActtype2bal(BigDecimal acttype2bal) {
        this.acttype2bal = acttype2bal;
    }

    public BigDecimal getActtype3bal() {
        return acttype3bal;
    }

    public void setActtype3bal(BigDecimal acttype3bal) {
        this.acttype3bal = acttype3bal;
    }

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getActattr() {
        return actattr;
    }

    public void setActattr(String actattr) {
        this.actattr = actattr;
    }
}
