package br.xksoberbado.jpaexample.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

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

    @Column(columnDefinition = "BINARY(16)")
    @ColumnTransformer(
        read = "CAST(DECRYPT('AES', 'myKey', cpf) AS VARCHAR)",
        write = "ENCRYPT('AES', 'myKey', ?)"
    )
    private String cpf;
}
