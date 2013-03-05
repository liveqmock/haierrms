package haier.activemq.service.sbs.txn.Tn062;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-15
 * Time: ����10:59
 * To change this template use File | Settings | File Templates.
 */
public class Tn062SOFDataDetail extends SOFDataDetail {
    /*
        T544-ACTNUM	�˺�	X(22)
        T544-BNKNUM	�к�	X(12)
        T544-CURCDE	�ұ�	X(03)
        T544-CUSIDT	�ͻ���	X(16)
        T544-ACTNAM	����	X(60)
        T544-ACTBAL	���	X(17)	�Ҷ��룬��0
        T544-AVLBAL	�������	X(17)	�Ҷ��룬��0
        T544-ORDBAL	��͸֧���	X(17)	�Ҷ��룬��0
        T544-ORWBAL	͸֧���	X(17)	�Ҷ��룬��0
        T544-CURNUM	����ұ�	X(3)
        T544-BRHNAM	����������	X(60)
        T544-BRHNUM	�����������	X(9)
     */
    private String actnum;
    private String bnknum;
    private String curcde;
    private String cusidt;
    private String actnam;
    private String actbal;
    private String avlbal;
    private String ordbal;
    private String orwbal;
    private String curnum;
    private String brhnam;
    private String brhnum;

    {
        offset = 0;
        fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        fieldLengths = new int[]{22, 12, 3, 16, 60, 17, 17, 17, 17, 3, 60, 9};
    }

    public String getActnum() {
        return actnum;
    }

    public void setActnum(String actnum) {
        this.actnum = actnum;
    }

    public String getBnknum() {
        return bnknum;
    }

    public void setBnknum(String bnknum) {
        this.bnknum = bnknum;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    public String getCusidt() {
        return cusidt;
    }

    public void setCusidt(String cusidt) {
        this.cusidt = cusidt;
    }

    public String getActnam() {
        return actnam;
    }

    public void setActnam(String actnam) {
        this.actnam = actnam;
    }

    public String getActbal() {
        return actbal;
    }

    public void setActbal(String actbal) {
        this.actbal = actbal;
    }

    public String getAvlbal() {
        return avlbal;
    }

    public void setAvlbal(String avlbal) {
        this.avlbal = avlbal;
    }

    public String getOrdbal() {
        return ordbal;
    }

    public void setOrdbal(String ordbal) {
        this.ordbal = ordbal;
    }

    public String getOrwbal() {
        return orwbal;
    }

    public void setOrwbal(String orwbal) {
        this.orwbal = orwbal;
    }

    public String getCurnum() {
        return curnum;
    }

    public void setCurnum(String curnum) {
        this.curnum = curnum;
    }

    public String getBrhnam() {
        return brhnam;
    }

    public void setBrhnam(String brhnam) {
        this.brhnam = brhnam;
    }

    public String getBrhnum() {
        return brhnum;
    }

    public void setBrhnum(String brhnum) {
        this.brhnum = brhnum;
    }
}
