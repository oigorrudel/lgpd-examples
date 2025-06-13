package br.xksoberbado.rabbitmqexample.config;

import br.xksoberbado.rabbitmqexample.service.AESService;
import br.xksoberbado.rabbitmqexample.service.IKeyService;
import br.xksoberbado.rabbitmqexample.service.RSAService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfigs {

    public static final String EXCHANGE_NAME = "my-exchange.direct";
    public static final String QUEUE_NAME = "my-queue";
    public static final String RK = "my.rk";

    private static final String DLQ_EXCHANGE_NAME = "";
    private static final String DLQ = QUEUE_NAME + ".dlq";

    private final RSAService rsaService;
    private final AESService aesService;

    @Value("${application.rsa-enabled}")
    private boolean rsaEnabled;

    @Value("${application.aes-enabled}")
    private boolean aesEnabled;

    @Bean
    RabbitTemplateCustomizer rabbitTemplateCustomizer(final ObjectMapper objectMapper) {
        return rabbitTemplate -> {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
            if (rsaEnabled) {
                rabbitTemplate.setBeforePublishPostProcessors(rsaEncryptProcessor());
            } else if (aesEnabled) {
                rabbitTemplate.setBeforePublishPostProcessors(aesEncryptProcessor());
            }
        };
    }

    @Bean
    RabbitListenerConfigurer rabbitListenerConfigurer(final ObjectMapper objectMapper,
                                                      final SimpleRabbitListenerContainerFactory factory) {
        return rabbitListenerEndpointRegistrar -> {
            factory.setMessageConverter(
                rsaEnabled || aesEnabled
                    ? new CustomJackson2JsonMessageConverter(objectMapper, rsaEnabled ? rsaService : aesService)
                    : new Jackson2JsonMessageConverter(objectMapper)
            );

            rabbitListenerEndpointRegistrar.setContainerFactory(factory);
        };
    }

    private MessagePostProcessor rsaEncryptProcessor() {
        return message -> new Message(rsaService.encrypt(message.getBody()), message.getMessageProperties());
    }

    private MessagePostProcessor aesEncryptProcessor() {
        return message -> new Message(aesService.encrypt(message.getBody()), message.getMessageProperties());
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
            .deadLetterExchange(DLQ_EXCHANGE_NAME)
            .deadLetterRoutingKey(DLQ)
            .build();
    }

    @Bean
    Queue dlqQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(RK);
    }

    @RequiredArgsConstructor
    private static class CustomJackson2JsonMessageConverter extends Jackson2JsonMessageConverter {

        private final IKeyService keyService;

        public CustomJackson2JsonMessageConverter(final ObjectMapper jsonObjectMapper,
                                                  final IKeyService keyService) {
            super(jsonObjectMapper);
            this.keyService = keyService;
        }

        @Override
        public Object fromMessage(final Message message) throws MessageConversionException {
            final var messageProperties = message.getMessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

            return super.fromMessage(new Message(keyService.decrypt(message.getBody()), messageProperties));
        }
    }
}
