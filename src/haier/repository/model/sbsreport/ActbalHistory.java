package haier.repository.model.sbsreport;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-9
 * Time: ����10:29
 * To change this template use File | Settings | File Templates.
 */
public class ActbalHistory {
    String txndate;
    String actno;      //�˺�
    String actname;    //����
    BigDecimal homecurbal; //�������
    BigDecimal rmbbal;     //��������
    String acttype;    //�ʻ����
    String acttypename;    //�ʻ��������
    BigDecimal roe;        //����  rate of exchange
    String currcode;   //����
    String currname;   //��������
    String actattr;    //�ʻ�����   �����������ʻ� �� �⣩

    //EXCEL������ֶ�
    int seqno;   //���
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

    public String getActtypename() {
        return acttypename;
    }

    public void setActtypename(String acttypename) {
        this.acttypename = acttypename;
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

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
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

    public String getActattr() {
        return actattr;
    }

    public void setActattr(String actattr) {
        this.actattr = actattr;
    }

    public String getTxndate() {
        return txndate;
    }

    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }
}
