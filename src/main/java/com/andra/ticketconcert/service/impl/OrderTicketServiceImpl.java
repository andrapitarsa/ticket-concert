package com.andra.ticketconcert.service.impl;

import com.andra.ticketconcert.dto.ReqOrderTicketDto;
import com.andra.ticketconcert.exception.BadRequestException;
import com.andra.ticketconcert.exception.NotAcceptableException;
import com.andra.ticketconcert.exception.NotFoundException;
import com.andra.ticketconcert.model.OrderTicket;
import com.andra.ticketconcert.model.Ticket;
import com.andra.ticketconcert.repository.CustomerRepository;
import com.andra.ticketconcert.repository.OrderTicketRepository;
import com.andra.ticketconcert.repository.TicketRepository;
import com.andra.ticketconcert.service.OrderTicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderTicketServiceImpl implements OrderTicketService {

    private OrderTicketRepository orderTicketRepository;

    private TicketRepository ticketRepository;

    private CustomerRepository customerRepository;

    public OrderTicketServiceImpl(OrderTicketRepository orderTicketRepository, TicketRepository ticketRepository, CustomerRepository customerRepository) {
        this.orderTicketRepository = orderTicketRepository;
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public void createOrder(ReqOrderTicketDto reqOrderTicketDto) {
        boolean isCustomerExist = customerRepository.existsById(reqOrderTicketDto.getCustomerId());
        if (!isCustomerExist) {
            throw new NotFoundException("Customer Not Found !");
        }

        Ticket ticket = ticketRepository.findByIdWithLock(reqOrderTicketDto.getTicketId()).orElseThrow(() -> new NotFoundException("Ticket Not Found !"));
        int totalTicketAvailable = ticket.getTicketCategory().getQuantity();
        int remainingTicketAvailable = totalTicketAvailable - reqOrderTicketDto.getQuantity();

        if (remainingTicketAvailable < 0) {
            throw new BadRequestException("Ticket purchases exceed quantity availability !");
        }
        if (!isInAcceptableBookingDate(ticket)) {
            throw new NotAcceptableException("Not Acceptable In Booking Date!");
        }

        doSaveOrder(reqOrderTicketDto, ticket);

        ticket.getTicketCategory().setQuantity(remainingTicketAvailable);
        if (remainingTicketAvailable == 0) {
            ticket.setIsBooked(true);
        }
        ticketRepository.save(ticket);
    }

    private void doSaveOrder(ReqOrderTicketDto reqOrderTicketDto, Ticket ticket) {
        OrderTicket orderTicket = new OrderTicket();
        orderTicket.setTicketId(ticket.getId());
        orderTicket.setCustomerId(ticket.getId());
        orderTicket.setOrderDate(LocalDateTime.now());
        orderTicket.setTotalPrice(getCalculationTotalPrice(ticket.getTicketCategory().getPrice(), reqOrderTicketDto.getQuantity()));
        orderTicket.setQuantity(reqOrderTicketDto.getQuantity());
        orderTicketRepository.save(orderTicket);
    }

    public static boolean isInAcceptableBookingDate(Ticket ticket) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        LocalDateTime startDateAcceptableBooking = ticket.getTicketCategory().getBookingStartDate();
        LocalDateTime endDateAcceptableBooking = ticket.getTicketCategory().getBookingEndDate();
        return (dateTimeNow.isEqual(startDateAcceptableBooking) || dateTimeNow.isAfter(startDateAcceptableBooking))
                && (dateTimeNow.isEqual(endDateAcceptableBooking) || dateTimeNow.isBefore(endDateAcceptableBooking));
    }

    private BigDecimal getCalculationTotalPrice(BigDecimal price, int quantity) {
        return price.multiply(new BigDecimal(quantity));
    }


}
