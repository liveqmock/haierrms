package haier.service.rms.pbcreport

import gateway.ftp.SbsFtp4PbcRateRpt
import groovy.sql.Sql
import javax.annotation.Resource
import org.apache.commons.dbcp.BasicDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pub.platform.advance.utils.PropertyManager
import groovy.text.SimpleTemplateEngine

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-9-6
 * Time: 下午6:03
 * To change this template use File | Settings | File Templates.
 */
@Service
class DataEtlService {
    private static final Logger logger = LoggerFactory.getLogger(DataEtlService.class);

    @Resource
    BasicDataSource cmsDataSource
    @Resource
    BasicDataSource ccmsDataSource
    @Resource
    BasicDataSource dataSource

    /**
     * 获取信贷系统的发生额数据 导出到本地文本文件 然后将文本文件中的数据导入到本地数据表中
     * @param strDate10
     */
    public void extractCmsTxndetlData(String strDate10) {
        String sqlfilename = "cms_txndetl.xml"
        String txtfilename = "_PbcRateDetl.txt"
        def cmsdb = new Sql(cmsDataSource)
        obtainCmsDbDataToLocalFile(cmsdb, sqlfilename, strDate10, txtfilename)
        loadCmsTxnDetlDataFromFileToLocalDB(cmsdb, strDate10)
    }
    public void extractCCmsTxndetlData(String strDate10) {
        def sqlfilename = "ccms_txndetl.xml"
        def txtfilename = "_PbcRateDetl.txt"
        def cmsdb = new Sql(ccmsDataSource)
        obtainCmsDbDataToLocalFile(cmsdb, sqlfilename, strDate10, txtfilename)
        loadCmsTxnDetlDataFromFileToLocalDB(cmsdb, strDate10)
    }

    //public void deleteLoanBalData(String strDate10){
    //    deleteLocalLoanBalData(strDate10)
    //}
    /**
     * 获取信贷系统的余额数据 导出到本地文本文件 然后将文本文件中的数据导入到本地数据表中
     * @param strDate10
     */
    public void extractCmsBalData(String strDate10) {
        def sqlfilename = "cms_balance.xml"
        def txtfilename = "_PbcRateBal.txt"
        def cmsdb = new Sql(cmsDataSource)
        obtainCmsDbDataToLocalFile(cmsdb,sqlfilename, strDate10, txtfilename)
        loadCmsBalDataFromFileToLocalDB(cmsdb, strDate10, 'CMS')
    }
    public void extractCCmsBalData(String strDate10) {
        def sqlfilename = "ccms_balance.xml"
        def txtfilename = "_PbcRateBal.txt"
        def cmsdb = new Sql(ccmsDataSource)
        obtainCmsDbDataToLocalFile(cmsdb,sqlfilename, strDate10, txtfilename)
        loadCmsBalDataFromFileToLocalDB(cmsdb,strDate10, 'CCMS')
    }

    //按照人行规则进行数据校验
    public String verifyLoanBal(){
        return "...."
    }


    def deleteLocalLoanBalData(String strDate10){
        def rmsdb = new Sql(dataSource)
        rmsdb.withTransaction {
            rmsdb.execute("delete from pbc_rate_loanbal where reportdate = ${strDate10}")
        }
    }
    def deleteLocalLoanDetlData(String strDate10){
        def rmsdb = new Sql(dataSource)
        rmsdb.withTransaction {
            rmsdb.execute("delete from pbc_rate_loandetl where reportdate = ${strDate10}")
        }
    }

    private def obtainCmsDbDataToLocalFile(cmsdb, String sqlfilename, String strDate10, String txtfilename) {
        def sqlfile = Thread.currentThread().getContextClassLoader().getResource("").getPath()
        sqlfile = sqlfile + "haier/service/rms/pbcreport/" + sqlfilename
        def sql = new File(sqlfile).getText("UTF-8")
        Map paramMap = new HashMap()

        //String yyyymm = strDate10.substring(0, 4) + strDate10.substring(5, 7)
        //String strDate8 = yyyymm + strDate10.substring(8, 10)
        //String begindate = yyyymm + "01"

        paramMap.put("begindate", strDate10[0..7] + "01")
        paramMap.put("enddate", strDate10)
        paramMap.put("reportdate", strDate10)
        def engine = new SimpleTemplateEngine()
        def template = engine.createTemplate(sql).make(paramMap)
        sql = template.toString()
        System.out.println(sql)
        def outfile = PropertyManager.getProperty("EXP_CMS_ROOTPATH") + strDate10 + txtfilename;
        exportCmsDbData2File(cmsdb, sql, outfile)
    }

