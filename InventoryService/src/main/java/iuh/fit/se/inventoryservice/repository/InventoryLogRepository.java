package iuh.fit.se.inventoryservice.repository;

import iuh.fit.se.inventoryservice.entities.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, String> {
}
