package haier.activemq.service.sbs.txn.Tn116;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-15
 * Time: ����10:59
 * To change this template use File | Settings | File Templates.
 */
public class Tn116SOFDataDetail extends SOFDataDetail {
    /*
    TXXX-FBTIDX	��ˮ��	X(18)	����룬�Ҳ��ո�
    TXXX-CUSACT	�����˺�	X(22)	����룬�Ҳ��ո�
    TXXX-RETNAM	�����	X(72)	����룬�Ҳ��ո�
    TXXX-BENACT	�տ��˺�	X(35)	��������ȡ��
    TXXX-BENNAM	�տ��	X(72)	����룬�Ҳ��ո�
    TXXX-BENBNK	�տ�����	X(72)	����룬�Ҳ��ո�
    TXXX-TXNAMT	���׽��	X(17)	�Ҷ��룬�󲹿ո�,������Ϊ�����������������һ������	+9(13).99
    TXXX-RETAUX	��;	X(30)
    TXXX-CTLRSN	���ԭ��	X(30)
    TXXX-TLRNAM	��˹�Ա��	X(10)
    TXXX-TERMNM	��������	X(10)
     */
    private String fbtidx;    //1
    private String cusact;
    private String retnam;
    private String benact;
    private String bennam;
    private String benbnk;
    private String txnamt;
    private String retaux;
    private String ctlrsn;
    private String tlrnam;
    private String termnm;     //11
    {
        offset = 0;
        fieldTypes = new int[]{1, 1, 1, 1, 1,  1, 1, 1, 1, 1,  1};
        fieldLengths = new int[]{18, 22, 72, 35, 72, 72, 17, 30, 30, 10, 10};
    }

    public String getFbtidx() {
        return fbtidx;
    }

    public void setFbtidx(String fbtidx) {
        this.fbtidx = fbtidx;
    }

    public String getCusact() {
        return cusact;
    }

    public void setCusact(String cusact) {
        this.cusact = cusact;
    }

    public String getRetnam() {
        return retnam;
    }

    public void setRetnam(String retnam) {
        this.retnam = retnam;
    }

    public String getBenact() {
        return benact;
    }

    public void setBenact(String benact) {
        this.benact = benact;
    }

    public String getBennam() {
        return bennam;
    }

    public void setBennam(String bennam) {
        this.bennam = bennam;
    }

    public String getBenbnk() {
        return benbnk;
    }

    public void setBenbnk(String benbnk) {
        this.benbnk = benbnk;
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }

    public String getRetaux() {
        return retaux;
    }

    public void setRetaux(String retaux) {
        this.retaux = retaux;
    }

    public String getCtlrsn() {
        return ctlrsn;
    }

    public void setCtlrsn(String ctlrsn) {
        this.ctlrsn = ctlrsn;
    }

    public String getTlrnam() {
        return tlrnam;
    }

    public void setTlrnam(String tlrnam) {
        this.tlrnam = tlrnam;
    }

    public String getTermnm() {
        return termnm;
    }

    public void setTermnm(String termnm) {
        this.termnm = termnm;
    }
}
