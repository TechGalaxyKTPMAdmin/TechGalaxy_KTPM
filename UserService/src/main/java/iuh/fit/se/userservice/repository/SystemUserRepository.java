package iuh.fit.se.userservice.repository;

import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.SystemUser;
import iuh.fit.se.userservice.entities.enumeration.SystemUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "systemUsers", path = "systemUsers", exported = false)
public interface SystemUserRepository extends JpaRepository<SystemUser, String>, JpaSpecificationExecutor<SystemUser> {
    // Tìm danh sách SystemUser theo trạng thái
    List<SystemUser> findBySystemUserStatus(SystemUserStatus status);

    // Tìm SystemUser theo email thông qua Account
    @Query("SELECT su FROM SystemUser su WHERE su.account.email = :email")
    SystemUser findSystemUserByEmail(@Param("email") String email);

    // Tìm tất cả SystemUser liên kết với một Account cụ thể
    List<SystemUser> findByAccount(Account account);
}
