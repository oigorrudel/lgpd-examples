package br.xksoberbado.rabbitmqexample.service;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class AESService implements IKeyService {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "OjRLGrVE3fzUgdYbKricEf0kZRid/jVv/OymM1AdEhs=";

    @Override
    @SneakyThrows
    public byte[] encrypt(final byte[] bytes) {
        final var cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getKey());

        return cipher.doFinal(bytes);
    }

    @Override
    @SneakyThrows
    public byte[] decrypt(final byte[] bytes) {
        final var cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getKey());

        return cipher.doFinal(bytes);
    }

    @SneakyThrows
    private SecretKey getKey() {
        final var key = Base64.getDecoder().decode(KEY);

        return new SecretKeySpec(key, 0, key.length, ALGORITHM);
    }
}
