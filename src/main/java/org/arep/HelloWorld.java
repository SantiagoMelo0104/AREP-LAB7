package org.arep;

import static spark.Spark.*;
public class HelloWorld {
    public static void main(String[] args) {

        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        secure("Certificados/ecikeystore.p12", "superonce0104", null, null);
        port(getPort());
        staticFiles.location("/public");
        post("/login", (req, res) -> {
            String username = req.queryParams("user");
            String password = req.queryParams("password");
            return SecureURLReader.secureReadUrl(username, password);
        });
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}