package haier.repository.dao;

import haier.repository.model.DisActlst;
import haier.repository.model.DisActlstExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DisActlstMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DIS_ACTLST
     *
     * @mbggenerated Sat Apr 09 20:29:23 CST 2011
     */
    int countByExample(DisActlstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DIS_ACTLST
     *
     * @mbggenerated Sat Apr 09 20:29:23 CST 2011
     */
    int deleteByExample(DisActlstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DIS_ACTLST
     *
     * @mbggenerated Sat Apr 09 20:29:23 CST 2011
     */
    int insert(DisActlst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DIS_ACTLST
     *
     * @mbggenerated Sat Apr 09 20:29:23 CST 2011
     */
    int insertSelective(DisActlst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DIS_ACTLST
     *
     * @mbggenerated Sat Apr 09 20:29:23 CST 2011
     */
    List<DisActlst> selectByExample(DisActlstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DIS_ACTLST
     *
     * @mbggenerated Sat Apr 09 20:29:23 CST 2011
     */
    int updateByExampleSelective(@Param("record") DisActlst record, @Param("example") DisActlstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DIS_ACTLST
     *
     * @mbggenerated Sat Apr 09 20:29:23 CST 2011
     */
    int updateByExample(@Param("record") DisActlst record, @Param("example") DisActlstExample example);

    /**
     * zhanrui  DISȫ��Ԥ��
     * @param txndate
     * @return
     */
    List<String> selectActBal(@Param("txndate") String txndate);
}