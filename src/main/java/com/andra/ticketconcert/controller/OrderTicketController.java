package com.andra.ticketconcert.controller;

import com.andra.ticketconcert.dto.ReqOrderTicketDto;
import com.andra.ticketconcert.dto.base.ResponseWrapper;
import com.andra.ticketconcert.service.OrderTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order-tickets")
public class OrderTicketController {

    @Autowired
    private OrderTicketService orderTicketService;

    @PostMapping("")
    public ResponseEntity<ResponseWrapper<Object>> getByUsername(@RequestBody ReqOrderTicketDto reqOrderTicket){
        orderTicketService.createOrder(reqOrderTicket);
        return ResponseEntity.ok(ResponseWrapper.success());
    }
}
