package com.photon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductDto(String id,
                         @NotBlank String name,
                         String description,
                         @Positive Double price) {
}
