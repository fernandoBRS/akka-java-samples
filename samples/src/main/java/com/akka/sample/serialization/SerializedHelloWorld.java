package com.akka.sample.serialization;

import java.io.IOException;
import com.akka.sample.serialization.models.greet.GreeterActor;
import com.akka.sample.serialization.models.greet.GreeterActorProtocol.*;
import com.akka.sample.serialization.models.print.PrinterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class SerializedHelloWorld
 {
    public void Run() {
        final ActorSystem system = ActorSystem.create("serializationSampleSystem");

        try {
            final ActorRef printerActor = system.actorOf(PrinterActor.props(), "printerActor");
            final ActorRef howdyGreeter = system.actorOf(GreeterActor.props("Howdy", printerActor), "howdyGreeter");
            
            WhoToGreet whoToGreetMessage = WhoToGreet.newBuilder()
                .setWho("Akka")
                .build();
            
            Greet greetCommand = Greet.newBuilder().build();

            howdyGreeter.tell(whoToGreetMessage, ActorRef.noSender());
            howdyGreeter.tell(greetCommand, ActorRef.noSender());

            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            system.terminate();
        }
    }
}
