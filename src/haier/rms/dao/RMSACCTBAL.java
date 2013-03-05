package haier.rms.dao;

import java.util.*;

import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;

public class RMSACCTBAL extends AbstractBasicBean implements Cloneable {
    public static List find(String sSqlWhere) {
        return new RMSACCTBAL().findByWhere(sSqlWhere);
    }

    public static List findAndLock(String sSqlWhere) {
        return new RMSACCTBAL().findAndLockByWhere(sSqlWhere);
    }

    public static RMSACCTBAL findFirst(String sSqlWhere) {
        return (RMSACCTBAL) new RMSACCTBAL().findFirstByWhere(sSqlWhere);
    }

    public static RMSACCTBAL findFirstAndLock(String sSqlWhere) {
        return (RMSACCTBAL) new RMSACCTBAL().findFirstAndLockByWhere(sSqlWhere);
    }

    public static RecordSet findRecordSet(String sSqlWhere) {
        return new RMSACCTBAL().findRecordSetByWhere(sSqlWhere);
    }

    public static List find(String sSqlWhere, boolean isAutoRelease) {
        RMSACCTBAL bean = new RMSACCTBAL();
        bean.setAutoRelease(isAutoRelease);
        return bean.findByWhere(sSqlWhere);
    }

    public static List findAndLock(String sSqlWhere, boolean isAutoRelease) {
        RMSACCTBAL bean = new RMSACCTBAL();
        bean.setAutoRelease(isAutoRelease);
        return bean.findAndLockByWhere(sSqlWhere);
    }

    public static RMSACCTBAL findFirst(String sSqlWhere, boolean isAutoRelease) {
        RMSACCTBAL bean = new RMSACCTBAL();
        bean.setAutoRelease(isAutoRelease);
        return (RMSACCTBAL) bean.findFirstByWhere(sSqlWhere);
    }

    public static RMSACCTBAL findFirstAndLock(String sSqlWhere, boolean isAutoRelease) {
        RMSACCTBAL bean = new RMSACCTBAL();
        bean.setAutoRelease(isAutoRelease);
        return (RMSACCTBAL) bean.findFirstAndLockByWhere(sSqlWhere);
    }

    public static RecordSet findRecordSet(String sSqlWhere, boolean isAutoRelease) {
        RMSACCTBAL bean = new RMSACCTBAL();
        bean.setAutoRelease(isAutoRelease);
        return bean.findRecordSetByWhere(sSqlWhere);
    }

    public static List findByRow(String sSqlWhere, int row) {
        return new RMSACCTBAL().findByWhereByRow(sSqlWhere, row);
    }

    String cusidt;
    String apcode;
    double lasbal;
    String curcde;
    String stockcd;
    String txdate;
    public static final String TABLENAME = "rms_acctbal";
    private String operate_mode = "add";
    public ChangeFileds cf = new ChangeFileds();

    public String getTableName() {
        return TABLENAME;
    }

    public void addObject(List list, RecordSet rs) {
        RMSACCTBAL abb = new RMSACCTBAL();
        abb.cusidt = rs.getString("cusidt");
        abb.setKeyValue("CUSIDT", "" + abb.getCusidt());
        abb.apcode = rs.getString("apcode");
        abb.setKeyValue("APCODE", "" + abb.getApcode());
        abb.lasbal = rs.getDouble("lasbal");
        abb.setKeyValue("LASBAL", "" + abb.getLasbal());
        abb.curcde = rs.getString("curcde");
        abb.setKeyValue("CURCDE", "" + abb.getCurcde());
        abb.stockcd = rs.getString("stockcd");
        abb.setKeyValue("STOCKCD", "" + abb.getStockcd());
        abb.txdate = rs.getString("txdate");
        abb.setKeyValue("TXDATE", "" + abb.getTxdate());
        list.add(abb);
        abb.operate_mode = "edit";
    }

    public String getCusidt() {
        if (this.cusidt == null) return "";
        return this.cusidt;
    }

    public String getApcode() {
        if (this.apcode == null) return "";
        return this.apcode;
    }

    public double getLasbal() {
        return this.lasbal;
    }

