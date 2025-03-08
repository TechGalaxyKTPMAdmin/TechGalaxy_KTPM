package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "colors", path = "colors", exported = false)
public interface ColorRepository extends JpaRepository<Color, String> {
    List<Color> findColorsByIdIsIn(List<String> ids);

}
