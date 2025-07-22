package com.photon.controller;

import com.photon.dto.ProductDto;
import com.photon.service.ProductService;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<ProductDto> create(@Valid @RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    // @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE) // http GET
    // http://localhost:8080/api/v1/products
    // http --stream GET http://localhost:8080/api/v1/products
    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    Flux<ProductDto> findAll() {
        return productService.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    Mono<ProductDto> findById(@PathVariable String id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    Mono<ProductDto> update(@PathVariable String id, @Valid @RequestBody ProductDto productDto) {
        return productService.update(id, productDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    Mono<Void> delete(@PathVariable String id) {
        return productService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    Mono<Void> deleteAll() {
        return productService.deleteAll();
    }
}
