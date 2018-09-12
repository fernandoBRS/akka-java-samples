package com.akka.sample.alarm.models;

import com.akka.sample.alarm.models.AlarmState.*;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AlarmActor extends AbstractLoggingActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final String password;

    private Receive enabled = receiveBuilder()
        .match(Activity.class, this::onActivity)
        .match(Disable.class, this::onDisable)
        .build();

    private Receive disabled = receiveBuilder()
        .match(Enable.class, this::onEnable)
        .build();

    public AlarmActor(String password) {
        this.password = password;
    }
    
    public static Props props(String password) {
        return Props.create(AlarmActor.class, () -> new AlarmActor(password));
    }

    @Override
    public void preStart() {
        getContext().become(disabled);
    }

    @Override
    public Receive createReceive() {
        return enabled;
    }

    private void onActivity(Activity ignored) {
        log.warning("oeoeoeoeoe, alarm alarm!!!");
    }

    private void onEnable(Enable enable) {
        if (isValidPassword(enable.password)) {
            log.info("Alarm enabled");
            getContext().become(enabled);
        } else {
            log.info("Someone failed to enable the alarm");
        }
    }
    
    private void onDisable(Disable disable) {
        if (isValidPassword(disable.password)) {
            log.info("Alarm disabled");
            getContext().become(disabled);
        } else {
            log.warning("Someone who didn't know the password tried to disable it");
        }
    }

    private boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}
