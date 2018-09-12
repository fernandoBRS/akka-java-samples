package com.akka.sample.alarm;

import java.io.IOException;
import com.akka.sample.alarm.models.*;
import com.akka.sample.alarm.models.AlarmState.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Alarm {
    public void Run() {
        final ActorSystem system = ActorSystem.create("alarmSystem");
        
        try {
            final ActorRef alarm = system.actorOf(AlarmActor.props("cat"), "alarm");

            alarm.tell(new Enable("dogs"), ActorRef.noSender());
            alarm.tell(new Enable("cat"), ActorRef.noSender());

            alarm.tell(new Activity(), ActorRef.noSender());
            alarm.tell(new Disable("dogs"), ActorRef.noSender());
            alarm.tell(new Disable("cat"), ActorRef.noSender());
            
            alarm.tell(new Activity(), ActorRef.noSender());
            
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            system.terminate();
        }
    }
}
