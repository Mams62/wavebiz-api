package com.wavebiz.api.repository;
import com.wavebiz.api.domain.Sale;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public interface SaleRepository extends JpaRepository<Sale, UUID> {
  long countByBusinessIdAndOccurredAtBetween(UUID businessId, Instant start, Instant end);
  @Query("select coalesce(sum(s.totalAmount), 0) from Sale s where s.businessId=:businessId and s.occurredAt>=:start and s.occurredAt<:end")
  BigDecimal totalForPeriod(@Param("businessId") UUID businessId, @Param("start") Instant start, @Param("end") Instant end);
}

