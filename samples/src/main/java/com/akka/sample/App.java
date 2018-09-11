package com.akka.sample;
import java.io.IOException;

import com.akka.sample.helloworld.HelloWorld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class App {
    public static void main(String[] args) {
        new HelloWorld().Run();
    }
}