    /**
     * 将信贷系统的数据库的查询结果导出到本地文本文件中
     */
    def exportCmsDbData2File = { cmsdb, String sql, String outfile ->
        //def cmsdb = new Sql(cmsDataSource)
        def outtxtFile = new File(outfile).newPrintWriter()
        def count = 0

        cmsdb.rows(sql).each { Map row ->
            def buffer = row.collect { k, v -> v == null ? '' : "$v" }.join(',')
            count++
            //println count + '::' + row
            outtxtFile.println(buffer)
        }
        outtxtFile.flush()
        outtxtFile.close()
    }

    /**
     *
     */
    def loadCmsBalDataFromFileToLocalDB = { cmsdb, strDate10, chan ->
        //def rmsdb = new Sql(dataSource)

        def insertSql = '''
            insert into pbc_rate_loanbal
            values (
                ?,?,?,?,?,
                ?,?,?,?,?,
                ?,?,?,?,?,
                ?,?,?,?,?,
                ?,?,?,?
                )
             '''

        //def appendFileds = [0, '0', '0', '', '', '']

        def filepath = PropertyManager.getProperty("EXP_CMS_ROOTPATH") + strDate10 + "_PbcRateBal.txt";
        //def strDate8 = strDate10.replaceAll('-', '')

        transformCmsData_bal(cmsdb, strDate10, filepath, insertSql, chan);

    }
    def loadCmsTxnDetlDataFromFileToLocalDB = { cmsdb, strDate10 ->
        def insertSql = '''
            insert into pbc_rate_loandetl
            values (
                ?,?,?,?,?,
                ?,?,?,?,?,
                ?,?,?,?,?,
                ?,?,?,?,?,
                ?,?
                )
             '''
        def filepath = PropertyManager.getProperty("EXP_CMS_ROOTPATH") + strDate10 + "_PbcRateDetl.txt";
        transformCmsData_detl(cmsdb, strDate10, filepath, insertSql);
    }

    def transformCmsData_bal = { cmsdb, strDate10, filepath, insertSql, chan ->
        def rmsdb = new Sql(dataSource)

        def count = 0;
        rmsdb.withTransaction {
            println '事务开始'
            //rmsdb.executeUpdate(deleteSql, strDate10)
            new File(filepath).splitEachLine(",") { line ->
                def fields = []
                fields << strDate10
                fields += line
                def sbsactno = fields[-1]
                fields = fields[0..-3]
                fields << 0.00
                fields << sbsactno
                fields << chan

                rmsdb.executeUpdate(insertSql, fields)
                count++;
            }
            println '事务完成'
        }
    }
    def transformCmsData_detl = { cmsdb, strDate10, filepath, insertSql ->
        def rmsdb = new Sql(dataSource)

        def count = 0;
        rmsdb.withTransaction {
            println '事务开始'
            //rmsdb.executeUpdate(deleteSql, strDate10)
            new File(filepath).splitEachLine(",") { line ->
                def fields = []
                fields << strDate10
                fields += line
                fields = fields[0..-2]
                fields << 0.00
                rmsdb.executeUpdate(insertSql, fields)
                count++;
                //println count
            }
            println '事务完成'
        }
    }

    //==========SBS=========================================================================

    /**
     * 通过Ftp过去SBS文件
     * @param strDate10
     * @return
     */
    public void extractDepositTxndetData(String strDate10) {
        SbsFtp4PbcRateRpt sbsFtp4PbcRateRpt = new SbsFtp4PbcRateRpt()
        String filename = sbsFtp4PbcRateRpt.getTxnDetlFileFromSBS(strDate10)
        //loadDepositTxndetlFileToLocalDB(strDate10, filename)
    }

