package haier.rms.xls;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-7-8
 * Time: 10:11:03
 * To change this template use File | Settings | File Templates.
 * remark������ҵ������
 */
public class SbuintbizBean {
    //���
    String productID;
    //��λ����
    String productName;
    //������ʽ
    String loantype;
    //�Ƿ�������
    String outboundflag;
    //������
    Double loanamt;
    //����
    String currtype;
    //��������
    String startdate;
    //������
    String enddate;
    //��������
    Double rate;
    //����Ϣ
    Double dailyincome;
    //��������
    String datadate;
    public void setProductID(String productID){
        this.productID = productID;
    }
    public String getProductID(){
        return productID;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public String getProductName(){
        return productName;
    }
    public void setLoantype(String loantype){
        this.loantype = loantype;
    }
    public String getLoantype(){
        return loantype;
    }
    public void setOutboundflag(String outboundflag){
        this.outboundflag = outboundflag;
    }
    public String getOutboundflag(){
        return outboundflag;
    }
    public void setLoanamt(Double loanamt){
        this.loanamt = loanamt;
    }
    public Double getLoanamt(){
        return loanamt;
    }
    public void setCurrtype(String currtype){
        this.currtype = currtype;
    }
    public String getCurrtype(){
        return currtype;
    }
    public void setStartdate(String startdate){
        this.startdate = startdate;
    }
    public String getStartdate(){
        return startdate;
    }
    public void setEnddate(String enddate){
        this.enddate = enddate;
    }
    public String getEnddate(){
        return enddate;
    }
    public void setRate(Double rate){
        this.rate = rate;
    }
    public Double getRate(){
        return rate;
    }
    public void setDailyincome(Double dailyincome){
        this.dailyincome = dailyincome;
    }
    public Double getDailyincome(){
        return dailyincome;
    }
    public void setDatadate(String datadate){
        this.datadate = datadate;
    }
    public String getDatadate(){
        return datadate;
    }
}
