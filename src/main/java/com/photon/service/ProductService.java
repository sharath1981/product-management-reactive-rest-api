package com.photon.service;

import com.photon.constant.AppConstant;
import com.photon.dto.ProductDto;
import com.photon.mapper.ProductMapper;
import com.photon.repository.ProductRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Mono<ProductDto> create(ProductDto productDto) {
        if (Objects.isNull(productDto)) {
            return Mono.empty();
        }
        final var product = productMapper.toProduct(productDto);
        return productRepository.save(product).map(productMapper::toProductDto);
    }

    public Flux<ProductDto> findAll() {
        return productRepository.findAll().map(productMapper::toProductDto);
    }

    public Mono<ProductDto> findById(String id) {
        return productRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException(AppConstant.PRODUCT_NOT_FOUND)))
                .map(productMapper::toProductDto);
    }

    public Mono<ProductDto> update(String id, ProductDto productDto) {
        if (Objects.isNull(productDto)) {
            return Mono.empty();
        }
        return productRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException(AppConstant.PRODUCT_NOT_FOUND)))
                .map(product -> productMapper.mergeToProduct(productDto, product))
                .flatMap(productRepository::save)
                .map(productMapper::toProductDto);
    }

    public Mono<Void> delete(String id) {
        return productRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException(AppConstant.PRODUCT_NOT_FOUND)))
                .flatMap(existing -> productRepository.deleteById(id));
    }

    public Mono<Void> deleteAll() {
        return productRepository.deleteAll();
    }
}
