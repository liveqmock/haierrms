package haier.service.rms.pbcreport

import groovy.sql.Sql
import haier.repository.model.pbcrateReport.A14112411Bean
import javax.annotation.Resource
import org.apache.commons.dbcp.BasicDataSource
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SubjectService {
    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);

    @Resource
    protected BasicDataSource dataSource;

    public List selectA1411A2411Data(String strdate10) {
        def localdb = new Sql(dataSource)

        DateTime dt = new DateTime(strdate10);
        def strdate10_lastmonth = dt.minusMonths(1).dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");
        def cm_rptid = getRptid(strdate10, localdb)
        def lm_rptid = getRptid(strdate10_lastmonth, localdb)

        countA1411A2411Data(strdate10, localdb, cm_rptid, lm_rptid)

        def sql = """
            select substr(t.pb_product_code, 1, 2) product_code,
                   t.pb_product_type product_type,
                   round(sum(t.cur_month_bal_1411)/10000,2) cm_1411,
                   round(sum(t.cur_month_bal_2411)/10000,2) cm_2411,
                   round(sum(t.last_month_bal_1411)/10000,2) lm_1411,
                   round(sum(t.last_month_bal_2411)/10000,2) lm_2411
              from PBC_RATE_PB_PRODUCT_REF t
             group by substr(t.pb_product_code, 1, 2), t.pb_product_type
             """
        def a14112411List = []
        localdb.eachRow(sql) {
            A14112411Bean bean = new A14112411Bean()
            bean.setProductCode(it.product_code)
            bean.setProductType(it.product_type)
            bean.setCm_1411(it.cm_1411)
            bean.setCm_2411(it.cm_2411)
            bean.setLm_1411(it.lm_1411)
            bean.setLm_2411(it.lm_2411)
            a14112411List << bean
        }
        localdb.close()
        return a14112411List
    }

    private String getRptid(strdate10, localdb) {
        def rpt_year = strdate10[0..3]
        def rpt_month = strdate10[5..6]
        def sql = """
             select rpt_id
                  from bi.A00_DIM_REPORT_DATE@haierbi
                 where CLASSIFICATION = '04'
                   and rpt_year = '${rpt_year}'
                   and rpt_month = '${rpt_month}'
                   and enabled_flag = '1'
         """
        def row = localdb.firstRow(sql)
        return row.rpt_id
    }

    private void countA1411A2411Data(strdate10, localdb, cm_rptid, lm_rptid) {

        def sql1411 = """
            update PBC_RATE_PB_PRODUCT_REF t
               set t.cur_month_bal_1411 =
                   (select cny_total
                      from bi.A02_REP_PBC_W010110@haierbi
                     where ind_code = t.pb_subject_code
                       and ref_rpt_id = '${cm_rptid}'),
                   t.last_month_bal_1411 =
                   (select cny_total
                      from bi.A02_REP_PBC_W010110@haierbi
                     where ind_code = t.pb_subject_code
                       and ref_rpt_id = '${lm_rptid}')
        """
        def sql2411 = """
            update PBC_RATE_PB_PRODUCT_REF t
               set t.cur_month_bal_2411 =
                   (select usd_total
                      from bi.A02_REP_PBC_W010111@haierbi
                     where ind_code = '2'||substr(t.pb_subject_code,2,4)
                       and ref_rpt_id = '${cm_rptid}'),
                   t.last_month_bal_2411 =
                   (select usd_total
                      from bi.A02_REP_PBC_W010111@haierbi
                     where ind_code = '2'||substr(t.pb_subject_code,2,4)
                       and ref_rpt_id = '${lm_rptid}')
        """
        localdb.executeUpdate(sql1411)
        localdb.executeUpdate(sql2411)
    }

    /**
     * 统计单位活期存款余额 用于校验规则2中扣除的部分数据
     * @return
     */
    public A14112411Bean get_P12MG4_Data() {
        def localdb = new Sql(dataSource)
        def sql = """
            select substr(t.pb_product_code, 1, 2) product_code,
                   t.pb_product_type product_type,
                   round(t.cur_month_bal_1411/10000,2) cm_1411,
                   round(t.cur_month_bal_2411/10000,2) cm_2411,
                   round(t.last_month_bal_1411/10000,2) lm_1411,
                   round(t.last_month_bal_2411/10000,2) lm_2411
              from PBC_RATE_PB_PRODUCT_REF t
             where pb_subject_code = '12MG4'
             """
        A14112411Bean bean = new A14112411Bean()
        localdb.eachRow(sql) {
            bean.setProductCode(it.product_code)
            bean.setProductType(it.product_type)
            bean.setCm_1411(it.cm_1411)
            bean.setCm_2411(it.cm_2411)
            bean.setLm_1411(it.lm_1411)
            bean.setLm_2411(it.lm_2411)
        }
        localdb.close()
        return bean
    }
}
