package model.service.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AESSecurityService {

    private final String PASSWORD = "VisitaMedica_MasterKey_App_v1";
    
    private final String SALT = "salt-fixe-acces-dades-java"; 
    
    private final String ENCRYPT_ALGORITHM = "AES";

    private SecretKeySpec getSecretKey() {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(PASSWORD.toCharArray(), SALT.getBytes(), 65536, 256);
            
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), ENCRYPT_ALGORITHM);
            
        } catch (Exception e) {
            throw new RuntimeException("Error generant la clau AES: " + e.getMessage());
        }
    }

    public String encripta(String valorOriginal) {
        if (valorOriginal == null) return null;
        try {
            SecretKeySpec secretKey = getSecretKey();

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] encrypted = cipher.doFinal(valorOriginal.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
            
        } catch (Exception e) {
            throw new RuntimeException("Error encriptant AES: " + e.getMessage());
        }
    }

    public String desencripta(String encrypted) {
        if (encrypted == null) return null;
        try {
            SecretKeySpec secretKey = getSecretKey();

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
            return new String(cipher.doFinal(encryptedBytes), StandardCharsets.UTF_8);
            
        } catch (Exception e) {
            throw new RuntimeException("Error desencriptant AES (¿Clau incorrecta?): " + e.getMessage());
        }
    }

    
}