package haier.rms.sbs.balance.sbs5834;

import gateway.CtgManager;
import gateway.common.util.SystemErrorException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询汇率。
 * User: zhanrui
 * Date: 2011-2-11
 * Time: 15:35:54
 * To change this template use File | Settings | File Templates.
 */
public class T5834Service {

    private static Logger logger = LoggerFactory.getLogger(T5834Service.class);

    private byte[] buffer;   //自SBS接受的数据包
    private String formcode;

    private final static int RESPONSE_HEADER_LENGTH = 29;

    private List<String> requestParam;
    private List<String> msgs = new ArrayList<String>();
    private T5834ResponseRecord  reponseRecord;

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

    //==============================================================

    /**
     * @param currdate yyyyMMdd
     * @param curcde   货币码
     * @param secccy   第二货币
     * @return  汇率
     */
    public String querySBS(String currdate, String curcde, String secccy) {

        List list = new ArrayList();

        list.add("111111");
        list.add("010");
        list.add("60");
        list.add(curcde);
        list.add(secccy);
        list.add(currdate);
        list.add("000000");
        list.add("");        //买入价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志
        list.add("");        //XX价
        list.add("");  //汇率标志

        list.add("0");  //单笔查询

        try {
            CtgManager ctg = new CtgManager();
            byte[] buffer = ctg.processBatchRequest("5834", list);

            processResponseHeader(buffer);

            if (!this.formcode.substring(0, 1).equals("T")) {     //异常情况处理
                String message = "查询SBS汇率时返回异常：" + formcode;
                msgs.add(message);
                SystemErrorException se = new SystemErrorException(message);
                //se.initCause(e);
                throw se;
            } else {      //报文发送成功
                processResponseBody();
            }
            //返回中间价
            if (StringUtils.isEmpty(this.reponseRecord.getRATVA4())) {
                throw new RuntimeException("SBS返回汇率中间价为空！");
            }
            return this.reponseRecord.getRATVA4();
        } catch (Exception e) {
            logger.error("获取SBS汇率时发生错误！",e);
            throw new RuntimeException("获取SBS汇率时发生错误！",e);
        }
    }

    /*
   批量查询包结果处理
    */
    private void processResponseBody() {
        int k = 0;
        try {
            int pos = 29;

            int[] fieldLengths = new T5834ResponseRecord().getFieldLengths();
            int recordLength = 0;
            for (int filedlength : fieldLengths) {
                recordLength += filedlength;
            }
            byte[] recordbuffer = new byte[recordLength];
            System.arraycopy(buffer, pos, recordbuffer, 0, recordbuffer.length);
            this.reponseRecord = new T5834ResponseRecord(recordbuffer);
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
        } catch (Exception e) {
            logger.error("报文解包时出现问题：" + k);
            throw new RuntimeException(e);
        }
    }


}