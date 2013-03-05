package gateway.SBS.BalanceQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * SBS账户余额查询
 * User: zhanrui
 * Date: 2010-05-10
 * Time: 15:35:54
 * To change this template use File | Settings | File Templates.
 */
public class BalanceQueryResult {


    byte[] buffer;   //自SBS接受的数据包
    String formcode;

    int totcnt;          //总记录数
    int curcnt;          //当前包笔数


    List<BalanceRecord> dtldat = new ArrayList();
    private static Log logger = LogFactory.getLog(BalanceQueryResult.class);

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

    public void add(BalanceRecord record) {
        this.dtldat.add(record);
    }

    public List<BalanceRecord> getAll() {
        return this.dtldat;
    }

    /*
   批量查询包结果处理
    */

    public void getBatchQueryMsg() {
        int k = 0;
        try {

            int pos = 29 +1 + 6 + 6;

            //业务包处理
            byte[] b1 = new byte[18];
            byte[] b2 = new byte[60];
            byte[] b3 = new byte[18];
            byte[] b4 = new byte[1];

            for (k = 0; k < curcnt; k++) {
                BalanceRecord record = new BalanceRecord();
                System.arraycopy(buffer, pos, b1, 0, b1.length);
                pos += b1.length;
                System.arraycopy(buffer, pos, b2, 0, b2.length);
                pos += b2.length;
                System.arraycopy(buffer, pos, b3, 0, b3.length);
                pos += b3.length;
                System.arraycopy(buffer, pos, b4, 0, b4.length);
                pos += b4.length;
                record.setCUSIDT(new String(b1));
                record.setAPCODE(new String(b2));
                record.setLASBAL(new String(b3));
                record.setCURCDE(new String(b4));
                this.add(record);
            }
        } catch (Exception e) {
            System.out.println("报文解包时出现问题：" + k);
            logger.error("报文解包时出现问题：" + k);
            throw new RuntimeException(e);
        }
    }


    /*
     包头信息处理
     构造方法
     */

    public BalanceQueryResult(byte[] buffer) {

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
            pos = 29 + 1;
            byte[] bTotcnt = new byte[6];
            byte[] bCurcnt = new byte[6];

            System.arraycopy(buffer, pos, bTotcnt, 0, bTotcnt.length);
            pos += bTotcnt.length;
            System.arraycopy(buffer, pos, bCurcnt, 0, bCurcnt.length);
            pos += bCurcnt.length;

            setTotcnt(Integer.parseInt(new String(bTotcnt)));
            setCurcnt(Integer.parseInt(new String(bCurcnt)));

        } catch (Exception e) {
            System.out.println("报文解包时出现问题：" + k);
            logger.error("报文解包时出现问题：" + k);
            throw new RuntimeException(e);
        }
    }


}