package iuh.fit.se.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.queue.notification-retry}")
    private String notificationRetryQueue;

    @Value("${rabbitmq.queue.notification-dlq}")
    private String notificationDlqQueue;

    @Value("${rabbitmq.routing-key.notification}")
    private String notificationRoutingKey;

    @Value("${rabbitmq.routing-key.notification-retry}")
    private String notificationRetryRoutingKey;

    @Value("${rabbitmq.routing-key.notification-dlq}")
    private String notificationDlqRoutingKey;

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(notificationQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", notificationRetryRoutingKey)
                .build();
    }

    // Retry Queue with TTL and DLQ
    @Bean
    public Queue notificationRetryQueue() {
        return QueueBuilder.durable(notificationRetryQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", notificationRoutingKey) // Back to main queue
                .withArgument("x-message-ttl", 5000) // 5 seconds retry
                .build();
    }

    // DLQ (store fatal errors)
    @Bean
    public Queue notificationDlqQueue() {
        return QueueBuilder.durable(notificationDlqQueue).build();
    }

    // Bindings
    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(orderExchange()).with(notificationRoutingKey);
    }

    @Bean
    public Binding notificationRetryBinding() {
        return BindingBuilder.bind(notificationRetryQueue()).to(orderExchange()).with(notificationRetryRoutingKey);
    }

    @Bean
    public Binding notificationDlqBinding() {
        return BindingBuilder.bind(notificationDlqQueue()).to(orderExchange()).with(notificationDlqRoutingKey);
    }

    // JSON Message Converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
