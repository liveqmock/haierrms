package haier.service.fundmonitor;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T8123.T8123Handler;
import haier.activemq.service.sbs.txn.T8123.T8123SOFDataDetail;
import haier.repository.dao.MtActbalMapper;
import haier.repository.dao.MtActtypeMapper;
import haier.repository.dao.fundmonitor.ActInfoManagerMapper;
import haier.repository.model.MtActtype;
import haier.repository.model.MtActtypeExample;
import haier.repository.model.MtActtypeKey;
import haier.repository.model.fundmonitor.MtActtypeUIBean;
import haier.service.common.SbsCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-20
 * Time: ����5:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ActInfoManagerService {
    private static final String BANKCD_SBS = "999";
    private static final String BANKCD_CCB = "105";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MtActtypeMapper mtActtypeMapper;
    @Autowired
    private MtActbalMapper mtActbalMapper;

    @Autowired
    private ActInfoManagerMapper actInfoManagerMapper;

    @Autowired
    private SbsCommonService sbsCommonService;
    @Autowired
    private ActBalOnlineService actBalOnlineService;


    public List<MtActtype> selectActtypeList(String bankcd) {
        MtActtypeExample example = new MtActtypeExample();
        example.createCriteria().andBankcdEqualTo(bankcd);
        example.setOrderByClause("actno");
        return mtActtypeMapper.selectByExample(example);
    }

    public List<MtActtypeUIBean> selectActtypeUIList(String bankcd) {
        return actInfoManagerMapper.selectActtypeUIList(bankcd);
    }

    public void insertActtypeRecord(MtActtypeUIBean mtActtypeUIBean, String bankcd) {
        if (mtActtypeUIBean.getBankcd() == null) {
            MtActtype mtActtype = mtActtypeUIBean;
            mtActtype.setActno(mtActtypeUIBean.getActnoUI());
            mtActtype.setBankcd(bankcd);
            mtActtypeMapper.insert(mtActtype);
        } else {
            MtActtypeExample example = new MtActtypeExample();
            example.createCriteria().andBankcdEqualTo(mtActtypeUIBean.getBankcd()).andActnoEqualTo(mtActtypeUIBean.getActno());

            MtActtype mtActtype = mtActtypeUIBean;
            mtActtype.setActno(mtActtypeUIBean.getActnoUI());
            mtActtypeMapper.updateByExample(mtActtype, example);
        }
    }

    public void updateSbsActtypeOneRecord(MtActtype mtActtype) {

        MtActtypeExample example = new MtActtypeExample();
        example.createCriteria().andBankcdEqualTo(mtActtype.getBankcd()).andActnoEqualTo(mtActtype.getActno());
        List<MtActtype> records = mtActtypeMapper.selectByExample(example);
        if (records.size() == 0) {
            mtActtypeMapper.insert(mtActtype);
        } else {
            mtActtypeMapper.updateByExample(mtActtype, example);
        }
    }

    /**
     * SBS�ʻ�ͬ������ȡSBS�������ʻ���Ϣ
     *
     * @return
     */
    public List<MtActtype> getSbsActInfoFromInterface() {
        List<MtActtype> mtActtypeList = selectMtActtypeList();

        List<MtActtype> mtActtypeListUI = new ArrayList<MtActtype>();

        assembleUIList(mtActtypeList, mtActtypeListUI, "1");
        assembleUIList(mtActtypeList, mtActtypeListUI, "3");
        return mtActtypeListUI;
    }

    @Transactional
    private void assembleUIList(List<MtActtype> mtActtypeList, List<MtActtype> mtActtypeListUI, String type) {
        SBSResponse4MultiRecord response = processSbsTxn_8123(type);
        List<SOFDataDetail> sofDataDetailList = response.getSofDataDetailList();
        MtActtypeKey mtActtypeKey = new MtActtypeKey();
        for (SOFDataDetail dataDetail : sofDataDetailList) {
            String actnum = "8010" + ((T8123SOFDataDetail) dataDetail).getActnum();
            String actnam = ((T8123SOFDataDetail) dataDetail).getActnam();

            boolean isFound = isActInfoExist(mtActtypeList, actnum);

            if (!isFound) {
                MtActtype mtActtype = new MtActtype();
                mtActtype.setBankcd(BANKCD_SBS);
                mtActtype.setActno(actnum);
                mtActtype.setActname(actnam);
                mtActtypeListUI.add(mtActtype);
            }else{
                //���±����ʻ����л���
                mtActtypeKey.setBankcd(BANKCD_SBS);
                mtActtypeKey.setActno(actnum);
                MtActtype mtActtypeRecord = mtActtypeMapper.selectByPrimaryKey(mtActtypeKey);
                mtActtypeRecord.setActname(actnam);
                mtActtypeMapper.updateByPrimaryKey(mtActtypeRecord);
            }
        }
    }

    private boolean isActInfoExist(List<MtActtype> mtActtypeList, String actnum) {
        boolean isFound = false;
        for (MtActtype mtActtype : mtActtypeList) {
            if (actnum.equals(mtActtype.getActno())) {
                isFound = true;
                //TODO  ͬʱ���� acttype���еĻ�����
                break;
            }
        }
        return isFound;
    }

    public List<MtActtype> selectMtActtypeList() {
        MtActtypeExample mtActtypeExample = new MtActtypeExample();
        mtActtypeExample.createCriteria().andBankcdEqualTo(BANKCD_SBS);
        return mtActtypeMapper.selectByExample(mtActtypeExample);
    }

    /**
     * SBS �ӿ�
     *
     * @param type 1-����,2-����,3-���,9-����
     * @return
     */
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

    /**
     * �������ø���ԭ��acttype��¼ �� ������¼
     *
     * @param mttypeList
     * @param category
     */
    public void batchsetActInfoCategory(MtActtype[] mttypeList, String category) {
        for (MtActtype mtActtype : mttypeList) {
            mtActtype.setCategory(category);
            updateSbsActtypeOneRecord(mtActtype);
        }
    }
}
