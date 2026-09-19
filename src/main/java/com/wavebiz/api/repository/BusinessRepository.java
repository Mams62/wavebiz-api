package com.wavebiz.api.repository;
import com.wavebiz.api.domain.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface BusinessRepository extends JpaRepository<Business, UUID> {}

