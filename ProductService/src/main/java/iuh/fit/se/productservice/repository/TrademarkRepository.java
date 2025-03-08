package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.Trademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "trademarks", path = "trademarks", exported = false)
public interface TrademarkRepository extends JpaRepository<Trademark, String> {
    Trademark findTrademarkByName(String name);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.trademark.id = :trademarkId")
    long countProductsByTrademarkId(@Param("trademarkId") String trademarkId);
}
