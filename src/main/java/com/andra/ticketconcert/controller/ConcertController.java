package com.andra.ticketconcert.controller;

import com.andra.ticketconcert.dto.base.ResponseWrapper;
import com.andra.ticketconcert.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/concerts")
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @GetMapping("")
    public ResponseEntity<ResponseWrapper<Object>> getListConcert(Pageable pageable){
        return ResponseEntity.ok(ResponseWrapper.success(concertService.getConcerts(pageable)));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper<Object>> searchListConcert( @RequestParam("value") String searchValue, Pageable pageable){
        return ResponseEntity.ok(ResponseWrapper.success(concertService.getConcertsByNameOrLocation(searchValue, pageable)));
    }
}
