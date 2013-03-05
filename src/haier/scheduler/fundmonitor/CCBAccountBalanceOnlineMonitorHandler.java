package haier.scheduler.fundmonitor;

import haier.service.fundmonitor.ActBalOnlineService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ͨ��FTP��ʽ��ȡSBS�Թ��ʻ���������ļ��������ļ���д�뱾�����ݿ����
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
