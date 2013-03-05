package haier.service.rms.pbcreport;

import haier.activemq.service.ftp.txn.cmsrpt4pbc.SbsFileHandler;
import haier.service.rms.pbcreport.model.SbsItemBalBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-5-25
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CmsQryService {
    private static final Logger logger = LoggerFactory.getLogger(CmsQryService.class);

    @Resource
    protected JdbcTemplate cmsJdbcTemplate;
    @Resource
    protected SimpleJdbcTemplate cmsSimpleJdbcTemplate;


    /**
     * @param strdate yyyy-MM-dd
     */
    public List<SbsItemBalBean> getSbsItemDataList(String strdate) {
        SbsFileHandler sbsFileHandler = new SbsFileHandler();
        List<String> resultList = sbsFileHandler.processSbsFile(strdate);
        List<SbsItemBalBean> sbsItemBalBeanList = assembleItemBalanceList(resultList);

        return sbsItemBalBeanList;
    }

    /**
     * 计算贷款余额合计
     * @param sbsItemBalBeanList
     * @return
     */
    public BigDecimal getCreditTotalBalance(List<SbsItemBalBean> sbsItemBalBeanList) {
        BigDecimal bal = findItemBalance(sbsItemBalBeanList, "总帐码", "1090", "001");
        bal = bal.add(findItemBalance(sbsItemBalBeanList, "总帐码", "1100", "001"));
        bal = bal.subtract(findItemBalance(sbsItemBalBeanList, "核算码", "1098", "001"));
        bal = bal.subtract(findItemBalance(sbsItemBalBeanList, "核算码", "1105", "001"));
        return bal;
    }

    /**
     * 计算贴现余额合计
     * @param sbsItemBalBeanList
     * @return
     */
    public BigDecimal getDiscountTotalBalance(List<SbsItemBalBean> sbsItemBalBeanList) {
        BigDecimal bal = findItemBalance(sbsItemBalBeanList, "总帐码", "1070", "001");
        bal = bal.add(findItemBalance(sbsItemBalBeanList, "总帐码", "1080", "001"));
        return bal;
    }

    private BigDecimal findItemBalance(List<SbsItemBalBean> sbsItemBalBeanList, String type, String code, String currcd) {
        for (SbsItemBalBean sbsItemBalBean : sbsItemBalBeanList) {
            if (sbsItemBalBean.getType().equals(type)
                    && sbsItemBalBean.getCode().equals(code)
                    && sbsItemBalBean.getCurrcd().equals(currcd)
                    ) {
                return sbsItemBalBean.getBalamt();
            }
        }
        return new BigDecimal(0);
    }

    private List<SbsItemBalBean> assembleItemBalanceList(List<String> resultList) {
        List<SbsItemBalBean> sbsItemBalBeans = new ArrayList<SbsItemBalBean>();
        for (String s : resultList) {
            SbsItemBalBean apcodeBalBean = new SbsItemBalBean();
            String[] fileds = StringUtils.split(s, "|");
            apcodeBalBean.setType(fileds[0]);
            apcodeBalBean.setCode(fileds[1]);
            apcodeBalBean.setCodename(fileds[2]);
            apcodeBalBean.setBalamt(transAmtString(fileds[3]));
            apcodeBalBean.setCurrcd(fileds[4]);
            sbsItemBalBeans.add(apcodeBalBean);
        }
        return sbsItemBalBeans;
    }

    private BigDecimal transAmtString(String strAmt) {
        int negativeSignPosition = strAmt.indexOf("-");
        if (negativeSignPosition == -1) {
            strAmt = strAmt.replaceAll("(\\,|\\s+)", "");
            return (new BigDecimal(strAmt));
        } else {
            strAmt = strAmt.replaceAll("(\\,|\\s+|\\-)", "");
            return (new BigDecimal(strAmt)).multiply(new BigDecimal(-1));
        }
    }

    public Connection getConnection() throws SQLException {
        return cmsJdbcTemplate.getDataSource().getConnection();
    }

    public Map getReporpDataMap(String yyyymm, String lastyyyymm) {
        Map<String, BigDecimal> datamap = new HashMap<String, BigDecimal>();
        //本月数据
        List<Map<String, Object>> recordList = selectCmsData_Level1_ForBalance(yyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put(record.get("currcode") + "_" + record.get("loanstatus"), (BigDecimal) record.get("balamt"));
        }
        recordList = selectCmsData_Level2_ForBalance(yyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("CNY_D_" + record.get("corpscale"), (BigDecimal) record.get("balamt"));
        }
        recordList = selectCmsData_Level3_ForBalance(yyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("CNY_D_" + record.get("corpscale") + "_" + record.get("guartype"), (BigDecimal) record.get("balamt"));
        }

        //上月数据
        recordList = selectCmsData_Level1_ForBalance(lastyyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("LAST_" + record.get("currcode") + "_" + record.get("loanstatus"), (BigDecimal) record.get("balamt"));
        }
        recordList = selectCmsData_Level2_ForBalance(lastyyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("LAST_CNY_D_" + record.get("corpscale"), (BigDecimal) record.get("balamt"));
        }
        recordList = selectCmsData_Level3_ForBalance(lastyyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("LAST_CNY_D_" + record.get("corpscale") + "_" + record.get("guartype"), (BigDecimal) record.get("balamt"));
        }

        //本月发生额
        recordList = selectCmsData_Level1_ForActualAmt(yyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("FS_" + record.get("currcode") + "_" + record.get("loanstatus"), (BigDecimal) record.get("amt"));
        }
        recordList = selectCmsData_Level2_ForActualAmt(yyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("FS_CNY_D_" + record.get("corpscale"), (BigDecimal) record.get("amt"));
        }
        recordList = selectCmsData_Level3_ForActualAmt(yyyymm);
        for (Map<String, Object> record : recordList) {
            datamap.put("FS_CNY_D_" + record.get("corpscale") + "_" + record.get("guartype"), (BigDecimal) record.get("amt"));
        }

        return datamap;
    }

    private List<Map<String, Object>> selectCmsData_Level1_ForBalance(String yyyymm) {
        String sql = "SELECT  curcd as currcode, substr(status,1,1) as loanstatus, SUM(LNBAL)  as balamt " +
                "  FROM (select CODE, " +
                "               CNAME, " +
                "               ORG_CODE, " +
                "               CORP_TYPE, " +
                "               CREDIT_CARD_NO, " +
                "               REGISTER_ADDRESS, " +
                "               CORP_SCALE, " +
                "               CREDENCENO, " +
                "               STATUS, " +
                "               to_char(PAYDATE, 'yyyymmdd'), " +
                "               to_char(ENDDATE, 'yyyymmdd'), " +
                "               CURCD, " +
                "               round(LNBAL) LNBAL, " +
                "               round(USDBAL), " +
                "               ROUND(CNYBAL), " +
                "               INTRATE, " +
                "               LOANCLASS, " +
                "               GUARTYPE, " +
                "               CORP_OWERINFO, " +
                "               LOANPROPERTY, " +
                "               LOANTYPE1, " +
                "               LOANTYPE2, " +
                "               GROPFLAG, " +
                "               REPORT_DATE " +
                "          from LOAN_BAL_INFO_LOG " +
                "         where REPORT_DATE = ? " +
                "        ) " +
                " GROUP BY curcd,substr(status,1,1) " +
                " order by curcd,substr(status,1,1)";
        logger.info(sql);
        return cmsSimpleJdbcTemplate.queryForList(sql, yyyymm);
    }

    private List<Map<String, Object>> selectCmsData_Level2_ForBalance(String yyyymm) {
        String sql = "SELECT  corp_scale as corpscale,  SUM(LNBAL)  as balamt " +
                "  FROM (select CODE, " +
                "               CNAME, " +
                "               ORG_CODE, " +
                "               CORP_TYPE, " +
                "               CREDIT_CARD_NO, " +
                "               REGISTER_ADDRESS, " +
                "               CORP_SCALE, " +
                "               CREDENCENO, " +
                "               STATUS, " +
                "               to_char(PAYDATE, 'yyyymmdd'), " +
                "               to_char(ENDDATE, 'yyyymmdd'), " +
                "               CURCD, " +
                "               round(LNBAL) LNBAL, " +
                "               round(USDBAL), " +
                "               ROUND(CNYBAL), " +
                "               INTRATE, " +
                "               LOANCLASS, " +
                "               GUARTYPE, " +
                "               CORP_OWERINFO, " +
                "               LOANPROPERTY, " +
                "               LOANTYPE1, " +
                "               LOANTYPE2, " +
                "               GROPFLAG, " +
                "               REPORT_DATE " +
                "          from LOAN_BAL_INFO_LOG " +
                "         where REPORT_DATE = ?  and curcd ='CNY' and substr(status,1,1) = 'D' " +
                "        ) " +
                " GROUP BY CORP_SCALE " +
                " order by CORP_SCALE";
        logger.info(sql);
        return cmsSimpleJdbcTemplate.queryForList(sql, yyyymm);
    }

    /**
     * 按企业规模类型 和 担保方式 汇总
     *
     * @param yyyymm
     * @return
     */
    private List<Map<String, Object>> selectCmsData_Level3_ForBalance(String yyyymm) {
        String sql = "SELECT  corp_scale as corpscale, guartype,  SUM(LNBAL)  as balamt " +
                "  FROM (select CODE, " +
                "               CNAME, " +
                "               ORG_CODE, " +
                "               CORP_TYPE, " +
                "               CREDIT_CARD_NO, " +
                "               REGISTER_ADDRESS, " +
                "               CORP_SCALE, " +
                "               CREDENCENO, " +
                "               STATUS, " +
                "               to_char(PAYDATE, 'yyyymmdd'), " +
                "               to_char(ENDDATE, 'yyyymmdd'), " +
                "               CURCD, " +
                "               round(LNBAL) LNBAL, " +
                "               round(USDBAL), " +
                "               ROUND(CNYBAL), " +
                "               INTRATE, " +
                "               LOANCLASS, " +
                "               GUARTYPE, " +
                "               CORP_OWERINFO, " +
                "               LOANPROPERTY, " +
                "               LOANTYPE1, " +
                "               LOANTYPE2, " +
                "               GROPFLAG, " +
                "               REPORT_DATE " +
                "          from LOAN_BAL_INFO_LOG " +
                "         where REPORT_DATE = ?  and curcd ='CNY' and substr(status,1,1) = 'D' " +
                "        ) " +
                " GROUP BY CORP_SCALE, GUARTYPE " +
                " order by CORP_SCALE, GUARTYPE";
        logger.info(sql);
        return cmsSimpleJdbcTemplate.queryForList(sql, yyyymm);
    }


    //===========================发生额==============================
    private List<Map<String, Object>> selectCmsData_Level1_ForActualAmt(String yyyymm) {
        String sql = "SELECT curcd as currcode, substr(type,1,1) as loanstatus ,SUM(AMT) as amt FROM ( " +
                "select CODE,CNAME,ORG_CODE,CREDIT_CARDNO,CORP_SCALE,CREDENCENO,TYPE, " +
                "       to_char(PAYDATE, 'yyyymmdd') paydate,CURCD, " +
                "       case substr(a.type,2,2) when '03' then -AMT else AMT end AMT, " +
                "       case a.CURCD when 'CNY' then 0 when 'USD' then ROUND(AMT) " +
                "         else round(AMT * (select CURRRATE from CURRRATE " +
                "                        where CURCD = a.CURCD and CURRRATE_DATE = " +
                "                              (select max(CURRRATE_DATE) from CURRRATE " +
                "                                where curcd = a.curcd)) / " +
                "                (select CURRRATE from CURRRATE " +
                "                  where CURCD = 'USD' and CURRRATE_DATE = " +
                "                        (select max(CURRRATE_DATE) from CURRRATE where curcd = 'USD'))) " +
                "       end tousd, " +
                "       case a.CURCD when 'CNY' then round(AMT) " +
                "         else round(AMT * (select CURRRATE " +
                "                         from CURRRATE " +
                "                        where CURCD = a.CURCD " +
                "                          and CURRRATE_DATE = " +
                "                              (select max(CURRRATE_DATE) from CURRRATE " +
                "                                where curcd = a.curcd))) " +
                "       end toCNY, GUAR " +
                "  from LOAN_PAY_AND_RTN_LOG a " +
                " WHERE REPORTNAME = 'C001FC5007437" + yyyymm + ".txt'  " +
                " )  " +
                " GROUP BY CURCD,substr(type,1,1) " +
                " ORDER BY CURCD,substr(type,1,1)";
        logger.info(sql);
        return cmsSimpleJdbcTemplate.queryForList(sql);
    }

    private List<Map<String, Object>> selectCmsData_Level2_ForActualAmt(String yyyymm) {
        String sql = "SELECT corp_scale as corpscale,SUM(AMT) as amt FROM ( " +
                "select CODE,CNAME,ORG_CODE,CREDIT_CARDNO,CORP_SCALE,CREDENCENO,TYPE, " +
                "       to_char(PAYDATE, 'yyyymmdd') paydate,CURCD, " +
                "       case substr(a.type,2,2) when '03' then -AMT else AMT end AMT, " +
                "       case a.CURCD when 'CNY' then 0 when 'USD' then ROUND(AMT) " +
                "         else round(AMT * (select CURRRATE from CURRRATE " +
                "                        where CURCD = a.CURCD and CURRRATE_DATE = " +
                "                              (select max(CURRRATE_DATE) from CURRRATE " +
                "                                where curcd = a.curcd)) / " +
                "                (select CURRRATE from CURRRATE " +
                "                  where CURCD = 'USD' and CURRRATE_DATE = " +
                "                        (select max(CURRRATE_DATE) from CURRRATE where curcd = 'USD'))) " +
                "       end tousd, " +
                "       case a.CURCD when 'CNY' then round(AMT) " +
                "         else round(AMT * (select CURRRATE " +
                "                         from CURRRATE " +
                "                        where CURCD = a.CURCD " +
                "                          and CURRRATE_DATE = " +
                "                              (select max(CURRRATE_DATE) from CURRRATE " +
                "                                where curcd = a.curcd))) " +
                "       end toCNY, GUAR " +
                "  from LOAN_PAY_AND_RTN_LOG a " +
                " WHERE REPORTNAME = 'C001FC5007437" + yyyymm + ".txt'  " +
                " and  type LIKE '%D0%'  and curcd = 'CNY' " +
                " )  " +
                " GROUP BY corp_scale " +
                " ORDER BY corp_scale";
        logger.info(sql);
        return cmsSimpleJdbcTemplate.queryForList(sql);
    }

    private List<Map<String, Object>> selectCmsData_Level3_ForActualAmt(String yyyymm) {
        String sql = "SELECT corp_scale as corpscale, guar as guartype ,SUM(AMT) as amt FROM ( " +
                "select CODE,CNAME,ORG_CODE,CREDIT_CARDNO,CORP_SCALE,CREDENCENO,TYPE, " +
                "       to_char(PAYDATE, 'yyyymmdd') paydate,CURCD, " +
                "       case substr(a.type,2,2) when '03' then -AMT else AMT end AMT, " +
                "       case a.CURCD when 'CNY' then 0 when 'USD' then ROUND(AMT) " +
                "         else round(AMT * (select CURRRATE from CURRRATE " +
                "                        where CURCD = a.CURCD and CURRRATE_DATE = " +
                "                              (select max(CURRRATE_DATE) from CURRRATE " +
                "                                where curcd = a.curcd)) / " +
                "                (select CURRRATE from CURRRATE " +
                "                  where CURCD = 'USD' and CURRRATE_DATE = " +
                "                        (select max(CURRRATE_DATE) from CURRRATE where curcd = 'USD'))) " +
                "       end tousd, " +
                "       case a.CURCD when 'CNY' then round(AMT) " +
                "         else round(AMT * (select CURRRATE " +
                "                         from CURRRATE " +
                "                        where CURCD = a.CURCD " +
                "                          and CURRRATE_DATE = " +
                "                              (select max(CURRRATE_DATE) from CURRRATE " +
                "                                where curcd = a.curcd))) " +
                "       end toCNY, GUAR " +
                "  from LOAN_PAY_AND_RTN_LOG a " +
                " WHERE REPORTNAME = 'C001FC5007437" + yyyymm + ".txt'  " +
                " and  type LIKE '%D0%'  and curcd = 'CNY' " +
                " )  " +
                " GROUP BY corp_scale,guar " +
                " ORDER BY corp_scale,guar";
        logger.info(sql);
        return cmsSimpleJdbcTemplate.queryForList(sql);
    }

    //====================================================================
    public String getDetailBalanceBuffer(String yyyymm) throws SQLException {
        SqlRowSet sqlRowSet = selectDetailBalanceList(yyyymm);
        return transResultsetToString(sqlRowSet);
    }

    public String getDetailActualAmtBuffer(String yyyymm) throws SQLException {
        SqlRowSet sqlRowSet = selectDetailActualAmtList(yyyymm);
        return transResultsetToString(sqlRowSet);
    }

    private String transResultsetToString(SqlRowSet sqlRowSet) throws SQLException {
        SqlRowSetMetaData sqlRowSetMetaData = sqlRowSet.getMetaData();
        int colCount = sqlRowSetMetaData.getColumnCount();
        StringBuffer sb = new StringBuffer();
        while (sqlRowSet.next()) {
            for (int i = 1; i <= colCount; i++) {
                int type = sqlRowSetMetaData.getColumnType(i);
                sb.append(getValue(sqlRowSet, i, type));
                if (i < colCount) {
                    sb.append(";");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private SqlRowSet selectDetailBalanceList(String yyyymm) {
        String sql = "select CODE,CNAME,ORG_CODE,CORP_TYPE,CREDIT_CARD_NO,REGISTER_ADDRESS,CORP_SCALE,CREDENCENO,\n" +
                "       STATUS,to_char(PAYDATE,'yyyymmdd'),to_char(ENDDATE,'yyyymmdd'),CURCD,round(LNBAL),\n" +
                "       round(USDBAL),ROUND(CNYBAL),INTRATE,LOANCLASS,GUARTYPE,CORP_OWERINFO,LOANPROPERTY,\n" +
                "       LOANTYPE1,LOANTYPE2,GROPFLAG,REPORT_DATE \n" +
                "from LOAN_BAL_INFO_LOG \n" +
                "where REPORT_DATE=?";
        return cmsJdbcTemplate.queryForRowSet(sql, yyyymm);
    }

    private SqlRowSet selectDetailActualAmtList(String yyyymm) {
        String sql = "select CODE,CNAME,ORG_CODE,CREDIT_CARDNO,CORP_SCALE,CREDENCENO,TYPE,\n" +
                "       to_char(PAYDATE, 'yyyymmdd') paydate,CURCD,AMT,\n" +
                "       case a.CURCD when 'CNY' then 0 when 'USD' then ROUND(AMT)\n" +
                "         else round(AMT * (select CURRRATE from CURRRATE\n" +
                "                        where CURCD = a.CURCD and CURRRATE_DATE =\n" +
                "                              (select max(CURRRATE_DATE) from CURRRATE\n" +
                "                                where curcd = a.curcd)) /\n" +
                "                (select CURRRATE from CURRRATE\n" +
                "                  where CURCD = 'USD' and CURRRATE_DATE =\n" +
                "                        (select max(CURRRATE_DATE) from CURRRATE where curcd = 'USD')))\n" +
                "       end tousd,\n" +
                "       case a.CURCD when 'CNY' then round(AMT)\n" +
                "         else round(AMT * (select CURRRATE\n" +
                "                         from CURRRATE\n" +
                "                        where CURCD = a.CURCD\n" +
                "                          and CURRRATE_DATE =\n" +
                "                              (select max(CURRRATE_DATE) from CURRRATE\n" +
                "                                where curcd = a.curcd)))\n" +
                "       end toCNY, GUAR\n" +
                "  from LOAN_PAY_AND_RTN_LOG a\n" +
                " WHERE REPORTNAME = 'C001FC5007437" + yyyymm + ".txt'";
        return cmsJdbcTemplate.queryForRowSet(sql);
    }

    private String getValue(final SqlRowSet rs, int colNum, int type) throws SQLException {
        switch (type) {
            case Types.ARRAY:
            case Types.BLOB:
            case Types.CLOB:
            case Types.DISTINCT:
            case Types.LONGVARBINARY:
            case Types.VARBINARY:
            case Types.BINARY:
            case Types.REF:
            case Types.STRUCT:
                return "undefined";
            default: {
                Object value = rs.getObject(colNum);
                if (rs.wasNull() || (value == null))
                    return ("null");
                else
                    return (value.toString());
            }
        }
    }
}
