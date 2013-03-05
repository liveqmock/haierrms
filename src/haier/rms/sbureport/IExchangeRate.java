package haier.rms.sbureport;

import org.apache.ibatis.session.SqlSession;


/**
 * 汇率转换.
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 8:22:32
 * To change this template use File | Settings | File Templates.
 */
public interface IExchangeRate {

     /**
     * 取最新汇率 （中间价）
     *
     * @param currdate    yyyy-MM-dd
     * @param tmpcurrcode  相对于人民币的币种
     * @return
     */
    String getLastExRate(SqlSession session, String currdate, String tmpcurrcode);
}