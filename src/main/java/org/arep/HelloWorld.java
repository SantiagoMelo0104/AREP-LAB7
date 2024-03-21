package org.arep;

import static spark.Spark.*;
/**
 * This class is a simple example of a web application using Spark.
 */
public class HelloWorld {
    /**
     * This is the main method that starts the web application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Secure the application with the provided keystore and password.
        secure("Certificados/ecikeystore.p12", "superonce0104", null, null);
        port(getPort());
        staticFiles.location("/public");
        // Define a route for the HTTP POST request to the "/login" URL.
        post("/login", (req, res) -> {
            // Get the username and password from the query parameters.
            String username = req.queryParams("user");
            String password = req.queryParams("password");
            // Call the secureReadUrl method to get the content of the URL.
            return SecureURLReader.secureReadUrl(username, password);
        });
    }

    /**
     * This method returns the port number that the application should listen on.
     * If the environment variable "PORT" is set, the method returns the value of
     * the variable. Otherwise, it returns the default port number 5000.
     * @return the port number
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}