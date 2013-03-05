package haier.rms.model;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-18
 * Time: 22:52:55
 * To change this template use File | Settings | File Templates.
 */
public class CubeFactActrInfo {
    String dim_cust_no;
    String dim_buss_item;
    String dim_currency;
    String dim_oprt_dept;
    String dim_cust_mngr;
    String dim_buss_date;
    String dim_data_date;
    double fact_value;

    public String getDim_cust_no() {
        return dim_cust_no;
    }

    public void setDim_cust_no(String dim_cust_no) {
        this.dim_cust_no = dim_cust_no;
    }

    public String getDim_buss_item() {
        return dim_buss_item;
    }

    public void setDim_buss_item(String dim_buss_item) {
        this.dim_buss_item = dim_buss_item;
    }

    public String getDim_currency() {
        return dim_currency;
    }

    public void setDim_currency(String dim_currency) {
        this.dim_currency = dim_currency;
    }

    public String getDim_oprt_dept() {
        return dim_oprt_dept;
    }

    public void setDim_oprt_dept(String dim_oprt_dept) {
        this.dim_oprt_dept = dim_oprt_dept;
    }

    public String getDim_cust_mngr() {
        return dim_cust_mngr;
    }

    public void setDim_cust_mngr(String dim_cust_mngr) {
        this.dim_cust_mngr = dim_cust_mngr;
    }

    public String getDim_buss_date() {
        return dim_buss_date;
    }

    public void setDim_buss_date(String dim_buss_date) {
        this.dim_buss_date = dim_buss_date;
    }

    public String getDim_data_date() {
        return dim_data_date;
    }

    public void setDim_data_date(String dim_data_date) {
        this.dim_data_date = dim_data_date;
    }

    public double getFact_value() {
        return fact_value;
    }

    public void setFact_value(double fact_value) {
        this.fact_value = fact_value;
    }
}
