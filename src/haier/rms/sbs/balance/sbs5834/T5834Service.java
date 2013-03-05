package haier.rms.sbs.balance.sbs5834;

import gateway.CtgManager;
import gateway.common.util.SystemErrorException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ѯ���ʡ�
 * User: zhanrui
 * Date: 2011-2-11
 * Time: 15:35:54
 * To change this template use File | Settings | File Templates.
 */
public class T5834Service {

    private static Logger logger = LoggerFactory.getLogger(T5834Service.class);

    private byte[] buffer;   //��SBS���ܵ����ݰ�
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
     * @param curcde   ������
     * @param secccy   �ڶ�����
     * @return  ����
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
        list.add("");        //�����
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־

        list.add("0");  //���ʲ�ѯ

        try {
            CtgManager ctg = new CtgManager();
            byte[] buffer = ctg.processBatchRequest("5834", list);

            processResponseHeader(buffer);

            if (!this.formcode.substring(0, 1).equals("T")) {     //�쳣�������
                String message = "��ѯSBS����ʱ�����쳣��" + formcode;
                msgs.add(message);
                SystemErrorException se = new SystemErrorException(message);
                //se.initCause(e);
                throw se;
            } else {      //���ķ��ͳɹ�
                processResponseBody();
            }
            //�����м��
            if (StringUtils.isEmpty(this.reponseRecord.getRATVA4())) {
                throw new RuntimeException("SBS���ػ����м��Ϊ�գ�");
            }
            return this.reponseRecord.getRATVA4();
        } catch (Exception e) {
            logger.error("��ȡSBS����ʱ��������",e);
            throw new RuntimeException("��ȡSBS����ʱ��������",e);
        }
    }

    /*
   ������ѯ���������
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
            logger.error("���Ľ��ʱ�������⣺" + k);
            throw new RuntimeException("���Ľ��ʱ�������⣺" + k, e);
        }
    }


    /*
     ��ͷ��Ϣ����
     ����
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
            logger.error("���Ľ��ʱ�������⣺" + k);
            throw new RuntimeException(e);
        }
    }


}