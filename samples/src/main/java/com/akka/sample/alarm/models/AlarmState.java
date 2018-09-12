package com.akka.sample.alarm.models;

public class AlarmState {
    public static class Activity { }

    public static class Enable {
        public final String password;
    
        public Enable(String password) {
            this.password = password;
        }
    }

    public static class Disable {
        public final String password;
    
        public Disable(String password) {
            this.password = password;
        }
    }
}
