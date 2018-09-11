package com.akka.sample.helloworld.models.actors;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.akka.sample.helloworld.models.greeting.*;

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
        this.greeting = message + ", " + whoToGreet.who;
    }

    private void onGreetMessage(Greet greet) {
        printerActor.tell(new Greeting(greeting), getSelf());
    }
}
