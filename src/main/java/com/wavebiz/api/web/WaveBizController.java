package com.wavebiz.api.web;

import com.wavebiz.api.service.WaveBizService;
import com.wavebiz.api.web.ApiDtos.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class WaveBizController {
  private final WaveBizService service;
  public WaveBizController(WaveBizService service) { this.service = service; }
  @GetMapping("/health") public Map<String,String> health() { return Map.of("status", "UP", "service", "wavebiz-api"); }
  @PostMapping("/businesses") @ResponseStatus(HttpStatus.CREATED)
  public BusinessResponse createBusiness(@Valid @RequestBody CreateBusinessRequest request) { return service.createBusiness(request); }
  @PostMapping("/businesses/{businessId}/products") @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse createProduct(@PathVariable UUID businessId, @Valid @RequestBody CreateProductRequest request) { return service.createProduct(businessId, request); }
  @GetMapping("/businesses/{businessId}/products")
  public List<ProductResponse> products(@PathVariable UUID businessId) { return service.products(businessId); }
  @PostMapping("/businesses/{businessId}/sales") @ResponseStatus(HttpStatus.CREATED)
  public SaleResponse createSale(@PathVariable UUID businessId, @Valid @RequestBody CreateSaleRequest request) { return service.createSale(businessId, request); }
  @GetMapping("/businesses/{businessId}/dashboard")
  public DashboardResponse dashboard(@PathVariable UUID businessId) { return service.dashboard(businessId); }
}

