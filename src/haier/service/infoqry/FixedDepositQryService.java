package haier.service.infoqry;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T8855.T8855Handler;
import haier.activemq.service.sbs.txn.T8855.T8855SOFDataDetail;
import haier.repository.dao.S1169CorplistMapper;
import haier.repository.model.S1169Corplist;
import haier.repository.model.S1169CorplistExample;
import haier.service.common.SbsCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-3-7
 * Time: 上午10:51
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FixedDepositQryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private S1169CorplistMapper s1169CorplistMapper;
    @Autowired
    private SbsCommonService sbsCommonService;


    public void processFixedDepositRecord(){
        List<SOFDataDetail> sbsToaList = queryAllFixedDepositRecordFromSBS();
        List<S1169Corplist> corplists = queryAllCorpList();

        List<FixedDepositBean> voList = new ArrayList<FixedDepositBean>();
        for (SOFDataDetail detail : sbsToaList) {

        }

    }

    private boolean isExistConfig(SOFDataDetail detail, List<S1169Corplist> corplists){
        for (S1169Corplist corp : corplists) {
//            if (corp.getCordname().trim().equals((T8855SOFDataDetail)detail.)) {
//            }
        }
        return false;
    }

    private List<SOFDataDetail> queryAllFixedDepositRecordFromSBS(){
        T8855Handler handler = new T8855Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("1");//客户类别    1-单位客户 2-个人客户 3-内部客户 4-银行同业客户
        paramList.add("1");//证件类别
        paramList.add(" ");//证件号
        paramList.add("1");//帐户类型 0-全部,1-定期存款,2-贷款,3-活期存款
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
}
