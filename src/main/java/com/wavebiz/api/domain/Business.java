package com.wavebiz.api.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "businesses")
public class Business {
    @Id private UUID id;
    @Column(nullable = false, length = 120) private String name;
    @Column(length = 20) private String phone;
    @Column(nullable = false, length = 3) private String currency;
    @Column(nullable = false) private Instant createdAt;

    protected Business() {}
    public Business(String name, String phone) {
        this.id = UUID.randomUUID(); this.name = name; this.phone = phone;
        this.currency = "NGN"; this.createdAt = Instant.now();
    }
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getCurrency() { return currency; }
    public Instant getCreatedAt() { return createdAt; }
}

