package haier.activemq.service.sbs.txn.T8123;

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
public class T8123Test {
    private static Logger logger = LoggerFactory.getLogger(T8123Test.class);

    public static void main(String[] argv) {
        T8123Handler handler = new T8123Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("       ");//7位客户号
        paramList.add("    ");//4位核算码
        paramList.add("   ");//3位币别
        paramList.add(" ");//1位帐户类型
        paramList.add("1");//1：单位 2：个人
        SBSRequest request = new SBSRequest("8123", paramList);

        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();

//        SOFDataHeader sofDataHeader = new SOFDataHeader4MultiRecord();
        SOFDataDetail sofDataDetail = new T8123SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        logger.debug("aaa");
    }
}
