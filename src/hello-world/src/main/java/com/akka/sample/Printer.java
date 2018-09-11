package com.akka.sample;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Printer extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static public Props props() {
        return Props.create(Printer.class, () -> new Printer());
    }

    static public class Greeting {
        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
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
