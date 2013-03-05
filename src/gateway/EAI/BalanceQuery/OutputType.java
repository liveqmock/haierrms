
package gateway.EAI.BalanceQuery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OutputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OutputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BUTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SAKNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TXT50_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TXT50_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WAERS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TYUE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputType", propOrder = {
    "butxt",
    "saknr",
    "txt501",
    "txt502",
    "waers",
    "tyue"
})
public class OutputType {

    @XmlElement(name = "BUTXT", required = true)
    protected String butxt;
    @XmlElement(name = "SAKNR", required = true)
    protected String saknr;
    @XmlElement(name = "TXT50_1", required = true)
    protected String txt501;
    @XmlElement(name = "TXT50_2", required = true)
    protected String txt502;
    @XmlElement(name = "WAERS", required = true)
    protected String waers;
    @XmlElement(name = "TYUE", required = true)
    protected String tyue;

    /**
     * Gets the value of the butxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUTXT() {
        return butxt;
    }

    /**
     * Sets the value of the butxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUTXT(String value) {
        this.butxt = value;
    }

    /**
     * Gets the value of the saknr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSAKNR() {
        return saknr;
    }

    /**
     * Sets the value of the saknr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSAKNR(String value) {
        this.saknr = value;
    }

    /**
     * Gets the value of the txt501 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTXT501() {
        return txt501;
    }

    /**
     * Sets the value of the txt501 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTXT501(String value) {
        this.txt501 = value;
    }

    /**
     * Gets the value of the txt502 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTXT502() {
        return txt502;
    }

    /**
     * Sets the value of the txt502 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTXT502(String value) {
        this.txt502 = value;
    }

    /**
     * Gets the value of the waers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWAERS() {
        return waers;
    }

    /**
     * Sets the value of the waers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWAERS(String value) {
        this.waers = value;
    }

    /**
     * Gets the value of the tyue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTYUE() {
        return tyue;
    }

    /**
     * Sets the value of the tyue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTYUE(String value) {
        this.tyue = value;
    }

}
