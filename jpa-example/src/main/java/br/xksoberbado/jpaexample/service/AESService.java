package br.xksoberbado.jpaexample.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;

@Service
public class AESService {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "+vgfv+vCWXEkyFNVHGF/05Hxz1KPp+ARHhFKYh5rUmM=";

    private SecretKey secretKey;
    private Cipher cryptCipher;
    private Cipher decryptCipher;

    @SneakyThrows
    public byte[] encrypt(final byte[] bytes) {
        if (Objects.isNull(cryptCipher)) {
            cryptCipher = getCipher(Cipher.ENCRYPT_MODE);
        }

        return cryptCipher.doFinal(bytes);
    }

    @SneakyThrows
    public byte[] decrypt(final byte[] bytes) {
        if (Objects.isNull(decryptCipher)) {
            decryptCipher = getCipher(Cipher.DECRYPT_MODE);
        }

        return decryptCipher.doFinal(bytes);
    }

    @SneakyThrows
    private Cipher getCipher(final Integer mode) {
        final var cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, getKey());

        return cipher;
    }

    @SneakyThrows
    private SecretKey getKey() {
        if (Objects.isNull(secretKey)) {
            final var key = Base64.getDecoder().decode(KEY.getBytes());

            secretKey = new SecretKeySpec(key, 0, key.length, ALGORITHM);
        }

        return secretKey;
    }
}
