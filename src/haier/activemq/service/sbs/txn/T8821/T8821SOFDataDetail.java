package haier.activemq.service.sbs.txn.T8821;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2012-05-23
 */
public class T8821SOFDataDetail extends SOFDataDetail {
    /*
    TXXX-TLRNUM	��Ա��	X(4) 	����룬�Ҳ��ո�
    TXXX-TLRNAM	��Ա����	X(8)	����룬�Ҳ��ո�
    TXXX-CURCDE	�ұ�	X(3)
    TXXX-DBTCNT	�跽����	9(6) 	��6λǰ��0
    TXXX-DBTAMT	�跽�ܽ��	S9(13).99	�Ҷ��룬���㣬SΪ������	��С���㣬��λС��
    TXXX-CRTCNT	��������	9(6) 	��6λǰ��0
    TXXX-CRTAMT	�����ܽ��	S9(13).99	�Ҷ��룬���㣬SΪ������	��С���㣬��λС��
     */
    private String tlrnum;
    private String tlrnam;
    private String curcde;
    private String dbtcnt;
    private String dbtamt;
    private String crtcnt;
    private String crtamt;

    {
        offset = 0;
        fieldTypes = new int[]{1, 1, 1, 1, 1, 1, 1};
        fieldLengths = new int[]{4, 8, 3, 6, 17, 6, 17};
    }

    public String getTlrnum() {
        return tlrnum;
    }

    public void setTlrnum(String tlrnum) {
        this.tlrnum = tlrnum;
    }

    public String getTlrnam() {
        return tlrnam;
    }

    public void setTlrnam(String tlrnam) {
        this.tlrnam = tlrnam;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    public String getDbtcnt() {
        return dbtcnt;
    }

    public void setDbtcnt(String dbtcnt) {
        this.dbtcnt = dbtcnt;
    }

    public String getDbtamt() {
        return dbtamt;
    }

    public void setDbtamt(String dbtamt) {
        this.dbtamt = dbtamt;
    }

    public String getCrtcnt() {
        return crtcnt;
    }

    public void setCrtcnt(String crtcnt) {
        this.crtcnt = crtcnt;
    }

    public String getCrtamt() {
        return crtamt;
    }

    public void setCrtamt(String crtamt) {
        this.crtamt = crtamt;
    }
}
