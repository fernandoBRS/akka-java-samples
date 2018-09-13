package com.akka.sample.supervisor;

import java.io.IOException;
import com.akka.sample.supervisor.models.Command;
import com.akka.sample.supervisor.models.actors.Supervisor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class SupervisorChild {
    public void Run() {
        final ActorSystem system = ActorSystem.create("supervisorAndChildSystem");
        
        try {
            final ActorRef supervisor = system.actorOf(Supervisor.props(), "supervisor");

            for (int i = 0; i < 10; i++) {
                supervisor.tell(new Command(), ActorRef.noSender());
            }
            
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            system.terminate();
        }
    }
}
