package haier.service.infoqry;

import haier.repository.dao.S1169CorplistMapper;
import haier.repository.model.S1169Corplist;
import haier.repository.model.S1169CorplistExample;
import haier.service.common.SbsCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 1169企业清单维护.
 * User: zhanrui
 * Date: 13-3-7
 * Time: 上午10:51
 */
@Service
public class Act1169ListService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private S1169CorplistMapper s1169CorplistMapper;

    @Autowired
    private SbsCommonService sbsCommonService;

    public List<S1169Corplist> selectCorpList(){
        S1169CorplistExample example = new S1169CorplistExample();
        example.createCriteria();
        return s1169CorplistMapper.selectByExample(example);
    }
    public void insertCorplistRecord(S1169Corplist corp){
        s1169CorplistMapper.insert(corp);
    }
    public void deleteCorplistRecord(S1169Corplist corp){
        s1169CorplistMapper.deleteByPrimaryKey(corp.getPkid());
    }
}
