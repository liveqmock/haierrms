package haier.rms.sbs.balance.sbs8121;

import java.lang.reflect.Field;

/**
 * 企业余额查询.
 * User: zhanrui
 * Date: 11-2-11
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class T8121ResponseRecord {
/*
    T839-CUSIDT	账号	  X(18)
    T839-APCODE	户名	  X(60)
    T839-LASBAL	余额	  X(18)
    T839-CURCDE	账户类别	X(1)
*/

    private String cusidt;
    private String apcode;
    private String lasbal;
    private String curcde;

    //对应应答报文中每条记录的各个字段的长度（注意与本class中变量定义顺序的一致性）
    private  int[] fieldLengths = {18, 60, 18, 1};


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

    public String getLasbal() {
        return lasbal;
    }

    public void setLasbal(String lasbal) {
        this.lasbal = lasbal;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    public int[] getFieldLengths() {
        return fieldLengths;
    }


    //============
    public T8121ResponseRecord() {

    }
    public T8121ResponseRecord(byte[] buffer) {
        Class clazz = this.getClass();

        try {
            Field[] fields = clazz.getDeclaredFields();
//          int pos = 29 + 1 + 6 + 6;
            int pos = 0;
            for (int i = 0; i < fieldLengths.length; i++) {
                byte[] b = new byte[fieldLengths[i]];
                System.arraycopy(buffer, pos, b, 0, b.length);
                String s = new String(b);
                fields[i].setAccessible(true);
                fields[i].set(this, s);
                pos += b.length;
            }
        } catch (Exception e) {
            System.out.println("error");

        }
    }

    public static void main(String[] args) {
        T8121ResponseRecord record = new T8121ResponseRecord(new byte[]{'1', '2', '2', '3', '3', '3', '4', '4', '4', '4'});
        System.out.println("ok");
    }


}
