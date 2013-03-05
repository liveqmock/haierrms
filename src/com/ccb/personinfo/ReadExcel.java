package com.ccb.personinfo;


import com.ccb.dao.LSPERSONALINFO;
import com.ccb.dao.LSTASKINFO;
import com.ccb.util.SeqUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;
import pub.platform.db.RecordSet;
import pub.platform.form.control.Action;
import pub.platform.utils.BusinessDate;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-23
 * Time: 16:53:25
 * To change this template use File | Settings | File Templates.
 */
public class ReadExcel extends Action {
    private static final Log logger = LogFactory.getLog(ReadExcel.class);
    public String getData(String path) {
        String perName = "";         //姓名
        String perID = "";           //身份证
        String deptCD = "";          //预算单位
        int version = 0;             //版本号
        int createCD = 0;            //登陆者
        String superDeptCD = "";     //一级预算单位
        String cfPerIDs = "";   //重复身份证ID
        try {
            LSPERSONALINFO perInfo = new LSPERSONALINFO();
            InputStream input = new FileInputStream(path);
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            cfPerIDs = getErrorData(sheet);
            if (!cfPerIDs.equals(""))
                return cfPerIDs;
            else {
                int rowLen = sheet.getLastRowNum();
                HSSFCell cell0 = null;              //姓名
                HSSFCell cell1 = null;              //身份证ID
                HSSFCell cell2 = null;              //预算单位
                HSSFCell cell3 = null;              //版本
                HSSFCell cell4 = null;              //创建者code
                HSSFCell cell5 = null;              //一级预算单位
                //数据插入
                for (int i = 1; i <= rowLen; i++) {
                    //姓名
                    cell0 = sheet.getRow(i).getCell(0);
                    switch (cell0.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            perName = cell0.getStringCellValue().trim();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            perName = Integer.toString((int) (cell0.getNumericCellValue()));
                            break;
                        default:
                            perName = null;
                            break;
                    }
                    //身份证ID
                    cell1 = sheet.getRow(i).getCell(1);
                    switch (cell1.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            perID = cell1.getStringCellValue().trim();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            perID = String.valueOf((int) (cell1.getNumericCellValue()));
                            break;
                        default:
                            perID = "";
                            break;
                    }
                    //预算单位
                    cell2 = sheet.getRow(i).getCell(2);
                    deptCD = cell2.getStringCellValue().trim();
                    //创建者CD
                    cell3 = sheet.getRow(i).getCell(3);
                    createCD = (int) (cell3.getNumericCellValue());
                    //一级预算单位
                    cell4 = sheet.getRow(i).getCell(4);
                    switch (cell4.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            superDeptCD = cell4.getStringCellValue().trim();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            superDeptCD = String.valueOf((int) (cell4.getNumericCellValue()));
                            break;
                        default:
                            superDeptCD = "";
                            break;
                    }
                    //数据载入表对象
                    perInfo.setPername(perName);
                    perInfo.setPerid(perID);
                    perInfo.setDeptcode(deptCD);
                    perInfo.setRecversion(version);
                    perInfo.setCreatecode(createCD);
                    perInfo.setSuperdeptcode(superDeptCD);
                    //内部序列号取得
                    String nbxlh = SeqUtil.getNbxh();
                    perInfo.setRecinsequence(nbxlh);
                    //当前时间设定为创建时间
                    perInfo.setCreatedate(BusinessDate.getTodaytime());
                    if (perInfo.insert() < 0)
                        return "-1";
                }
                LSTASKINFO task = new LSTASKINFO();
                task.setTaskid(SeqUtil.getTaskSeq());
                task.setTablename("LSPERSONALINFO");
//                task.setTablepk(req.getFieldValue(i, "recinsequence"));
                task.setTasktype("add");
                task.setTasktime(BusinessDate.getTodaytime());
//                task.setOperid(this.getOperator().getOperid());
                task.setRemarks("数据批量导入！");
                if (task.insert() < 0) {
                    return "-1";
                }
            }
        } catch (Exception ex) {
            logger.error("Excel数据批量导入错误：" + ex.getStackTrace().toString());
            ex.printStackTrace();
            return "-1";
        }
        return "0";
    }

    /*错误数据验证
    * 验证1：身份证重复
    * 验证2；身份证位数校验??
    * */

    public String getErrorData(HSSFSheet sheet) {
        String perIDLst = "";    //重复身份证号
        String perID = "";       //身份证ID
        int rowLen = sheet.getLastRowNum();
        HSSFCell cell = null;
        //验证重复身份证ID，有；取出；
        for (int i = 1; i <= rowLen; i++) {
            cell = sheet.getRow(i).getCell(1);
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    perID = cell.getStringCellValue().trim();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    perID = String.valueOf((int) (cell.getNumericCellValue()));
                    break;
                default:
                    perID = "";
                    break;
            }
            DatabaseConnection cn = ConnectionManager.getInstance().get();
            String sqlStr = "select 1 from ls_personalinfo t where t.perid='" + perID + "'";
            RecordSet rec = null;
            rec = cn.executeQuery(sqlStr);
            if (rec.getRecordCount() != 0) {
                if (perIDLst.equals(""))
                    perIDLst = perID;
                else
                    perIDLst = perIDLst + "," + perID;
            }
            if (rec != null) {
                rec.close();
            }
        }
        return perIDLst;
    }
}
