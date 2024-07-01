package com.foot.team_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PlayerDTO(

        Long id,

        @Size(max = 25, message = "25 characters maximum for name")
        @NotNull(message = "name is required")
        @NotEmpty(message = "name can not be empty")
        String name,

        @Positive(message = "number must be positive")
        Integer no,

        int matchPlayed,

        @Size(max = 50, message = "50 characters maximum for position")
        @NotNull(message = "position is required")
        @NotEmpty(message = "position can not be empty")
        String position,

        Long teamId, // expose uniquement team ID

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
