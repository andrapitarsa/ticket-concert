package com.andra.ticketconcert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResConcertDto {
    private Integer concertId;
    private String concertName;
    private LocalDateTime date;
    private String artistName;
    private String location;
}
