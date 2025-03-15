package iuh.fit.se.orderservice.entity;


import iuh.fit.se.orderservice.entity.enumeration.OrderStatus;
import iuh.fit.se.orderservice.entity.enumeration.PaymentStatus;
import iuh.fit.se.orderservice.entity.enumeration.SearchQuery;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "UserActions")
public class UserActtion {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "search_query")
    private String searchQuery;

    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    private SearchQuery actionType;

    @Column(name = "action_details")
    private String actionDetails;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
