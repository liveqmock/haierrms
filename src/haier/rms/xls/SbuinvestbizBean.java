package haier.rms.xls;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-7-8
 * Time: 11:07:59
 * To change this template use File | Settings | File Templates.
 * remark：投资银行业务表
 */
public class SbuinvestbizBean {
    //序号
    String productID;
    //单位名称
    String productName;
    //业务种类
    String biztype;
    //投资金额
    Double investamt;
    //币种
    String currtype;
    //发放日期
    String startdate;
    //到期日
    String enddate;
    //贷款利率
    Double rate;
    //日利息
    Double dailyincome;
    //数据日期
    String datadate;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public Double getInvestamt() {
        return investamt;
    }

    public void setInvestamt(Double investamt) {
        this.investamt = investamt;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getDailyincome() {
        return dailyincome;
    }

    public void setDailyincome(Double dailyincome) {
        this.dailyincome = dailyincome;
    }

    public String getDatadate() {
        return datadate;
    }

    public void setDatadate(String datadate) {
        this.datadate = datadate;
    }
}
