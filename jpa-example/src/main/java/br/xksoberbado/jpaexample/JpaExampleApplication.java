package br.xksoberbado.jpaexample;

import br.xksoberbado.jpaexample.model.Person;
import br.xksoberbado.jpaexample.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class JpaExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaExampleApplication.class, args);
    }

    @Autowired
    private PersonRepository repository;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            repository.saveAll(
                List.of(
                    Person.builder().id(UUID.randomUUID()).name("João").cpf("11122233300").build(),
                    Person.builder().id(UUID.randomUUID()).name("Maria").cpf("44455566699").build()
                )
            );

            final var persons = repository.findAll();
            persons.forEach(System.out::println);
        };
    }
}
