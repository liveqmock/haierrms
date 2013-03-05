package haier.service.rms.disreport;

import haier.repository.dao.disreport.DisReportMapper;
import haier.repository.model.disreport.ActbalHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-8
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DisReportService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DisReportMapper disReportMapper;

    public void setDisReportMapper(DisReportMapper disReportMapper) {
        this.disReportMapper = disReportMapper;
    }

    //======
    /**
     * 生成DIS全面预算系统所需的记录
     * @param txndate
     * @return
     */
    public List<ActbalHistory> selectDisActbal(String txndate){
        return disReportMapper.selectActBal(txndate);
    }


}
