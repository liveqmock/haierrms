package haier.rms.exchangerate;


import gateway.common.util.SystemErrorException;
import gateway.CtgManager;
import gateway.SBS.ExchangeRate.ExRateRecordBean;
import gateway.SBS.ExchangeRate.ExRateQueryResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SBSHandler {
    private static final Log logger = LogFactory.getLog(SBSHandler.class);

    public SqlSession getSession() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("Configuration.xml");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IBATIS �����ļ���ȡ����");
            throw new RuntimeException(e);
        }
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory.openSession();
    }


    //������ѯ

    /**
     * @param currdate yyyyMMdd
     * @param curcde   ������
     * @param secccy   �ڶ�����
     * @return
     */
    public void querySBS(SqlSession session,String currdate, String curcde, String secccy) {

        List list = new ArrayList();

        //String sbsdate = txdate.substring(0, 4) + txdate.substring(5, 7) + txdate.substring(8, 10);

        list.add("111111");
        list.add("010");
        list.add("60");
        list.add(curcde);
        list.add(secccy);
        list.add(currdate);
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

        try {
            CtgManager ctg = new CtgManager();
            byte[] buffer = ctg.processBatchRequest("5834", list);

            ExRateQueryResult queryresult;
            queryresult = new ExRateQueryResult(buffer);
            String formcode = queryresult.getFormcode();

            if (!formcode.substring(0, 1).equals("T")) {     //�쳣�������
                //String message =  MySystemInternetMessages.M14000010E.toString();
                String message = "SBS���ؽ����쳣��" + formcode;
                SystemErrorException se = new SystemErrorException(message);
                //se.initCause(e);
                throw se;
            } else {      //���ķ��ͳɹ�
                ExRateRecordBean record = queryresult.getRecord();
                session.delete("ExchangeRate.deleteOneRecord",record);
                session.insert("ExchangeRate.insertOneRecord",record);
                session.commit();
                int i=0;
            }
        } catch (Exception e) {

        } finally{
            //session.close();
        }
    }

    public static void main(String[] args){
        SBSHandler handler = new SBSHandler();
        SqlSession session=handler.getSession();
        handler.querySBS(session,"20100601","013","001");
        session.close();
    }
}