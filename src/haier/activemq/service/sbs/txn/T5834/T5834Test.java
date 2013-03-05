package haier.activemq.service.sbs.txn.T5834;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4SingleRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.Tn062.Tn062SOFDataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-14
 * Time: ����3:30
 * To change this template use File | Settings | File Templates.
 */
public class T5834Test {
    private static Logger logger = LoggerFactory.getLogger(T5834Test.class);

    public static void main(String[] argv) {
        T5834Handler handler = new T5834Handler();
        List<String> list = new ArrayList<String>();

        list.add("111111"); //����
        list.add("010");    //��Ա������
        list.add("60");     //��Ա���ź�

        //list.add(curcde);
        list.add("014");
        //list.add(secccy);
        list.add("001");
        //list.add(currdate);
        list.add("20110621");

        list.add("000000");
        list.add("");        //�����
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־
        list.add("");        //XX��
        list.add("");  //���ʱ�־

        list.add("0");  //���ʲ�ѯ


        SBSRequest request = new SBSRequest("5834", list);

        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();

        SOFDataDetail sofDataDetail = new T5834SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        logger.debug("aaa"+ response.getFormcode());
    }
}
