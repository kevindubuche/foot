package com.foot.team_service.exception;

import java.util.Date;

public record ErrorDetails(
        Date timestamp,
        String message,
        String details
) {
}
