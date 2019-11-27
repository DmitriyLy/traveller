package org.dmly.traveller.ticket.service.impl;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.ticket.model.entity.Order;
import org.dmly.traveller.ticket.model.entity.Ticket;
import org.dmly.traveller.ticket.persistence.repository.OrderRepository;
import org.dmly.traveller.ticket.persistence.repository.TicketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {

    @Spy
    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void cancelReservation_validOrder_orderCancelled() {
        Order order = new Order();
        order.setId(1);
        order.setDueDate(LocalDateTime.now().plusDays(2));

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        ticketService.cancelReservation(order.getId(), "test");

        assertTrue(order.isCancelled());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void cancelReservation_invalidId_orderNotCancelled() {
        when(orderRepository.findById(100)).thenReturn(Optional.empty());
        ticketService.cancelReservation(100, "test");

        verify(orderRepository, never()).save(ArgumentMatchers.any(Order.class));
    }

    @Test
    public void completeReservation_validOrder_orderCompleted() {
        Order order = new Order();
        order.setId(1);
        order.setDueDate(LocalDateTime.now().plusDays(2));

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        ticketService.completeReservation(order.getId());

        assertTrue(order.isCompleted());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void buyTicket_validTrip_ticketCreated() {
        String tripId = "123";
        String clientName = "Guest";

        Ticket ticket = ticketService.buyTicket(tripId, clientName);
        assertNotNull(ticket);
        assertNotNull(ticket.getUid());
        assertEquals(ticket.getTripId(), tripId);
        assertEquals(clientName, ticket.getName());

        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test(expected = InvalidParameterException.class)
    public void buyTicket_invalidTripId_exceptionThrown() {
        String clientName = "Guest";
        String tripId = null;

        ticketService.buyTicket(tripId, clientName);
    }
}