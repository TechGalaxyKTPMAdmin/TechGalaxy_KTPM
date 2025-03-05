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
@Table(name = "Product_Variant_Details",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_variant_id", "color_id", "memory_id"})
)
public class ProductVariantDetail {

    @Id
    @UuidGenerator
    private String id;
    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "memory_id")
    private Memory memory;

    @Column
    private Integer viewsCount = 0;

    @Column
    private Integer quantity;

    @Column
    private Double price;

    @Column
    private Double sale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.AVAILABLE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "productVariantDetail", cascade = CascadeType.ALL)
    private List<ProductsImage> productsImage;
}
