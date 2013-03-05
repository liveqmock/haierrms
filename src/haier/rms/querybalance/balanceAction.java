package haier.rms.querybalance;

/**
 * <p>Title: 后台业务组件</p>
 *
 * <p>Description: 后台业务组件</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: 公司</p>
 *
 * @author
 * @version 1.0
 */

import gateway.CtgManager;
import gateway.SBS.BalanceQuery.BalanceQueryResult;
import gateway.SBS.BalanceQuery.BalanceRecord;
import haier.rms.dao.RMSACCTBAL;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.form.control.Action;
import pub.platform.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class balanceAction extends Action {
    String yymmdate;
    String type;

    //单包查询
    /*
    public int querySBS_old() {
        RMSACCTBAL rmsacctbal = new RMSACCTBAL();
        BatchQueryResult queryresult = new BatchQueryResult();

        List list = new ArrayList();


        String txdate = req.getFieldValue("txdate");
        String sbsdate = txdate.substring(0, 4) + txdate.substring(5, 7) + txdate.substring(8, 10);
        String stockcd = req.getFieldValue("stockcd");
        //TODO: parameter 异常判断


        list.add(sbsdate);                   //交易日期
        //list.add(stockcd.equals("1")?"A":"H");                      //A-A股，H-H股
        if (stockcd.equals("1")) {
            list.add("A");
        } else if (stockcd.equals("2")) {
            list.add("H");
        } else {
            list.add("C");
        }

        list.add("000001");                 //  起始笔数 6


        try {
            CtgManager ctg = new CtgManager();

            byte[] buffer = ctg.processBatchRequest("8121", list);

            byte[] bFormcode = new byte[4];
            System.arraycopy(buffer, 21, bFormcode, 0, 4);
            String formcode = new String(bFormcode);


            int rtn = -1;
            if (!formcode.substring(0, 1).equals("T")) {     //异常情况处理
                rtn = -1;
            } else {      //报文发送成功
                rtn = 0;

                ctg.getBatchQueryMsg(buffer, queryresult);

                try {
                    dc.setAuto(false);
                    dc.begin();

                    rmsacctbal.deleteByWhere(" where stockcd = '" + stockcd + "' ");

                    List<BalanceRecord> records = queryresult.getAll();
//                int count = Integer.parseInt(queryresult.getTotcnt());
                    int count = records.size();
                    BalanceRecord record = new BalanceRecord();

                    for (int i = 0; i < count; i++) {
                        rmsacctbal.setCusidt(records.get(i).getCUSIDT());
                        rmsacctbal.setApcode(records.get(i).getAPCODE());
                        rmsacctbal.setLasbal(new BigDecimal(records.get(i).getLASBAL()).doubleValue());
                        rmsacctbal.setCurcde(records.get(i).getCURCDE());
                        rmsacctbal.setTxdate(txdate);
                        rmsacctbal.setStockcd(stockcd);
                        rmsacctbal.insert();
                    }
                    dc.commit();
                } catch (Exception ex) {
                    dc.rollback();
                    throw new Exception(ex);
                }

            }

            if (rtn == -1) {
//                msgs.add(log);
                return rtn;
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
     */
    
    public int querySBS() {

        RMSACCTBAL dao = new RMSACCTBAL();

        //String txdate = req.getFieldValue("txdate");
        //String sbsdate = txdate.substring(0, 4) + txdate.substring(5, 7) + txdate.substring(8, 10);
        String stockcd = req.getFieldValue("stockcd");

//        String txdate = req.getFieldValue("txdate");
//        yymmdate = txdate.substring(0, 4) + txdate.substring(5, 7);
//        type = req.getFieldValue("type");


        try {
            CtgManager ctg = new CtgManager();

            dao.deleteByWhere(" where stockcd='" + stockcd + "'");
            //初始请求笔数
            int beginnum = 1;
            while (beginnum >= 1) {
                beginnum = queryNextPkg(ctg, dao, beginnum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage(PropertyManager.getProperty("300"));
            return -1;

        }
//        this.res.setType(0);
//        this.res.setResult(true);
//        this.res.setMessage(PropertyManager.getProperty("200"));
//        this.res.setMessage("aaaa测试");
        return 0;

    }


    /*
   后续包处理
    */
    private int queryNextPkg(CtgManager ctg, RMSACCTBAL dao, int beginnum) {

        List list = new ArrayList();

        String txdate = req.getFieldValue("txdate");
        String sbsdate = txdate.substring(0, 4) + txdate.substring(5, 7) + txdate.substring(8, 10);
        String stockcd = req.getFieldValue("stockcd");

        list.add(sbsdate);                   //交易日期
        if (stockcd.equals("1")) {
            list.add("A");
        } else if (stockcd.equals("2")) {
            list.add("H");
        } else {
            list.add("C");
        }
        list.add(StringUtils.addPrefix(Integer.toString(beginnum), "0", 6));                 //  起始笔数

        BalanceQueryResult queryresult ;
        try {
            byte[] buffer = ctg.processBatchRequest("8121", list);

            queryresult =  new BalanceQueryResult(buffer);
            String formcode = queryresult.getFormcode();

            if (!formcode.substring(0, 1).equals("T")) {     //异常情况处理
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage("SBS数据查询错误，返回错误信息为：" + formcode + "=" + PropertyManager.getProperty(formcode));
                return -1;

            } else {      //报文发送成功
                queryresult.getBatchQueryMsg();


                List<BalanceRecord> records = queryresult.getAll();
                int count = records.size();
                BalanceRecord record = new BalanceRecord();

                for (int i = 0; i < count; i++) {
                    //dao.setId(i + beginnum);
                    dao.setApcode(records.get(i).getAPCODE());
                    dao.setCurcde(records.get(i).getCURCDE());
                    dao.setCusidt(records.get(i).getCUSIDT());
//                    dao.setCountnum(Integer.parseInt(records.get(i).getCountnum()));
                    dao.setLasbal(new BigDecimal(records.get(i).getLASBAL()).doubleValue());
                    dao.setStockcd(stockcd);
                    dao.setTxdate(txdate);
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

        //计算下一步起始笔数
        int nextbeginnum = queryresult.getCurcnt() + beginnum;
        if (queryresult.getTotcnt() - nextbeginnum <= 0) {
            nextbeginnum = -1;
        }

        //返回剩余笔数
        return nextbeginnum;

    }

}