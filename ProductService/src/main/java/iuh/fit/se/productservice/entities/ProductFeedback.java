package iuh.fit.se.productservice.entities;

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
@Table(name = "Product_Feedbacks")
public class ProductFeedback {

    @Id
    @UuidGenerator
    private String id;

    @Column
    private Integer feedbackRating;

    @Column(columnDefinition = "TEXT")
    private String feedbackText;

    private String customerId;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "productFeedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImgProductFeedback> imgProductFeedbacks;
}
