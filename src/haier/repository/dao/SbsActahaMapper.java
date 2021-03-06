package haier.repository.dao;

import haier.repository.model.SbsActaha;
import haier.repository.model.SbsActahaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SbsActahaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAHA
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int countByExample(SbsActahaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAHA
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int deleteByExample(SbsActahaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAHA
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int insert(SbsActaha record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAHA
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int insertSelective(SbsActaha record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAHA
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    List<SbsActaha> selectByExample(SbsActahaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAHA
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int updateByExampleSelective(@Param("record") SbsActaha record, @Param("example") SbsActahaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTAHA
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    int updateByExample(@Param("record") SbsActaha record, @Param("example") SbsActahaExample example);
}