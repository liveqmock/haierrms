package haier.rms.sbs.balance.sbs8121;

import gateway.CtgManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.utils.StringUtils;
import pub.tools.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询企业账户余额。
 * User: zhanrui
 * Date: 2011-2-11
 * Time: 15:35:54
 * To change this template use File | Settings | File Templates.
 */
public class T8121Service {

    private static Logger logger = LoggerFactory.getLogger(T8121Service.class);

    private byte[] buffer;   //自SBS接受的数据包
    private String formcode;

    private String floflg;        //后续包标志
    private int totcnt;          //总记录数
    private int curcnt;          //当前包笔数

    private final static int RESPONSE_HEADER_LENGTH = 29;

    private List<String> requestParam;
    private List<String> msgs = new ArrayList<String>();
    private List<T8121ResponseRecord> reponseRecords = new ArrayList<T8121ResponseRecord>();

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

    public void add(T8121ResponseRecord record) {
        this.reponseRecords.add(record);
    }

    public List<T8121ResponseRecord> getAll() {
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

    public List<T8121ResponseRecord> getReponseRecords() {
        return reponseRecords;
    }

    //==============================================================

    public void querySBS() {

        CtgManager ctg = new CtgManager();
        this.reponseRecords = new ArrayList<T8121ResponseRecord>();

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

        requestList.add(StringUtils.addPrefix(Integer.toString(beginnum), "0", 6));                 //  起始笔数

        try {
            byte[] buffer = ctg.processBatchRequest("8121", requestList);

            processResponseHeader(buffer);

            if (!formcode.substring(0, 1).equals("T")) {     //异常情况处理
                String formstr = Message.getString("messages", formcode,null);
                String msg = "SBS数据查询错误，返回错误信息为：" + formcode + "=" +formstr;
                logger.error(msg);
                msgs.add(msg);
                //TODO
                return -1;
            } else {      //报文发送成功
                processResponseBody();
            }

        } catch (Exception e) {
            String msg = "与SBS通讯出现异常";
            msgs.add(msg);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
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

            //T8121ResponseRecord record = new T8121ResponseRecord(buffer);

            int[] fieldLengths = new T8121ResponseRecord().getFieldLengths();


            int recordLength = 0;
            for (int filedlength:fieldLengths){
                 recordLength += filedlength;
            }

            for (k = 0; k < curcnt; k++) {
                byte[] recordbuffer = new byte[recordLength];
                System.arraycopy(buffer, pos, recordbuffer, 0, recordbuffer.length);
                T8121ResponseRecord record = new T8121ResponseRecord(recordbuffer);
                if (record.getCusidt().trim().length()!=18) {
                    System.out.println(record.getCusidt());
                }
                pos += recordbuffer.length;
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

    private void processResponseHeader(byte[] buffer) {

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
    }


}