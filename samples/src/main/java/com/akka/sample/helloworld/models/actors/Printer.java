package com.akka.sample.helloworld.models.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.akka.sample.helloworld.models.greeting.Greeting;

public class Printer extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static public Props props() {
        return Props.create(Printer.class, () -> new Printer());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Greeting.class, this::onGreetingMessage)
            .build();
    }

    private void onGreetingMessage(Greeting greeting) {
        log.info(greeting.message);
    }
}
