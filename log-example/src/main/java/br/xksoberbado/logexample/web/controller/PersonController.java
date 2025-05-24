package br.xksoberbado.logexample.web.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/persons")
public class PersonController {

    @PostMapping
    public void create(@RequestBody PersonBody body) {
        log.info("Create. [body: {}]", body);
    }

    public record PersonBody(String name,
                             String cpf) {
    }

//    @Data
//    public static class PersonBody {
//        private String name;
//        private String cpf;
//    }
}
