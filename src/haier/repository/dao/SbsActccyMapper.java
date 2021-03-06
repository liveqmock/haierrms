package haier.repository.dao;

import haier.repository.model.SbsActccy;
import haier.repository.model.SbsActccyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbsActccyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCCY
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int countByExample(SbsActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCCY
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int deleteByExample(SbsActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCCY
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int insert(SbsActccy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCCY
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int insertSelective(SbsActccy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCCY
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    List<SbsActccy> selectByExample(SbsActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCCY
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int updateByExampleSelective(@Param("record") SbsActccy record, @Param("example") SbsActccyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCCY
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int updateByExample(@Param("record") SbsActccy record, @Param("example") SbsActccyExample example);
}