package haier.repository.dao;

import haier.repository.model.PbcRateVerifyitem;
import haier.repository.model.PbcRateVerifyitemExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PbcRateVerifyitemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PBC_RATE_VERIFYITEM
     *
     * @mbggenerated Tue Sep 20 13:55:00 CST 2011
     */
    int countByExample(PbcRateVerifyitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PBC_RATE_VERIFYITEM
     *
     * @mbggenerated Tue Sep 20 13:55:00 CST 2011
     */
    int deleteByExample(PbcRateVerifyitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PBC_RATE_VERIFYITEM
     *
     * @mbggenerated Tue Sep 20 13:55:00 CST 2011
     */
    int insert(PbcRateVerifyitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PBC_RATE_VERIFYITEM
     *
     * @mbggenerated Tue Sep 20 13:55:00 CST 2011
     */
    int insertSelective(PbcRateVerifyitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PBC_RATE_VERIFYITEM
     *
     * @mbggenerated Tue Sep 20 13:55:00 CST 2011
     */
    List<PbcRateVerifyitem> selectByExample(PbcRateVerifyitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PBC_RATE_VERIFYITEM
     *
     * @mbggenerated Tue Sep 20 13:55:00 CST 2011
     */
    int updateByExampleSelective(@Param("record") PbcRateVerifyitem record, @Param("example") PbcRateVerifyitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RMS.PBC_RATE_VERIFYITEM
     *
     * @mbggenerated Tue Sep 20 13:55:00 CST 2011
     */
    int updateByExample(@Param("record") PbcRateVerifyitem record, @Param("example") PbcRateVerifyitemExample example);
}