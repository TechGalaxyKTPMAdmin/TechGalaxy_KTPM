package iuh.fit.se.productservice.entities;

import iuh.fit.se.productservice.entities.enumeration.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products_Variants")
public class ProductVariant {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 255)
    private String avatar;

    @Column
    private Boolean featured;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "usage_category_id")
    private UsageCategory usageCategory;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<ProductVariantDetail> productVariantDetails;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<ProductFeedback> feedbacks;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<Value> values;
}
