package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.ProductsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "productsImages", path = "productsImages", exported = false)
public interface ProductsImageRepository extends JpaRepository<ProductsImage, String> {
    List<ProductsImage> findAllByProductVariantDetailId(String productVariantDetailId);

    @Query("SELECT p FROM ProductsImage p WHERE p.productVariantDetail.productVariant.id = :productVariantId")
    List<ProductsImage> findAllByProductVariantId(@Param("productVariantId") String productVariantId);

//    List<ProductsImage> findAllByProductVariant_IdDetail_ProductVariant(String productVariantId);
}
