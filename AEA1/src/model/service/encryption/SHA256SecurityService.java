package model.service.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA256SecurityService {

    public String encripta(String valorOriginal) {
        if (valorOriginal == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA256");
            byte[] digestBytes = md.digest(valorOriginal.getBytes());
            return Base64.getEncoder().encodeToString(digestBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error en SHA256", e);
        }
    }

}