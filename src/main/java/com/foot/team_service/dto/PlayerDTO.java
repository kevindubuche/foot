package com.foot.team_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayerDTO {
    private Long id;
    private String name;
    private Integer no;
    private int matchPlayed;
    private String position;
    private Long teamId; // expose uniquement team ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
