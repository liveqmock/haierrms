package haier.activemq.service.sbs.txn.T8855;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * 8855	��ѯ�ͻ��˻��б�
 * User: zhanrui
 * Date: 2013-3-7
 */
public class T8855SOFDataDetail extends SOFDataDetail {
    /*
    TXXX-CUSACT	�˺�	X(18) 	����룬�Ҳ��ո�
    TXXX-CUSNAM	����	X(60)	����룬�Ҳ��ո�
    TXXX-BOKNUM	�浥��	X(18) 	����룬�Ҳ��ո�
    TXXX-ACTTYP	�˻�����	X(1)	����룬�Ҳ��ո�	1-���ڴ��,2-����,3-���ڴ��
    TXXX-BOKBAL	���	S9(13).99	�Ҷ��룬���㣬SΪ������	��С���㣬��λС��
    TXXX-AVABAL	�������	S9(13).99	�Ҷ��룬���㣬SΪ������	��С���㣬��λС��
    TXXX-OPNDAT	������	X(10)	YYYY-MM-DD
    TXXX-VALDAT	��Ϣ��	X(10)	YYYY-MM-DD
    TXXX-DPTPRD	��������	9(2)	�Ҷ��룬����
    TXXX-EXPDAT	������	X(10)	YYYY-MM-DD
    TXXX-INTRAT	����	9(3).9(6)	�Ҷ��룬����	��С���㣬��λС��
    TXXX-ACTSTS	״̬	X(1)		1-������0-����
     */
    private String cusact;
    private String cusnam;
    private String boknum;
    private String acttyp;
    private String bokbal;
    private String avabal;
    private String opndat;
    private String valdat;
    private String dptprd;
    private String expdat;
    private String intrat;
    private String actsts;

    {
        offset = 0;
        fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        fieldLengths = new int[]{18, 60, 18, 1, 17, 17, 10, 10, 2, 10, 10, 1};
    }

    public String getCusact() {
        return cusact;
    }

    public void setCusact(String cusact) {
        this.cusact = cusact;
    }

    public String getCusnam() {
        return cusnam;
    }

    public void setCusnam(String cusnam) {
        this.cusnam = cusnam;
    }

    public String getBoknum() {
        return boknum;
    }

    public void setBoknum(String boknum) {
        this.boknum = boknum;
    }

    public String getActtyp() {
        return acttyp;
    }

    public void setActtyp(String acttyp) {
        this.acttyp = acttyp;
    }

    public String getBokbal() {
        return bokbal;
    }

    public void setBokbal(String bokbal) {
        this.bokbal = bokbal;
    }

    public String getAvabal() {
        return avabal;
    }

    public void setAvabal(String avabal) {
        this.avabal = avabal;
    }

    public String getOpndat() {
        return opndat;
    }

    public void setOpndat(String opndat) {
        this.opndat = opndat;
    }

    public String getValdat() {
        return valdat;
    }

    public void setValdat(String valdat) {
        this.valdat = valdat;
    }

    public String getDptprd() {
        return dptprd;
    }

    public void setDptprd(String dptprd) {
        this.dptprd = dptprd;
    }

    public String getExpdat() {
        return expdat;
    }

    public void setExpdat(String expdat) {
        this.expdat = expdat;
    }

    public String getIntrat() {
        return intrat;
    }

    public void setIntrat(String intrat) {
        this.intrat = intrat;
    }

    public String getActsts() {
        return actsts;
    }

    public void setActsts(String actsts) {
        this.actsts = actsts;
    }
}
