package haier.activemq.service.sbs.txn.T5834;

import haier.activemq.gateway.sbs.NewCtgManager;
import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4SingleRecord;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-14
 * Time: обнГ3:00
 * To change this template use File | Settings | File Templates.
 */
public class T5834Handler {

    public void run(SBSRequest request, SBSResponse4SingleRecord response) {
        NewCtgManager ctgManager = new NewCtgManager();
        ctgManager.processSingleResponsePkg(request, response);
    }
}
