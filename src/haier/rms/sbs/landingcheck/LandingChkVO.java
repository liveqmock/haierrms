package haier.rms.sbs.landingcheck;

import haier.activemq.service.sbs.txn.Tn116.Tn116SOFDataDetail;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 12-2-17
 * Time: ионГ7:31
 * To change this template use File | Settings | File Templates.
 */
public class LandingChkVO extends Tn116SOFDataDetail {
    private BigDecimal  v_txnamt;

    public BigDecimal getV_txnamt() {
        return v_txnamt;
    }

    public void setV_txnamt(BigDecimal v_txnamt) {
        this.v_txnamt = v_txnamt;
    }
}
