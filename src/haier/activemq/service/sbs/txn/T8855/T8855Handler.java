package haier.activemq.service.sbs.txn.T8855;

import haier.activemq.gateway.sbs.NewCtgManager;
import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;

/**
 * 8855	查询客户账户列表
 * User: zhanrui
 * Date: 2013-3-7
 */
public class T8855Handler {

    public void run(SBSRequest request, SBSResponse4MultiRecord response) {
        NewCtgManager ctgManager = new NewCtgManager();
        ctgManager.processMultiResponsePkg(request, response);
    }
}
