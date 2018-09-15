package com.akka.sample.serialization.models.print;

import com.akka.sample.serialization.models.print.PrinterActorProtocol.Greeting;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PrinterActor extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static public Props props() {
        return Props.create(PrinterActor.class, () -> new PrinterActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Greeting.class, this::onGreetingMessage)
            .build();
    }

    private void onGreetingMessage(Greeting greeting) {
        log.info(greeting.getMessage());
    }
}
