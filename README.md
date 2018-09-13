# Actor Model Design Pattern with Java and Akka Toolkit

Samples to build reactive, concurrent, and distributed applications in Java using Akka toolkit.

## Samples

- [Hello World: Changing Actor State](./docs/hello-world.md)
- [Burglary Alarm: Changing Actor Behavior (Finite State Machine)](./docs/alarm.md)
- [Transient Fault Handling: Implementing a Supervisor](./docs/supervisor.md)

## Prerequisites

- [Java Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Apache Maven](https://maven.apache.org/install.html)

    *Additionally if you're using VS Code, this guideline can be useful: [Java in Visual Studio Code](https://code.visualstudio.com/docs/languages/java).*

## Getting Started

Clone this repository and access the **samples** root folder. Then compile and run the project:

    $ mvn compile exec:exec

## Changing the running sample

In **App.java** file you can select the sample (or multiple samples) you want to run:

```java
public class App {
    public static void main(String[] args) {
        // new HelloWorld().Run();
        new Alarm().Run();
    }
}
```
