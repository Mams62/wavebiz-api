package com.wavebiz.api.service;

import com.wavebiz.api.domain.*;
import com.wavebiz.api.repository.*;
import com.wavebiz.api.web.ApiDtos.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Service
public class WaveBizService {
  private final BusinessRepository businesses; private final ProductRepository products; private final SaleRepository sales;
  public WaveBizService(BusinessRepository businesses, ProductRepository products, SaleRepository sales) {
    this.businesses = businesses; this.products = products; this.sales = sales;
  }
  @Transactional public BusinessResponse createBusiness(CreateBusinessRequest request) {
    Business saved = businesses.save(new Business(request.name().trim(), blankToNull(request.phone())));
    return new BusinessResponse(saved.getId(), saved.getName(), saved.getPhone(), saved.getCurrency());
  }
  @Transactional public ProductResponse createProduct(UUID businessId, CreateProductRequest request) {
    requireBusiness(businessId);
    Product p = products.save(new Product(businessId, request.name().trim(), blankToNull(request.sku()), request.sellingPrice(), request.openingQuantity(), request.lowStockThreshold()));
    return productResponse(p);
  }
  @Transactional(readOnly=true) public List<ProductResponse> products(UUID businessId) {
    requireBusiness(businessId); return products.findAllByBusinessIdOrderByNameAsc(businessId).stream().map(this::productResponse).toList();
  }
  @Transactional public SaleResponse createSale(UUID businessId, CreateSaleRequest request) {
    requireBusiness(businessId);
    if (request.items().stream().map(SaleLineRequest::productId).distinct().count() != request.items().size())
      throw new IllegalArgumentException("A product may only appear once per sale");
    Sale sale = new Sale(businessId, request.paymentMethod(), request.occurredAt() == null ? Instant.now() : request.occurredAt());
    for (SaleLineRequest line : request.items()) {
      Product product = products.findOwnedForUpdate(line.productId(), businessId).orElseThrow(() -> new NoSuchElementException("Product not found"));
      sale.addItem(product, line.quantity());
    }
    return saleResponse(sales.save(sale));
  }
  @Transactional(readOnly=true) public DashboardResponse dashboard(UUID businessId) {
    Business business = requireBusiness(businessId);
    LocalDate today = LocalDate.now(ZoneOffset.UTC); Instant start = today.atStartOfDay().toInstant(ZoneOffset.UTC); Instant end = start.plus(1, java.time.temporal.ChronoUnit.DAYS);
    List<Product> all = products.findAllByBusinessIdOrderByNameAsc(businessId);
    long low = all.stream().filter(p -> p.getQuantity().compareTo(p.getLowStockThreshold()) <= 0).count();
    return new DashboardResponse(sales.totalForPeriod(businessId, start, end), sales.countByBusinessIdAndOccurredAtBetween(businessId, start, end), all.size(), low, business.getCurrency());
  }
  private Business requireBusiness(UUID id) { return businesses.findById(id).orElseThrow(() -> new NoSuchElementException("Business not found")); }
  private ProductResponse productResponse(Product p) { return new ProductResponse(p.getId(), p.getName(), p.getSku(), p.getSellingPrice(), p.getQuantity(), p.getLowStockThreshold()); }
  private SaleResponse saleResponse(Sale s) { return new SaleResponse(s.getId(), s.getTotalAmount(), s.getPaymentMethod(), s.getOccurredAt(), s.getItems().stream().map(i -> new SaleLineResponse(i.getProductId(), i.getProductName(), i.getQuantity(), i.getUnitPrice(), i.getLineTotal())).toList()); }
  private static String blankToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}

