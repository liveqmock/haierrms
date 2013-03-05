package haier.repository.dao.pbcrateReport;

import haier.repository.model.pbcrateReport.PbcrateBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-10-11
 * Time: ÉÏÎç11:21
 * To change this template use File | Settings | File Templates.
 */
public interface PbcrateReportMapper {


    int updateusdamtLoanbal(@Param("strdate") String strdate, @Param("yyyymm") String yyyymm);

    int updateusdamtDepositbal(@Param("strdate") String strdate, @Param("yyyymm") String yyyymm);

    List<PbcrateBean> qryPbcrateLoanBal(@Param("strdate") String strdate);

    List<PbcrateBean> qryPbcrateDepositBal(@Param("strdate") String strdate);

    List<PbcrateBean> qryPbcrateLoanDetl(@Param("strdate") String strdate);

    //List<PbcrateBean> qryPbcrateDepositDetl(@Param("strdate") String strdate);

    int updateusdamtld(@Param("strdate") String strdt,@Param("yyyymm") String yyyymm);

    int updateusdamtdd(@Param("strdate") String strdate, @Param("yyyymm") String yyyymm);

}
