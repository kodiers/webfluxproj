package com.example.webfluxproj.controllers;

import com.example.webfluxproj.domain.Category;
import com.example.webfluxproj.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().description("Cat1").build(),
                                Category.builder().description("Cat2").build()));
        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));
        webTestClient.get().uri("/api/v1/categories/111")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreate() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cat").build());

        webTestClient.post().uri("/api/v1/categories")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cat").build());

        webTestClient.put()
                .uri("/api/v1/categories/1")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatch() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Other cat").build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cat").build());

        webTestClient.patch()
                .uri("/api/v1/categories/1")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository).save(any());
    }

    @Test
    public void testPatchNotUpdate() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Other cat").build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Other cat").build());

        webTestClient.patch()
                .uri("/api/v1/categories/1")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository, never()).save(any());
    }
}