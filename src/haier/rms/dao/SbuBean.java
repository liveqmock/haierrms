package haier.rms.dao;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-3-19
 * Time: 13:53:12
 * To change this template use File | Settings | File Templates.
 */
public class SbuBean {

    //��Ŀ
    String item;

    //���Ԥ��
    BigDecimal yearbudget;

    //�����ۼƣ�T�ܣ�-����Ԥ��
    BigDecimal y_budget;
    //�����ۼƣ�T�ܣ�-����ʵ��
    BigDecimal y_actual;
    //�����ۼƣ�T�ܣ�-Ԥ����ʵ�ʲ���
    BigDecimal y_b_a_diff;

    //���ܣ�T�ܣ�-��Ԥ��
    BigDecimal t_budget;
    //���ܣ�T�ܣ�-��ʵ��
    BigDecimal t_actual;
    //���ܣ�T�ܣ�--Ԥ����ʵ�ʲ���
    BigDecimal t_BADiff;

    //���ܣ�T��+1��-��Ԥ��
    BigDecimal t1_budget;
    //���ܣ�T��+1��-��Ԥ��
    BigDecimal t1_forecast;
    //���ܣ�T��+1��--Ԥ����Ԥ�����
    BigDecimal t1_BFDiff;


    //���ܣ�T��+2��-��Ԥ��
    BigDecimal t2_budget;
    //���ܣ�T��+2��-��Ԥ��
    BigDecimal t2_forecast;
    //���ܣ�T��+2��--Ԥ����Ԥ�����
    BigDecimal t2_BFDiff;

    //���ܣ�T��+3��-��Ԥ��
    BigDecimal t3_budget;
    //���ܣ�T��+3��-��Ԥ��
    BigDecimal t3_forecast;
    //���ܣ�T��+3��--Ԥ����Ԥ�����
    BigDecimal t3_BFDiff;

    //���ܣ�T��+4��-��Ԥ��
    BigDecimal t4_budget;
    //���ܣ�T��+4��-��Ԥ��
    BigDecimal t4_forecast;
    //���ܣ�T��+4��--Ԥ����Ԥ�����
    BigDecimal t4_BFDiff;

    //���ܣ�T��+5��-��Ԥ��
    BigDecimal t5_budget;
    //���ܣ�T��+5��-��Ԥ��
    BigDecimal t5_forecast;
    //���ܣ�T��+5��--Ԥ����Ԥ�����
    BigDecimal t5_BFDiff;

    //���ܣ�T��+6��-��Ԥ��
    BigDecimal t6_budget;
    //���ܣ�T��+6��-��Ԥ��
    BigDecimal t6_forecast;
    //���ܣ�T��+6��--Ԥ����Ԥ�����
    BigDecimal t6_BFDiff;


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getYearbudget() {
        return yearbudget;
    }

    public void setYearbudget(BigDecimal yearbudget) {
        this.yearbudget = yearbudget;
    }

    public BigDecimal getY_budget() {
        return y_budget;
    }

    public void setY_budget(BigDecimal y_budget) {
        this.y_budget = y_budget;
    }

    public BigDecimal getY_actual() {
        return y_actual;
    }

    public void setY_actual(BigDecimal y_actual) {
        this.y_actual = y_actual;
    }

    public BigDecimal getY_b_a_diff() {
        return y_b_a_diff;
    }

    public void setY_b_a_diff(BigDecimal y_b_a_diff) {
        this.y_b_a_diff = y_b_a_diff;
    }

    public BigDecimal getT_budget() {
        return t_budget;
    }

    public void setT_budget(BigDecimal t_budget) {
        this.t_budget = t_budget;
    }

    public BigDecimal getT_actual() {
        return t_actual;
    }

    public void setT_actual(BigDecimal t_actual) {
        this.t_actual = t_actual;
    }

    public BigDecimal getT_BADiff() {
        return t_BADiff;
    }

    public void setT_BADiff(BigDecimal t_BADiff) {
        this.t_BADiff = t_BADiff;
    }

    public BigDecimal getT1_budget() {
        return t1_budget;
    }

    public void setT1_budget(BigDecimal t1_budget) {
        this.t1_budget = t1_budget;
    }

    public BigDecimal getT1_forecast() {
        return t1_forecast;
    }

    public void setT1_forecast(BigDecimal t1_forecast) {
        this.t1_forecast = t1_forecast;
    }

    public BigDecimal getT1_BFDiff() {
        return t1_BFDiff;
    }

    public void setT1_BFDiff(BigDecimal t1_BFDiff) {
        this.t1_BFDiff = t1_BFDiff;
    }

    public BigDecimal getT2_budget() {
        return t2_budget;
    }

    public void setT2_budget(BigDecimal t2_budget) {
        this.t2_budget = t2_budget;
    }

    public BigDecimal getT2_forecast() {
        return t2_forecast;
    }

    public void setT2_forecast(BigDecimal t2_forecast) {
        this.t2_forecast = t2_forecast;
    }

    public BigDecimal getT2_BFDiff() {
        return t2_BFDiff;
    }

    public void setT2_BFDiff(BigDecimal t2_BFDiff) {
        this.t2_BFDiff = t2_BFDiff;
    }

    public BigDecimal getT3_budget() {
        return t3_budget;
    }

    public void setT3_budget(BigDecimal t3_budget) {
        this.t3_budget = t3_budget;
    }

    public BigDecimal getT3_forecast() {
        return t3_forecast;
    }

    public void setT3_forecast(BigDecimal t3_forecast) {
        this.t3_forecast = t3_forecast;
    }

    public BigDecimal getT3_BFDiff() {
        return t3_BFDiff;
    }

    public void setT3_BFDiff(BigDecimal t3_BFDiff) {
        this.t3_BFDiff = t3_BFDiff;
    }

    public BigDecimal getT4_budget() {
        return t4_budget;
    }

    public void setT4_budget(BigDecimal t4_budget) {
        this.t4_budget = t4_budget;
    }

    public BigDecimal getT4_forecast() {
        return t4_forecast;
    }

    public void setT4_forecast(BigDecimal t4_forecast) {
        this.t4_forecast = t4_forecast;
    }

    public BigDecimal getT4_BFDiff() {
        return t4_BFDiff;
    }

    public void setT4_BFDiff(BigDecimal t4_BFDiff) {
        this.t4_BFDiff = t4_BFDiff;
    }

    public BigDecimal getT5_budget() {
        return t5_budget;
    }

    public void setT5_budget(BigDecimal t5_budget) {
        this.t5_budget = t5_budget;
    }

    public BigDecimal getT5_forecast() {
        return t5_forecast;
    }

    public void setT5_forecast(BigDecimal t5_forecast) {
        this.t5_forecast = t5_forecast;
    }

    public BigDecimal getT5_BFDiff() {
        return t5_BFDiff;
    }

    public void setT5_BFDiff(BigDecimal t5_BFDiff) {
        this.t5_BFDiff = t5_BFDiff;
    }

    public BigDecimal getT6_budget() {
        return t6_budget;
    }

    public void setT6_budget(BigDecimal t6_budget) {
        this.t6_budget = t6_budget;
    }

    public BigDecimal getT6_forecast() {
        return t6_forecast;
    }

    public void setT6_forecast(BigDecimal t6_forecast) {
        this.t6_forecast = t6_forecast;
    }

    public BigDecimal getT6_BFDiff() {
        return t6_BFDiff;
    }

    public void setT6_BFDiff(BigDecimal t6_BFDiff) {
        this.t6_BFDiff = t6_BFDiff;
    }
}
