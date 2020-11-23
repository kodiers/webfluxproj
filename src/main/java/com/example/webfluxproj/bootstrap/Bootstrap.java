package com.example.webfluxproj.bootstrap;

import com.example.webfluxproj.domain.Category;
import com.example.webfluxproj.domain.Vendor;
import com.example.webfluxproj.repositories.CategoryRepository;
import com.example.webfluxproj.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) {
            System.out.println("loading category data");
            categoryRepository.save(Category.builder().description("Fruits").build());
            categoryRepository.save(Category.builder().description("Nuts").build());
            categoryRepository.save(Category.builder().description("Breads").build());
            categoryRepository.save(Category.builder().description("Meats").build());
            categoryRepository.save(Category.builder().description("Eggs").build());
        }
        if (vendorRepository.count().block() == 0) {
            System.out.println("loading vendor data");
            vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Buck").build());
            vendorRepository.save(Vendor.builder().firstName("Michael").lastName("Weston").build());
            vendorRepository.save(Vendor.builder().firstName("Jessie").lastName("Watters").build());
            vendorRepository.save(Vendor.builder().firstName("Bill").lastName("Nershi").build());
            vendorRepository.save(Vendor.builder().firstName("Jimmy").lastName("Buffet").build());
        }
    }
}
