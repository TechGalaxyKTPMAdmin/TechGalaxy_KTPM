package iuh.fit.se.userservice.repository;

import iuh.fit.se.userservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles", exported = false)

public interface RoleRepository extends JpaRepository<Role, String>,
        JpaSpecificationExecutor<Role> {
    boolean existsByName(String name);

    Role findByName(String name);

    List<Role> findByIdIn(List<String> id);


    List<Role> findByNameIn(List<String> name);

    @Query("SELECT r FROM Role r JOIN r.accounts a WHERE a.email = :email")
    List<Role> findRolesByAccountEmail(@Param("email") String email);
}
