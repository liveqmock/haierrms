package com.ccb.personinfo;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-7-1
 * Time: 18:45:38
 * To change this template use File | Settings | File Templates.
 */

import com.ccb.dao.LSPERSONALINFO;
import com.ccb.dao.LSTASKINFO;
import com.ccb.util.SeqUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.RecordSet;
import pub.platform.form.control.Action;
import pub.platform.utils.BusinessDate;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-11
 * Time: 8:23:38
 * To change this template use File | Settings | File Templates.
 */
public class PerInfoEidtAction  extends Action {
    // 日志对象
    private static final Log logger = LogFactory.getLog(PerInfoEidtAction.class);
    // 贷款信息对象
    LSPERSONALINFO perInfo = null;
   //日志信息对象
    LSTASKINFO task = null;

    // 流水记录表
    /*
    *添加新纪录
    * */
    public int add() {
        perInfo = new LSPERSONALINFO();
        for (int i= 0;i < this.req.getRecorderCount();i++){
            try{
                // 检测身份证号有无重复
                RecordSet rec = dc.executeQuery("select 1 from ls_personalinfo t where t.perid = '"
                        + req.getFieldValue(i, "perid").trim() + "'");
                while (rec.next()) {
                    this.res.setType(0);
                    this.res.setResult(false);
                    this.res.setMessage(PropertyManager.getProperty("309") + "\r\n身份证号码："
                            + req.getFieldValue(i, "perid").trim());
                    return -1;
                }
                if (rec != null) {
                    rec.close();
                }
                 // 初始化数据bean
                perInfo.initAll(i, req);
                //生成内部序列号
                String nbxlh = SeqUtil.getNbxh();
                perInfo.setRecinsequence(nbxlh);
                // 版本号
                perInfo.setRecversion(0);
                //操作者code
//                perInfo.setCreatecode(1);
                //操作时间
                perInfo.setCreatedate(BusinessDate.getTodaytime());
                 if (perInfo.insert() < 0) {
                    this.res.setType(0);
                    this.res.setResult(false);
                    this.res.setMessage(PropertyManager.getProperty("300"));
                    return -1;
                }
                //TODO 流水日志表记入
                task = new LSTASKINFO(); 
                task.setTaskid(SeqUtil.getTaskSeq());
                task.setTablename("LSPERSONALINFO");
                task.setTablepk(nbxlh);
                task.setTasktype("add");
                task.setTasktime(BusinessDate.getTodaytime());
                task.setOperid(this.getOperator().getOperid());
                task.insert();
//                if (task.insert() < 0) {
//                    this.res.setType(0);
//                    this.res.setResult(false);
//                    this.res.setMessage(PropertyManager.getProperty("300"));
//                    return -1;
//                }

            }catch (Exception ex1) {
                logger.error("个人信息添加记录出错：" + ex1.getMessage());
                ex1.printStackTrace();
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage(PropertyManager.getProperty("300"));
                return -1;
            }
        }
       this.res.setType(0);
        this.res.setResult(true);
        this.res.setMessage(PropertyManager.getProperty("200"));
        return 0;
    }

    /*
    * 修改记录
    * */
    public int edit(){
        perInfo = new LSPERSONALINFO();
        for (int i= 0;i < this.req.getRecorderCount();i++){
            try{
                //修改
                // 初始化数据bean
                perInfo.initAll(i, req);
                //版本号
                int iBeforeVersion = 0;    //更新前的版本号
                if (req.getFieldValue(i, "recversion") != null && !req.getFieldValue(i, "recversion").equals("")) {
                    iBeforeVersion = Integer.parseInt(req.getFieldValue(i, "recversion"));
                }
                int iAfterVersion = 0;
                RecordSet rs = dc.executeQuery("select recversion from ls_personalinfo where recinsequence='"
                        + req.getFieldValue(i, "recinsequence") + "'");
                 while (rs.next()) {
                    iAfterVersion = rs.getInt("recVersion");
                    if (iBeforeVersion != iAfterVersion) {
                        this.res.setType(0);
                        this.res.setResult(false);
                        this.res.setMessage(PropertyManager.getProperty("301"));
                        return -1;
                    } else {
                        // 版本号加1
                        iBeforeVersion = iBeforeVersion + 1;
                        perInfo.setRecversion(iBeforeVersion);
                    }
                }
                if (rs != null) {
                    rs.close();
                }
                //createcode
                perInfo.setCreatecode(req.getFieldIntValue(i,"createcode"));
                //createdate
                perInfo.setCreatedate(BusinessDate.getTodaytime());
                if (perInfo.updateByWhere(" where recinsequence='" + req.getFieldValue(i, "recinsequence") + "'") < 0) {
                    this.res.setType(0);
                    this.res.setResult(false);
                    this.res.setMessage(PropertyManager.getProperty("300"));
                    return -1;
                }
                task = new LSTASKINFO();
                task.setTaskid(SeqUtil.getTaskSeq());
                task.setTablename("LSPERSONALINFO");
                task.setTablepk(req.getFieldValue(i, "recinsequence"));
                task.setTasktype("edit");
                task.setTasktime(BusinessDate.getTodaytime());
                task.setOperid(this.getOperator().getOperid());
                if (task.insert() < 0) {
                    this.res.setType(0);
                    this.res.setResult(false);
                    this.res.setMessage(PropertyManager.getProperty("300"));
                    return -1;
                }
            }catch (Exception ex1) {
                logger.error("个人信息修改记录出错：" + ex1.getMessage());
                ex1.printStackTrace();
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage(PropertyManager.getProperty("300"));
                return -1;
            }
        }

        this.res.setType(0);
        this.res.setResult(true);
        this.res.setMessage(PropertyManager.getProperty("200"));
        return 0;
    }

    /*
    * 删除记录
    * */
    public int delete(){
        perInfo = new LSPERSONALINFO();
        try{
            for (int i = 0 ; i < req.getRecorderCount() ; i++){
                // 初始化数据bean
                perInfo.initAll(i, req);
                if (perInfo.deleteByWhere(" where recinsequence='" + req.getFieldValue(i,"recinsequence") + "'") < 0){
                    this.res.setType(0);
                    this.res.setResult(false);
                    this.res.setMessage(PropertyManager.getProperty("300"));
                    return -1;
                    //流水日志的更新。。。
                }
                task = new LSTASKINFO();
                task.setTaskid(SeqUtil.getTaskSeq());
                task.setTablename("LSPERSONALINFO");
                task.setTablepk(req.getFieldValue(i, "recinsequence"));
                task.setTasktype("delete");
                task.setTasktime(BusinessDate.getTodaytime());
                task.setOperid(this.getOperator().getOperid());
                if (task.insert() < 0) {
                    this.res.setType(0);
                    this.res.setResult(false);
                    this.res.setMessage(PropertyManager.getProperty("300"));
                    return -1;
                }
            }
        } catch (Exception ex){
            logger.error("个人信息删除记录出错：" + ex.getMessage());
            ex.printStackTrace();
            this.res.setType(0);
            this.res.setResult(false);
            this.res.setMessage(PropertyManager.getProperty("300"));
        }
        this.res.setType(0);
        return 0;
    }
}
