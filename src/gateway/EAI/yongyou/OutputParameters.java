
package gateway.EAI.yongyou;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="RowSet" type="{http://xmlns.oracle.com/pcbpel/adapter/db/dbo/UP_Getdeposit_ByCorp/}RowSet" maxOccurs="unbounded" minOccurs="0"/>
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
    "rowSet"
})
@XmlRootElement(name = "OutputParameters", namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/dbo/UP_Getdeposit_ByCorp/")
public class OutputParameters {

    @XmlElement(name = "RowSet", namespace = "", nillable = true)
    protected List<RowSet> rowSet;

    /**
     * Gets the value of the rowSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rowSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRowSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RowSet }
     * 
     * 
     */
    public List<RowSet> getRowSet() {
        if (rowSet == null) {
            rowSet = new ArrayList<RowSet>();
        }
        return this.rowSet;
    }

}
