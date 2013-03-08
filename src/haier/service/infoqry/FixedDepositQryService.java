package haier.service.infoqry;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T8855.T8855Handler;
import haier.activemq.service.sbs.txn.T8855.T8855SOFDataDetail;
import haier.repository.dao.S1169CorplistMapper;
import haier.repository.dao.infoqry.HfcBiMapper;
import haier.repository.model.S1169Corplist;
import haier.repository.model.S1169CorplistExample;
import haier.repository.model.infoqry.FixedDepositBean;
import haier.service.common.SbsCommonService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-3-7
 * Time: ����10:51
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FixedDepositQryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private S1169CorplistMapper s1169CorplistMapper;
    @Autowired
    private HfcBiMapper hfcBiMapper;

    @Autowired
    private SbsCommonService sbsCommonService;


    public List<FixedDepositBean>  getFixedDepositRecord(String corpName){
        List<SOFDataDetail> sbsToaList = queryAllFixedDepositRecordFromSBS();
        List<S1169Corplist> corplists = queryAllCorpList();

        List<FixedDepositBean> voList = new ArrayList<FixedDepositBean>();
        for (SOFDataDetail detail : sbsToaList) {
            if (StringUtils.isNotEmpty(corpName)) {
                if (!((T8855SOFDataDetail) detail).getCusnam().contains(corpName)) {
                    continue;
                }
            }
            if (isExistConfig((T8855SOFDataDetail) detail, corplists)
                    && "1".equals(((T8855SOFDataDetail) detail).getActsts())) {
                FixedDepositBean vo = new FixedDepositBean();
                vo.setCorpName(((T8855SOFDataDetail) detail).getCusnam().trim());
                vo.setCorpCode("");
                vo.setBankName("����˾");
                vo.setActNum(((T8855SOFDataDetail) detail).getCusact().trim());
                vo.setVoucherNum(((T8855SOFDataDetail) detail).getBoknum().trim());

                String bal = ((T8855SOFDataDetail) detail).getBokbal();
                vo.setBalamt(new BigDecimal(bal));
                vo.setStartDate(((T8855SOFDataDetail) detail).getValdat());
                vo.setEndDate(((T8855SOFDataDetail) detail).getExpdat());

                String intrat = ((T8855SOFDataDetail) detail).getIntrat();
                vo.setIntRate(new BigDecimal(intrat));
                vo.setPeriod(Integer.parseInt(((T8855SOFDataDetail) detail).getDptprd()));

                String curcde = ((T8855SOFDataDetail) detail).getCusact().substring(15,18);
                vo.setCurrCode(curcde);
                if ("001".equals(curcde)) {
                    vo.setCurrName("�����");
                }else{
                    vo.setCurrName(sbsCommonService.selectCurrNameByCurrCode(curcde));
                }
                voList.add(vo);
            }
        }
        return voList;
    }

    private boolean isExistConfig(T8855SOFDataDetail detail, List<S1169Corplist> corplists){
        for (S1169Corplist corp : corplists) {
            if (corp.getCordname().trim().equals(detail.getCusnam().trim())) {
                return true;
            }
        }
        return false;
    }

    private List<SOFDataDetail> queryAllFixedDepositRecordFromSBS(){
        T8855Handler handler = new T8855Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("1");//�ͻ����    1-��λ�ͻ� 2-���˿ͻ� 3-�ڲ��ͻ� 4-����ͬҵ�ͻ�
        paramList.add("1");//֤�����
        paramList.add(" ");//֤����
        paramList.add("1");//�ʻ����� 0-ȫ��,1-���ڴ��,2-����,3-���ڴ��
        SBSRequest request = new SBSRequest("8855", paramList);

        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();
        SOFDataDetail sofDataDetail = new T8855SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);
        handler.run(request, response);

        return response.getSofDataDetailList();
    }

    private List<S1169Corplist> queryAllCorpList(){
        S1169CorplistExample example = new S1169CorplistExample();
        example.createCriteria();
        return s1169CorplistMapper.selectByExample(example);
    }

    //�ⲿ�������� ��BI��ȡ
    public List<FixedDepositBean>  getFixedDepositRecordFromBI(){
        return hfcBiMapper.selectHistoryActBal();
    }
}
