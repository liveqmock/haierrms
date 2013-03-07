package haier.activemq.service.sbs.txn.T8855;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4MultiRecord;
import haier.activemq.service.sbs.core.SOFDataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 8855	��ѯ�ͻ��˻��б�
 * User: zhanrui
 * Date: 2013-3-7
 */
public class T8855Test {
    private static Logger logger = LoggerFactory.getLogger(T8855Test.class);

    public static void main(String[] argv) {
        T8855Handler handler = new T8855Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add("1");//�ͻ����    1-��λ�ͻ� 2-���˿ͻ� 3-�ڲ��ͻ� 4-����ͬҵ�ͻ�
        paramList.add("1");//֤�����
        paramList.add(" ");//֤����
        paramList.add("1");//�ʻ����� 0-ȫ��,1-���ڴ��,2-����,3-���ڴ��
        SBSRequest request = new SBSRequest("8855", paramList);

        SBSResponse4MultiRecord response = new SBSResponse4MultiRecord();
        SOFDataDetail sofDataDetail = new T8855SOFDataDetail();

        response.setSofDataDetail(sofDataDetail);

        handler.run(request, response);
        logger.debug("aaa");
    }
}
