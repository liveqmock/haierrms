package haier.rms.sbs.detlbook.sbs8853;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-1-17
 * Time: обнГ5:09
 * To change this template use File | Settings | File Templates.
 */
public interface SBSInterface {

    public void setTxnCode(String txncode);

    public void setRequestHeader();

    public List getResponseList();

}
