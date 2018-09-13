package com.akka.sample.supervisor.models.actors;

import com.akka.sample.supervisor.models.Command;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class UnreliableActor extends AbstractLoggingActor {
    private long messages = 0L;

    public static Props props() {
        return Props.create(UnreliableActor.class, () -> new UnreliableActor());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(Command.class, this::onCommand)
            .build();
    }

    private void onCommand(Command command) {
        messages++;

        if (messages % 4 == 0) {
            throw new RuntimeException("Oh no, I got four commands, can't handle more");
        } 

        log().info("Messages counter: {}", messages);
    }
}
