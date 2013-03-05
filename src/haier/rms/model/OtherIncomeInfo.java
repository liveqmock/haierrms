package haier.rms.model;

/**
 * 其它业务收入
 * User: zhanrui
 */
public class OtherIncomeInfo {
    String productid;
    String productname;
    String biztype;
    String currtype;
    String startdate;
    String enddate;
    double bizamt;
    double rate;
    double dailyincome;
    String operdate;
    String opertime;
    String remark;
    String datadate;

    public String getDatadate() {
        return datadate;
    }
    public void setDatadate(String datadate) {
        this.datadate = datadate;
    }
    
    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public String getCurrtype() {
        return currtype;
    }

    public void setCurrtype(String currtype) {
        this.currtype = currtype;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public double getBizamt() {
        return bizamt;
    }

    public void setBizamt(double bizamt) {
        this.bizamt = bizamt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDailyincome() {
        return dailyincome;
    }

    public void setDailyincome(double dailyincome) {
        this.dailyincome = dailyincome;
    }

    public String getOperdate() {
        return operdate;
    }

    public void setOperdate(String operdate) {
        this.operdate = operdate;
    }

    public String getOpertime() {
        return opertime;
    }

    public void setOpertime(String opertime) {
        this.opertime = opertime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}