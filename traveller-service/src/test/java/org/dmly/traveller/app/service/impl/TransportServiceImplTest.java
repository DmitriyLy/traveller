package org.dmly.traveller.app.service.impl;

import org.dmly.traveller.app.model.entity.travel.Order;
import org.dmly.traveller.app.model.entity.travel.Ticket;
import org.dmly.traveller.app.model.entity.travel.Trip;
import org.dmly.traveller.app.persistence.repository.transport.OrderRepository;
import org.dmly.traveller.app.persistence.repository.transport.RouteRepository;
import org.dmly.traveller.app.persistence.repository.transport.TicketRepository;
import org.dmly.traveller.app.persistence.repository.transport.TripRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceImplTest {

    @Spy
    @InjectMocks
    private TransportServiceImpl transportService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private TripRepository tripRepository;

    @Before
    public void setup() {
    }

    @Test
    public void cancelReservation_validOrder_orderCancelled() {
        Order order = new Order();
        order.setId(1);
        order.setDueDate(LocalDateTime.now().plusDays(2));

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        transportService.cancelReservation(order.getId(), "test");

        assertTrue(order.isCancelled());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void cancelReservation_invalidId_orderNotCancelled() {
        when(orderRepository.findById(100)).thenReturn(Optional.empty());
        transportService.cancelReservation(100, "test");

        verify(orderRepository, never()).save(Matchers.any(Order.class));
    }

    @Test
    public void completeReservation_validOrder_orderCompleted() {
        Order order = new Order();
        order.setId(1);
        order.setDueDate(LocalDateTime.now().plusDays(2));

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        transportService.completeReservation(order.getId());

        assertTrue(order.isCompleted());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void buyTicket_validTrip_ticketCreated() {
        Trip trip = new Trip();
        trip.setId(1);
        String clientName = "Guest";

        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));
        Ticket ticket = transportService.buyTicket(trip.getId(), clientName);
        assertNotNull(ticket);
        assertNotNull(ticket.getUid());
        assertEquals(ticket.getTrip(), trip);
        assertEquals(clientName, ticket.getName());

        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
}