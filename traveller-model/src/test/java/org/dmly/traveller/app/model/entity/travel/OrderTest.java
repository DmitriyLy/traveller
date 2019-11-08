package org.dmly.traveller.app.model.entity.travel;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class OrderTest {

    private Order order;

    @Before
    public void setup() throws Exception {
        order = new Order();
        order.setId(1);
        order.setDueDate(LocalDateTime.now().plusDays(2));
    }

    @Test
    public void  complete_validOrder_orderCompleted() {
        order.complete();
        assertEquals(order.getState(), OrderState.COMPLETED);
        assertTrue(order.isCompleted());
    }

    @Test(expected=Exception.class)
    public void complete_expiredOrder_exceptionThrown() {
        order.setDueDate(LocalDateTime.now().minusDays(1));
        order.complete();

        fail("Exception should be thrown");
    }

    @Test
    public void cancel_validOrder_orderCancelled() {
        order.cancel("test");
        assertEquals(order.getState(), OrderState.CANCELLED);
        assertTrue(order.isCancelled());
        assertEquals(order.getCancellationReason(), "test");
    }

}