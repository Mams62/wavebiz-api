# WaveBiz API

Spring Boot and PostgreSQL backend shared by the WaveBiz Android and PWA clients.

## Implemented vertical slice

- business creation;
- product and inventory creation;
- transactional sale recording;
- row locking during stock deduction;
- insufficient-stock protection;
- persisted daily dashboard totals;
- PostgreSQL schema migrations with Flyway;
- configurable PWA CORS allowlist;
- RFC 9457-style Problem Details errors.

## Local setup

1. Copy `.env.example` to `.env` and choose local credentials.
2. Run `docker compose up -d postgres`.
3. Open this directory in IntelliJ IDEA and run `WaveBizApiApplication`.
4. Check `GET http://localhost:8080/api/v1/health`.

Java 17 and Maven 3.9+ are required. Authentication and staff authorization are the next security milestone. Do not expose this milestone publicly as a production API until those controls are implemented.

In IntelliJ IDEA, import `pom.xml` as a Maven project and select a Java 17 Project SDK. The Maven compiler release is pinned to Java 17 in `pom.xml`.
