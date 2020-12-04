package com.criollofood.bootapp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;

public class Pbkdf2Sha256PasswordEncoder implements PasswordEncoder {
    private static final Logger LOGGER = LogManager.getLogger(Pbkdf2Sha256PasswordEncoder.class);

    public final Integer DEFAULT_ITERATIONS = 216000;
    public final String algorithm = "pbkdf2_sha256";
    private Integer iterations;
    private String salt;

    public String getEncodedHash(String password, String salt, int iterations) {
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            System.exit(1);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Could NOT generate secret key");
            e.printStackTrace();
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String password = rawPassword.toString();
        if (Objects.isNull(iterations) || Objects.isNull(salt)) {
            return password;
        }
        String hash = getEncodedHash(rawPassword.toString(), salt, iterations);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 4) {
            return false;
        }
        iterations = Integer.parseInt(parts[1]);
        salt = parts[2];
        String hash = encode(rawPassword.toString());

        return hash.equals(encodedPassword);
    }
}
