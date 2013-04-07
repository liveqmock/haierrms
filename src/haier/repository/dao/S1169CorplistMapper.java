package haier.repository.dao;

import haier.repository.model.S1169Corplist;
import haier.repository.model.S1169CorplistExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface S1169CorplistMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int countByExample(S1169CorplistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int deleteByExample(S1169CorplistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int deleteByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int insert(S1169Corplist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int insertSelective(S1169Corplist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    List<S1169Corplist> selectByExample(S1169CorplistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    S1169Corplist selectByPrimaryKey(String pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int updateByExampleSelective(@Param("record") S1169Corplist record, @Param("example") S1169CorplistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int updateByExample(@Param("record") S1169Corplist record, @Param("example") S1169CorplistExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int updateByPrimaryKeySelective(S1169Corplist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.S1169_CORPLIST
     *
     * @mbggenerated Thu Mar 07 14:33:52 CST 2013
     */
    int updateByPrimaryKey(S1169Corplist record);
}