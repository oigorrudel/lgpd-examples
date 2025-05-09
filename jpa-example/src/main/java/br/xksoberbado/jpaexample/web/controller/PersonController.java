package br.xksoberbado.jpaexample.web.controller;

import br.xksoberbado.jpaexample.model.Person;
import br.xksoberbado.jpaexample.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository repository;

    @GetMapping
    public List<Person> get() {
        return repository.findAll()
            .stream()
            .peek(p -> p.getCpf().trim())
            .toList();
    }
}
