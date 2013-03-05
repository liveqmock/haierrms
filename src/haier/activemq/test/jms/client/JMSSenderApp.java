package haier.activemq.test.jms.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMSSenderApp {

	public static void main(String[] args) {
		
	    try {
			ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("app-context.xml");
//			JMSSender jmsSender = (JMSSender)ctx.getBean("jmsSender");
//			JMSSenderReply jmsSender = (JMSSenderReply)ctx.getBean("jmsSenderReply");
			JMSSenderConvert jmsSender = (JMSSenderConvert)ctx.getBean("jmsSenderConvert");
			jmsSender.sendMessage();
	    	System.exit(0);
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	}	
}
