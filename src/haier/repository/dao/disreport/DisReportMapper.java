package haier.repository.dao.disreport;

import haier.repository.model.disreport.ActbalHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DisReportMapper {

    /**
     * zhanrui  DISÈ«ÃæÔ¤Ëã
     * @param txndate
     * @return
     */
    List<ActbalHistory> selectActBal(@Param("txndate") String txndate);
}