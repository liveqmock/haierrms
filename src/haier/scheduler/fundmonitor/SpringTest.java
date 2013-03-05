package haier.scheduler.fundmonitor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-7-1
 * Time: ÉÏÎç7:07
 * To change this template use File | Settings | File Templates.
 */
public class SpringTest {
        public static void main(String[] argv) {
        SpringTest main = new SpringTest();
        main.springstart();
    }

    private void springstart() {
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("file:F:/svn-haierrms/out/artifacts/haierrms2/WEB-INF/applicationContext.xml");
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("file:**out\\artifacts\\haierrms2\\WEB-INF\\applicationContext.xml");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");
        CCBAccountBalanceOnlineMonitorHandler bean = (CCBAccountBalanceOnlineMonitorHandler) ctx.getBean("ccbactbalMonitor");
        bean.run();

    }
}
