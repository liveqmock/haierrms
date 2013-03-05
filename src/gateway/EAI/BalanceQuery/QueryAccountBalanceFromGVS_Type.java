
package gateway.EAI.BalanceQuery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="in" type="{http://www.example.org/QueryAccountBalanceFromGVS/}InputType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "in"
})
@XmlRootElement(name = "QueryAccountBalanceFromGVS")
public class QueryAccountBalanceFromGVS_Type {

    @XmlElement(required = true)
    protected InputType in;

    /**
     * Gets the value of the in property.
     * 
     * @return
     *     possible object is
     *     {@link InputType }
     *     
     */
    public InputType getIn() {
        return in;
    }

    /**
     * Sets the value of the in property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputType }
     *     
     */
    public void setIn(InputType value) {
        this.in = value;
    }

}
