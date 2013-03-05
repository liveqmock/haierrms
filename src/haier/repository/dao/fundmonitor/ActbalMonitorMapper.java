package haier.repository.dao.fundmonitor;

import haier.repository.model.fundmonitor.MtActbalBean;
import haier.repository.model.fundmonitor.MtActbalSumBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ActbalMonitorMapper {

    /**
     * zhanrui
     * @param txndate
     * @return
     */
    List<MtActbalSumBean> selectSumDataList(@Param("bankcd") String bankcd, @Param("txndate") String txndate);
    List<MtActbalBean> selectActBalList(@Param("bankcd") String bankcd, @Param("txndate") String txndate,
                                           @Param("qrytime") String qrytime);

}