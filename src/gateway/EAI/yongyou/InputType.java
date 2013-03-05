
package gateway.EAI.yongyou;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DBAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Month" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="begindate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enddate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputType", propOrder = {
    "dbAccount",
    "month",
    "begindate",
    "enddate"
})
public class InputType {

    @XmlElement(name = "DBAccount", required = true)
    protected String dbAccount;
    @XmlElement(name = "Month", required = true)
    protected String month;
    @XmlElement(required = true)
    protected String begindate;
    @XmlElement(required = true)
    protected String enddate;

    /**
     * Gets the value of the dbAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDBAccount() {
        return dbAccount;
    }

    /**
     * Sets the value of the dbAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDBAccount(String value) {
        this.dbAccount = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonth(String value) {
        this.month = value;
    }

    /**
     * Gets the value of the begindate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBegindate() {
        return begindate;
    }

    /**
     * Sets the value of the begindate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBegindate(String value) {
        this.begindate = value;
    }

    /**
     * Gets the value of the enddate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * Sets the value of the enddate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnddate(String value) {
        this.enddate = value;
    }

}
