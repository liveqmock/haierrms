package haier.service.rms.sbsbatch;

import haier.repository.dao.*;
import haier.repository.dao.sbsreport.SbsReportMapper;
import haier.repository.model.*;
import haier.repository.model.sbsreport.ActbalHistory;
import haier.repository.model.sbsreport.ActbalRankBean;
import haier.repository.model.sbsreport.CorpBalanceBean;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-8
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ActbalService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SbsActbalMapper actbalMapper;
    @Autowired
    private SbsActcxrMapper actcxrMapper;
    @Autowired
    private SbsActahaMapper actahaMapper;
    @Autowired
    private SbsReportMapper sbsrptMapper;
    @Autowired
    private SbsActapcMapper actapcMapper;

    @Autowired
    private MtActtypeMapper mtActtypeMapper;

    public void setActbalMapper(SbsActbalMapper actbalMapper) {
        this.actbalMapper = actbalMapper;
    }

    public void setActcxrMapper(SbsActcxrMapper actcxrMapper) {
        this.actcxrMapper = actcxrMapper;
    }

    public void setActahaMapper(SbsActahaMapper actahaMapper) {
        this.actahaMapper = actahaMapper;
    }
    //======

    public int insertActbal(SbsActbal actbal) {
        return actbalMapper.insert(actbal);
    }

    public int deleteActbalSameDateRecord(String strdate) {
        SbsActbalExample example = new SbsActbalExample();
        example.createCriteria().andTxndateEqualTo(strdate);
        return actbalMapper.deleteByExample(example);
    }

    public int insertActcxr(SbsActcxr actcxr) {
        return actcxrMapper.insert(actcxr);
    }

    public int deleteActcxrSameDateRecord(String strdate) {
        SbsActcxrExample example = new SbsActcxrExample();
        example.createCriteria().andTxndateEqualTo(strdate);
        return actcxrMapper.deleteByExample(example);
    }

    public int insertActaha(SbsActaha actaha) {
        return actahaMapper.insert(actaha);
    }
    public int insertActapc(SbsActapc actapc) {
        return actapcMapper.insert(actapc);
    }

    public int deleteActahaAllRecord() {
        SbsActahaExample example = new SbsActahaExample();
        example.createCriteria().andActtypeIsNotNull();
        return actahaMapper.deleteByExample(example);
    }
    public int deleteActapcAllRecord() {
        SbsActapcExample example = new SbsActapcExample();
        example.createCriteria();
        return actapcMapper.deleteByExample(example);
    }

    /**
     * SBS 帐户历史余额查询
     *
     * @param txndate
     * @return
     */
    public List<ActbalHistory> selectHistoryActBal(String txndate, String acttype) {
        return sbsrptMapper.selectHistoryActBal(txndate, acttype);
    }
    public List<ActbalHistory> selectHistoryActBalByCorpName(String startdate,String enddate, String corpname) {
        return sbsrptMapper.selectHistoryActBalByCorpName(startdate, enddate, corpname);
    }

    /**
     * 查询某日A股或H股币种清单
     *
     * @param txndate
     * @param acttype
     * @return
     */
    public List<ActbalHistory> selectCurrcodeList(String txndate, String acttype) {
        return sbsrptMapper.selectCurrcodeList(txndate, acttype);
    }

    /**
     * 查询集团内外帐户区分列表
     *
     * @return
     */
    public List<RmsSbsactattr> selectActattrList() {
        return sbsrptMapper.selectActattrList();
    }

    /**
     * zhanrui 20110509  关联交易月控报表(本币为人民币的数据)
     *
     * @param txndate
     * @param acttype
     * @return
     */
    public long selectRelatedTxnReportRMBData(String txndate, String acttype) {
        Long result = sbsrptMapper.selectRelatedTxnReportRMBData(txndate, acttype);
        if (result == null) {
            result = 0L;
        }
        return result;
    }

    public long selectRelatedTxnReportForeignData(String txndate, String acttype) {
        Long result = sbsrptMapper.selectRelatedTxnReportForeignData(txndate, acttype);
        if (result == null) {
            result = 0L;
        }
        return result;
    }

    /**
     * zhanrui 20110509  统计月度余额增加
     *
     * @param startYearMonth
     * @return
     */
    public List<ActbalRankBean> selectBalanceIncreaseTop10(String startYearMonth) {
        String[] dates = getMonthHeadAndTailDate(startYearMonth);
        if (dates == null) {
            return null;
        }
        return sbsrptMapper.selectAccountBalanceIncreaseTop10(dates[0], dates[1]);
    }

    public List<ActbalRankBean> selectBalanceDecreaseTop10(String startYearMonth) {
        String[] dates = getMonthHeadAndTailDate(startYearMonth);
        if (dates == null) {
            return null;
        }
        return sbsrptMapper.selectAccountBalanceDecreaseTop10(dates[0], dates[1]);
    }

    /**
     * 统计月度客户存款余额增加排名TOP10
     *
     * @param startYearMonth
     * @return
     */
    public List<ActbalRankBean> selectCustomerBalanceIncreaseTop10(String startYearMonth) {
        String[] dates = getMonthHeadAndTailDate(startYearMonth);
        if (dates == null) {
            return null;
        }
        return sbsrptMapper.selectCustomerBalanceIncreaseTop10(dates[0], dates[1]);
    }

    public List<ActbalRankBean> selectCustomerBalanceDecreaseTop10(String startYearMonth) {
        String[] dates = getMonthHeadAndTailDate(startYearMonth);
        if (dates == null) {
            return null;
        }
        return sbsrptMapper.selectCustomerBalanceDecreaseTop10(dates[0], dates[1]);
    }

    /**
     * 按企业名称检索某月份内余额变动情况
     *
     * @param corpname
     * @param yearmonth
     * @return
     */
    public List<CorpBalanceBean> selectCustomerBalanceOneMonth(String corpname, String yearmonth) {
        return sbsrptMapper.selectCustomerBalanceOneMonth(corpname, yearmonth);
    }


    /**
     * 根据指定月份日期，查找该月内的有余额的最后一天的日期和第一天的日期（第一天无论有无余额）
     *
     * @param startYearMonth 2011年5月
     * @return
     */
    private String[] getMonthHeadAndTailDate(String startYearMonth) {
        String strdate;
        try {
            Date date = new SimpleDateFormat("yyyy年MM月").parse(startYearMonth);
            strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            logger.info("日期输入错误");
            throw new RuntimeException("日期输入错误");
        }
        LocalDate localDate = new LocalDate(strdate).dayOfMonth().withMaximumValue();
        LocalDate.Property origmonth = localDate.monthOfYear();
        SbsActbalExample example = new SbsActbalExample();
        int count = 0;
        do {
            strdate = localDate.toString("yyyy-MM-dd");
            example.clear();
            example.createCriteria().andTxndateEqualTo(strdate);
            count = actbalMapper.countByExample(example);
            localDate = localDate.minusDays(1);
            if (!origmonth.equals(localDate.monthOfYear())) {
                return null;
            }
        } while (count == 0);
        String lastdate = strdate;

        localDate = new LocalDate(strdate).dayOfMonth().withMinimumValue();
        count = 0;
        do {
            strdate = localDate.toString("yyyy-MM-dd");
            example.clear();
            example.createCriteria().andTxndateEqualTo(strdate);
            count = actbalMapper.countByExample(example);
            localDate = localDate.plusDays(1);
            if (!origmonth.equals(localDate.monthOfYear())) {
                return null;
            }
        } while (count == 0);

        String firstdate = strdate;
        return new String[]{lastdate, firstdate};
    }

    /**
     * 本地帐户属性定义表 (只获取SBS的帐户定义)
     * @param category
     * @return
     */
    public List<MtActtype> selectActtype4SbsActList(String category) {
        MtActtypeExample example = new MtActtypeExample();
        example.createCriteria().andBankcdEqualTo("999").andCategoryEqualTo(category);
        example.setOrderByClause("actno");
        return mtActtypeMapper.selectByExample(example);
    }
    public List<MtActtype> selectActtype4SbsActList() {
        MtActtypeExample example = new MtActtypeExample();
        example.createCriteria().andBankcdEqualTo("999");
        example.setOrderByClause("actno");
        return mtActtypeMapper.selectByExample(example);
    }

    public List<MtActtype> selectActnosByCorpName(String corpName){
        MtActtypeExample example = new MtActtypeExample();
        example.createCriteria().andBankcdEqualTo("999").andActnameLike("%" + corpName + "%");
        return mtActtypeMapper.selectByExample(example);
    }

}
