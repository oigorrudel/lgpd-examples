package br.xksoberbado.rabbitmqexample.event;

import br.xksoberbado.rabbitmqexample.config.RabbitMQConfigs;
import br.xksoberbado.rabbitmqexample.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PersonConsumers {

    @RabbitListener(queues = RabbitMQConfigs.QUEUE_NAME)
    public void receive(@Payload final Person person) {
        log.info("Received: {}", person);
    }
}
