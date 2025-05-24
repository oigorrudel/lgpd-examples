# jpa-example

Utilizando Spring Boot, JPA e Hibernate existem algumas possibilidades de aplicar transformações dos valores na leitura e escrita de algum banco relacional.
<br>As possibilidades: 
 - utilizar alguma implementação de **AttributeConverter** aplicando chaves **RSA**;
 - utilizar a anotação **@ColumnTransformer** do <i>Hibernate</i> aplicando funções de encryption/decryption do banco relacional (este exemplo);

```h2
 select *, DECRYPT('AES', 'myKey', cpf) as decrypted from person
```
