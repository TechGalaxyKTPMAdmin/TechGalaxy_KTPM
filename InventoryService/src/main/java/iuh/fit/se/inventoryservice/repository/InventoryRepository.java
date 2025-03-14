package iuh.fit.se.inventoryservice.repository;

import iuh.fit.se.inventoryservice.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    Optional<Inventory> findByProductVariantDetailId(String productVariantDetailId);
}
