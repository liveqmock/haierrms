package haier.repository.model.disreport;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-9
 * Time: ����10:29
 * To change this template use File | Settings | File Templates.
 */
public class ActbalHistory {
    private int seqno;
    private String actno;
    private String actname;
    private String acttype;
    private String acttypename;
    private String curcde; //���ִ���
    private String curnmc; //��������
    private int actcnt; //ͬ�����ʻ�����
    private BigDecimal balamt;

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    public String getCurnmc() {
        return curnmc;
    }

    public void setCurnmc(String curnmc) {
        this.curnmc = curnmc;
    }


    public BigDecimal getBalamt() {
        return balamt;
    }

    public void setBalamt(BigDecimal balamt) {
        this.balamt = balamt;
    }

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public int getActcnt() {
        return actcnt;
    }

    public void setActcnt(int actcnt) {
        this.actcnt = actcnt;
    }

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
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
}
