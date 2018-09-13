package com.akka.sample;
import com.akka.sample.alarm.Alarm;
import com.akka.sample.helloworld.HelloWorld;
import com.akka.sample.supervisor.SupervisorChild;

public class App {
    public static void main(String[] args) {
        // new HelloWorld().Run();
        // new Alarm().Run();
        new SupervisorChild().Run();
    }
}
