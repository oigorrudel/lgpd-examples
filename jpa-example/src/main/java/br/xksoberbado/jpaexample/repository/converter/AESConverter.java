package br.xksoberbado.jpaexample.repository.converter;

import br.xksoberbado.jpaexample.service.AESService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class AESConverter implements AttributeConverter<String, byte[]> {

    private final AESService aesService;

    @Override
    public byte[] convertToDatabaseColumn(final String attribute) {
        return Optional.ofNullable(attribute)
            .map(att -> aesService.encrypt(attribute.getBytes()))
            .orElse(null);
    }

    @Override
    public String convertToEntityAttribute(final byte[] dbData) {
        return Optional.ofNullable(dbData)
            .map(data -> new String(aesService.decrypt(data)))
            .orElse(null);
    }
}
