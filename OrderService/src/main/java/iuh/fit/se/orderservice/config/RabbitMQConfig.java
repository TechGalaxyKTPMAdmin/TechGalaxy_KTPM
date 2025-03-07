package iuh.fit.se.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // Khai báo tên các Exchange
    @Value("${rabbitmq.exchange.order}")
    private String orderExchange;

    // Khai báo tên các Queue
    @Value("${rabbitmq.queue.order-created}")
    private String orderCreatedQueue;

    @Value("${rabbitmq.queue.order-status-updated}")
    private String orderStatusUpdatedQueue;

    @Value("${rabbitmq.queue.inventory-update}")
    private String inventoryUpdateQueue;

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    // Khai báo routing keys
    @Value("${rabbitmq.routing-key.order-created}")
    private String orderCreatedRoutingKey;

    @Value("${rabbitmq.routing-key.order-status-updated}")
    private String orderStatusUpdatedRoutingKey;

    @Value("${rabbitmq.routing-key.inventory-update}")
    private String inventoryUpdateRoutingKey;

    @Value("${rabbitmq.routing-key.notification}")
    private String notificationRoutingKey;

    // Tạo Exchange
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    // Tạo các Queue
    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(orderCreatedQueue, true);
    }

    @Bean
    public Queue orderStatusUpdatedQueue() {
        return new Queue(orderStatusUpdatedQueue, true);
    }

    @Bean
    public Queue inventoryUpdateQueue() {
        return new Queue(inventoryUpdateQueue, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    // Binding các Queue với Exchange sử dụng routing keys
    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder
                .bind(orderCreatedQueue())
                .to(orderExchange())
                .with(orderCreatedRoutingKey);
    }

    @Bean
    public Binding orderStatusUpdatedBinding() {
        return BindingBuilder
                .bind(orderStatusUpdatedQueue())
                .to(orderExchange())
                .with(orderStatusUpdatedRoutingKey);
    }

    @Bean
    public Binding inventoryUpdateBinding() {
        return BindingBuilder
                .bind(inventoryUpdateQueue())
                .to(orderExchange())
                .with(inventoryUpdateRoutingKey);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(orderExchange())
                .with(notificationRoutingKey);
    }

    // Cấu hình message converter để chuyển đổi objects sang JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Cấu hình RabbitTemplate để gửi messages
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
