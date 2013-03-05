package haier.scheduler.fundmonitor;

import haier.service.fundmonitor.ActBalOnlineService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 通过FTP方式获取SBS对公帐户当日余额文件，解析文件后写入本地数据库表中
 * User: zhanrui
 * Date: 2011-4-8
 * Time: 18:17:24
 * <p/>
 */

public class CCBAccountBalanceOnlineMonitorHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ActBalOnlineService corpBalOnlineService;


    //====================================================================
    public void run() {
        corpBalOnlineService.queryAndInsertAllActBalFromCCB();
    }

}
