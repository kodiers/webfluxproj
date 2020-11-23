package com.example.webfluxproj.repositories;

import com.example.webfluxproj.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
