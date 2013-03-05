package haier.activemq.service.sbs.txn.Tn062;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4SingleRecord;
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
public class Tn062Test {
    private static Logger logger = LoggerFactory.getLogger(Tn062Test.class);

    public static void main(String[] argv) {
        Tn062Handler handler = new Tn062Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("11001016500056057958  ");//账号	X(22)
        paramList.add("105         ");//行号	X(12)
        paramList.add("CNY");//3位币别
        SBSRequest request = new SBSRequest("n062", paramList);

        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();

        SOFDataDetail sofDataDetail = new Tn062SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        logger.debug("aaa");
    }
}
