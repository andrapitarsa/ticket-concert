package com.andra.ticketconcert.repository;

import com.andra.ticketconcert.model.OrderTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTicketRepository extends JpaRepository<OrderTicket, Integer> {
}
