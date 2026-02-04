package tg.univlome.epl.cookplusserver.utils;

import java.util.Base64;

/**
 * Utility class for JWT token generation and validation. Handles token creation
 * with 3-day expiration.
 *
 * @author DAKEY Ahoefa Light
 */
public class TokenUtil {

    private static final String SECRET_KEY = "CookPlusServerSecretKeyFor3DaysTokenExpiration2024";
    private static final long EXPIRATION_TIME = 3 * 24 * 60 * 60 * 1000; // 3 jours en millisecondes

    public static String generateToken(Long userId, String email, String role) {
        long now = System.currentTimeMillis();
        long expiryTime = now + EXPIRATION_TIME;

        String header = encodeBase64("{\"typ\":\"JWT\",\"alg\":\"HS256\"}");
        String payload = encodeBase64("{\"sub\":\"" + userId + "\",\"email\":\"" + email + "\",\"role\":\"" + role
                + "\",\"iat\":" + (now / 1000) + ",\"exp\":" + (expiryTime / 1000) + "}");

        String signature = generateSignature(header + "." + payload);

        return header + "." + payload + "." + signature;
    }

    public static TokenPayload validateToken(String token) {
        if (token == null || !token.contains(".")) {
            return null;
        }

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }

        String header = parts[0];
        String payload = parts[1];
        String signature = parts[2];

        String expectedSignature = generateSignature(header + "." + payload);
        if (!expectedSignature.equals(signature)) {
            return null;
        }

        try {
            String decodedPayload = new String(Base64.getDecoder().decode(payload));

            // Parsing simplifié du JSON
            if (!decodedPayload.contains("\"exp\":")) {
                return null;
            }

            String expStr = extractJsonValue(decodedPayload, "exp");
            long expiryTime = Long.parseLong(expStr) * 1000;

            if (System.currentTimeMillis() > expiryTime) {
                return null; // Token expiré
            }

            String userId = extractJsonValue(decodedPayload, "sub");
            String email = extractJsonValue(decodedPayload, "email");
            String role = extractJsonValue(decodedPayload, "role");

            return new TokenPayload(Long.parseLong(userId), email, role);
        } catch (Exception e) {
            return null;
        }
    }

    private static String generateSignature(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest((input + SECRET_KEY).getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private static String encodeBase64(String input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input.getBytes());
    }

    private static String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) {
            return null;
        }

        int valueStart = startIndex + searchKey.length();
        int valueEnd = json.indexOf(",", valueStart);
        if (valueEnd == -1) {
            valueEnd = json.indexOf("}", valueStart);
        }

        String value = json.substring(valueStart, valueEnd).trim();
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    public static class TokenPayload {

        public final Long userId;
        public final String email;
        public final String role;

        public TokenPayload(Long userId, String email, String role) {
            this.userId = userId;
            this.email = email;
            this.role = role;
        }
    }
}
