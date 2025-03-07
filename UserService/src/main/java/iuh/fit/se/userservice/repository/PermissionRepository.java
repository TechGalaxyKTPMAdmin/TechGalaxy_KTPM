package iuh.fit.se.userservice.repository;

import iuh.fit.se.userservice.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "permissions", path = "permissions", exported = false)
public interface PermissionRepository extends JpaRepository<Permission, String>,
        JpaSpecificationExecutor<Permission> {
    boolean existsByModuleAndApiPathAndMethod(String module, String apiPath, String method);

    List<Permission> findByIdIn(List<String> id);

    //    findByName
    Permission findByName(String name);

}