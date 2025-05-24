package br.xksoberbado.rabbitmqexample.web.controller;

import br.xksoberbado.rabbitmqexample.config.RabbitMQConfigs;
import br.xksoberbado.rabbitmqexample.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public void post() {
        final var payload = new Person(UUID.randomUUID(), "Maria");

        rabbitTemplate.convertAndSend(RabbitMQConfigs.EXCHANGE_NAME, RabbitMQConfigs.RK, payload);
    }
}
