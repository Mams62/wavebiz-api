package com.wavebiz.api.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "sales")
public class Sale {
    @Id private UUID id;
    @Column(nullable = false) private UUID businessId;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private PaymentMethod paymentMethod;
    @Column(nullable = false) private Instant occurredAt;
    @Column(nullable = false) private Instant createdAt;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> items = new ArrayList<>();

    protected Sale() {}
    public Sale(UUID businessId, PaymentMethod method, Instant occurredAt) {
        this.id = UUID.randomUUID(); this.businessId = businessId; this.paymentMethod = method;
        this.occurredAt = occurredAt; this.createdAt = Instant.now(); this.totalAmount = BigDecimal.ZERO;
    }
    public void addItem(Product product, BigDecimal quantity) {
        product.deduct(quantity);
        SaleItem item = new SaleItem(this, product, quantity);
        items.add(item); totalAmount = totalAmount.add(item.getLineTotal());
    }
    public UUID getId() { return id; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public Instant getOccurredAt() { return occurredAt; }
    public List<SaleItem> getItems() { return List.copyOf(items); }
}

