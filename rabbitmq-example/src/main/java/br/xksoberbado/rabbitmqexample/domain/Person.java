package br.xksoberbado.rabbitmqexample.domain;

import java.util.UUID;

public record Person(UUID id,
                     String name) {
}
