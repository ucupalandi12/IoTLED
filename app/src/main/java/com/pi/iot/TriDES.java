package com.pi.iot;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TriDES {
    private static final Logger logger = Logger.getLogger(TriDES.class.getName());

    private static String key;

    public TriDES(String myEncryptionKey) {
        key = myEncryptionKey;
    }

    /**
     * Method to encrypt the string
     */
    public static String encrypt(String unencryptedString) {
        if (unencryptedString == null) {
            return "";
        }
        try {
//            byte[] plainTextMessage = Base64.encodeBase64(unencryptedString.getBytes("utf-8"));

            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24); //24 bytes = 3 x 8bytes (DES key is 8 8 bytes)

            for (int j = 0, k = 16; j < 8; ) {
                keyBytes[k++] = keyBytes[j++];
            } //fungsi mekanisme enkripsi pada input plaintext (enciphering)

            SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede"); //ShareSecretKey
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            // DESede : Algoritma TriDES (block cipher),
            // ECB : modus operasi enkripsi blok menggunakan ECB(Electronic Code Book)
            // Padding atau menambahkan bit-bit di belakang input plaintext
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] plainTextBytes = unencryptedString.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            String base64EncryptedString = new String(Base64.encodeBase64(buf));

            return base64EncryptedString;
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to decrypt an encrypted string
     *
     * @param encryptedString
     */
    public String  decrypt(String encryptedString) {
        if (encryptedString == null) {
            return "";
        }
        try {
            byte[] message = Base64.decodeBase64(encryptedString.getBytes("utf-8"));

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            for (int j = 0, k = 16; j < 8; ) {
                keyBytes[k++] = keyBytes[j++];
            }

            SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede"); //ShareSecretKey
            Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            decipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] plainText = decipher.doFinal(message);

            return new String(plainText, "UTF-8");
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
    }
}