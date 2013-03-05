package haier.activemq.service.sbs.txn.Tn116;

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
 * Time: ÏÂÎç3:30
 * To change this template use File | Settings | File Templates.
 */
public class Tn116Test {
    private static Logger logger = LoggerFactory.getLogger(Tn116Test.class);

    public static void main(String[] argv) {
        Tn116Handler handler = new Tn116Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("20111231");//YYYYMMDD
        SBSRequest request = new SBSRequest("n116", paramList);

        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();

//        SOFDataHeader sofDataHeader = new SOFDataHeader4MultiRecord();
        SOFDataDetail sofDataDetail = new Tn116SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        logger.debug("aaa");
    }
}
