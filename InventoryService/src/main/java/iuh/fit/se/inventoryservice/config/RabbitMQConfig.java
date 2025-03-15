package iuh.fit.se.inventoryservice.config;

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

    @Value("${rabbitmq.queue.order-created}")
    private String orderCreatedQueue;

    @Value("${rabbitmq.queue.order-created-retry}")
    private String orderCreatedRetryQueue;

    @Value("${rabbitmq.queue.order-created-dlq}")
    private String orderCreatedDlqQueue;

    @Value("${rabbitmq.queue.inventory-update}")
    private String inventoryUpdateQueue;

    @Value("${rabbitmq.queue.inventory-update-retry}")
    private String inventoryUpdateRetryQueue;

    @Value("${rabbitmq.queue.inventory-update-dlq}")
    private String inventoryUpdateDlqQueue;

    @Value("${rabbitmq.queue.inventory-rollback}")
    private String inventoryRollbackQueue;

    @Value("${rabbitmq.routing-key.order-created}")
    private String orderCreatedRoutingKey;

    @Value("${rabbitmq.routing-key.order-created-retry}")
    private String orderCreatedRetryRoutingKey;

    @Value("${rabbitmq.routing-key.order-created-dlq}")
    private String orderCreatedDlqRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-update}")
    private String inventoryUpdateRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-update-retry}")
    private String inventoryUpdateRetryRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-update-dlq}")
    private String inventoryUpdateDlqRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-rollback}")
    private String inventoryRollbackRoutingKey;

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return QueueBuilder.durable(orderCreatedQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", orderCreatedRetryRoutingKey)
                .build();
    }

    @Bean
    public Queue orderCreatedRetryQueue() {
        return QueueBuilder.durable(orderCreatedRetryQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", orderCreatedQueue)
                .withArgument("x-message-ttl", 5000)
                .build();
    }

    @Bean
    public Queue orderCreatedDlqQueue() {
        return QueueBuilder.durable(orderCreatedDlqQueue).build();
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder.bind(orderCreatedQueue()).to(orderExchange()).with(orderCreatedRoutingKey);
    }

    @Bean
    public Binding orderCreatedRetryBinding() {
        return BindingBuilder.bind(orderCreatedRetryQueue()).to(orderExchange()).with(orderCreatedRetryRoutingKey);
    }

    @Bean
    public Binding orderCreatedDlqBinding() {
        return BindingBuilder.bind(orderCreatedDlqQueue()).to(orderExchange()).with(orderCreatedDlqRoutingKey);
    }

    @Bean
    public Queue inventoryUpdateQueue() {
        return QueueBuilder.durable(inventoryUpdateQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", inventoryUpdateRetryRoutingKey)
                .build();
    }

    @Bean
    public Queue inventoryUpdateRetryQueue() {
        return QueueBuilder.durable(inventoryUpdateRetryQueue)
                .withArgument("x-dead-letter-exchange", orderExchange)
                .withArgument("x-dead-letter-routing-key", inventoryUpdateQueue)
                .withArgument("x-message-ttl", 5000)
                .build();
    }

    @Bean
    public Queue inventoryUpdateDlqQueue() {
        return QueueBuilder.durable(inventoryUpdateDlqQueue).build();
    }

    @Bean
    public Binding inventoryUpdateBinding() {
        return BindingBuilder.bind(inventoryUpdateQueue()).to(orderExchange()).with(inventoryUpdateRoutingKey);
    }

    @Bean
    public Binding inventoryUpdateRetryBinding() {
        return BindingBuilder.bind(inventoryUpdateRetryQueue()).to(orderExchange()).with(inventoryUpdateRetryRoutingKey);
    }

    @Bean
    public Binding inventoryUpdateDlqBinding() {
        return BindingBuilder.bind(inventoryUpdateDlqQueue()).to(orderExchange()).with(inventoryUpdateDlqRoutingKey);
    }

    @Bean
    public Queue inventoryRollbackQueue() {
        return new Queue(inventoryRollbackQueue, true);
    }

    @Bean
    public Binding inventoryRollbackBinding() {
        return BindingBuilder.bind(inventoryRollbackQueue()).to(orderExchange()).with(inventoryRollbackRoutingKey);
    }

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
