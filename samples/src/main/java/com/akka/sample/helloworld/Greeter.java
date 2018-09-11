package com.akka.sample.helloworld;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.akka.sample.helloworld.Printer.Greeting;;

public class Greeter extends AbstractActor {
    private final String message;
    private final ActorRef printerActor;
    private String greeting = "";

    public Greeter(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }

    static public Props props(String message, ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }

    static public class WhoToGreet {
        public final String who;

        public WhoToGreet(String who) {
            this.who = who;
        }
    }

    static public class Greet {
        public Greet() {
        }
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
