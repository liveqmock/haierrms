package haier.service.rms.externalcustomer;

import haier.repository.dao.CrmCustomerBaseMapper;
import haier.repository.dao.SbsActbalMapper;
import haier.repository.model.SbsActbal;
import haier.repository.model.SbsActbalExample;
import haier.view.rms.externalcustomer.LtdratioBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-20
 * Time: ÉÏÎç10:41
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ExternalCustService {
    @Autowired
    private CrmCustomerBaseMapper crmCustomerBaseMapper;

    public List<LtdratioBean> seletectedLtdrRecords(String startdate,String enddate,String company,String dept) {
        return crmCustomerBaseMapper.selectedLtdratioRecord(startdate,enddate,company,dept);
    }

    public List<LtdratioBean> selectedDistActname(String startdate,String enddate,String company,String dept) {
        return crmCustomerBaseMapper.selectedDistActname(startdate,enddate,company,dept);
    }

    public CrmCustomerBaseMapper getCrmCustomerBaseMapper() {
        return crmCustomerBaseMapper;
    }

    public void setCrmCustomerBaseMapper(CrmCustomerBaseMapper crmCustomerBaseMapper) {
        this.crmCustomerBaseMapper = crmCustomerBaseMapper;
    }
}
