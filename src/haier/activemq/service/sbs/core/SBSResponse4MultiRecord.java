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
 * Time: 下午5:15
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

            //SOFDATA处理 (判断form号？ 判断SOFDATA长度？)
            if (formcode.substring(0, 1).equals("T")) {
                this.sofDataHeader.assembleFields(this.buffer);

                //自定义记录总长度
                int oneRecordLengthBySelfDefine = this.sofDataDetail.getMessageLength();
                //响应报文中BODY头部的数据总长度
                int dataLengthByResponse = this.sofDataHeader.getDatalen();

                //BODY的数据区起始位置
                int recordOffset = this.sofHeader.getMessageLength() + this.sofDataHeader.getMessageLength();
                //本包记录数
                int recordCount = Integer.parseInt(this.sofDataHeader.getCurcnt());
                //本包数据记录区在整个报文buffer中起始偏移位置
                int srcPos = recordOffset;

                String className = this.sofDataDetail.getClass().getName();
                if (oneRecordLengthBySelfDefine == -1) { //对于通用查询报文（单条记录不定长，换行符分隔）
                    dataLengthByResponse = dataLengthByResponse - 6 - 6; //TODO 需确认
                    byte[] databuf = new byte[dataLengthByResponse];
                    System.arraycopy(buffer, srcPos, databuf, 0, databuf.length);
                    String strDateBuf = new String(databuf, "GBK");
                    BufferedReader br = new BufferedReader(new StringReader(strDateBuf));
                    for (int i = 0; i < recordCount; i++) {
                        String line = br.readLine();
                        SOFDataDetail sofDataDetail = (SOFDataDetail) Class.forName(className).newInstance();
                        //TODO 未处理完 转为DEP处理
                        //BeanUtils.copyProperties(sofDataDetail, this.sofDataDetail);
                        this.sofDataDetailList.add(sofDataDetail);
                        srcPos += oneRecordLengthBySelfDefine;
                    }
                } else { //一般固定长度报文
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
            logger.error("对SBS报文处理时出现错误。", e);
            throw new RuntimeException("对SBS报文处理时出现错误。", e);
        }

    }

    //============================================================
}
