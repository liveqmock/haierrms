
package gateway.EAI.BalanceQuery;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.7
 * Fri May 07 17:03:17 CST 2010
 * Generated source version: 2.2.7
 * 
 */

public final class QueryAccountBalanceFromGVS_QueryAccountBalanceFromGVSSOAP_Client {

    private static final QName SERVICE_NAME = new QName("http://www.example.org/QueryAccountBalanceFromGVS/", "QueryAccountBalanceFromGVS");

    private QueryAccountBalanceFromGVS_QueryAccountBalanceFromGVSSOAP_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = QueryAccountBalanceFromGVS_Service.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        QueryAccountBalanceFromGVS_Service ss = new QueryAccountBalanceFromGVS_Service(wsdlURL, SERVICE_NAME);
        QueryAccountBalanceFromGVS port = ss.getQueryAccountBalanceFromGVSSOAP();  
        
        {
        System.out.println("Invoking queryAccountBalanceFromGVS...");
        gateway.EAI.BalanceQuery.InputType _queryAccountBalanceFromGVS_in = null;
        java.util.List<gateway.EAI.BalanceQuery.OutputType> _queryAccountBalanceFromGVS__return = port.queryAccountBalanceFromGVS(_queryAccountBalanceFromGVS_in);
        System.out.println("queryAccountBalanceFromGVS.result=" + _queryAccountBalanceFromGVS__return);


        }

        System.exit(0);
    }

}
