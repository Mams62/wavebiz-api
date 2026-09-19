package com.wavebiz.api.web;

import com.wavebiz.api.domain.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public final class ApiDtos {
  private ApiDtos() {}
  public record CreateBusinessRequest(@NotBlank @Size(max=120) String name, @Size(max=20) String phone) {}
  public record BusinessResponse(UUID id, String name, String phone, String currency) {}
  public record CreateProductRequest(@NotBlank @Size(max=160) String name, @Size(max=80) String sku,
      @NotNull @DecimalMin("0.00") BigDecimal sellingPrice,
      @NotNull @DecimalMin("0.000") BigDecimal openingQuantity,
      @NotNull @DecimalMin("0.000") BigDecimal lowStockThreshold) {}
  public record ProductResponse(UUID id, String name, String sku, BigDecimal sellingPrice, BigDecimal quantity, BigDecimal lowStockThreshold) {}
  public record SaleLineRequest(@NotNull UUID productId, @NotNull @DecimalMin("0.001") BigDecimal quantity) {}
  public record CreateSaleRequest(@NotEmpty List<@Valid SaleLineRequest> items, @NotNull PaymentMethod paymentMethod, Instant occurredAt) {}
  public record SaleLineResponse(UUID productId, String name, BigDecimal quantity, BigDecimal unitPrice, BigDecimal lineTotal) {}
  public record SaleResponse(UUID id, BigDecimal totalAmount, PaymentMethod paymentMethod, Instant occurredAt, List<SaleLineResponse> items) {}
  public record DashboardResponse(BigDecimal todaySales, long todayTransactions, long productCount, long lowStockCount, String currency) {}
}

