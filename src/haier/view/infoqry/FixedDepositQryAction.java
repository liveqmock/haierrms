package haier.view.infoqry;

import haier.service.infoqry.FixedDepositBean;
import haier.service.infoqry.FixedDepositQryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * SBS定期存款查询（以及BI中外部银行定期存款汇总信息查询）.
 * User: zhanrui
 * Date: 13-3-6
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class FixedDepositQryAction implements Serializable {
    private static final long serialVersionUID = 8064944968186579065L;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String corpName;
    private BigDecimal total;
    private List<FixedDepositBean> detlList;

    @ManagedProperty(value = "#{fixedDepositQryService}")
    private FixedDepositQryService fixedDepositQryService;


    public String onQuery() {
        detlList = fixedDepositQryService.getFixedDepositRecord(corpName);
        return null;
    }

    public void calculateTotal(Object o) {
        this.total = new BigDecimal("0.00");
        if(o != null) {
            if(o instanceof String) {
                String[] sortFields = ((String) ((String) o).trim()).split("\\|");
                for (FixedDepositBean detl : this.detlList) {
                    String corpname = detl.getCorpName().trim();
                    String currname = detl.getCurrName().trim();
                    if (corpname.equals(sortFields[0]) && currname.equals(sortFields[1])) {
                        this.total = this.total.add(detl.getBalamt());
                    }
                }


/*
                for(MyRowObject p : (List<MyRowObject>) dataTableModel.getWrappedData()) {
                    switch(sortColumnCase) { // sortColumnCase was set in the onSort event
                        case 0:
                            if(p.getcolumn0data().getName().equals(name)) {
                                this.total += p.getcolumn0data().getValue();
                            }
                            break;
                        case 1:
                            if(p.getcolumn1data().getName().equals(name)) {
                                this.total += p.getcolumn1data().getValue();
                            }
                            break;
                    }
                }
*/
            }
        }
    }
    //====================

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public List<FixedDepositBean> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<FixedDepositBean> detlList) {
        this.detlList = detlList;
    }

    public FixedDepositQryService getFixedDepositQryService() {
        return fixedDepositQryService;
    }

    public void setFixedDepositQryService(FixedDepositQryService fixedDepositQryService) {
        this.fixedDepositQryService = fixedDepositQryService;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
