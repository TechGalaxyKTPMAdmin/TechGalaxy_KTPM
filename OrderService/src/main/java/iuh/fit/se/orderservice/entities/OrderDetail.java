package iuh.fit.se.orderservice.entities;

import iuh.fit.se.orderservice.entities.enumeration.DetailStatus;
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
@Builder
@Entity
@Table(name = "Orders_Details")
public class OrderDetail {

    @Id
    @UuidGenerator
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DetailStatus detailStatus = DetailStatus.PROCESSING;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @Column(name = "product_variant_detail_id", nullable = false)
    private String productVariantDetailId;

    @Column
    private Integer quantity;

    @Column
    private Double price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
