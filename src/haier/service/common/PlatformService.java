package haier.service.common;

import haier.repository.dao.*;
import haier.repository.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.security.OperatorManager;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ƽ̨������
 * User: zhanrui
 * Date: 11-4-5
 * Time: ����7:42
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PlatformService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PtoperMapper ptoperMapper;

    @Autowired

    private PtoplogMapper ptoplogMapper;

    @Autowired
    private PtdeptMapper ptdeptMapper;

    @Autowired
    private PtenudetailMapper enudetailMapper;

    @Autowired
    private PtmenuMapper menuMapper;

    @Autowired
    private SysSchedulerLogMapper sysSchedulerLogMapper;



    public  OperatorManager getOperatorManager(){
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("�û�δ��¼��");
        }
        return om;
    }

    //���� ������|��������
    public List<String> selectBranchLevelString(String branchid){
        return ptdeptMapper.selectBranchLevelString(branchid);
    }

    //���� ������
    public  List<String> selectBranchLevelList(String branchid){
        return ptdeptMapper.selectBranchLevelList(branchid);
    }


    /**
     * ������ָ����Աͬ����Ĺ�Ա�б�
     * @param tellerid  ָ����ԱID
     * @return   list ��not null��
     */
    public List<Ptoper> selectBranchTellers(String tellerid) {
            PtoperExample example = new PtoperExample();
            example.createCriteria().andOperidEqualTo(tellerid);
            List<Ptoper> records = ptoperMapper.selectByExample(example);
            if (records.size() == 0) {
                return records;
            } else {
                String deptid = records.get(0).getDeptid();
                example.clear();
                example.createCriteria().andDeptidEqualTo(deptid);
                return ptoperMapper.selectByExample(example);
            }
    }

    /**
     * ����ָ��ö���嵥
     * @param enuid
     * @return
     */
    public List<Ptenudetail> selectEnuDetail(String enuid) {
            PtenudetailExample example = new PtenudetailExample();
            example.createCriteria().andEnutypeEqualTo(enuid);
            example.setOrderByClause(" dispno ");
            return enudetailMapper.selectByExample(example);
    }

    /**
     * ��ȡö�ٱ���ĳһ��� ��չ����ֵ
     * @param enuType
     * @param enuItemValue
     * @return
     */
    public String selectEnuExpandValue(String enuType, String enuItemValue){
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType).andEnuitemvalueEqualTo(enuItemValue);
        //TODO �����жϣ�
        return enudetailMapper.selectByExample(example).get(0).getEnuitemexpand();
    }

    /**
     * ���� ö��ֵ����չֵ�Ķ�Ӧ��ϵ MAP
     * @param enuType
     * @return
     */
    public Map<String,String> selectEnuItemValueToExpandValueMap(String enuType){
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo(enuType);
        List<Ptenudetail> records = enudetailMapper.selectByExample(example);
        Map<String,String> enuMap = new HashMap<String, String>();
        for (Ptenudetail record : records) {
            enuMap.put(record.getEnuitemvalue(), record.getEnuitemexpand());
        }
        return enuMap;
    }

    //******************************
    /**
     * ��ѯĳһ��ĵ�����־
     * @param curryear
     * @return
     * @throws java.text.ParseException
     */
    public List<SysSchedulerLog> selectSchedulerLogByYear(String curryear) throws ParseException {
        SysSchedulerLogExample example = new SysSchedulerLogExample();
        String firstDayThisYear = new SimpleDateFormat("yyyy-01-01").format(new Date());
        //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(firstDayThisYear);
        //example.createCriteria().andTimeGreaterThanOrEqualTo(date);
        example.createCriteria();
        List<SysSchedulerLog> sysSchedulerLogs = sysSchedulerLogMapper.selectByExample(example);
        return sysSchedulerLogs;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertNewOperationLog(Ptoplog ptoplog){
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("�û�δ��¼��");
        }

        ptoplog.setOperId(om.getOperatorId());
        ptoplog.setOperName(om.getOperatorName());
        ptoplog.setOperBranchid(om.getOperator().getDeptid());
        ptoplog.setHostIp(om.getRemoteAddr());
        ptoplog.setOpDate(new Date());

        ptoplogMapper.insert(ptoplog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertNewOperationLog(String actionId, String actionName, String opLog){
        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
        if (om == null) {
            throw new RuntimeException("�û�δ��¼��");
        }

        Ptoplog ptoplog = new Ptoplog();
        ptoplog.setOperId(om.getOperatorId());
        ptoplog.setOperName(om.getOperatorName());
        ptoplog.setOperBranchid(om.getOperator().getDeptid());
        ptoplog.setHostIp(om.getRemoteAddr());
        ptoplog.setOpDate(new Date());

        ptoplog.setActionId(actionId);
        ptoplog.setActionName(actionName);
        ptoplog.setOpLog(opLog);

        ptoplogMapper.insert(ptoplog);
    }
}