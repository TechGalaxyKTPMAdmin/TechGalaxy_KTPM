package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "memories", path = "memories", exported = false)
public interface MemoryRepository extends JpaRepository<Memory, String> {

    List<Memory> findMemoriesByIdIsIn(List<String> ids);
}
