package org.arep;

import static spark.Spark.*;
public class HelloWorld {
    public static void main(String[] args) {
        staticFiles.location("/public");
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);

        secure("Certificados/ecikeystore.p12", "superonce0104", null, null);
        port(getPort());
        get("/public", (req, res) -> "Hello World");
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}