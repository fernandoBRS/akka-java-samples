package com.akka.sample.supervisor.models.actors;

import java.time.Duration;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;

public class Supervisor extends AbstractLoggingActor {
    private final ActorRef child = getContext().actorOf(UnreliableActor.props(), "child");

    public static Props props() {
        return Props.create(Supervisor.class, () -> new Supervisor());
    }
    
    @Override
    public Receive createReceive() {
        // Forward all command messages the supervisor receives to the child
        return receiveBuilder()
            .matchAny(command -> child.forward(command, getContext()))
            .build();
    }
    
    @Override
    public SupervisorStrategy supervisorStrategy() {
        // Number of times a child actor is allowed to be restarted before the child actor is stopped
        Integer maxNumberOfRetries = 2;

        // Duration of the time window for max number of retries.
        Duration withinTimeRange = Duration.ofSeconds(10);

        return new OneForOneStrategy(maxNumberOfRetries, withinTimeRange,
            DeciderBuilder
                .match(RuntimeException.class, ex -> SupervisorStrategy.restart())
                .match(NullPointerException.class, ex -> SupervisorStrategy.restart())
                .match(ArithmeticException.class, ex -> SupervisorStrategy.resume())
                .match(IllegalArgumentException.class, ex -> SupervisorStrategy.stop())
                .match(UnsupportedOperationException.class, ex -> SupervisorStrategy.stop())
                .matchAny(o -> SupervisorStrategy.escalate())
                .build());
    }
}
