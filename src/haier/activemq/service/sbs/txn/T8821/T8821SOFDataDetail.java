package haier.activemq.service.sbs.txn.T8821;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2012-05-23
 */
public class T8821SOFDataDetail extends SOFDataDetail {
    /*
    TXXX-TLRNUM	柜员号	X(4) 	左对齐，右补空格
    TXXX-TLRNAM	柜员名称	X(8)	左对齐，右补空格
    TXXX-CURCDE	币别	X(3)
    TXXX-DBTCNT	借方笔数	9(6) 	满6位前补0
    TXXX-DBTAMT	借方总金额	S9(13).99	右对齐，左补零，S为正负号	带小数点，两位小数
    TXXX-CRTCNT	贷方笔数	9(6) 	满6位前补0
    TXXX-CRTAMT	贷方总金额	S9(13).99	右对齐，左补零，S为正负号	带小数点，两位小数
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
