package org.dmly.traveller.app.rest.service;

import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicBoolean;

@Path("sse")
public class SseResource {
    private final AtomicBoolean terminationFlag = new AtomicBoolean();

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Path("time")
    public void currentTime(@Context SseEventSink sseEventSink, @Context Sse sse) {
        new Thread(() -> {
            while (!terminationFlag.get()) {
                OutboundSseEvent event = sse.newEventBuilder()
                        .data(String.class, LocalTime.now().toString())
                        .build();
                sseEventSink.send(event);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
            sseEventSink.close();
        }).start();
    }

    @PreDestroy
    public void preDestroy() {
        terminationFlag.set(true);
    }
}
