package gateway.CorpPayFeeQuery;

/**
 * 批量代扣查询结果对应的失败记录类
 * User: zhanrui
 * Date: 2009-7-6
 * Time: 15:44:45
 * To change this template use File | Settings | File Templates.
 */
public class CorpPayFeeRecord {

    /*
    总位数 110
    
    付款帐号  18位
    付款单位  60位
    付款银行   10位
    笔数      6位
    手续费    16位
     */
    String corpacct;
    String corpname;
    String bankname;
    String countnum;
    String fee;

    public String getCorpacct() {
        return corpacct;
    }

    public void setCorpacct(String corpacct) {
        this.corpacct = corpacct;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getCountnum() {
        return countnum;
    }

    public void setCountnum(String countnum) {
        this.countnum = countnum;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }


}