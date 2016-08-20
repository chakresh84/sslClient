package RestClient.RestClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
public class App 
{
	
	
	 
    public static void main( String[] args )
    {
		/* HostnameVerifier allHostsValid = new HostnameVerifier() {
			 public boolean verify(String hostname, SSLSession session) {
				 return true;
			 }
		 };
		 
	   javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
*/
	   RestTemplate rt = new RestTemplate();
       String nonSSL = "http://localhost:8082/SpringRestfulWebServicesWithJSONExample/countries";
       String ssl = "https://localhost:8443/SpringRestfulWebServicesWithJSONExample/countries";
       
       HttpHeaders hh = new HttpHeaders();
       hh.setContentType(MediaType.APPLICATION_JSON);
       HttpEntity<String> entity = new HttpEntity<String>(hh);
       
       /*System.setProperty("javax.net.ssl.keyStore", "G:\\software\\apache-tomcat-7.0.70\\webapps\\mkyongkeystore");
       System.setProperty("javax.net.ssl.trustStore", "G:\\software\\apache-tomcat-7.0.70\\webapps\\mkyongkeystore");
       System.setProperty("javax.net.ssl.keyStorePassword", "welcome");
       System.setProperty("javax.net.ssl.trustStorePassword", "welcome");*/

       
       ResponseEntity<String> exchange = rt.exchange(ssl, HttpMethod.GET,entity,String.class);
       
       System.out.println(exchange.getBody());
    }
}
