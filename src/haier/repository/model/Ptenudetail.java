package haier.repository.model;

import java.math.BigDecimal;

public class Ptenudetail extends PtenudetailKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PTENUDETAIL.ENUITEMLABEL
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    private String enuitemlabel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PTENUDETAIL.ENUITEMDESC
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    private String enuitemdesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PTENUDETAIL.DISPNO
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    private BigDecimal dispno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PTENUDETAIL.ENUITEMEXPAND
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    private String enuitemexpand;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PTENUDETAIL.ENUITEMLABEL
     *
     * @return the value of PTENUDETAIL.ENUITEMLABEL
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public String getEnuitemlabel() {
        return enuitemlabel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PTENUDETAIL.ENUITEMLABEL
     *
     * @param enuitemlabel the value for PTENUDETAIL.ENUITEMLABEL
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public void setEnuitemlabel(String enuitemlabel) {
        this.enuitemlabel = enuitemlabel == null ? null : enuitemlabel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PTENUDETAIL.ENUITEMDESC
     *
     * @return the value of PTENUDETAIL.ENUITEMDESC
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public String getEnuitemdesc() {
        return enuitemdesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PTENUDETAIL.ENUITEMDESC
     *
     * @param enuitemdesc the value for PTENUDETAIL.ENUITEMDESC
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public void setEnuitemdesc(String enuitemdesc) {
        this.enuitemdesc = enuitemdesc == null ? null : enuitemdesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PTENUDETAIL.DISPNO
     *
     * @return the value of PTENUDETAIL.DISPNO
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public BigDecimal getDispno() {
        return dispno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PTENUDETAIL.DISPNO
     *
     * @param dispno the value for PTENUDETAIL.DISPNO
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public void setDispno(BigDecimal dispno) {
        this.dispno = dispno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PTENUDETAIL.ENUITEMEXPAND
     *
     * @return the value of PTENUDETAIL.ENUITEMEXPAND
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public String getEnuitemexpand() {
        return enuitemexpand;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PTENUDETAIL.ENUITEMEXPAND
     *
     * @param enuitemexpand the value for PTENUDETAIL.ENUITEMEXPAND
     *
     * @mbggenerated Fri Apr 08 16:05:09 CST 2011
     */
    public void setEnuitemexpand(String enuitemexpand) {
        this.enuitemexpand = enuitemexpand == null ? null : enuitemexpand.trim();
    }
}