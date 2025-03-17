package iuh.fit.se.notificationservice.repository;

import iuh.fit.se.notificationservice.entities.EmailLog;
import iuh.fit.se.notificationservice.entities.enumeration.EmailLogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, String> {
    boolean existsByOrderIdAndStatus(String orderId, EmailLogStatus status);

}
