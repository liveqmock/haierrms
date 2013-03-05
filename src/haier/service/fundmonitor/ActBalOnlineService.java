package haier.service.fundmonitor;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SBSResponse4SingleRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T8123.T8123Handler;
import haier.activemq.service.sbs.txn.T8123.T8123SOFDataDetail;
import haier.activemq.service.sbs.txn.Tn062.Tn062Handler;
import haier.activemq.service.sbs.txn.Tn062.Tn062SOFDataDetail;
import haier.repository.dao.MtActbalMapper;
import haier.repository.dao.MtActtypeMapper;
import haier.repository.dao.fundmonitor.ActbalMonitorMapper;
import haier.repository.model.MtActbal;
import haier.repository.model.MtActbalExample;
import haier.repository.model.MtActtype;
import haier.repository.model.MtActtypeExample;
import haier.repository.model.fundmonitor.MtActbalBean;
import haier.repository.model.fundmonitor.MtActbalSumBean;
import haier.service.common.SbsCommonService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-20
 * Time: ����5:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ActBalOnlineService {
    private static final String BANKCD_SBS = "999";
    private static final String BANKCD_CCB = "105";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MtActtypeMapper mtActtypeMapper;
    @Autowired
    private MtActbalMapper mtActbalMapper;
    @Autowired
    private ActbalMonitorMapper actbalMonitorMapper;

    @Autowired
    private SbsCommonService sbsCommonService;

    /**
     * SBS ��ҵ��� ��ѯ
     */

    @Transactional
    public void queryAndInsertAllActBalFromSBS() {
        SBSResponse4MultiRecord response = processSbsTxn_8123("1");
        List<SOFDataDetail> sofDataDetailList = response.getSofDataDetailList();
        response = processSbsTxn_8123("3");
        sofDataDetailList.addAll(response.getSofDataDetailList());
        //TODO  �ж�Ϊ��

        Date date = new Date();
        String strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String yyyymmdd = new SimpleDateFormat("yyyyMMdd").format(date);
        String strtime = new SimpleDateFormat("HH:mm:ss").format(date);

        MtActbalExample example = new MtActbalExample();
        example.clear();
        example.createCriteria().andBankcdEqualTo(BANKCD_SBS).andQrydateEqualTo(strdate).andQrytimeEqualTo(strtime);
        mtActbalMapper.deleteByExample(example);

        Map<String, BigDecimal> roeMap = new HashMap<String, BigDecimal>();
        BigDecimal bd100 = new BigDecimal("100");


        for (SOFDataDetail dataDetail : sofDataDetailList) {
            MtActbal mtActbal = new MtActbal();
            mtActbal.setBankcd(BANKCD_SBS);
            String actnum = "8010" + ((T8123SOFDataDetail) dataDetail).getActnum();
            mtActbal.setActno(actnum);

            String stramt = ((T8123SOFDataDetail) dataDetail).getActbal();
            BigDecimal balamt = new BigDecimal(stramt.replaceAll("(\\,|\\s+)", ""));

            mtActbal.setBalamt(balamt);


            //���ִ���
            if (balamt.doubleValue() == 0) {
                mtActbal.setExrate(new BigDecimal(0));
                mtActbal.setRmbamt(new BigDecimal(0));
            } else {
                String curcde = actnum.substring(15, 18);
                if (curcde.equals("001")) {
                    mtActbal.setRmbamt(balamt);
                    mtActbal.setExrate(bd100);
                } else {
                    BigDecimal roe = roeMap.get(curcde);
                    if (roe == null) {
                        roe = sbsCommonService.queryExchangeRate(curcde, yyyymmdd);
                        roeMap.put(curcde, roe);
                    }
                    mtActbal.setExrate(roe);
                    mtActbal.setRmbamt(balamt.divide(bd100).multiply(roe).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }

            mtActbal.setQrydate(strdate);
            mtActbal.setQrytime(strtime);

            mtActbalMapper.insert(mtActbal);
        }
        //ɾ���Ǳ�������
        deleteNotCurrentDateCorpBalInfo(strdate, BANKCD_SBS);
    }

    /**
     * ������ҵ����ѯ
     */
    @Transactional
    public void queryAndInsertAllActBalFromCCB() {

        MtActtypeExample example = new MtActtypeExample();
        example.createCriteria().andBankcdEqualTo(BANKCD_CCB);
        List<MtActtype> mtActtypeList = mtActtypeMapper.selectByExample(example);

        Date date = new Date();
        String strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String strtime = new SimpleDateFormat("HH:mm:ss").format(date);

/*
        for (MtActtype mtActtype : mtActtypeList) {
            queryAndInsertOneActBalFromCCB(mtActtype.getActno(), strdate, strtime);
        }
*/

        int index = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(PropertyManager.getIntProperty("QRY_CCB_ACTBAL_THREADS"));
        for (MtActtype mtActtype : mtActtypeList) {
            Runnable r = new CcbActBalQryThread(index, mtActtype.getActno(), strdate, strtime, this.mtActbalMapper);
            executorService.execute(r);
            index++;
        }
        executorService.shutdown();

        //ɾ���Ǳ�������
        deleteNotCurrentDateCorpBalInfo(strdate, BANKCD_CCB);
    }

    /**
     * ����
     *
     * @param actno
     */
    public void queryAndInsertOneActBalFromCCB(String actno, String strdate, String strtime) {
        SBSResponse4SingleRecord response = processSbsTxn_n062(actno);

        Tn062SOFDataDetail sofDataDetail = (Tn062SOFDataDetail) response.getSofDataDetail();

        MtActbal mtActbal = new MtActbal();

        mtActbal.setBankcd(BANKCD_CCB);
        mtActbal.setActno(actno);

        if (!response.getFormcode().substring(0, 1).equals("T")) {
            logger.error("�����쳣��SBS����FORM�ţ�" + response.getFormcode() + response.getForminfo() + " �ʺţ�" + actno);
            mtActbal.setBalamt(new BigDecimal(0));
            mtActbal.setRmbamt(new BigDecimal(0));
            String forminfo = response.getForminfo();
            if (forminfo.length() > 200) {
                forminfo = forminfo.substring(0, 200);
            }
            mtActbal.setRemark(forminfo);
        } else {
            String strActbal = sofDataDetail.getActbal();
            BigDecimal balamt = new BigDecimal(strActbal);
            mtActbal.setBalamt(balamt);
            mtActbal.setRmbamt(balamt);

        }

        mtActbal.setExrate(new BigDecimal(100));
        mtActbal.setQrydate(strdate);
        mtActbal.setQrytime(strtime);

        mtActbalMapper.insert(mtActbal);

    }


    public List<MtActbalSumBean> selectSumDataList(String bankcd, String txndate) {
        return actbalMonitorMapper.selectSumDataList(bankcd, txndate);
    }

    /**
     * ��ȡĳʱ������嵥
     * @param bankcd
     * @param txndate
     * @param qrytime
     * @return
     */
    public List<MtActbalBean> selectActBalList(String bankcd, String txndate, String qrytime) {
        List<MtActbalBean> mtActbalBeans = actbalMonitorMapper.selectActBalList(bankcd, txndate, qrytime);
        return mtActbalBeans;
    }
    //====================================================================================

    private SBSResponse4SingleRecord processSbsTxn_n062(String actno) {
        Tn062Handler handler = new Tn062Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add(StringUtils.rightPad(actno, 22, ' '));//�˺�	X(22)
        paramList.add(StringUtils.rightPad(BANKCD_CCB, 12, ' '));//�к�	X(12)
        paramList.add("CNY");//3λ�ұ�

        SBSRequest request = new SBSRequest("n062", paramList);
        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();
        response.setSofDataDetail(new Tn062SOFDataDetail());

        handler.run(request, response);
        return response;
    }


    private SBSResponse4MultiRecord processSbsTxn_8123(String type) {
        T8123Handler handler = new T8123Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("       ");//7λ�ͻ���
        paramList.add("    ");//4λ������
        paramList.add("   ");//3λ�ұ�
        paramList.add(type);//1λ�ʻ�����
        paramList.add("1");//1����λ 2������

        SBSRequest request = new SBSRequest("8123", paramList);
        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();

        SOFDataDetail sofDataDetail = new T8123SOFDataDetail();
        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);

        String formcode = response.getFormcode();
        if (!formcode.substring(0, 1).equals("T")) {
            String forminfo = response.getForminfo();
            throw new RuntimeException("�����쳣��" + formcode + (forminfo == null ? " " : forminfo) + PropertyManager.getProperty(formcode));
        }
        return response;
    }

    private void deleteNotCurrentDateCorpBalInfo(String strdate, String bankcd) {
        MtActbalExample mtActbalExample = new MtActbalExample();
        mtActbalExample.createCriteria().andQrydateLessThan(strdate).andBankcdEqualTo(bankcd);
        mtActbalMapper.deleteByExample(mtActbalExample);
    }


}
