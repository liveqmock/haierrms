package haier.repository.model;

public class MtActtypeKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MT_ACTTYPE.ACTNO
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    private String actno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MT_ACTTYPE.BANKCD
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    private String bankcd;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MT_ACTTYPE.ACTNO
     *
     * @return the value of MT_ACTTYPE.ACTNO
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public String getActno() {
        return actno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MT_ACTTYPE.ACTNO
     *
     * @param actno the value for MT_ACTTYPE.ACTNO
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public void setActno(String actno) {
        this.actno = actno == null ? null : actno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MT_ACTTYPE.BANKCD
     *
     * @return the value of MT_ACTTYPE.BANKCD
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public String getBankcd() {
        return bankcd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MT_ACTTYPE.BANKCD
     *
     * @param bankcd the value for MT_ACTTYPE.BANKCD
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public void setBankcd(String bankcd) {
        this.bankcd = bankcd == null ? null : bankcd.trim();
    }
}