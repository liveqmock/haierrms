package haier.rms.sbs.balance.sbs8121;

import java.lang.reflect.Field;

/**
 * ��ҵ����ѯ.
 * User: zhanrui
 * Date: 11-2-11
 * Time: ����2:38
 * To change this template use File | Settings | File Templates.
 */
public class T8121ResponseRecord {
/*
    T839-CUSIDT	�˺�	  X(18)
    T839-APCODE	����	  X(60)
    T839-LASBAL	���	  X(18)
    T839-CURCDE	�˻����	X(1)
*/

    private String cusidt;
    private String apcode;
    private String lasbal;
    private String curcde;

    //��ӦӦ������ÿ����¼�ĸ����ֶεĳ��ȣ�ע���뱾class�б�������˳���һ���ԣ�
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
