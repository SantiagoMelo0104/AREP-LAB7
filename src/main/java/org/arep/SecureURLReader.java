package org.arep;



import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
/**
 * This class is used to read the content of a URL securely.
 */
public class SecureURLReader {
    /**
     * This method reads the content of a URL securely using the provided user and password.
     * @param user     the username to use for authentication
     * @param password the password to use for authentication
     * @return the content of the URL as a string
     * @throws KeyStoreException            if there is an error loading the keystore
     * @throws FileNotFoundException         if the keystore file is not found
     * @throws IOException                    if there is an error reading the keystore or URL
     * @throws NoSuchAlgorithmException       if there is no such algorithm
     * @throws CertificateException            if there is an error with the certificate
     * @throws KeyManagementException         if there is an error initializing the SSL context
     */
    public static String secureReadUrl(String user, String password) {
        String secureResponse = "";
        try {

            // Create a file and a password representation
            File trustStoreFile = new File("Certificados/myTrustStore.p12");
            char[] trustStorePassword = "123456".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);

            //Print the trustManagers returned by the TMF
            //only for debugging
            for(TrustManager t: tmf.getTrustManagers()){
                System.out.println(t);
            }

            //Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

            // We can now read this URL
            secureResponse = readURL("https://localhost:5010/login?user=" + user + "&password=" + password);

            // This one can't be read because the Java default truststore has been
            // changed.

        } catch (KeyStoreException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(SecureURLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secureResponse;
    }
    /**

     * This method reads the content of a URL.
     * @param sitetoread the URL to read
     * @return the content of the URL as a string
     * @throws IOException if there is an error reading the URL
     */
    public static String readURL(String sitetoread) {
        StringBuffer response = new StringBuffer();
        try {
            // Crea el objeto que representa una URL2
            URL siteURL = new URL(sitetoread);
            // Crea el objeto que URLConnection
            URLConnection urlConnection = siteURL.openConnection();
            // Obtiene los campos del encabezado y los almacena en un estructura Map
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            // Obtiene una vista del mapa como conjunto de pares <K,V>
            // para poder navegarlo
            Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
            // Recorre la lista de campos e imprime los valores
            for (Map.Entry<String, List<String>> entry : entrySet) {
                String headerName = entry.getKey();

                //Si el nombre es nulo, significa que es la linea de estado
                if (headerName != null) {
                    System.out.print(headerName + ":");
                }
                List<String> headerValues = entry.getValue();
                for (String value : headerValues) {
                    System.out.print(value);
                }
                System.out.println("");
            }

            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                response.append(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
            response.append(x);
        }
        return response.toString();
    }
}

