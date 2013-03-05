package haier.rms.model;

/**
 * 投资银行业务收入
 * User: zhanrui
 * Date: 2010-4-9
 * Time: 9:43:53
 * To change this template use File | Settings | File Templates.
 */
public class InvestInfo {
    String productid;
    String productname;
    String biztype;
    String currtype;
    String startdate;
    String enddate;
    double investamt;
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

    public double getInvestamt() {
        return investamt;
    }

    public void setInvestamt(double investamt) {
        this.investamt = investamt;
    }
}