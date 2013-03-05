package haier.activemq.test;

import haier.activemq.test.jms.client.JMSSender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-7-4
 * Time: ÏÂÎç3:53
 * To change this template use File | Settings | File Templates.
 */
public class Mytest {
    public static void main(String[] args) {
//        ApplicationContext context = new FileSystemXmlApplicationContext("f:/applicationContext.xml");
/*
        ApplicationContext context = new ClassPathXmlApplicationContext("activemqContext.xml");
        System.out.println("context success!");
        SpringProducer springProducer = (SpringProducer) context.getBean("producer");
        try {
            springProducer.start();
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
*/

        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
        System.out.println("context success!");
        JMSSender jmsSender = (JMSSender) context.getBean("jmsSender");
        try {
            jmsSender.sendMessage();
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
