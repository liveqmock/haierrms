package gateway.SBS.ExchangeRate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SBS汇率查询   单包
 * User: zhanrui
 */
public class ExRateQueryResult {


    byte[] buffer;   //自SBS接受的数据包
    String formcode;

    ExRateRecordBean record = new ExRateRecordBean();
    private static Log logger = LogFactory.getLog(ExRateQueryResult.class);

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public String getFormcode() {
        return formcode;
    }

    public void setFormcode(String formcode) {
        this.formcode = formcode;
    }

    public ExRateRecordBean getRecord() {
        return record;
    }

    public void setRecord(ExRateRecordBean record) {
        this.record = record;
    }

    /*
     包信息处理
     构造方法
     */

    public ExRateQueryResult(byte[] buffer) {

        this.setBuffer(buffer);
        int k = 0;

        try {
            int pos = 21;
            byte[] bFormcode = new byte[4];
            System.arraycopy(buffer, pos, bFormcode, 0, 4);
            this.setFormcode(new String(bFormcode));

            if (!this.formcode.substring(0, 1).equals("T")) {
                return;  //formcode 非“T”开头，则返回报文为出错报文，不再进行包体分析
            }
            //包头处理
            pos = 29;

            byte[] b1 = new byte[3];
            byte[] b2 = new byte[3];
            byte[] b3 = new byte[8];
            byte[] b4 = new byte[6];
            byte[] b5 = new byte[1];
            byte[] b6 = new byte[15];
            byte[] b7 = new byte[1];
            byte[] b8 = new byte[15];
            byte[] b9 = new byte[1];
            byte[] b10= new byte[15];
            byte[] b11= new byte[1];
            byte[] b12= new byte[15];
            byte[] b13= new byte[1];
            byte[] b14= new byte[15];
            byte[] b15= new byte[1];
            byte[] b16= new byte[15];
            byte[] b17= new byte[1];
            byte[] b18= new byte[15];
            byte[] b19= new byte[1];
            byte[] b20= new byte[15];
            byte[] b21= new byte[1];
            byte[] b22= new byte[15];

            System.arraycopy(buffer, pos, b1, 0, b1.length);
            pos += b1.length;
            record.setCURCDE(new String(b1));
            System.arraycopy(buffer, pos, b2, 0, b2.length);
            pos += b2.length;
            record.setSECCCY(new String(b2));
            System.arraycopy(buffer, pos, b3, 0, b3.length);
            pos += b3.length;
            record.setEFFDAT(new String(b3));
            System.arraycopy(buffer, pos, b4, 0, b4.length);
            pos += b4.length;
            record.setEFFTIM(new String(b4));

            //汇率1
            System.arraycopy(buffer, pos, b5, 0, b5.length);
            pos += b5.length;
            record.setRATFL1(new String(b5));
            System.arraycopy(buffer, pos, b6, 0, b6.length);
            pos += b6.length;
            record.setRATVA1(new String(b6));

            System.arraycopy(buffer, pos, b7, 0, b7.length);
            pos += b5.length;
            record.setRATFL2(new String(b7));
            System.arraycopy(buffer, pos, b8, 0, b8.length);
            pos += b6.length;
            record.setRATVA2(new String(b8));

            System.arraycopy(buffer, pos, b9, 0, b9.length);
            pos += b9.length;
            record.setRATFL3(new String(b9));
            System.arraycopy(buffer, pos, b10, 0, b10.length);
            pos += b10.length;
            record.setRATVA3(new String(b10));

            System.arraycopy(buffer, pos, b11, 0, b11.length);
            pos += b11.length;
            record.setRATFL4(new String(b11));
            System.arraycopy(buffer, pos, b12, 0, b12.length);
            pos += b12.length;
            record.setRATVA4(new String(b12));

            System.arraycopy(buffer, pos, b13, 0, b13.length);
            pos += b13.length;
            record.setRATFL5(new String(b13));
            System.arraycopy(buffer, pos, b14, 0, b14.length);
            pos += b14.length;
            record.setRATVA5(new String(b14));

            System.arraycopy(buffer, pos, b15, 0, b15.length);
            pos += b15.length;
            record.setRATFL6(new String(b15));
            System.arraycopy(buffer, pos, b16, 0, b16.length);
            pos += b16.length;
            record.setRATVA6(new String(b16));

            System.arraycopy(buffer, pos, b17, 0, b17.length);
            pos += b17.length;
            record.setRATFL7(new String(b17));
            System.arraycopy(buffer, pos, b18, 0, b18.length);
            pos += b18.length;
            record.setRATVA7(new String(b18));

            System.arraycopy(buffer, pos, b19, 0, b19.length);
            pos += b19.length;
            record.setRATFL8(new String(b19));
            System.arraycopy(buffer, pos, b20, 0, b20.length);
            pos += b20.length;
            record.setRATVA8(new String(b20));

            System.arraycopy(buffer, pos, b21, 0, b21.length);
            pos += b21.length;
            record.setRATFL9(new String(b21));
            System.arraycopy(buffer, pos, b22, 0, b22.length);
            pos += b22.length;
            record.setRATVA9(new String(b22));

        } catch (Exception e) {
            System.out.println("报文解包时出现问题：" + k);
            logger.error("报文解包时出现问题：" + k);
            throw new RuntimeException(e);
        }
    }


}