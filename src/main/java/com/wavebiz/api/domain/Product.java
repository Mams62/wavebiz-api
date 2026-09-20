package com.wavebiz.api.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
    @Id private UUID id;
    @Column(nullable = false) private UUID businessId;
    @Column(nullable = false, length = 160) private String name;
    @Column(length = 80) private String sku;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal sellingPrice;
    @Column(nullable = false, precision = 19, scale = 3) private BigDecimal quantity;
    @Column(nullable = false, precision = 19, scale = 3) private BigDecimal lowStockThreshold;
    @Version private long version;
    @Column(nullable = false) private Instant createdAt;

    protected Product() {}
    public Product(UUID businessId, String name, String sku, BigDecimal price, BigDecimal quantity, BigDecimal threshold) {
        this.id = UUID.randomUUID(); this.businessId = businessId; this.name = name; this.sku = sku;
        this.sellingPrice = price; this.quantity = quantity; this.lowStockThreshold = threshold; this.createdAt = Instant.now();
    }
    public void deduct(BigDecimal amount) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");
        if (quantity.compareTo(amount) < 0) throw new IllegalStateException("Insufficient stock for " + name);
        quantity = quantity.subtract(amount);
    }
    public UUID getId() { return id; }
    public UUID getBusinessId() { return businessId; }
    public String getName() { return name; }
    public String getSku() { return sku; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getLowStockThreshold() { return lowStockThreshold; }
    public Instant getCreatedAt() { return createdAt; }
}

