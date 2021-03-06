package haier.repository.dao;

import haier.repository.model.Ptoperrole;
import haier.repository.model.PtoperroleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PtoperroleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPERROLE
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int countByExample(PtoperroleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPERROLE
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int deleteByExample(PtoperroleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPERROLE
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int insert(Ptoperrole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPERROLE
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int insertSelective(Ptoperrole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPERROLE
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    List<Ptoperrole> selectByExample(PtoperroleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPERROLE
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int updateByExampleSelective(@Param("record") Ptoperrole record, @Param("example") PtoperroleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PTOPERROLE
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    int updateByExample(@Param("record") Ptoperrole record, @Param("example") PtoperroleExample example);
}