package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "attributes", path = "attributes", exported = false)
public interface AttributeRepository extends JpaRepository<Attribute, String> {

}
