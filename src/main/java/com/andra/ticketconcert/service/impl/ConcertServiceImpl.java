package com.andra.ticketconcert.service.impl;

import com.andra.ticketconcert.dto.ResConcertDto;
import com.andra.ticketconcert.mapper.ConcertMapper;
import com.andra.ticketconcert.model.Concert;
import com.andra.ticketconcert.repository.ConcertRepository;
import com.andra.ticketconcert.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConcertServiceImpl implements ConcertService {

    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ConcertMapper concertMapper;

    @Override
    public Page<ResConcertDto> getConcerts(Pageable pageable) {
        Page<Concert> concerts = concertRepository.findByDateGreaterThan(LocalDateTime.now(), pageable);
        return concertMapper.toResConcertDtoPage(concerts);
    }

    @Override
    public List<ResConcertDto> getConcertsByNameOrLocation(String searchValue, Pageable pageable) {
        List<Concert> concerts = concertRepository.findByNameOrLocation(searchValue, searchValue, pageable);
        return concertMapper.toListResConcertDto(concerts);
    }
}
