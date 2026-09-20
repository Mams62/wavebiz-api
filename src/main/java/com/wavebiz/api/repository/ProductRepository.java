package com.wavebiz.api.repository;
import com.wavebiz.api.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.*;
public interface ProductRepository extends JpaRepository<Product, UUID> {
  List<Product> findAllByBusinessIdOrderByNameAsc(UUID businessId);
  long countByBusinessIdAndQuantityLessThanEqual(UUID businessId, java.math.BigDecimal quantity);
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Product p where p.id = :id and p.businessId = :businessId")
  Optional<Product> findOwnedForUpdate(@Param("id") UUID id, @Param("businessId") UUID businessId);
}

