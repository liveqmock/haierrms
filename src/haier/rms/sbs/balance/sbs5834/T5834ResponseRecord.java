package haier.rms.sbs.balance.sbs5834;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-2-14
 * Time: ����1:54
 * To change this template use File | Settings | File Templates.
 */
public class T5834ResponseRecord {
    /*
        T536-CURCDE	������	X(3)
        T536-SECCCY	�ڶ�����	X(3)
        T536-EFFDAT	��������	X(8)	YYYYMMDD
        T536-EFFTIM	����ʱ��	X(6)	����룬�Ҳ��ո�
        T536-RATFL1	���ʱ�־1	X(1)
        T536-RATVA1	�����	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL2	���ʱ�־2	X(1)
        T536-RATVA2	������	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL3	���ʱ�־3	X(1)
        T536-RATVA3	�ֳ������	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL4	���ʱ�־4	X(1)
        T536-RATVA4	�м��	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL5	���ʱ�־5	X(1)
        T536-RATVA5	ƽ�ּ�	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL6	���ʱ�־6	X(1)
        T536-RATVA6	�����	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL7	���ʱ�־7	X(1)
        T536-RATVA7	��Ԫ�ڲ������	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL8	���ʱ�־8	X(1)
        T536-RATVA8	�������л�׼��	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
        T536-RATFL9	���ʱ�־9	X(1)
        T536-RATVA9	�г���	ZZZ,ZZ9.9999999	�Ҷ��룬�󲹿ո�
*/

    String CURCDE;
    String SECCCY;
    String EFFDAT;
    String EFFTIM;
    String RATFL1;
    String RATVA1;
    String RATFL2;
    String RATVA2;
    String RATFL3;
    String RATVA3;
    String RATFL4;
    String RATVA4;
    String RATFL5;
    String RATVA5;
    String RATFL6;
    String RATVA6;
    String RATFL7;
    String RATVA7;
    String RATFL8;
    String RATVA8;
    String RATFL9;
    String RATVA9;

    //��ӦӦ������ÿ����¼�ĸ����ֶεĳ��ȣ�ע���뱾class�б�������˳���һ���ԣ�
    private int[] fieldLengths = {3, 3, 8, 6, 1, 15,1, 15,1, 15,1, 15,1, 15,1, 15,1, 15,1, 15,1,15};

    public String getCURCDE() {
        return CURCDE;
    }

    public void setCURCDE(String CURCDE) {
        this.CURCDE = CURCDE;
    }

    public String getSECCCY() {
        return SECCCY;
    }

    public void setSECCCY(String SECCCY) {
        this.SECCCY = SECCCY;
    }

    public String getEFFDAT() {
        return EFFDAT;
    }

    public void setEFFDAT(String EFFDAT) {
        this.EFFDAT = EFFDAT;
    }

    public String getEFFTIM() {
        return EFFTIM;
    }

    public void setEFFTIM(String EFFTIM) {
        this.EFFTIM = EFFTIM;
    }

    public String getRATFL1() {
        return RATFL1;
    }

    public void setRATFL1(String RATFL1) {
        this.RATFL1 = RATFL1;
    }

    public String getRATVA1() {
        return RATVA1;
    }

    public void setRATVA1(String RATVA1) {
        this.RATVA1 = RATVA1;
    }

    public String getRATFL2() {
        return RATFL2;
    }

    public void setRATFL2(String RATFL2) {
        this.RATFL2 = RATFL2;
    }

    public String getRATVA2() {
        return RATVA2;
    }

    public void setRATVA2(String RATVA2) {
        this.RATVA2 = RATVA2;
    }

    public String getRATFL3() {
        return RATFL3;
    }

    public void setRATFL3(String RATFL3) {
        this.RATFL3 = RATFL3;
    }

    public String getRATVA3() {
        return RATVA3;
    }

    public void setRATVA3(String RATVA3) {
        this.RATVA3 = RATVA3;
    }

    public String getRATFL4() {
        return RATFL4;
    }

    public void setRATFL4(String RATFL4) {
        this.RATFL4 = RATFL4;
    }

    public String getRATVA4() {
        return RATVA4;
    }

    public void setRATVA4(String RATVA4) {
        this.RATVA4 = RATVA4;
    }

    public String getRATFL5() {
        return RATFL5;
    }

    public void setRATFL5(String RATFL5) {
        this.RATFL5 = RATFL5;
    }

    public String getRATVA5() {
        return RATVA5;
    }

    public void setRATVA5(String RATVA5) {
        this.RATVA5 = RATVA5;
    }

    public String getRATFL6() {
        return RATFL6;
    }

    public void setRATFL6(String RATFL6) {
        this.RATFL6 = RATFL6;
    }

    public String getRATVA6() {
        return RATVA6;
    }

    public void setRATVA6(String RATVA6) {
        this.RATVA6 = RATVA6;
    }

    public String getRATFL7() {
        return RATFL7;
    }

    public void setRATFL7(String RATFL7) {
        this.RATFL7 = RATFL7;
    }

    public String getRATVA7() {
        return RATVA7;
    }

    public void setRATVA7(String RATVA7) {
        this.RATVA7 = RATVA7;
    }

    public String getRATFL8() {
        return RATFL8;
    }

    public void setRATFL8(String RATFL8) {
        this.RATFL8 = RATFL8;
    }

    public String getRATVA8() {
        return RATVA8;
    }

    public void setRATVA8(String RATVA8) {
        this.RATVA8 = RATVA8;
    }

    public String getRATFL9() {
        return RATFL9;
    }

    public void setRATFL9(String RATFL9) {
        this.RATFL9 = RATFL9;
    }

    public String getRATVA9() {
        return RATVA9;
    }

    public void setRATVA9(String RATVA9) {
        this.RATVA9 = RATVA9;
    }

    public int[] getFieldLengths() {
        return fieldLengths;
    }

    //============
    public T5834ResponseRecord() {

    }

    public T5834ResponseRecord(byte[] buffer) {
        Class clazz = this.getClass();

        try {
            Field[] fields = clazz.getDeclaredFields();
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

}
