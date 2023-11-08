package com.example.hotelreservationsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CancelDTO {

    @NotNull
    @Positive
    private Long id;

    public Long getId() {
        return id;
    }

    public CancelDTO setId(Long id) {
        this.id = id;
        return this;
    }
}
