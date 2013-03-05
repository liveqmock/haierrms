package com.ccb.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.RecordSet;
import pub.platform.form.control.Action;

public class TrackMiscAction extends Action {
    private static final Log logger = LogFactory.getLog(TrackMiscAction.class);

    /**
     * <p/>
     * 初始化查询
     * 1、自本地消费信息表中查询初始状态的数据记录数
     * 2、查询异常（发送超时和发现失败）的数据记录数
     * 3、查询还款信息表中的尚未还款记录数
     *
     * @return
     */
    public int initQuery() {
        int count = 0;
        StringBuffer show_content = new StringBuffer();
        try {
            // 查询初始状态消费信息记录数
            count = queryStatusCnt("10");
            show_content.append(String.valueOf(count));
            // 查询发送失败和发送超时的消费信息记录数
            count = queryStatusArrCnt(new String[]{"11", "12"});
            show_content.append("_").append(String.valueOf(count));
            // 查询还款信息表中的尚未还款记录数
            count = queryNotPaybackCnt();
            show_content.append("_").append(String.valueOf(count));
            // 查询未发送卡基本信息记录数
            count = queryCardBaseInfos();
            show_content.append("_").append(String.valueOf(count));
            // 查询注销卡基本信息记录数
            count = queryCardBaseNullInfos();
            show_content.append("_").append(String.valueOf(count));

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage(PropertyManager.getProperty("300"));
            return -1;
        }

        //返回初始状态的数据记录数_异常（发送超时和发现失败）的数据记录数_尚未还款记录数
        this.res.setFieldName("importData");
        this.res.setFieldType("text");
        this.res.setEnumType("0");
        this.res.setFieldValue(new String(show_content));
        this.res.setType(4);
        this.res.setResult(true);
        return 0;
    }

    // 查询公务卡未发送记录
    private int queryCardBaseInfos(){
        int count = 0;
        String selectsql = "select count(*) as sendCnt from ls_cardbaseinfo where status = '1' and sentflag = '0'";
        RecordSet rs = null;
        rs = dc.executeQuery(selectsql);
        while (rs.next()) {
            count = rs.getInt("sendCnt");
        }
        return count;
    }
     // 查询公务卡未发送记录
    private int queryCardBaseNullInfos(){
        int count = 0;
        String selectsql = "select count(*) as sendCnt from ls_cardbaseinfo where status = '128' and sentflag = '0'";
        RecordSet rs = null;
        rs = dc.executeQuery(selectsql);
        while (rs.next()) {
            count = rs.getInt("sendCnt");
        }
        return count;
    }
    // 查询所有某一状态的消费数据数

    private int queryStatusCnt(String status) {
        return queryStatusArrCnt(new String[]{status});
    }

    // 查询所有某几状态的消费数据数

    private int queryStatusArrCnt(String[] status) {

        int count = 0;
        StringBuffer wheresqlbfr = new StringBuffer(" where status in ('");
        int statusCount = status.length;
        for (int i = 0; i < statusCount; i++) {
            wheresqlbfr.append(status[i]);
            if (i + 1 < statusCount) {
                wheresqlbfr.append("','");
            }
        }
        wheresqlbfr.append("')");
        String wheresql = new String(wheresqlbfr);
        String selectsql = "select count(*) as statusCount from ls_consumeinfo " + wheresql;
        RecordSet rs = null;
        rs = dc.executeQuery(selectsql);
        while (rs.next()) {
            count = rs.getInt("statusCount");
        }
        return count;
    }
    // 查询还款信息表中的尚未还款记录数

    private int queryNotPaybackCnt() {

        int count = 0;
        String selectsql = "select count(*) as notCount from ls_paybackinfo where status = '00'";
        RecordSet rs = null;
        rs = dc.executeQuery(selectsql);
        while (rs.next()) {
            count = rs.getInt("notCount");
        }
        return count;
    }
}
