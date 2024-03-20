package org.arep;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;
import static spark.Spark.*;

public class UserDB {
    private static Map<String, String> data = new HashMap<>();

    public static void main(String[] args) throws NoSuchAlgorithmException {

        data.put("santiago", getSecurePassword("12345"));
        data.put("admin", getSecurePassword("admin"));

        secure("Certificados/ecikeystore.p12", "superonce0104", null, null);
        port(getPort());
        get("/login", (req, res) -> {
            String username = req.queryParams("user");
            String password = req.queryParams("password");

            if (data.containsKey(username)) {
                String storedPasswordHash = data.get(password);
                String inputPasswordHash = getSecurePassword(password);
                if (storedPasswordHash.equals(inputPasswordHash)) {
                    return "Inicio de sesión exitoso";
                } else {
                    return "La clave ingresada es incorrecta";
                }
            } else {
                return "El usuario no existe";
            }
        });

    }
    public static String getSecurePassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
    static int getPort () {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5010; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
