package haier.rms.querycorppayfee;

/**
 * <p>Title: ��̨ҵ�����</p>
 *
 * <p>Description: ��̨ҵ�����</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: ��˾</p>
 *
 * @author
 * @version 1.0
 */

import com.ccb.dao.LNCOOPPROJ;
import com.ccb.util.StringUtil;
import gateway.BalanceRecord;
import gateway.BatchQueryResult;
import gateway.CorpPayFeeQuery.CorpPayFeeQueryResult;
import gateway.CorpPayFeeQuery.CorpPayFeeRecord;
import gateway.CtgManager;
import haier.rms.dao.RMSACCTBAL;
import haier.rms.dao.RMSCORPPAYFEE;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.RecordSet;
import pub.platform.form.control.Action;
import pub.platform.utils.BusinessDate;
import pub.platform.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class corppayfeeAction extends Action {
   String yymmdate;
    String type;
 
    public int querySBS() {

        RMSCORPPAYFEE dao = new RMSCORPPAYFEE();

//        List list = new ArrayList();


        String txdate = req.getFieldValue("txdate");
         yymmdate = txdate.substring(0, 4) + txdate.substring(5, 7);
         type = req.getFieldValue("type");
        //TODO: parameter �쳣�ж�


        try {
            CtgManager ctg = new CtgManager();

            dao.deleteByWhere(" where 1=1 ");
            //��ʼ�������
            int beginnum = 1;
            while(beginnum >= 1){
                beginnum = queryNextPkg(ctg,dao,beginnum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage(PropertyManager.getProperty("300"));
            return -1;

        }
        this.res.setType(0);
        this.res.setResult(true);
        this.res.setMessage(PropertyManager.getProperty("200"));
        return 0;

    }


    /*
   ����������
    */
    private int queryNextPkg(CtgManager ctg, RMSCORPPAYFEE dao, int beginnum) {

        List list = new ArrayList();

        list.add(yymmdate);                   //��������
        list.add(type);                      // ��ѯ���� 0��δ�ɷ� 1���ѽɷ� 2��ȫ��

        list.add(StringUtils.addPrefix(Integer.toString(beginnum), "0", 6));                 //  ��ʼ����

        CorpPayFeeQueryResult queryresult;
        try {
            byte[] buffer = ctg.processBatchRequest("n065", list);

            queryresult = new CorpPayFeeQueryResult(buffer);
            String formcode = queryresult.getFormcode();

            if (!formcode.substring(0, 1).equals("T")) {     //�쳣�������
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage("SBS���ݲ�ѯ���󣬷��ش�����ϢΪ��" +formcode+"=" + PropertyManager.getProperty(formcode));
                return -1;

            } else {      //���ķ��ͳɹ�
                queryresult.getBatchQueryMsg();


                List<CorpPayFeeRecord> records = queryresult.getAll();
                int count = records.size();
                CorpPayFeeRecord record = new CorpPayFeeRecord();

                for (int i = 0; i < count; i++) {
                    dao.setId(i+beginnum);
                    dao.setCorpacct(records.get(i).getCorpacct());
                    dao.setCorpname(records.get(i).getCorpname());
                    dao.setBankname(records.get(i).getBankname());
                    dao.setCountnum(Integer.parseInt(records.get(i).getCountnum()));
                    dao.setFee(new BigDecimal(records.get(i).getFee()).doubleValue());
                    dao.insert();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage(PropertyManager.getProperty("300"));
            return -1;
        }

/*
        int lastcount = queryresult.getTotcnt() - queryresult.getCurcnt();
        if (lastcount < 0) {
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage("SBSͨѶ����������������⡣");
            return -1;
        }
*/
        //������һ����ʼ����
        int nextbeginnum = queryresult.getCurcnt() + beginnum;
        if (queryresult.getTotcnt() - nextbeginnum <=0) {
            nextbeginnum = -1;
        }

        //����ʣ�����
        return nextbeginnum;

    }

}