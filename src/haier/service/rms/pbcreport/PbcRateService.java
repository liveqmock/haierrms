package haier.service.rms.pbcreport;

import haier.common.CurcdeMap;
import haier.repository.dao.*;
import haier.repository.dao.pbcrateReport.PbcrateReportMapper;
import haier.repository.model.*;
import haier.repository.model.pbcrateReport.PbcrateBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-9-20
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PbcRateService {
    private final Map curMap = CurcdeMap.setCurcdeMap();
    private final double usdRate = Double.parseDouble(curMap.get("USD").toString());
    private final DecimalFormat df = new DecimalFormat("#.00");
    @Resource
    private PbcRateLoanbalMapper loanbalMapper;
    @Resource
    private PbcRateLoandetlMapper loandetlMapper;
    @Resource
    private PbcRateDepositbalMapper depositbalMapper;
    @Resource
    private PbcRateDepositdetlMapper depositdetlMapper;
    @Resource
    private PbcRateBalverifyMapper balverifyMapper;
    @Resource
    private PbcRateDetlverifyMapper detlverifyMapper;
    @Resource
    private PbcRateVerifyitemMapper verifyitemMapper;
    @Resource
    private PbcrateReportMapper pbcrateReportMapper;

    /**
     * 更新折算美元字段
     */

    public String updateUSDAMT(String strDate10, String tabFlag) {
        String yyyymm = strDate10.substring(0, 4) + strDate10.substring(5, 7);
        String strDate8 = yyyymm + strDate10.substring(8, 10);
        int updateCnt = 0;
        try {
            if (tabFlag.equals("loanbal")) {
                updateCnt = pbcrateReportMapper.updateusdamtLoanbal(strDate10, yyyymm);
            } else if (tabFlag.equals("depositbal")) {
                updateCnt = pbcrateReportMapper.updateusdamtDepositbal(strDate10, yyyymm);
            } else if (tabFlag.equals("loandetl")) {
                updateCnt = pbcrateReportMapper.updateusdamtld(strDate10, yyyymm);
            } else if (tabFlag.equals("depositdetl")) {
                updateCnt = pbcrateReportMapper.updateusdamtdd(strDate10, yyyymm);
            }
        } catch (Exception ex) {
            throw new RuntimeException("更新出错" + ex.getMessage());
        }
        return String.valueOf(updateCnt);
    }

    /**
     * 获取人行报表校验数据
     */

    public List<PbcrateBean> pbcSelectbal(String strDate10, String ldflag) {
        if (ldflag.equals("loan")) {
            return pbcrateReportMapper.qryPbcrateLoanBal(strDate10);
        } else {
            return pbcrateReportMapper.qryPbcrateDepositBal(strDate10);
        }
    }

    public List<PbcrateBean> pbcSelectDetl(String strDate10, String ldflag) {
        if (ldflag.equals("loan")) {
            return pbcrateReportMapper.qryPbcrateLoanDetl(strDate10);
        } else {
            return null;
        }
    }

    public String selectLoanbalToString(String strDate10) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        PbcRateLoanbalExample example = new PbcRateLoanbalExample();
        example.createCriteria().andReportdateEqualTo(strDate10)
                .andIoubalamtNotEqualTo(new BigDecimal("0")).andCurrencycodeEqualTo("CNY");
        List<PbcRateLoanbal> pbcRateLoanbals = loanbalMapper.selectByExample(example);
        String rtnStr = "";
        rtnStr = getRecToString(pbcRateLoanbals);
        return rtnStr;
    }

    public String selectLoandetlToString(String strDate10) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        PbcRateLoandetlExample example = new PbcRateLoandetlExample();
        example.createCriteria().andReportdateEqualTo(strDate10).andCurrencycodeEqualTo("CNY");
        List<PbcRateLoandetl> pbcRateLoandetls = loandetlMapper.selectByExample(example);
        String rtnStr = "";
        rtnStr = getRecToString(pbcRateLoandetls);
        return rtnStr;
    }

    public String selectDepositbalToString(String strDate10) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        PbcRateDepositbalExample example = new PbcRateDepositbalExample();
        example.createCriteria().andReportdateEqualTo(strDate10)
                .andBalamtNotEqualTo(new BigDecimal("0")).andCurrencycodeEqualTo("CNY");
        List<PbcRateDepositbal> pbcRateDepositbals = depositbalMapper.selectByExample(example);
        String rtnStr = "";

        rtnStr = getRecToString(pbcRateDepositbals);
        return rtnStr;
    }

    public String selectDepositdetlToString(String strDate10) {
        String yyyymm = strDate10.substring(0, 4) + strDate10.substring(5, 7);
        String strDate8 = yyyymm + strDate10.substring(8, 10);
        PbcRateDepositdetlExample example = new PbcRateDepositdetlExample();
        //
        example.createCriteria().andReportdateEqualTo(strDate8);
        List<PbcRateDepositdetl> pbcRateDepositdetls = depositdetlMapper.selectByExample(example);
        String rtnStr = "";
        try {
            rtnStr = getRecToString(pbcRateDepositdetls);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return rtnStr;
    }

    public String getRecToString(List<?> objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StringBuffer sb = new StringBuffer();
        int line = 0;
        DecimalFormat dfBal=new DecimalFormat("0.00");
        DecimalFormat dfIntrate=new DecimalFormat("0.00000");
        for (Object record : objects) {
            line++;
            Field[] fields = record.getClass().getDeclaredFields();
            String curcde = "";
            int count = fields.length;
            int i = 0;
            for (Field field : fields) {
                i++;
                String propertyName = field.getName();
                String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                Method m = record.getClass().getMethod(methodName);
                Object o = (m.invoke(record) == null ? (Object) "" : m.invoke(record));
                //System.out.println(o.toString());
                if ("getBalamt".equals(methodName)
                        || "getIoubalamt".equals(methodName)
                        ) {
                    sb.append(dfBal.format(o));
                }else if ("getIntrate".equals(methodName)) {
                    sb.append(dfIntrate.format(o));
                }else{
                    sb.append(o.toString());
                }

                if (i < fields.length - 1) {
                    sb.append("|");
                }
                if (i == fields.length - 1) {
                    break;
                }
            }
            if (line < objects.size()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public List<PbcRateLoanbal> selectLoanbalList(String strDate10) {
        String yyyymm = strDate10.substring(0, 4) + strDate10.substring(5, 7);
        PbcRateLoanbalExample example = new PbcRateLoanbalExample();
        example.createCriteria().andReportdateEqualTo(strDate10);
        return loanbalMapper.selectByExample(example);
    }
}
