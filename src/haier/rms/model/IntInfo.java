package haier.rms.model;

/**
 * 国际业务收入
 * User: zhanrui
 */
public class IntInfo {
    String productid;
    String productname;
    String loantype;
    String currtype;
    String outboundflag;
    String startdate;
    String enddate;
    double rate;
    double dailyincome;
    double loanamt;
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

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getCurrtype() {
        return currtype;
    }

    public void setCurrtype(String currtype) {
        this.currtype = currtype;
    }

    public String getOutboundflag() {
        return outboundflag;
    }

    public void setOutboundflag(String outboundflag) {
        this.outboundflag = outboundflag;
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

    public double getLoanamt() {
        return loanamt;
    }

    public void setLoanamt(double loanamt) {
        this.loanamt = loanamt;
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