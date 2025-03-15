package iuh.fit.se.paymentservice.config;

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

    @Value("${rabbitmq.queue.inventory-reserved}")
    private String inventoryReservedQueue;

    @Value("${rabbitmq.queue.inventory-reserved-retry}")
    private String inventoryReservedRetryQueue;

    @Value("${rabbitmq.queue.inventory-reserved-dlq}")
    private String inventoryReservedDlqQueue;

    @Value("${rabbitmq.routing-key.inventory-reserved}")
    private String inventoryReservedRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-reserved-retry}")
    private String inventoryReservedRetryRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-reserved-dlq}")
    private String inventoryReservedDlqRoutingKey;

    // Exchange
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    // Main queue - sẽ retry khi lỗi
    @Bean
    public Queue inventoryReservedQueue() {
        return QueueBuilder.durable(inventoryReservedQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", inventoryReservedRetryRoutingKey)
                .build();
    }

    // Retry queue (TTL + quay lại main queue)
    @Bean
    public Queue inventoryReservedRetryQueue() {
        return QueueBuilder.durable(inventoryReservedRetryQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", inventoryReservedRoutingKey)
                .withArgument("x-message-ttl", 5000) // Retry sau 5 giây
                .build();
    }

    // DLQ (Lưu những message lỗi nghiêm trọng không xử lý được)
    @Bean
    public Queue inventoryReservedDlqQueue() {
        return QueueBuilder.durable(inventoryReservedDlqQueue).build();
    }

    // Bindings
    @Bean
    public Binding inventoryReservedBinding() {
        return BindingBuilder.bind(inventoryReservedQueue()).to(orderExchange()).with(inventoryReservedRoutingKey);
    }

    @Bean
    public Binding inventoryReservedRetryBinding() {
        return BindingBuilder.bind(inventoryReservedRetryQueue()).to(orderExchange()).with(inventoryReservedRetryRoutingKey);
    }

    @Bean
    public Binding inventoryReservedDlqBinding() {
        return BindingBuilder.bind(inventoryReservedDlqQueue()).to(orderExchange()).with(inventoryReservedDlqRoutingKey);
    }

    // RabbitTemplate và Message Converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
