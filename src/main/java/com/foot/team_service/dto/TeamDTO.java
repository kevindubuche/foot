package com.foot.team_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record TeamDTO(
        Long id,

        @Size(max = 25, message = "25 characters maximum for name")
        @NotNull(message = "name is required")
        @NotEmpty(message = "name can not be empty")
        String name,

        @Size(max = 10, message = "10 characters maximum for acronym")
        @NotNull(message = "acronym is required")
        @NotEmpty(message = "acronym can not be empty")
        String acronym,

        @NotNull(message = "budget is required")
        Integer budget,

        List<@Valid PlayerDTO> players,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
