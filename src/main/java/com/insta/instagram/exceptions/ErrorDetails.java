package com.insta.instagram.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ErrorDetails {

    private String message;
    private String details;
    private LocalDateTime timetamp;

    public ErrorDetails(String message, String details, LocalDateTime timetamp) {
        super();
        this.message = message;
        this.details = details;
        this.timetamp = timetamp;
    }

}
