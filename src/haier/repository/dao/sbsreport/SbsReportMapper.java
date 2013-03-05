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
     * zhanrui  SBS��ʷ����ѯ
     * @param txndate
     * @return
     */
    List<ActbalHistory> selectHistoryActBal(@Param("txndate") String txndate, @Param("acttype") String acttype);

    /**
     * ��ѯĳ��A�ɻ�H�ɱ����嵥
     * @param txndate
     * @param acttype
     * @return
     */
    List<ActbalHistory> selectCurrcodeList(@Param("txndate") String txndate, @Param("acttype") String acttype);

    /**
     * ��ѯ���������ʻ������б�
     * @return
     */
    @Select("select * from rms_sbsactattr")
    List<RmsSbsactattr> selectActattrList();

    /**
     *  zhanrui 20110509  ���������¿ر���(����Ϊ����ҵ�����)
     * @param txndate
     * @param acttype
     * @return
     */
    Long selectRelatedTxnReportRMBData(@Param("txndate") String txndate, @Param("acttype") String acttype);
    Long selectRelatedTxnReportForeignData(@Param("txndate") String txndate, @Param("acttype") String acttype);

    /**
     * zhanrui 20110509  ͳ���¶��������
     * @param lastdate
     * @param firstdate
     * @return
     */
    List<ActbalRankBean> selectAccountBalanceIncreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);
    List<ActbalRankBean> selectAccountBalanceDecreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);

    /**
     * ͳ���¶ȿͻ���������������TOP10
     * @param lastdate
     * @param firstdate
     * @return
     */
    List<ActbalRankBean> selectCustomerBalanceIncreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);

    /**
     * ͳ���¶ȿͻ��������������TOP10
     * @param lastdate
     * @param firstdate
     * @return
     */
    List<ActbalRankBean> selectCustomerBalanceDecreaseTop10(@Param("lastdate") String lastdate,@Param("firstdate") String firstdate);

    /**
     * ����ҵ���Ƽ���ĳ�·������䶯���(��Ԫ)
     * @return
     */
    List<CorpBalanceBean> selectCustomerBalanceOneMonth(@Param("corpname") String corpname,@Param("yearmonth") String yearmonth);

}