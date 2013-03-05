
package gateway.EAI.yongyou;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gateway.EAI.yongyou package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InputParametersDBAccount_QNAME = new QName("", "DBAccount");
    private final static QName _InputParametersEnddate_QNAME = new QName("", "enddate");
    private final static QName _InputParametersBegindate_QNAME = new QName("", "begindate");
    private final static QName _InputParametersMonth_QNAME = new QName("", "Month");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gateway.EAI.yongyou
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RowSet.Row.Column }
     * 
     */
    public RowSet.Row.Column createRowSetRowColumn() {
        return new RowSet.Row.Column();
    }

    /**
     * Create an instance of {@link QueryFinanceBalanceFromYYProcessResponse }
     * 
     */
    public QueryFinanceBalanceFromYYProcessResponse createQueryFinanceBalanceFromYYProcessResponse() {
        return new QueryFinanceBalanceFromYYProcessResponse();
    }

    /**
     * Create an instance of {@link InputParameters }
     * 
     */
    public InputParameters createInputParameters() {
        return new InputParameters();
    }

    /**
     * Create an instance of {@link RowSet }
     * 
     */
    public RowSet createRowSet() {
        return new RowSet();
    }

    /**
     * Create an instance of {@link OutputType }
     * 
     */
    public OutputType createOutputType() {
        return new OutputType();
    }

    /**
     * Create an instance of {@link RowSet.Row }
     * 
     */
    public RowSet.Row createRowSetRow() {
        return new RowSet.Row();
    }

    /**
     * Create an instance of {@link OutputParameters }
     * 
     */
    public OutputParameters createOutputParameters() {
        return new OutputParameters();
    }

    /**
     * Create an instance of {@link InputType }
     * 
     */
    public InputType createInputType() {
        return new InputType();
    }

    /**
     * Create an instance of {@link QueryFinanceBalanceFromYYProcessRequest }
     * 
     */
    public QueryFinanceBalanceFromYYProcessRequest createQueryFinanceBalanceFromYYProcessRequest() {
        return new QueryFinanceBalanceFromYYProcessRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DBAccount", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersDBAccount(String value) {
        return new JAXBElement<String>(_InputParametersDBAccount_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "enddate", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersEnddate(String value) {
        return new JAXBElement<String>(_InputParametersEnddate_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "begindate", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersBegindate(String value) {
        return new JAXBElement<String>(_InputParametersBegindate_QNAME, String.class, InputParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Month", scope = InputParameters.class)
    public JAXBElement<String> createInputParametersMonth(String value) {
        return new JAXBElement<String>(_InputParametersMonth_QNAME, String.class, InputParameters.class, value);
    }

}
