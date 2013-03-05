package haier.rms.eaireport;

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

import haier.rms.model.EaiCorpBal;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.form.control.Action;

import java.io.Reader;
import java.util.List;

public class balanceAction extends Action {
    String querydate;

    /**
     * EAI 接口 企业余额查询  IBATIS
     *
     * @return
     */
    public int queryEAI() {

        String txdate = req.getFieldValue("txdate");
        SqlSession session = null;
        try {

            Reader reader = Resources.getResourceAsReader("Configuration.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();


            QueryAccountBalanceFromGVS query = new QueryAccountBalanceFromGVS();
            java.util.List<gateway.EAI.BalanceQuery.OutputType> outputList = query.QueryCorpBalance();

            EaiCorpBal corpbal = new EaiCorpBal();

            int i = 0;
            session.delete("EaiCorpBal.deleteAll");
            for (gateway.EAI.BalanceQuery.OutputType output : outputList) {
                i++;
                corpbal.setId(String.valueOf(i));
                corpbal.setCorpname(output.getBUTXT());
                corpbal.setBankname(output.getSAKNR());
                corpbal.setItem(output.getTXT501());
                corpbal.setCorpacct(output.getTXT502());
                corpbal.setBalance(Double.parseDouble(output.getTYUE()));
                corpbal.setCurrcd(output.getWAERS());
                corpbal.setQuerydate(txdate);

                session.insert("EaiCorpBal.insertRecord", corpbal);
            }
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage(PropertyManager.getProperty("300"));
            return -1;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        this.res.setType(0);
        this.res.setResult(true);
        this.res.setMessage(PropertyManager.getProperty("200"));
        return 0;

    }

    /**
     * 用友系统查询 IBATIS
     *
     * @return
     */
    public int queryEAI_YY() {

        String txdate = req.getFieldValue("txdate");
        SqlSession session = null;
        try {

            Reader reader = Resources.getResourceAsReader("Configuration.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();

            List<YYCorpInfoBean> corplist = null;
            corplist = session.selectList("EaiYYCorpBal.selectCorpInfo");
            QueryFinanceBalanceFromYY query = new QueryFinanceBalanceFromYY();
            java.util.List<gateway.EAI.yongyou.OutputType> outputList = query.QueryCorpBalance(corplist, txdate);

            EaiCorpBal corpbal = new EaiCorpBal();

            int i = 0;
            session.delete("EaiYYCorpBal.deleteAll");
            for (gateway.EAI.yongyou.OutputType output : outputList) {
                if (output.getCcodeName() == null) {
                    continue;
                }
                i++;
                corpbal.setId(String.valueOf(i));
                corpbal.setCorpname(getCorpName(output.getCcodeName(),corplist));
//                corpbal.setBankname(output.getSAKNR());
                corpbal.setItem(output.getCcodeName());
                corpbal.setCorpacct(output.getCcode());
                corpbal.setBalance(Double.parseDouble(output.getMe()));
                corpbal.setCurrcd(output.getBz());
                corpbal.setQuerydate(txdate);

                session.insert("EaiYYCorpBal.insertRecord", corpbal);
            }
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage(PropertyManager.getProperty("300"));
            return -1;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        this.res.setType(0);
        this.res.setResult(true);
        this.res.setMessage(PropertyManager.getProperty("200"));
        return 0;
    }

    /**
     * 根据账套号查询企业名称
     * @param acctid
     * @return
     */
    private String   getCorpName(String acctid,  List<YYCorpInfoBean> corplist) {
        String rtn = "企业名称为空";
        for (YYCorpInfoBean corp:corplist) {
            if (corp.getAcctid().equals(acctid)) {
                rtn = corp.getCorpname();
                break;
            }
        }
        return rtn;
    }
}