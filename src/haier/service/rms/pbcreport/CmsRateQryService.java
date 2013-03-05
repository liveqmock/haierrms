package haier.service.rms.pbcreport;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 人民银行利率水平报表
 * User: zhanrui
 * Date: 11-5-25
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CmsRateQryService {
    private static final Logger logger = LoggerFactory.getLogger(CmsRateQryService.class);

    @Resource
    protected JdbcTemplate cmsJdbcTemplate;
    @Resource
    protected SimpleJdbcTemplate cmsSimpleJdbcTemplate;


    //====================================================================
    public String getDetailBalanceBuffer(String filename) throws SQLException {
        SqlRowSet sqlRowSet = selectDetailBalanceList(filename);
        return transResultsetToString(sqlRowSet);
    }


    private String transResultsetToString(SqlRowSet sqlRowSet) throws SQLException {
        SqlRowSetMetaData sqlRowSetMetaData = sqlRowSet.getMetaData();
        int colCount = sqlRowSetMetaData.getColumnCount();
        StringBuffer sb = new StringBuffer();
        while (sqlRowSet.next()) {
            for (int i = 1; i <= colCount; i++) {
                int type = sqlRowSetMetaData.getColumnType(i);
                sb.append(getValue(sqlRowSet, i, type));
                if (i < colCount) {
                    sb.append(";");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private SqlRowSet selectDetailBalanceList(String filename) {
        String sql = "";
        try {
            //String fullPath=this.getClass().getClassLoader().getResource("/haier/service/rms/pbcreport/cms_balance.xml").getPath();
            String fullPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            logger.info(fullPath);
            sql = FileUtils.readFileToString(new File(fullPath + "haier/service/rms/pbcreport/" + filename), "UTF-8");
        } catch (IOException e) {
            logger.error("读取信贷SQL语句出错。", e);
            throw new RuntimeException("读取信贷SQL语句出错。", e);
        }

        return cmsJdbcTemplate.queryForRowSet(sql);
    }


    private String getValue(final SqlRowSet rs, int colNum, int type) throws SQLException {
        switch (type) {
            case Types.ARRAY:
            case Types.BLOB:
            case Types.CLOB:
            case Types.DISTINCT:
            case Types.LONGVARBINARY:
            case Types.VARBINARY:
            case Types.BINARY:
            case Types.REF:
            case Types.STRUCT:
                return "undefined";
            default: {
                Object value = rs.getObject(colNum);
                if (rs.wasNull() || (value == null))
                    return ("");
                else
                    return (value.toString());
            }
        }
    }
}
