package haier.repository.model.fundmonitor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-24
 * Time: ÏÂÎç3:20
 * To change this template use File | Settings | File Templates.
 */
public class MtActbalChartBean implements Serializable{
    private static final long serialVersionUID = -8769308806013623236L;
    private String qrytime;
    private BigDecimal Category1;
    private BigDecimal Category2;
    private BigDecimal Category3;
    private BigDecimal Category4;
    private BigDecimal Category5;
    private BigDecimal CategorySum;

    public String getQrytime() {
        return qrytime;
    }

    public void setQrytime(String qrytime) {
        this.qrytime = qrytime;
    }

    public BigDecimal getCategory1() {
        return Category1;
    }

    public void setCategory1(BigDecimal category1) {
        Category1 = category1;
    }

    public BigDecimal getCategory2() {
        return Category2;
    }

    public void setCategory2(BigDecimal category2) {
        Category2 = category2;
    }

    public BigDecimal getCategory3() {
        return Category3;
    }

    public void setCategory3(BigDecimal category3) {
        Category3 = category3;
    }

    public BigDecimal getCategory4() {
        return Category4;
    }

    public void setCategory4(BigDecimal category4) {
        Category4 = category4;
    }

    public BigDecimal getCategory5() {
        return Category5;
    }

    public void setCategory5(BigDecimal category5) {
        Category5 = category5;
    }

    public BigDecimal getCategorySum() {
        return CategorySum;
    }

    public void setCategorySum(BigDecimal categorySum) {
        CategorySum = categorySum;
    }
}
