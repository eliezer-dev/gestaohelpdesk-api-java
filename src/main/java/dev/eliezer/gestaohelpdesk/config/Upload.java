package dev.eliezer.gestaohelpdesk.config;

import java.security.SecureRandom;

public class Upload {
    public static String UPLOAD_FOLDER = ".//uploads//";

    public static String TMP_FOLDER = ".//tmp//";

    public static String generateRandomHex(int byteLength, String originalFileName) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : randomBytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        String filename = stringBuilder.toString() + "-" + originalFileName;
        return filename;
    }
}

