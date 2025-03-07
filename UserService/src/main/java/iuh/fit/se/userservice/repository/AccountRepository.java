package iuh.fit.se.userservice.repository;

import iuh.fit.se.userservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts", exported = false)
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {


    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Account> findByRefreshTokenAndEmail(String token, String email);

    @Query("SELECT a FROM Account a JOIN a.systemUsers su")
    List<Account> findAllSystemUserAccounts();


}
