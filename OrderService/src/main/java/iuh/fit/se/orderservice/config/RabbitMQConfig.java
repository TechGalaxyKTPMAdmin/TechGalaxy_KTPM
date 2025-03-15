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

    @Value("${rabbitmq.queue.payment-completed-retry}")
    private String paymentCompletedRetryQueue;

    @Value("${rabbitmq.queue.payment-completed-dlq}")
    private String paymentCompletedDlqQueue;

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

    @Value("${rabbitmq.routing-key.payment-completed-retry}")
    private String paymentCompletedRetryRoutingKey;

    @Value("${rabbitmq.routing-key.payment-completed-dlq}")
    private String paymentCompletedDlqRoutingKey;

    @Value("${rabbitmq.routing-key.payment-failed}")
    private String paymentFailedRoutingKey;


    @Value("${rabbitmq.queue.notification-retry}")
    private String notificationRetryQueue;

    @Value("${rabbitmq.queue.notification-dlq}")
    private String notificationDlqQueue;

    @Value("${rabbitmq.routing-key.notification-retry}")
    private String notificationRetryRoutingKey;

    @Value("${rabbitmq.routing-key.notification-dlq}")
    private String notificationDlqRoutingKey;
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    // === QUEUES ===

    @Bean
    public Queue orderStatusUpdatedQueue() {
        return new Queue(orderStatusUpdatedQueue, true);
    }

    @Bean
    public Queue inventoryFailedQueue() {
        return new Queue(inventoryFailedQueue, true);
    }


    @Bean
    public Queue orderReplyQueue() {
        return new Queue(orderReplyQueue, true);
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

    @Bean
    public Queue paymentCompletedQueue() {
        return QueueBuilder.durable(paymentCompletedQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", paymentCompletedRetryRoutingKey)
                .build();
    }

    @Bean
    public Queue paymentCompletedRetryQueue() {
        return QueueBuilder.durable(paymentCompletedRetryQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", paymentCompletedQueue)
                .withArgument("x-message-ttl", 5000) // Retry sau 5 giây
                .build();
    }

    @Bean
    public Queue paymentCompletedDlqQueue() {
        return QueueBuilder.durable(paymentCompletedDlqQueue).build();
    }

    @Bean
    public Queue paymentFailedQueue() {
        return new Queue(paymentFailedQueue, true);
    }

    // === BINDINGS ===

    @Bean
    public Binding orderStatusUpdatedBinding() {
        return BindingBuilder.bind(orderStatusUpdatedQueue()).to(orderExchange()).with(orderStatusUpdatedRoutingKey);
    }

    @Bean
    public Binding inventoryFailedBinding() {
        return BindingBuilder.bind(inventoryFailedQueue()).to(orderExchange()).with(inventoryFailedRoutingKey);
    }

    @Bean
    public Binding paymentCompletedBinding() {
        return BindingBuilder.bind(paymentCompletedQueue()).to(orderExchange()).with(paymentCompletedRoutingKey);
    }

    @Bean
    public Binding paymentCompletedRetryBinding() {
        return BindingBuilder.bind(paymentCompletedRetryQueue()).to(orderExchange()).with(paymentCompletedRetryRoutingKey);
    }

    @Bean
    public Binding paymentCompletedDlqBinding() {
        return BindingBuilder.bind(paymentCompletedDlqQueue()).to(orderExchange()).with(paymentCompletedDlqRoutingKey);
    }

    @Bean
    public Binding paymentFailedBinding() {
        return BindingBuilder.bind(paymentFailedQueue()).to(orderExchange()).with(paymentFailedRoutingKey);
    }

    // === RabbitTemplate + MessageConverter ===
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
