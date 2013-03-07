package haier.activemq.service.sbs.txn.T8855;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * 8855	查询客户账户列表
 * User: zhanrui
 * Date: 2013-3-7
 */
public class T8855SOFDataDetail extends SOFDataDetail {
    /*
    TXXX-CUSACT	账号	X(18) 	左对齐，右补空格
    TXXX-CUSNAM	户名	X(60)	左对齐，右补空格
    TXXX-BOKNUM	存单号	X(18) 	左对齐，右补空格
    TXXX-ACTTYP	账户种类	X(1)	左对齐，右补空格	1-定期存款,2-贷款,3-活期存款
    TXXX-BOKBAL	余额	S9(13).99	右对齐，左补零，S为正负号	带小数点，两位小数
    TXXX-AVABAL	可用余额	S9(13).99	右对齐，左补零，S为正负号	带小数点，两位小数
    TXXX-OPNDAT	开户日	X(10)	YYYY-MM-DD
    TXXX-VALDAT	起息日	X(10)	YYYY-MM-DD
    TXXX-DPTPRD	存期月数	9(2)	右对齐，左补零
    TXXX-EXPDAT	到期日	X(10)	YYYY-MM-DD
    TXXX-INTRAT	利率	9(3).9(6)	右对齐，左补零	带小数点，两位小数
    TXXX-ACTSTS	状态	X(1)		1-正常，0-销户
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
