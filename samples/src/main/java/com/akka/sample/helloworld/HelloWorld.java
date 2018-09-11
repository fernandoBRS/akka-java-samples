package com.akka.sample.helloworld;
import java.io.IOException;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.akka.sample.helloworld.models.actors.*;
import com.akka.sample.helloworld.models.greeting.*;

public class HelloWorld {
    public void Run() {
        final ActorSystem system = ActorSystem.create("samplesSystem");
        
        try {
            final ActorRef printerActor = system.actorOf(Printer.props(), "printerActor");
            final ActorRef howdyGreeter = system.actorOf(Greeter.props("Howdy", printerActor), "howdyGreeter");
            final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", printerActor), "helloGreeter");
            final ActorRef goodDayGreeter = system.actorOf(Greeter.props("Good day", printerActor), "goodDayGreeter");

            howdyGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
            howdyGreeter.tell(new Greet(), ActorRef.noSender());

            howdyGreeter.tell(new WhoToGreet("Lightbend"), ActorRef.noSender());
            howdyGreeter.tell(new Greet(), ActorRef.noSender());

            helloGreeter.tell(new WhoToGreet("Java"), ActorRef.noSender());
            helloGreeter.tell(new Greet(), ActorRef.noSender());

            goodDayGreeter.tell(new WhoToGreet("Play"), ActorRef.noSender());
            goodDayGreeter.tell(new Greet(), ActorRef.noSender());

            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            system.terminate();
        }
    }
}
