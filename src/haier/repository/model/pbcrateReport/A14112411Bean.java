package haier.repository.model.pbcrateReport;

import java.math.BigDecimal;

public class A14112411Bean {
    private String productCode;
    private String productType;   //1:存款 2:贷款
    private BigDecimal cm_1411;   //本月余额
    private BigDecimal cm_2411;
    private BigDecimal lm_1411;   //上月余额
    private BigDecimal lm_2411;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCm_1411() {
        return cm_1411;
    }

    public void setCm_1411(BigDecimal cm_1411) {
        this.cm_1411 = cm_1411;
    }

    public BigDecimal getCm_2411() {
        return cm_2411;
    }

    public void setCm_2411(BigDecimal cm_2411) {
        this.cm_2411 = cm_2411;
    }

    public BigDecimal getLm_1411() {
        return lm_1411;
    }

    public void setLm_1411(BigDecimal lm_1411) {
        this.lm_1411 = lm_1411;
    }

    public BigDecimal getLm_2411() {
        return lm_2411;
    }

    public void setLm_2411(BigDecimal lm_2411) {
        this.lm_2411 = lm_2411;
    }
}
