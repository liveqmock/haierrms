
package gateway.EAI.yongyou;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for outputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="outputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ccode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="me" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ccode_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outputType", propOrder = {
    "ccode",
    "me",
    "ccodeName",
    "bz"
})
public class OutputType {

    @XmlElement(required = true)
    protected String ccode;
    @XmlElement(required = true)
    protected String me;
    @XmlElement(name = "ccode_name", required = true)
    protected String ccodeName;
    @XmlElement(required = true)
    protected String bz;

    /**
     * Gets the value of the ccode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcode() {
        return ccode;
    }

    /**
     * Sets the value of the ccode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcode(String value) {
        this.ccode = value;
    }

    /**
     * Gets the value of the me property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMe() {
        return me;
    }

    /**
     * Sets the value of the me property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMe(String value) {
        this.me = value;
    }

    /**
     * Gets the value of the ccodeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcodeName() {
        return ccodeName;
    }

    /**
     * Sets the value of the ccodeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcodeName(String value) {
        this.ccodeName = value;
    }

    /**
     * Gets the value of the bz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBz() {
        return bz;
    }

    /**
     * Sets the value of the bz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBz(String value) {
        this.bz = value;
    }

}
