package haier.service.common;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4SingleRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T5834.T5834Handler;
import haier.activemq.service.sbs.txn.T5834.T5834SOFDataDetail;
import haier.repository.dao.SbsActccyMapper;
import haier.repository.model.SbsActccy;
import haier.repository.model.SbsActccyExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.platform.advance.utils.PropertyManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��SBS��صĹ��õķ���.
 * User: zhanrui
 * Date: 11-6-21
 * Time: ����2:13
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SbsCommonService {

    @Autowired
    private SbsActccyMapper sbsActccyMapper;

    public Map<String, BigDecimal> queryExchangeRateList(List<String> curcdeList, String yyyymmdd) {

        if (curcdeList == null || curcdeList.size() == 0 || yyyymmdd.length() != 8) {
            throw new IllegalArgumentException("T8534��ѯ���ʽ��ײ�������");
        }
        Map<String, BigDecimal> roeMap = new HashMap<String, BigDecimal>();
        for (String curcde : curcdeList) {
            roeMap.put(curcde, queryExchangeRate(curcde, yyyymmdd));
        }
        return roeMap;
    }

    public BigDecimal queryExchangeRate(String curcde, String yyyymmdd) {

        if (curcde.length() != 3 || yyyymmdd.length() != 8) {
            throw new IllegalArgumentException("T8534��ѯ���ʽ��ײ������ȴ���");
        }

        T5834Handler handler = new T5834Handler();
        List<String> paramList = new ArrayList<String>();

        paramList.add("111111"); //����
        paramList.add("010");    //��Ա������
        paramList.add("60");     //��Ա���ź�

        //list.add(curcde);
        paramList.add(curcde);
        //list.add(secccy);
        paramList.add("001");
        //list.add(currdate);
        paramList.add(yyyymmdd);

        paramList.add("000000");
        paramList.add("");        //�����
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־
        paramList.add("");        //XX��
        paramList.add("");  //���ʱ�־

        paramList.add("0");  //���ʲ�ѯ


        SBSRequest request = new SBSRequest("5834", paramList);
        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();

        SOFDataDetail sofDataDetail = new T5834SOFDataDetail();
        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);

        String formcode = response.getFormcode();
        if (!formcode.substring(0, 1).equals("T")) {
            String forminfo = response.getForminfo();
            throw new RuntimeException("�����쳣��" + formcode + (forminfo == null ? " " : forminfo) + PropertyManager.getProperty(formcode));
        }

        String strRoe = ((T5834SOFDataDetail) sofDataDetail).getRATVA4();

        if (StringUtils.isEmpty(strRoe)) {
            throw new RuntimeException("SBS���ػ����м��Ϊ�գ�");
        }

        if (StringUtils.isEmpty(strRoe.trim())) {
            return new BigDecimal("0");
        } else {
            return new BigDecimal(strRoe.replaceAll("(\\,|\\s+)", ""));
        }

    }

    public String selectCurrNameByCurrCode(String currCode){
        SbsActccyExample example = new SbsActccyExample();
        example.createCriteria().andCurcdeEqualTo(currCode);
        List<SbsActccy> records  = sbsActccyMapper.selectByExample(example);
        if (records.isEmpty()) {
            return null;
        }else{
            return records.get(0).getCurnmc();
        }
    }
    public List<SbsActccy> selectCurrNameByCurrCode(){
        SbsActccyExample example = new SbsActccyExample();
        example.createCriteria();
        return sbsActccyMapper.selectByExample(example);
    }
}
