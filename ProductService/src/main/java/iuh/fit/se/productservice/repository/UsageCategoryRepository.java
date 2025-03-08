package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.UsageCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "usageCategories", path = "usageCategories", exported = false)
public interface UsageCategoryRepository  extends JpaRepository<UsageCategory, String> {

}
