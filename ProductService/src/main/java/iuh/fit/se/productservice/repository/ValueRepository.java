package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "values", path = "values", exported = false)
public interface ValueRepository extends JpaRepository<Value,String> {
        @Query("""
    SELECT v FROM Value v
    WHERE v.attribute.name = :attributeName
    AND v.id = (
        SELECT MIN(v2.id) 
        FROM Value v2 
        WHERE v2.attribute.name = :attributeName 
        AND v2.value = v.value
    )
    """)
        List<Value> findDistinctValuesByNameAndAttributeName(@Param("attributeName") String attributeName);

        List<Value> findAllByProductVariantId(String variantId);


}
