package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.ProductVariantDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "productVariantDetails", path = "productVariantDetails", exported = false)
public interface ProductVariantDetailRepository extends JpaRepository<ProductVariantDetail, String> {

    List<ProductVariantDetail> findAllByProductVariantId(String productVariantId);

    List<ProductVariantDetail> findAllByIdIsIn(List<String> ids);

    @Modifying
    @Query("UPDATE ProductVariantDetail p SET p.price = :price, p.sale = :sale  WHERE p.productVariant.id = :variantId")
    int updatePriceByVariantId(@Param("price") Double price, @Param("sale") Double sale, @Param("variantId") String variantId);

    @Query(value = """
            SELECT DISTINCT pvd
            FROM ProductVariantDetail pvd
            JOIN pvd.productVariant pv 
            JOIN pv.product p
            JOIN p.trademark t
            JOIN pvd.memory m
            JOIN pv.values vl 
            WHERE (:trademark IS NULL OR t.id IN :trademark)
              AND (pvd.price BETWEEN COALESCE(:minPrice, 0) AND COALESCE(:maxPrice, 999999999))
              AND (:memory IS NULL OR m.id IN :memory)
              AND (:usageCategoryId IS NULL OR pv.usageCategory.id IN :usageCategoryId)
              AND (:values IS NULL OR vl.value IN :values)
              AND pvd.id IN (
                           SELECT MIN(pvd2.id)
                           FROM ProductVariantDetail pvd2
                           GROUP BY pvd2.productVariant.id
                       )
            """)
    Page<ProductVariantDetail> findFilteredProductDetails(
            @Param("trademark") List<String> trademark,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("memory") List<String> memory,
            @Param("usageCategoryId") List<String> usageCategoryId,
            @Param("values") List<String> values,
            Pageable pageable
    );

    @Query("SELECT pvd FROM ProductVariantDetail pvd JOIN pvd.productVariant pv JOIN pvd.color c JOIN pvd.memory m WHERE pv.id = :productVariantId AND c.id = :colorId AND m.id = :memoryId")
    ProductVariantDetail findProductVariantDetailByProductVariantAndColorAndMemory(@Param("productVariantId") String productVariantId, @Param("colorId") String colorId, @Param("memoryId") String memoryId);

    @Query("SELECT COUNT(od) FROM OrderDetail od WHERE od.productVariantDetail.id = :productVariantDetailId")
    long countOrderDetailsByProductVariantDetailId(@Param("productVariantDetailId") String productVariantDetailId);
}
