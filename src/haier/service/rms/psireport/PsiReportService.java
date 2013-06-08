package haier.service.rms.psireport;

import haier.repository.dao.SbsActapcMapper;
import haier.repository.dao.SbsActbalMapper;
import haier.repository.model.SbsActapc;
import haier.repository.model.SbsActapcExample;
import haier.repository.model.SbsActbal;
import haier.repository.model.SbsActbalExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: zhanrui
 * Date: 2013-5-8
 * Time: ÏÂÎç4:35
 */
@Service
public class PsiReportService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SbsActbalMapper sbsActbalMapper;
    @Autowired
    private SbsActapcMapper sbsActapcMapper;

    public List<SbsActbal> selectActbal(String txndate){
        SbsActbalExample example = new SbsActbalExample();
        example.createCriteria().andTxndateEqualTo(txndate);
        return sbsActbalMapper.selectByExample(example);
    }
    public List<SbsActapc> selectActapc(){
        SbsActapcExample example = new SbsActapcExample();
        example.createCriteria();
        return sbsActapcMapper.selectByExample(example);
    }
}
