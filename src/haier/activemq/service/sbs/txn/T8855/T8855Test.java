package haier.activemq.service.sbs.txn.T8855;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 8855	查询客户账户列表
 * User: zhanrui
 * Date: 2013-3-7
 */
public class T8855Test {
    private static Logger logger = LoggerFactory.getLogger(T8855Test.class);

    public static void main(String[] argv) {
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
        logger.debug("aaa");
    }
}
