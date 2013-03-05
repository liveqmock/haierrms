package haier.repository.dao.sbsreport;

import haier.repository.model.RmsSbsactattr;
import haier.repository.model.sbsreport.ActbalHistory;
import haier.repository.model.sbsreport.ActbalRankBean;
import haier.repository.model.sbsreport.CorpBalanceBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SbsReportMapper {

    /**
     * zhanrui  SBS历史余额查询
     * @param txndate
     * @return
     */
    List<ActbalHistory> selectHistoryActBal(@Param("txndate") String txndate, @Param("acttype") String acttype);

    /**
     * 查询某日A股或H股币种清单
     * @param txndate
     * @param acttype
     * @return
     */
    List<ActbalHistory> selectCurrcodeList(@Param("txndate") String txndate, @Param("acttype") String acttype);

    /**
     * 查询集团内外帐户区分列表
     * @return
     */
    @Select("select * from rms_sbsactattr")
    List<RmsSbsactattr> selectActattrList();

    /**
     *  zhanrui 20110509  关联交易月控报表(本币为人民币的数据)
     * @param txndate
     * @param acttype
     * @return
     */
    Long selectRelatedTxnReportRMBData(@Param("txndate") String txndate, @Param("acttype") String acttype);
    Long selectRelatedTxnReportForeignData(@Param("txndate") String txndate, @Param("acttype") String acttype);

    /**
     * zhanrui 20110509  统计月度余额增加
     * @param lastdate
     * @param firstdate
     * @return
     */
    List<ActbalRankBean> selectAccountBalanceIncreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);
    List<ActbalRankBean> selectAccountBalanceDecreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);

    /**
     * 统计月度客户存款余额增加排名TOP10
     * @param lastdate
     * @param firstdate
     * @return
     */
    List<ActbalRankBean> selectCustomerBalanceIncreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);

    /**
     * 统计月度客户存款余额减少排名TOP10
     * @param lastdate
     * @param firstdate
     * @return
     */
    List<ActbalRankBean> selectCustomerBalanceDecreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);

    /**
     * 按企业名称检索某月份内余额变动情况(万元)
     * @return
     */
    List<CorpBalanceBean> selectCustomerBalanceOneMonth(@Param("corpname") String corpname,@Param("yearmonth") String yearmonth);

}