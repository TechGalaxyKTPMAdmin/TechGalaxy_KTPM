package iuh.fit.se.orderservice.config;

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

    @Value("${rabbitmq.queue.order-status-updated}")
    private String orderStatusUpdatedQueue;

    @Value("${rabbitmq.queue.inventory-failed}")
    private String inventoryFailedQueue;

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.queue.order-reply}")
    private String orderReplyQueue;

    @Value("${rabbitmq.queue.payment-completed}")
    private String paymentCompletedQueue;

    @Value("${rabbitmq.queue.payment-failed}")
    private String paymentFailedQueue;

    @Value("${rabbitmq.routing-key.order-status-updated}")
    private String orderStatusUpdatedRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-failed}")
    private String inventoryFailedRoutingKey;

    @Value("${rabbitmq.routing-key.notification}")
    private String notificationRoutingKey;

    @Value("${rabbitmq.routing-key.payment-completed}")
    private String paymentCompletedRoutingKey;

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Queue orderStatusUpdatedQueue() {
        return new Queue(orderStatusUpdatedQueue, true);
    }

    @Bean
    public Queue inventoryFailedQueue() {
        return new Queue(inventoryFailedQueue, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    @Bean
    public Queue orderReplyQueue() {
        return new Queue(orderReplyQueue, true);
    }

    @Bean
    public Queue paymentCompletedQueue() {
        return new Queue(paymentCompletedQueue, true);
    }

    @Bean
    public Queue paymentFailedQueue() {
        return new Queue(paymentFailedQueue, true);
    }

    @Bean
    public Binding orderStatusUpdatedBinding() {
        return BindingBuilder.bind(orderStatusUpdatedQueue()).to(orderExchange()).with(orderStatusUpdatedRoutingKey);
    }

    @Bean
    public Binding inventoryFailedBinding() {
        return BindingBuilder.bind(inventoryFailedQueue()).to(orderExchange()).with(inventoryFailedRoutingKey);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue()).to(orderExchange()).with(notificationRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding paymentCompletedBinding() {
        return BindingBuilder.bind(paymentCompletedQueue())
                .to(orderExchange())
                .with(paymentCompletedRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
