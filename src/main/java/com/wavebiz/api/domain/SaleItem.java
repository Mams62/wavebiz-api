package com.wavebiz.api.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Table(name = "sale_items")
public class SaleItem {
    @Id private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "sale_id") private Sale sale;
    @Column(nullable = false) private UUID productId;
    @Column(nullable = false) private String productName;
    @Column(nullable = false, precision = 19, scale = 3) private BigDecimal quantity;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal unitPrice;
    @Column(nullable = false, precision = 19, scale = 2) private BigDecimal lineTotal;
    protected SaleItem() {}
    SaleItem(Sale sale, Product product, BigDecimal quantity) {
        this.id = UUID.randomUUID(); this.sale = sale; this.productId = product.getId();
        this.productName = product.getName(); this.quantity = quantity; this.unitPrice = product.getSellingPrice();
        this.lineTotal = unitPrice.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }
    public UUID getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getLineTotal() { return lineTotal; }
}
