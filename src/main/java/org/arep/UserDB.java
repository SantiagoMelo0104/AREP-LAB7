package org.arep;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;
import static spark.Spark.*;

/**
 * UserDB class manages a simple in-memory database of users and their corresponding hashed passwords.
 * This is a simple REST API that provides a login endpoint for users.
 *
 * @author santiago
 */
public class UserDB {
    /**
     * Static map that stores usernames and their corresponding hashed passwords.
     */
    private static Map<String, String> data = new HashMap<>();

    /**
     * The main method starts the server and sets the necessary configurations.
     *
     * @param args Command line arguments.
     * @throws NoSuchAlgorithmException Thrown when the hash algorithm isn't found.
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        data.put("santiago", getSecurePassword("12345"));
        data.put("admin", getSecurePassword("admin"));

        // Secure connection and port configuration.
        secure("Certificados/ecikeystore.p12", "superonce0104", null, null);
        port(getPort());

        // Setting up the login endpoint.
        get("/login", (req, res) -> {
            String username = req.queryParams("user");
            String password = req.queryParams("password");

            if (data.containsKey(username)) {
                String storedPasswordHash = data.get(username);
                String inputPasswordHash = getSecurePassword(password);

                if (checkPassword(username, password)) {
                    return "Inicio de sesión exitoso 😎😎";
                } else {
                    return "La clave ingresada es incorrecta 😓😓";
                }
            } else {
                return "El usuario no existe 🥱🥱";
            }
        });
    }

    /**
     * Method to get the secure password hash of the given input.
     *
     * @param password Plain text password.
     * @return The encoded password hash.
     * @throws NoSuchAlgorithmException Thrown when the hash algorithm isn't found.
     */
    public static String getSecurePassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Method to check if the given password matches any user's password in the HashMap.
     *
     * @param username  username to search for.
     * @param password Plain text password for validation.
     * @return Validation result.
     * @throws NoSuchAlgorithmException Thrown when the hash algorithm isn't found.
     */
    public static boolean checkPassword(String username, String password) throws NoSuchAlgorithmException {
        String storedPasswordHash = data.get(username);
        if (storedPasswordHash == null) {
            return false;
        }
        String inputPasswordHash = getSecurePassword(password);
        return storedPasswordHash.equals(inputPasswordHash);
    }

    /**
     * Static method to get the port from the environment or return a default port if it's not defined.
     *
     * @return An integer value of the valid port.
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5010; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
