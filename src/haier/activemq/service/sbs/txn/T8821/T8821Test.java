package haier.activemq.service.sbs.txn.T8821;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-14
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public class T8821Test {
    private static Logger logger = LoggerFactory.getLogger(T8821Test.class);

    public static void main(String[] argv) {
        T8821Handler handler = new T8821Handler();
        List<String> paramList = new ArrayList<String>();

        paramList.add("0016");//4位柜员号
        paramList.add("20120101");//8位起始日期
        paramList.add("20120430");//8位终止日期
        SBSRequest request = new SBSRequest("8821", paramList);

        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();

//        SOFDataHeader sofDataHeader = new SOFDataHeader4MultiRecord();
        SOFDataDetail sofDataDetail = new T8821SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        logger.debug("aaa");
    }
}
