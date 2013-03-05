package haier.service.rms;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T8821.T8821Handler;
import haier.activemq.service.sbs.txn.T8821.T8821SOFDataDetail;
import haier.activemq.service.sbs.txn.Tn116.Tn116Handler;
import haier.activemq.service.sbs.txn.Tn116.Tn116SOFDataDetail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-2-16
 * Time: …œŒÁ9:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DepService {
    public List<SOFDataDetail> processLandingChkTxn(String yyyymmdd){
        Tn116Handler handler = new Tn116Handler();
        List<String> paramList = new ArrayList<String>();
        yyyymmdd = yyyymmdd.substring(0,4) + yyyymmdd.substring(5,7) + yyyymmdd.substring(8,10);
        paramList.add(yyyymmdd);//YYYYMMDD
        SBSRequest request = new SBSRequest("n116", paramList);

        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();
        Tn116SOFDataDetail sofDataDetail = new Tn116SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        return response.getSofDataDetailList();
        
    }
    public List<SOFDataDetail> processTellerTxnRpt(String tellerId, String startDate10, String endDate10){
        T8821Handler handler = new T8821Handler();
        List<String> paramList = new ArrayList<String>();

        String startDate8 = startDate10.substring(0,4) + startDate10.substring(5,7) + startDate10.substring(8,10);
        String endDate8 = endDate10.substring(0,4) + endDate10.substring(5,7) + endDate10.substring(8,10);

        paramList.add(tellerId);//4ŒªπÒ‘±∫≈
        paramList.add(startDate8);//YYYYMMDD
        paramList.add(endDate8);//YYYYMMDD

        SBSRequest request = new SBSRequest("8821", paramList);

        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();
        T8821SOFDataDetail sofDataDetail = new T8821SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        return response.getSofDataDetailList();

    }
}
