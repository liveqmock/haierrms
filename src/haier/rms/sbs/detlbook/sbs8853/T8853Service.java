package haier.rms.sbs.detlbook.sbs8853;

import gateway.CtgManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 以SBS系统中的账号、日期为条件，查询该账户的当日或者历史交易明细，并生成返回数据包。
 * User: zhanrui
 * Date: 2011-1-17
 * Time: 15:35:54
 * To change this template use File | Settings | File Templates.
 */
public class T8853Service {

    private static Logger logger = LoggerFactory.getLogger(T8853Service.class);

    private byte[] buffer;   //自SBS接受的数据包
    private String formcode;

    private String floflg;          //后续包标志
    private int totcnt;          //总记录数
    private int curcnt;          //当前包笔数

    private final static int RESPONSE_HEADER_LENGTH = 29;

    private List<String> requestParam;
    private List<String> msgs = new ArrayList<String>();
    private List<T8853ResponseRecord> reponseRecords = new ArrayList<T8853ResponseRecord>();

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

    public void add(T8853ResponseRecord record) {
        this.reponseRecords.add(record);
    }

    public List<T8853ResponseRecord> getAll() {
        return this.reponseRecords;
    }

    public String getFloflg() {
        return floflg;
    }

    public void setFloflg(String floflg) {
        this.floflg = floflg;
    }

    public List getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(List requestParam) {
        this.requestParam = requestParam;
    }

    public List<String> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<String> msgs) {
        this.msgs = msgs;
    }

    public List<T8853ResponseRecord> getReponseRecords() {
        return reponseRecords;
    }

    //==============================================================



    public void querySBS() {

        CtgManager ctg = new CtgManager();
        this.reponseRecords = new ArrayList<T8853ResponseRecord>();

        //初始请求笔数
        int beginnum = 1;
        while (beginnum >= 1) {
            beginnum = processNextPkg(ctg, beginnum);
        }

    }

    /*
   后续包处理
    */
    private int processNextPkg(CtgManager ctg, int beginnum) {

        List<String> requestList = new ArrayList<String>();

        for (String param : requestParam) {
            requestList.add(param);
        }

        //requestList.add(StringUtils.addPrefix(Integer.toString(beginnum), "0", 6));                 //  起始笔数
        requestList.add(StringUtils.leftPad(Integer.toString(beginnum), 6, "0"));                 //  起始笔数

        try {
            buffer = ctg.processBatchRequest("8853", requestList);
        } catch (Exception e) {
            String msg = "与SBS通讯出现异常";
            msgs.add(msg);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }

        String errmsg = processResponseHeader(buffer);
        if (!formcode.substring(0, 1).equals("T")) {     //异常情况处理
            //String formstr = Message.getString("messages", formcode,null);
            if (StringUtils.isEmpty(errmsg)) {
                errmsg = Message.getString("messages", formcode, null);
            }
            String msg = "SBS数据查询错误，返回错误信息为：" + formcode + "=" + errmsg;
            logger.error(msg);
            msgs.add(msg);
            throw new RuntimeException(msg);
        } else {      //报文发送成功
            processResponseBody();
        }

        //计算下一步起始笔数
        int nextbeginnum = curcnt + beginnum;
        if (totcnt - nextbeginnum <= 0) {
            nextbeginnum = -1;
        }

        //返回剩余笔数
        return nextbeginnum;

    }

    /*
   批量查询包结果处理
    */
    private void processResponseBody() {
        int k = 0;
        try {
            int pos = 29 + 1 + 6 + 6;

            //业务包处理
            byte[] b1 = new byte[7];
            byte[] b2 = new byte[4];
            byte[] b3 = new byte[3];
            byte[] b4 = new byte[8];
            byte[] b5 = new byte[4];
            byte[] b6 = new byte[4];
            byte[] b7 = new byte[2];
            byte[] b8 = new byte[21];
            byte[] b9 = new byte[21];
            byte[] b10 = new byte[32];

            for (k = 0; k < curcnt; k++) {
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

                System.arraycopy(buffer, pos, b6, 0, b6.length);
                pos += b6.length;
                System.arraycopy(buffer, pos, b7, 0, b7.length);
                pos += b7.length;
                System.arraycopy(buffer, pos, b8, 0, b8.length);
                pos += b8.length;
                System.arraycopy(buffer, pos, b9, 0, b9.length);
                pos += b9.length;
                System.arraycopy(buffer, pos, b10, 0, b10.length);
                pos += b10.length;

                T8853ResponseRecord record = new T8853ResponseRecord();
                record.setCusidt(new String(b1));
                record.setApcode(new String(b2));
                record.setCurcde(new String(b3));
                record.setStmdat(new String(b4));
                record.setTlrnum(new String(b5));

                record.setVchset(new String(b6));
                record.setSetseq(new String(b7));
                record.setTxnamt(new String(b8));
                record.setLasbal(new String(b9));
                record.setFurinf(new String(b10));

                record.setFormatedActnum(record.getCusidt()+"-"+record.getApcode()+"-"+record.getCurcde());
                //record.setFormatedTxnamt(new BigDecimal(record.getTxnamt()));

                this.reponseRecords.add(record);
            }
        } catch (Exception e) {
            logger.error("报文解包时出现问题：" + k);
            throw new RuntimeException("报文解包时出现问题：" + k, e);
        }
    }


    /*
     包头信息处理
     设置
     */

    private String processResponseHeader(byte[] buffer) {

        this.setBuffer(buffer);
        int k = 0;

        try {
            int pos = 21;
            byte[] bFormcode = new byte[4];
            System.arraycopy(buffer, pos, bFormcode, 0, 4);
            this.setFormcode(new String(bFormcode));

            if (!this.formcode.substring(0, 1).equals("T")) {
                short msgLen = byteToShort(buffer[28], buffer[27]);
                byte[] bErrmsg = new byte[msgLen];
                System.arraycopy(buffer, 29, bErrmsg, 0, bErrmsg.length);
                return new String(bErrmsg);  //formcode 非“T”开头，则返回报文为出错报文，不再进行包体分析
            }
            //包头处理
            pos = 29;
            byte[] bFloflg = new byte[1];
            byte[] bTotcnt = new byte[6];
            byte[] bCurcnt = new byte[6];

            System.arraycopy(buffer, pos, bFloflg, 0, bFloflg.length);
            pos += bFloflg.length;
            System.arraycopy(buffer, pos, bTotcnt, 0, bTotcnt.length);
            pos += bTotcnt.length;
            System.arraycopy(buffer, pos, bCurcnt, 0, bCurcnt.length);
            pos += bCurcnt.length;

            setFloflg(new String(bFloflg));
            setTotcnt(Integer.parseInt(new String(bTotcnt)));
            setCurcnt(Integer.parseInt(new String(bCurcnt)));

        } catch (Exception e) {
            logger.error("报文解包时出现问题：" + k);
            throw new RuntimeException(e);
        }
        return null;
    }

    private short byteToShort(byte high, byte low) {
        byte[] msglenBuf = new byte[2];
        msglenBuf[0] = high;
        msglenBuf[1] = low;
        short tmp1 = (short)(msglenBuf[0] & 0xFF);
        short tmp2 = (short)((msglenBuf[1] << 8) & 0xFF00);
        return (short)(tmp2 | tmp1);
    }

}