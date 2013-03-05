package haier.activemq.service.sbs.txn.T8123;

import haier.activemq.service.sbs.core.SOFDataDetail;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-15
 * Time: ����10:59
 * To change this template use File | Settings | File Templates.
 */
public class T8123SOFDataDetail extends SOFDataDetail {
    /*
    TXXX-ACTNUM	�˺�	X(14) 	����룬�Ҳ��ո�
    TXXX-ACTNAM	����	X(60) 	����룬�Ҳ��ո�
    TXXX-ACTBAL	���	X(21) 	����룬�Ҳ��ո�
    TXXX-ACTTYP	�˻�����	X(1)	����룬�Ҳ��ո�	1-����,2-����,3-���,9-����
    TXXX-ASSTYP	�Ƿ�֤��	X(1)	����룬�Ҳ��ո�	1-��֤��
     */
    private String actnum;
    private String actnam;
    private String actbal;
    private String acttyp;
    private String asstyp;

    {
        offset = 0;
        fieldTypes = new int[]{1, 1, 1, 1, 1};
        fieldLengths = new int[]{14, 60, 21, 1, 1};
    }

    public String getActnum() {
        return actnum;
    }

    public void setActnum(String actnum) {
        this.actnum = actnum;
    }

    public String getActbal() {
        return actbal;
    }

    public void setActbal(String actbal) {
        this.actbal = actbal;
    }

    public String getActtyp() {
        return acttyp;
    }

    public void setActtyp(String acttyp) {
        this.acttyp = acttyp;
    }

    public String getAsstyp() {
        return asstyp;
    }

    public void setAsstyp(String asstyp) {
        this.asstyp = asstyp;
    }

    public String getActnam() {
        return actnam;
    }

    public void setActnam(String actnam) {
        this.actnam = actnam;
    }
}
