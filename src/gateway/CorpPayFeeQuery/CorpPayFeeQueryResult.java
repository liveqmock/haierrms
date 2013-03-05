package gateway.CorpPayFeeQuery;

import gateway.BalanceRecord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * �Թ�֧�������Ѳ�ѯ
 * User: zhanrui
 * Date: 2009-7-6
 * Time: 15:35:54
 * To change this template use File | Settings | File Templates.
 */
public class CorpPayFeeQueryResult {


    byte[] buffer;   //��SBS���ܵ����ݰ�
    String formcode;

//    String floflg;          //��������־
    int totcnt;          //�ܼ�¼��
    int curcnt;          //��ǰ������


    List<CorpPayFeeRecord> dtldat = new ArrayList();
    private static Log logger = LogFactory.getLog(CorpPayFeeQueryResult.class);

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

    public int getTotcnt() {
        return totcnt;
    }

    public void setTotcnt(int totcnt) {
        this.totcnt = totcnt;
    }

    public int getCurcnt() {
        return curcnt;
    }

    public void setCurcnt(int curcnt) {
        this.curcnt = curcnt;
    }

    public void add(CorpPayFeeRecord record) {
        this.dtldat.add(record);
    }

    public List<CorpPayFeeRecord> getAll() {
        return this.dtldat;
    }

    /*
   ������ѯ���������
    */

    public void getBatchQueryMsg() {
        int k = 0;
        try {

/*
            //��ͷ����
            int pos = 29;
//            byte[] bFloflg = new byte[1];
            byte[] bTotcnt = new byte[6];
            byte[] bCurcnt = new byte[6];

//            System.arraycopy(buffer, pos, bFloflg, 0, bFloflg.length);
//            pos += bFloflg.length;
            System.arraycopy(buffer, pos, bTotcnt, 0, bTotcnt.length);
            pos += bTotcnt.length;
            System.arraycopy(buffer, pos, bCurcnt, 0, bCurcnt.length);
            pos += bCurcnt.length;

//            result.setFloflg(new String(bFloflg));
            result.setTotcnt(new String(bCurcnt));
            result.setCurcnt(new String(bCurcnt));

            int curcnt = Integer.parseInt(result.getCurcnt());
*/

            int pos = 29 + 6 + 6;

            //ҵ�������
            byte[] b1 = new byte[18];
            byte[] b2 = new byte[60];
            byte[] b3 = new byte[10];
            byte[] b4 = new byte[6];
            byte[] b5 = new byte[16];

            for (k = 0; k < curcnt; k++) {
                CorpPayFeeRecord record = new CorpPayFeeRecord();
                System.arraycopy(buffer, pos, b1, 0, b1.length);
                pos += b1.length;
                System.arraycopy(buffer, pos, b2, 0, b2.length);
                pos += b2.length;
                System.arraycopy(buffer, pos, b3, 0, b3.length);
                pos += b3.length;
                System.arraycopy(buffer, pos, b4, 0, b4.length);
                pos += b4.length;
                System.arraycopy(buffer, pos, b5, 0, b5.length);
                pos += b5.length;
                record.setCorpacct(new String(b1));
                record.setCorpname(new String(b2));
                record.setBankname(new String(b3));
                record.setCountnum(new String(b4));
                record.setFee(new String(b5));
                this.add(record);
            }
//            return result;
        } catch (Exception e) {
            System.out.println("���Ľ��ʱ�������⣺" + k);
            logger.error("���Ľ��ʱ�������⣺" + k);
//            Debug.debug(e);
            throw new RuntimeException(e);
        }
    }


    /*
     ��ͷ��Ϣ����
     ����
     */

    public CorpPayFeeQueryResult(byte[] buffer) {

        this.setBuffer(buffer);
        int k = 0;

        try {
            int pos = 21;
            byte[] bFormcode = new byte[4];
            System.arraycopy(buffer, pos, bFormcode, 0, 4);
            this.setFormcode(new String(bFormcode));

            if (!this.formcode.substring(0, 1).equals("T")) {
                return;  //formcode �ǡ�T����ͷ���򷵻ر���Ϊ�����ģ����ٽ��а������
            }
            //��ͷ����
            pos = 29;
//            byte[] bFloflg = new byte[1];
            byte[] bTotcnt = new byte[6];
            byte[] bCurcnt = new byte[6];

//            System.arraycopy(buffer, pos, bFloflg, 0, bFloflg.length);
//            pos += bFloflg.length;
            System.arraycopy(buffer, pos, bTotcnt, 0, bTotcnt.length);
            pos += bTotcnt.length;
            System.arraycopy(buffer, pos, bCurcnt, 0, bCurcnt.length);
            pos += bCurcnt.length;

//            result.setFloflg(new String(bFloflg));
            setTotcnt(Integer.parseInt(new String(bTotcnt)));
            setCurcnt(Integer.parseInt(new String(bCurcnt)));

        } catch (Exception e) {
            System.out.println("���Ľ��ʱ�������⣺" + k);
            logger.error("���Ľ��ʱ�������⣺" + k);
            throw new RuntimeException(e);
        }
    }


}