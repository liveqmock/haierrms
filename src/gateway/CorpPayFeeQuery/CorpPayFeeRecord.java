package gateway.CorpPayFeeQuery;

/**
 * �������۲�ѯ�����Ӧ��ʧ�ܼ�¼��
 * User: zhanrui
 * Date: 2009-7-6
 * Time: 15:44:45
 * To change this template use File | Settings | File Templates.
 */
public class CorpPayFeeRecord {

    /*
    ��λ�� 110
    
    �����ʺ�  18λ
    ���λ  60λ
    ��������   10λ
    ����      6λ
    ������    16λ
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