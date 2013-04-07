package haier.repository.dao;

import haier.repository.model.Ptoplog;
import haier.repository.model.PtoplogExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PtoplogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int countByExample(PtoplogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int deleteByExample(PtoplogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int deleteByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int insert(Ptoplog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int insertSelective(Ptoplog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    List<Ptoplog> selectByExample(PtoplogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    Ptoplog selectByPrimaryKey(String guid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int updateByExampleSelective(@Param("record") Ptoplog record, @Param("example") PtoplogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int updateByExample(@Param("record") Ptoplog record, @Param("example") PtoplogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int updateByPrimaryKeySelective(Ptoplog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PTOPLOG
     *
     * @mbggenerated Fri Mar 08 18:06:57 CST 2013
     */
    int updateByPrimaryKey(Ptoplog record);
}