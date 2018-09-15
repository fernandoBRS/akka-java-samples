package com.akka.sample.serialization.models.greet;

import com.akka.sample.serialization.models.greet.GreeterActorProtocol.*;
import com.akka.sample.serialization.models.print.PrinterActorProtocol.Greeting;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class GreeterActor extends AbstractActor {
    private final String message;
    private final ActorRef printerActor;
    private String greeting = "";

    public GreeterActor(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }

    static public Props props(String message, ActorRef printerActor) {
        return Props.create(GreeterActor.class, () -> new GreeterActor(message, printerActor));
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(WhoToGreet.class, this::onWhoToGreetMessage)
            .match(Greet.class, this::onGreetMessage)
            .build();
    }

    private void onWhoToGreetMessage(WhoToGreet whoToGreet) {
        this.greeting = message + ", " + whoToGreet.getWho();
    }

    private void onGreetMessage(Greet greet) {
        Greeting greetingMessage = Greeting.newBuilder()
                .setMessage(greeting)
                .build();

        printerActor.tell(greetingMessage, getSelf());
    }
}
