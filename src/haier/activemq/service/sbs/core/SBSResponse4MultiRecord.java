package haier.activemq.service.sbs.core;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-14
 * Time: ����5:15
 * To change this template use File | Settings | File Templates.
 */
public class SBSResponse4MultiRecord extends SBSResponse {
    private static Logger logger = LoggerFactory.getLogger(SBSResponse4MultiRecord.class);

    private SOFDataHeader4MultiRecord sofDataHeader = new SOFDataHeader4MultiRecord();
    private SOFDataDetail sofDataDetail;
    private List<SOFDataDetail> sofDataDetailList = new ArrayList<SOFDataDetail>();

    public SOFDataHeader4MultiRecord getSofDataHeader() {
        return sofDataHeader;
    }

    public void setSofDataHeader(SOFDataHeader4MultiRecord sofDataHeader) {
        this.sofDataHeader = sofDataHeader;
    }

    public void setSofDataDetail(SOFDataDetail sofDataDetail) {
        this.sofDataDetail = sofDataDetail;
    }

    public List<SOFDataDetail> getSofDataDetailList() {
        return sofDataDetailList;
    }

//==========================================================

    public void init(byte[] buffer) {

        try {
            this.buffer = buffer;
            this.sofHeader.assembleFields(this.buffer);
            this.formcode = this.sofHeader.getFormCode();

            //SOFDATA���� (�ж�form�ţ� �ж�SOFDATA���ȣ�)
            if (formcode.substring(0, 1).equals("T")) {
                this.sofDataHeader.assembleFields(this.buffer);

                //�Զ����¼�ܳ���
                int oneRecordLengthBySelfDefine = this.sofDataDetail.getMessageLength();
                //��Ӧ������BODYͷ���������ܳ���
                int dataLengthByResponse = this.sofDataHeader.getDatalen();

                //BODY����������ʼλ��
                int recordOffset = this.sofHeader.getMessageLength() + this.sofDataHeader.getMessageLength();
                //������¼��
                int recordCount = Integer.parseInt(this.sofDataHeader.getCurcnt());
                //�������ݼ�¼������������buffer����ʼƫ��λ��
                int srcPos = recordOffset;

                String className = this.sofDataDetail.getClass().getName();
                if (oneRecordLengthBySelfDefine == -1) { //����ͨ�ò�ѯ���ģ�������¼�����������з��ָ���
                    dataLengthByResponse = dataLengthByResponse - 6 - 6; //TODO ��ȷ��
                    byte[] databuf = new byte[dataLengthByResponse];
                    System.arraycopy(buffer, srcPos, databuf, 0, databuf.length);
                    String strDateBuf = new String(databuf, "GBK");
                    BufferedReader br = new BufferedReader(new StringReader(strDateBuf));
                    for (int i = 0; i < recordCount; i++) {
                        String line = br.readLine();
                        SOFDataDetail sofDataDetail = (SOFDataDetail) Class.forName(className).newInstance();
                        //TODO δ������ תΪDEP����
                        //BeanUtils.copyProperties(sofDataDetail, this.sofDataDetail);
                        this.sofDataDetailList.add(sofDataDetail);
                        srcPos += oneRecordLengthBySelfDefine;
                    }
                } else { //һ��̶����ȱ���
                    for (int i = 0; i < recordCount; i++) {
                        byte[] recordBuffer = new byte[oneRecordLengthBySelfDefine];
                        System.arraycopy(buffer, srcPos, recordBuffer, 0, recordBuffer.length);
                        //TODO  this.sofDataDetail init?
                        this.sofDataDetail.assembleFields(recordBuffer);
                        SOFDataDetail sofDataDetail = (SOFDataDetail) Class.forName(className).newInstance();
                        BeanUtils.copyProperties(sofDataDetail, this.sofDataDetail);
                        this.sofDataDetailList.add(sofDataDetail);
                        srcPos += oneRecordLengthBySelfDefine;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("��SBS���Ĵ���ʱ���ִ���", e);
            throw new RuntimeException("��SBS���Ĵ���ʱ���ִ���", e);
        }

    }

    //============================================================
}
