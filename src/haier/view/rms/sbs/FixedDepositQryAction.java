package haier.view.rms.sbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

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

}
