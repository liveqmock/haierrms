package haier.activemq.service.sbs.txn.T8123;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-14
 * Time: ����4:31
 * To change this template use File | Settings | File Templates.
 */
public class T8123TiaRecord {
    /*
    CTF-CUSIDT	�ͻ���	X(7)	�̶�ֵ
    CTF-APCODE	������	X(1)
    CTF-CURCDE	�ұ�	X(3)	����룬�Ҳ��ո�
    CTF-ACTTYP	�ʻ�����	X(1)	����룬�Ҳ��ո�	1-����,2-����,3-���,9-����
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
