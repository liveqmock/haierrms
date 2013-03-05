package haier.activemq.service.sbs.txn.T8821;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2012-05-23
 */
public class T8821SOFDataDetailOld extends SOFDataDetail {
    private String strLine;

    {
        offset = 0;
        fieldTypes = new int[]{1};
        fieldLengths = new int[]{-1};
    }

    public String getStrLine() {
        return strLine;
    }

    public void setStrLine(String strLine) {
        this.strLine = strLine;
    }
}
