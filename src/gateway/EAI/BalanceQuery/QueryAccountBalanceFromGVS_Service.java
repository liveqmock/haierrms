
/*
 * 
 */

package gateway.EAI.BalanceQuery;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.2.7
 * Fri May 07 17:03:18 CST 2010
 * Generated source version: 2.2.7
 * 
 */


@WebServiceClient(name = "QueryAccountBalanceFromGVS", 
                  wsdlLocation = "file:/D:/rms/wsdl/QueryAccountBalanceFromGVS.wsdl",
                  targetNamespace = "http://www.example.org/QueryAccountBalanceFromGVS/") 
public class QueryAccountBalanceFromGVS_Service extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://www.example.org/QueryAccountBalanceFromGVS/", "QueryAccountBalanceFromGVS");
    public final static QName QueryAccountBalanceFromGVSSOAP = new QName("http://www.example.org/QueryAccountBalanceFromGVS/", "QueryAccountBalanceFromGVSSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/rms/wsdl/QueryAccountBalanceFromGVS.wsdl");
//            url = new URL("file:/F:/svn-haierrms/source/trunc/src/gateway/EAI/BalanceQuery/QueryAccountBalanceFromGVS.wsdl");
//            url = new URL("http://10.135.1.110:7001/EAI/service/GVS/FinanceBalance/QueryAccountBalanceFromGVS/QueryAccountBalanceFromGVS?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:d:/rms/wsdl/QueryAccountBalanceFromGVS.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public QueryAccountBalanceFromGVS_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public QueryAccountBalanceFromGVS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public QueryAccountBalanceFromGVS_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns QueryAccountBalanceFromGVS
     */
    @WebEndpoint(name = "QueryAccountBalanceFromGVSSOAP")
    public QueryAccountBalanceFromGVS getQueryAccountBalanceFromGVSSOAP() {
        return super.getPort(QueryAccountBalanceFromGVSSOAP, QueryAccountBalanceFromGVS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns QueryAccountBalanceFromGVS
     */
    @WebEndpoint(name = "QueryAccountBalanceFromGVSSOAP")
    public QueryAccountBalanceFromGVS getQueryAccountBalanceFromGVSSOAP(WebServiceFeature... features) {
        return super.getPort(QueryAccountBalanceFromGVSSOAP, QueryAccountBalanceFromGVS.class, features);
    }

}
