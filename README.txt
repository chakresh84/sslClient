Need to follow below steps in order to access SSL restful service

1. Add JVM SSL parameters.
You can add ssl JVM parameters in either ways as mentioned below

A. Add JVM parameter in the tomcat SERVER JVM/STANDALONE application
-Djavax.net.ssl.trustStore=G:\software\apache-tomcat-7.0.70\webapps\mykeystore
-Djavax.net.ssl.trustStorePassword=password
-Djavax.net.ssl.keyStore=G:\software\apache-tomcat-7.0.70\webapps\mykeystore
-Djavax.net.ssl.keyStorePassword=password

OR

B. Add JVM parameters via Program

System.setProperty("javax.net.ssl.keyStore", "G:\\software\\apache-tomcat-7.0.70\\webapps\\mykeystore");
System.setProperty("javax.net.ssl.trustStore", "G:\\software\\apache-tomcat-7.0.70\\webapps\\mykeystore");
System.setProperty("javax.net.ssl.keyStorePassword", "password");
System.setProperty("javax.net.ssl.trustStorePassword", "password");


2. Run the rest client


3. Handle below error if you got after run the client.

"java.security.cert.CertificateException: No name matching <<localhost>> found:

NOTE: here LOCALHOST is CN in the certificate.

--To check CN info on cert

keytool -v -list -keystore mykeystore

<<You can follow either ways to resolve the issue>>

Method 1: 
Change the Certificate CN name. PLEASE FOLLOW below steps STEP BY STEP
The first step is to verify the CN (Common Name) in the certificate. Please not that, you cannot change the CN in an already created certificate. This is because, a certificate is designed specifically so that this data can’t be modified after its creation. It is part of the certificate.

CN=localhost, OU=home, O=home, L=city,ST=state, C=in
So you need to create a new certificate with the hostname as “localhost” or any other hostname you want to use. Example is given below. You can use the java keytool tool to create your self-signed certificate with the “localhost” CN.

keytool -genkey -alias serverkey -keypass nosecret -keyalg RSA -sigalg SHA1withRSA -keystore server.keystore -storepass nostoresecret
What is your first and last name?
  [Unknown]:  localhost
What is the name of your organizational unit?
  [Unknown]:  globinch ws
What is the name of your organization?
  [Unknown]:  globinch
What is the name of your City or Locality?
  [Unknown]:  Bangalore
What is the name of your State or Province?
  [Unknown]:  KAR
What is the two-letter country code for this unit?
  [Unknown]:  IN
Is CN=localhost, OU=globinch ws, O=globinch, L=Bangalore, ST=KAR, C=IN correct?
  [no]:  yes
That’s all. You need to apply this certificate and setup the web server. Now if you use endpoint such as https://localhost:8443/MyService/..” ,the java security run-time will accept it.

OR 

Method2:

Implement the verify() method of HostnameVerifier
HostnameVerifier is the base interface for hostname verification. As per java doc “During handshaking, if the URL’s hostname and the server’s identification hostname mismatch, the verification mechanism can call back to implementers of this interface to determine if this connection should be allowed”.
These callbacks are used when the default rules for URL hostname verification fail.
The HttpsURLConnection class uses HostnameVerifier and SSLSocketFactory. You can set the HostnameVerifier using the setDefaultHostnameVerifier(setDefaultHostnameVerifier) method. See example below.

javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
    new javax.net.ssl.HostnameVerifier(){
 
        public boolean verify(String hostname,
                javax.net.ssl.SSLSession sslSession) {
            if (hostname.equals("localhost")) {
                return true;
            }
            return false;
        }
        });

        