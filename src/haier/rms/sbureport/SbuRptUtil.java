package haier.rms.sbureport;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-7-6
 * Time: 17:44:01
 * To change this template use File | Settings | File Templates.
 */
public class SbuRptUtil extends AbstractMainETL {

    public void  deleteAllData(){
        session.delete("InterestInfo.deleteall_sburpt");
        session.commit();
    }

    /**
     * ɾ����ǰ���ڵ�����
     * @param strCurrDate ��ʽΪyyyyMMdd
     */
    public void  deleteCurrDateData(String strCurrDate){
        session.delete("InterestInfo.deletecurrdate_sburpt",strCurrDate);
        session.commit();//?
    }

}
