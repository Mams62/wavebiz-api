package com.wavebiz.api.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class SaleTest {
  @Test void deductsStockAndCalculatesTotal() {
    Product p = new Product(UUID.randomUUID(), "Rice", "RICE-01", new BigDecimal("2500.00"), new BigDecimal("10.000"), BigDecimal.ONE);
    Sale sale = new Sale(p.getBusinessId(), PaymentMethod.CASH, Instant.now());
    sale.addItem(p, new BigDecimal("2.000"));
    assertEquals(new BigDecimal("8.000"), p.getQuantity());
    assertEquals(new BigDecimal("5000.00"), sale.getTotalAmount());
  }
  @Test void rejectsInsufficientStock() {
    Product p = new Product(UUID.randomUUID(), "Milk", null, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO);
    Sale sale = new Sale(p.getBusinessId(), PaymentMethod.TRANSFER, Instant.now());
    assertThrows(IllegalStateException.class, () -> sale.addItem(p, new BigDecimal("2")));
  }
}

