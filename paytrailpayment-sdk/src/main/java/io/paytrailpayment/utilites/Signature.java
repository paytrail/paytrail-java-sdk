package io.paytrailpayment.utilites;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Signature {

    private static final Set<String> SUPPORTED_ENC = new HashSet<>(Arrays.asList("sha256", "sha512"));

    private static String computeShaHash(String message, String secret, String encType) throws NoSuchAlgorithmException, InvalidKeyException {
        if (!SUPPORTED_ENC.contains(encType.toLowerCase())) {
            throw new IllegalArgumentException("Not supported encryption");
        }

        Mac shaMac = Mac.getInstance("Hmac" + encType.toUpperCase());
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "Hmac" + encType.toUpperCase());
        shaMac.init(secretKey);

        byte[] hashBytes = shaMac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes).toLowerCase();
    }

    public static String calculateHmac(String secret, Map<String, String> hparams, String body, String encType) throws NoSuchAlgorithmException, InvalidKeyException {
        List<String> includedKeys = hparams.entrySet().stream()
                .filter(e -> e.getKey().startsWith("checkout-"))
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> e.getKey() + ":" + e.getValue())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        includedKeys.add(body);

        return computeShaHash(String.join("\n", includedKeys), secret, encType);
    }

    public static boolean validateHmac(Map<String, String> hparams, String body, String signature, String secretKey) {
        try {
            String hmac = calculateHmac(secretKey, hparams, body, "sha256");
            return Objects.equals(hmac, signature);
        } catch (Exception e) {
            return false;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}