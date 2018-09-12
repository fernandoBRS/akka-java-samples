package com.akka.sample.alarm.models;

import com.akka.sample.alarm.models.AlarmState.*;
import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AlarmActor extends AbstractLoggingActor {
    private Receive enabled;
    private Receive disabled;
    private final String password;
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public AlarmActor(String password) {
        this.password = password;
    }
    
    public static Props props(String password) {
        return Props.create(AlarmActor.class, () -> new AlarmActor(password));
    }

    @Override
    public Receive createReceive() {
        enabled = receiveBuilder()
            .match(Activity.class, this::onActivity)
            .match(Disable.class, this::onDisable)
            .build();

        disabled = receiveBuilder()
            .match(Enable.class, this::onEnable)
            .build();

        // The default behavior changes only when an Enable message is received
        return disabled;
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
