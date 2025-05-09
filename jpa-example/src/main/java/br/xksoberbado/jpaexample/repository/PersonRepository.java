package br.xksoberbado.jpaexample.repository;

import br.xksoberbado.jpaexample.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
