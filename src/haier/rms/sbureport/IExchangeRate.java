package haier.rms.sbureport;

import org.apache.ibatis.session.SqlSession;


/**
 * ����ת��.
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 8:22:32
 * To change this template use File | Settings | File Templates.
 */
public interface IExchangeRate {

     /**
     * ȡ���»��� ���м�ۣ�
     *
     * @param currdate    yyyy-MM-dd
     * @param tmpcurrcode  ���������ҵı���
     * @return
     */
    String getLastExRate(SqlSession session, String currdate, String tmpcurrcode);
}