    public String getCurcde() {
        if (this.curcde == null) return "";
        return this.curcde;
    }

    public String getStockcd() {
        if (this.stockcd == null) return "";
        return this.stockcd;
    }

    public String getTxdate() {
        if (this.txdate == null) return "";
        return this.txdate;
    }

    public void setCusidt(String cusidt) {
        sqlMaker.setField("cusidt", cusidt, Field.TEXT);
        if (this.operate_mode.equals("edit")) {
            if (!this.getCusidt().equals(cusidt)) cf.add("cusidt", this.cusidt, cusidt);
        }
        this.cusidt = cusidt;
    }

    public void setApcode(String apcode) {
        sqlMaker.setField("apcode", apcode, Field.TEXT);
        if (this.operate_mode.equals("edit")) {
            if (!this.getApcode().equals(apcode)) cf.add("apcode", this.apcode, apcode);
        }
        this.apcode = apcode;
    }

    public void setLasbal(double lasbal) {
        sqlMaker.setField("lasbal", "" + lasbal, Field.NUMBER);
        if (this.operate_mode.equals("edit")) {
            if (this.getLasbal() != lasbal) cf.add("lasbal", this.lasbal + "", lasbal + "");
        }
        this.lasbal = lasbal;
    }

    public void setCurcde(String curcde) {
        sqlMaker.setField("curcde", curcde, Field.TEXT);
        if (this.operate_mode.equals("edit")) {
            if (!this.getCurcde().equals(curcde)) cf.add("curcde", this.curcde, curcde);
        }
        this.curcde = curcde;
    }

    public void setStockcd(String stockcd) {
        sqlMaker.setField("stockcd", stockcd, Field.TEXT);
        if (this.operate_mode.equals("edit")) {
            if (!this.getStockcd().equals(stockcd)) cf.add("stockcd", this.stockcd, stockcd);
        }
        this.stockcd = stockcd;
    }

    public void setTxdate(String txdate) {
        sqlMaker.setField("txdate", txdate, Field.TEXT);
        if (this.operate_mode.equals("edit")) {
            if (!this.getTxdate().equals(txdate)) cf.add("txdate", this.txdate, txdate);
        }
        this.txdate = txdate;
    }

    public void init(int i, ActionRequest actionRequest) throws Exception {
        if (actionRequest.getFieldValue(i, "cusidt") != null) {
            this.setCusidt(actionRequest.getFieldValue(i, "cusidt"));
        }
        if (actionRequest.getFieldValue(i, "apcode") != null) {
            this.setApcode(actionRequest.getFieldValue(i, "apcode"));
        }
        if (actionRequest.getFieldValue(i, "lasbal") != null && actionRequest.getFieldValue(i, "lasbal").trim().length() > 0) {
            this.setLasbal(Double.parseDouble(actionRequest.getFieldValue(i, "lasbal")));
        }
        if (actionRequest.getFieldValue(i, "curcde") != null) {
            this.setCurcde(actionRequest.getFieldValue(i, "curcde"));
        }
        if (actionRequest.getFieldValue(i, "stockcd") != null) {
            this.setStockcd(actionRequest.getFieldValue(i, "stockcd"));
        }
        if (actionRequest.getFieldValue(i, "txdate") != null) {
            this.setTxdate(actionRequest.getFieldValue(i, "txdate"));
        }
    }

    public void init(ActionRequest actionRequest) throws Exception {
        this.init(0, actionRequest);
    }

    public void initAll(int i, ActionRequest actionRequest) throws Exception {
        this.init(i, actionRequest);
    }

    public void initAll(ActionRequest actionRequest) throws Exception {
        this.initAll(0, actionRequest);
    }

    public Object clone() throws CloneNotSupportedException {
        RMSACCTBAL obj = (RMSACCTBAL) super.clone();
        obj.setCusidt(obj.cusidt);
        obj.setApcode(obj.apcode);
        obj.setLasbal(obj.lasbal);
        obj.setCurcde(obj.curcde);
        obj.setStockcd(obj.stockcd);
        obj.setTxdate(obj.txdate);
        return obj;
    }
}