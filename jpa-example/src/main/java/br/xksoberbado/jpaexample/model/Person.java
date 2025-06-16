package br.xksoberbado.jpaexample.model;

import br.xksoberbado.jpaexample.repository.converter.AESConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    private UUID id;

    private String name;

    @Column(columnDefinition = "TINYBLOB")
    @Convert(converter = AESConverter.class)
//    @ColumnTransformer(
//        read = "AES_DECRYPT(cpf, '+vgfv+vCWXEkyFNVHGF/05Hxz1KPp+ARHhFKYh5rUmM=')",
//        write = "AES_ENCRYPT(?, '+vgfv+vCWXEkyFNVHGF/05Hxz1KPp+ARHhFKYh5rUmM=')"
//    )
    private String cpf;
}
