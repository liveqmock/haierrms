package haier.repository.dao;

import haier.repository.model.MtActtype;
import haier.repository.model.MtActtypeExample;
import haier.repository.model.MtActtypeKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MtActtypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int countByExample(MtActtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int deleteByExample(MtActtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int deleteByPrimaryKey(MtActtypeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int insert(MtActtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int insertSelective(MtActtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    List<MtActtype> selectByExample(MtActtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    MtActtype selectByPrimaryKey(MtActtypeKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int updateByExampleSelective(@Param("record") MtActtype record, @Param("example") MtActtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int updateByExample(@Param("record") MtActtype record, @Param("example") MtActtypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int updateByPrimaryKeySelective(MtActtype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    int updateByPrimaryKey(MtActtype record);
}