package com.andra.ticketconcert.service;

import com.andra.ticketconcert.dto.ReqOrderTicketDto;

public interface OrderTicketService {

    void createOrder(ReqOrderTicketDto reqOrderTicketDto);
}
