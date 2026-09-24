package com.mycompany.systemsoftproject.services;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {

    // The Fixed Key which ensures we can always decrypt what we havve stored
    private static SecretKey getFixedKey() {
        // Must be exactly 16 characters for AES-128
        String hardcodedKey = "ntu-systems-2026"; 
        return new SecretKeySpec(hardcodedKey.getBytes(), "AES");
    }

    // Encrypt logic
    public static String encrypt(String data) {
        try {
            SecretKey key = getFixedKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    //  Decrypt logic
    public static String decrypt(String encryptedData) {
        try {
            SecretKey key = getFixedKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}
