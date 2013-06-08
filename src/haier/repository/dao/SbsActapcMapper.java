package haier.repository.dao;

import haier.repository.model.SbsActapc;
import haier.repository.model.SbsActapcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbsActapcMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int countByExample(SbsActapcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int deleteByExample(SbsActapcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int deleteByPrimaryKey(String apcode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int insert(SbsActapc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int insertSelective(SbsActapc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    List<SbsActapc> selectByExample(SbsActapcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    SbsActapc selectByPrimaryKey(String apcode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int updateByExampleSelective(@Param("record") SbsActapc record, @Param("example") SbsActapcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int updateByExample(@Param("record") SbsActapc record, @Param("example") SbsActapcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int updateByPrimaryKeySelective(SbsActapc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAPC
     *
     * @mbggenerated Mon May 27 16:55:21 CST 2013
     */
    int updateByPrimaryKey(SbsActapc record);
}