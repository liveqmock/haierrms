package gateway.SBS.BalanceQuery;

/**
 * 批量代扣查询结果对应的失败记录类
 * User: zhanrui
 * Date: 2009-7-6
 * Time: 15:44:45
 * To change this template use File | Settings | File Templates.
 */
public class BalanceRecord {

/*
    T839-CUSIDT	账号	  X(18)
    T839-APCODE	户名	  X(60)
    T839-LASBAL	余额	  X(18)
    T839-CURCDE	账户类别	X(1)
*/

    String CUSIDT;
    String APCODE;
    String LASBAL;
    String CURCDE;

    public String getCUSIDT() {
        return CUSIDT;
    }

    public void setCUSIDT(String CUSIDT) {
        this.CUSIDT = CUSIDT;
    }

    public String getAPCODE() {
        return APCODE;
    }

    public void setAPCODE(String APCODE) {
        this.APCODE = APCODE;
    }

    public String getLASBAL() {
        return LASBAL;
    }

    public void setLASBAL(String LASBAL) {
        this.LASBAL = LASBAL;
    }

    public String getCURCDE() {
        return CURCDE;
    }

    public void setCURCDE(String CURCDE) {
        this.CURCDE = CURCDE;
    }


}