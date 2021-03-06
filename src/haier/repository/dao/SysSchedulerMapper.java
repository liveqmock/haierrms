package haier.repository.dao;

import haier.repository.model.SysScheduler;
import haier.repository.model.SysSchedulerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysSchedulerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SCHEDULER
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int countByExample(SysSchedulerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SCHEDULER
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int deleteByExample(SysSchedulerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SCHEDULER
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int insert(SysScheduler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SCHEDULER
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int insertSelective(SysScheduler record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SCHEDULER
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    List<SysScheduler> selectByExample(SysSchedulerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SCHEDULER
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int updateByExampleSelective(@Param("record") SysScheduler record, @Param("example") SysSchedulerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYS_SCHEDULER
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int updateByExample(@Param("record") SysScheduler record, @Param("example") SysSchedulerExample example);
}