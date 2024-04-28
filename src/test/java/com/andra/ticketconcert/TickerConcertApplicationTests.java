package com.andra.ticketconcert;

import com.andra.ticketconcert.dto.ReqOrderTicketDto;
import com.andra.ticketconcert.exception.NotAcceptableException;
import com.andra.ticketconcert.exception.NotFoundException;
import com.andra.ticketconcert.model.OrderTicket;
import com.andra.ticketconcert.model.Ticket;
import com.andra.ticketconcert.model.TicketCategory;
import com.andra.ticketconcert.repository.CustomerRepository;
import com.andra.ticketconcert.repository.OrderTicketRepository;
import com.andra.ticketconcert.repository.TicketRepository;
import com.andra.ticketconcert.service.OrderTicketService;
import com.andra.ticketconcert.service.impl.OrderTicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TickerConcertApplicationTests {

    @Mock
    private OrderTicketRepository orderTicketRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private CustomerRepository customerRepository;

    private OrderTicketService orderTicketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderTicketService = new OrderTicketServiceImpl(orderTicketRepository, ticketRepository, customerRepository);
    }

    @Test
    void testCreateOrder_WhenCustomerNotFound_ThrowsNotFoundException() {
        // Arrange
        ReqOrderTicketDto reqOrderTicketDto = new ReqOrderTicketDto();
        reqOrderTicketDto.setCustomerId(1);
        when(customerRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderTicketService.createOrder(reqOrderTicketDto));
        verify(customerRepository, times(1)).existsById(1);
    }

    @Test
    void testCreateOrder_WhenTicketNotFound_ThrowsNotFoundException() {
        // Arrange
        ReqOrderTicketDto reqOrderTicketDto = new ReqOrderTicketDto();
        reqOrderTicketDto.setTicketId(1);
        reqOrderTicketDto.setCustomerId(1);
        when(customerRepository.existsById(anyInt())).thenReturn(true);
        when(ticketRepository.findByIdWithLock(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderTicketService.createOrder(reqOrderTicketDto));
        verify(customerRepository, times(1)).existsById(1);
        verify(ticketRepository, times(1)).findByIdWithLock(1);
    }

    @Test
    void testCreateOrder_WhenNotInAcceptableBookingDate_ThrowsNotAcceptableException() {
        // Arrange
        ReqOrderTicketDto reqOrderTicketDto = new ReqOrderTicketDto();
        reqOrderTicketDto.setTicketId(1);
        Ticket ticket = new Ticket();
        TicketCategory ticketCategory = new TicketCategory();
        ticketCategory.setBookingStartDate(LocalDateTime.now().plusDays(1)); // Set start date in the future
        ticketCategory.setBookingEndDate(LocalDateTime.now().plusDays(2)); // Set end date in the future
        ticket.setTicketCategory(ticketCategory);
        when(customerRepository.existsById(anyInt())).thenReturn(true);
        when(ticketRepository.findByIdWithLock(1)).thenReturn(Optional.of(ticket));

        // Act & Assert
        assertThrows(NotAcceptableException.class, () -> orderTicketService.createOrder(reqOrderTicketDto));
        verify(customerRepository, times(1)).existsById(anyInt());
        verify(ticketRepository, times(1)).findByIdWithLock(1);
    }

    @Test
    void testCreateOrder_WhenValidParameters_ReturnsOrderTicket() {
        // Arrange
        ReqOrderTicketDto reqOrderTicketDto = new ReqOrderTicketDto();
        reqOrderTicketDto.setCustomerId(1);
        reqOrderTicketDto.setTicketId(1);
        reqOrderTicketDto.setQuantity(3);
        Ticket ticket = new Ticket();
        TicketCategory ticketCategory = new TicketCategory();
        ticketCategory.setQuantity(5);
        ticket.setTicketCategory(ticketCategory);
        when(customerRepository.existsById(1)).thenReturn(true);
        when(ticketRepository.findByIdWithLock(1)).thenReturn(Optional.of(ticket));

        // Act
        orderTicketService.createOrder(reqOrderTicketDto);

        // Assert
        verify(customerRepository, times(1)).existsById(1);
        verify(ticketRepository, times(1)).findByIdWithLock(1);
        verify(orderTicketRepository, times(1)).save(any(OrderTicket.class));
        assertEquals(2, ticket.getTicketCategory().getQuantity());
    }

    @Test
    void testIsInAcceptableBookingDate_WhenNowIsEqualToStartDate_ReturnsTrue() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        TicketCategory ticketCategory = new TicketCategory(); // Booking for one day
        ticketCategory.setBookingStartDate(now);
        ticketCategory.setBookingEndDate(now.plusDays(1));
        Ticket ticket = new Ticket();
        ticket.setTicketCategory(ticketCategory);

        // Act
        boolean result = OrderTicketServiceImpl.isInAcceptableBookingDate(ticket);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInAcceptableBookingDate_WhenNowIsEqualToEndDate_ReturnsTrue() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        TicketCategory ticketCategory = new TicketCategory(); // Booking for one day
        ticketCategory.setBookingStartDate(now.minusDays(1));
        ticketCategory.setBookingEndDate(now);
        Ticket ticket = new Ticket();
        ticket.setTicketCategory(ticketCategory);

        // Act
        boolean result = OrderTicketServiceImpl.isInAcceptableBookingDate(ticket);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInAcceptableBookingDate_WhenNowIsBetweenStartAndEndDate_ReturnsTrue() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        TicketCategory ticketCategory = new TicketCategory(); // Booking for one day
        ticketCategory.setBookingStartDate(now.minusDays(1));
        ticketCategory.setBookingEndDate(now.plusDays(1));
        Ticket ticket = new Ticket();
        ticket.setTicketCategory(ticketCategory);

        // Act
        boolean result = OrderTicketServiceImpl.isInAcceptableBookingDate(ticket);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsInAcceptableBookingDate_WhenNowIsBeforeStartDate_ReturnsFalse() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        TicketCategory ticketCategory = new TicketCategory(); // Booking for one day
        ticketCategory.setBookingStartDate(now.plusDays(1));
        ticketCategory.setBookingEndDate(now.plusDays(2));
        Ticket ticket = new Ticket();
        ticket.setTicketCategory(ticketCategory);

        // Act
        boolean result = OrderTicketServiceImpl.isInAcceptableBookingDate(ticket);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsInAcceptableBookingDate_WhenNowIsAfterEndDate_ReturnsFalse() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        TicketCategory ticketCategory = new TicketCategory(); // Booking for one day
        ticketCategory.setBookingStartDate(now.minusDays(2));
        ticketCategory.setBookingEndDate(now.minusDays(1));
        Ticket ticket = new Ticket();
        ticket.setTicketCategory(ticketCategory);

        // Act
        boolean result = OrderTicketServiceImpl.isInAcceptableBookingDate(ticket);

        // Assert
        assertFalse(result);
    }
}
