package haier.rms.sbureport;


/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 8:22:32
 * To change this template use File | Settings | File Templates.
 */
public interface IMainETL {

    /**
     * �����������
     * @param strStartDate    yyyy-MM-dd
     * @param strEndDate      yyyy-MM-dd
     * @param bussItem
     */
    void processCurrYearData(String strStartDate, String strEndDate, String bussItem);

     /**
     * ���������������ݣ�ֱ�ӷ���һ������
     * @param strCurrDate    yyyy-MM-dd   ��ǰ����
     * @param bussItem
     */
    double getLastYearTopLevelData(String strCurrDate, String bussItem);

}