    public void extractDepositBalanceData(String strDate10) {
        SbsFtp4PbcRateRpt sbsFtp4PbcRateRpt = new SbsFtp4PbcRateRpt()
        String filename = sbsFtp4PbcRateRpt.getBalanceFileFromSBS(strDate10)
        loadDepositBalanceFileToLocalDB(strDate10, filename)
    }


/*
    def loadDepositTxndetlFileToLocalDB = { strDate10, filename ->
        def insertSql = '''
                        insert into pbc_rate_depositdetl
                            values (
                                ?,?,?,?,?,
                                ?,?,?,?,?,
                                ?,?,?
                                )
                        '''
        def deleteSql = "delete from pbc_rate_depositdetl where reportdate = ?"
        //def appendFileds = [0, '0', '0', '', '', '']

        transformSbsData(strDate10, filename, deleteSql, insertSql, appendFileds);
    }
*/
    def loadDepositBalanceFileToLocalDB = { strDate10, filename ->
/*
        def insertSql = '''
                            insert into pbc_rate_depositbal(pkid, bankcode, clientname, organizationcode, certtype,
                                certno, depositaccountno, depositaccounttypecode, depositagreementno, productstypecode,
                                depositstartdate, depositenddate, currencycode, balamt, rateflexibletypecode, intrate,
                                reportdate, recversion, archiveflag, deletedflag, date_create, date_update,
                                date_delete, date_archive)
                            values (
                                ?,?,?,?,?,?,?,?,?,?,
                                ?,?,?,?,?,?,?,?,?,?,
                                sysdate,?,?,?
                                )
                        '''
*/
        def insertSql = '''
                            insert into pbc_rate_depositbal
                            values (
                                ?,?,?,?,?,
                                ?,?,?,?,?,
                                ?,?,?
                                )
                        '''
        def deleteSql = "delete from pbc_rate_depositbal where reportdate = ?"
        //def appendFileds = [0, '0', '0', '', '', '']

        //def filepath = PropertyManager.getProperty("FTP_SBS_ROOTPATH") + strDate10 + "_savbal_2.010";
        def strDate8 = strDate10.replaceAll('-', '')

        transformSbsData(strDate10, filename, deleteSql, insertSql);
    }
    def transformSbsData = { String strDate10, String filepath, String deleteSql, String insertSql ->
        def rmsdb = new Sql(dataSource)

        rmsdb.withTransaction {
            rmsdb.executeUpdate(deleteSql, strDate10)
            def count = 0;
            new File(filepath).splitEachLine("\\|") { line ->
                def fields = []
                //UUID uuid = UUID.randomUUID()
                //fields << uuid.toString()

                /*
                       bankcode,
                            clientname,
                            organizationcode,
                            certtype,
                                certno,
                                depositaccountno, depositaccounttypecode, depositagreementno, productstypecode,
                                depositstartdate, depositenddate, currencycode, balamt, rateflexibletypecode, intrate,
                                reportdate, recversion, archiveflag, deletedflag, date_create, date_update,
                                date_delete, date_archive)

                 */

                fields << strDate10
                fields << line[0]  // bankcode
                fields << '0'  //客户类型
                fields << line[5]  //depositaccountno
                line[7..8].each {fields << it}         // depositagreementno  ..
                def startdate = line[9].trim()
                if (startdate != "") {
                    startdate = startdate[0..3] + "-" + startdate[4..5] + "-" + startdate[6..7]
                }
                fields << startdate         // depositstartdate

                def enddate = line[10].trim()
                if (enddate != "") {
                    if (enddate != '11111111') {
                        enddate = enddate[0..3] + "-" + enddate[4..5] + "-" + enddate[6..7]
                    }
                }
                fields << enddate          // depositenddate
                line[11..14].each {fields << it}         // depositagreementno  ..

                def sqlCols = []
                fields.each { field ->
                    if (field.trim() == '11111111') {
                        sqlCols << ''
                    } else {
                        sqlCols << field.trim()
                    }
                }
                sqlCols << 0.00
                count++
                //println count

                rmsdb.executeUpdate(insertSql, sqlCols)
            }
        }
    }

}
