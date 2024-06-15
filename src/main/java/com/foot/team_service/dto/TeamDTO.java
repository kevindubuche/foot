package com.foot.team_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private String acronym;
    private Integer budget;
    private List<PlayerDTO> players;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Nested DTO pour Player
    @Data
    public static class PlayerDTO {
        private Long id;
        private String name;
        private Integer no;
        private int matchPlayed;
        private String position;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
