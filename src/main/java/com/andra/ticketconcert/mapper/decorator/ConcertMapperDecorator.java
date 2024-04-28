package com.andra.ticketconcert.mapper.decorator;

import com.andra.ticketconcert.dto.ResConcertDto;
import com.andra.ticketconcert.mapper.ConcertMapper;
import com.andra.ticketconcert.model.Concert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ConcertMapperDecorator implements ConcertMapper {

    @Override
    public ResConcertDto toResConcertDto(Concert concert) {
        if (concert == null) {
            return null;
        }

        ResConcertDto resConcertDto = new ResConcertDto();
        resConcertDto.setConcertId(concert.getId());
        resConcertDto.setConcertName(concert.getName());
        resConcertDto.setDate(concert.getDate());
        if (concert.getArtist() != null) {
            resConcertDto.setArtistName(concert.getArtist().getName());
        }
        if (concert.getVenue() != null) {
            resConcertDto.setLocation(concert.getVenue().getLocation());
        }

        return resConcertDto;
    }

    @Override
    public List<ResConcertDto> toListResConcertDto(List<Concert> concerts) {
        if (concerts == null || concerts.isEmpty()) {
            return Collections.emptyList();
        }

        List<ResConcertDto> list = new ArrayList<ResConcertDto>(concerts.size());
        for (Concert concert : concerts) {
            list.add(toResConcertDto(concert));
        }

        return list;
    }
}
