package com.andra.ticketconcert.service;

import com.andra.ticketconcert.dto.ResConcertDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConcertService {

    Page<ResConcertDto> getConcerts(Pageable pageable);

    List<ResConcertDto> getConcertsByNameOrLocation(String searchValue, Pageable pageable);
}
