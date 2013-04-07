package haier.repository.dao.infoqry;

import haier.repository.model.infoqry.FixedDepositBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HfcBiMapper {

    /**
     * zhanrui  SBS¿˙ ∑”‡∂Ó≤È—Ø
     * @param txndate
     * @return
     */

    @Select("SELECT distinct \n" +
            "                --t.csm_grp_code,\n" +
            "                --t.csm_grp_name,\n" +
            "                --t.data_type,\n" +
            "                t.csm_other_name as csmOtherName,\n" +
            "                --t.flag,\n" +
            "                t.cur_code as currName,\n" +
            "                t.loan_amount as balamt,\n" +
            "                t.int_rate as intRate,\n" +
            "                t.issue_date as startDate,\n" +
            "                t.term_date as endDate\n" +
            "  FROM bi.w02_interest@haierbi t\n" +
            " where t.data_type = '01'\n" +
            "   and t.status = '2'\n" +
            "   and t.delete_flag = '0'\n" +
            "   and t.flag = '1169'")
    List<FixedDepositBean> selectHistoryActBal();

}