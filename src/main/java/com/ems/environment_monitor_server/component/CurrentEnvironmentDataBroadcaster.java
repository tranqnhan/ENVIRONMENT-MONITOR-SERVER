package com.ems.environment_monitor_server.component;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.ems.environment_monitor_server.pojo.CurrentEnvironmentUpdate;
import com.ems.environment_monitor_server.pojo.CurrentEnvironmentUpdateGet;

@Component
public class CurrentEnvironmentDataBroadcaster {

    private final SimpMessagingTemplate messaging;

    public CurrentEnvironmentDataBroadcaster(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUpdated(CurrentEnvironmentUpdate update) {
        messaging.convertAndSend(
            "/topic/" + update.getMonitorDeviceUsername() + "/current",
            new CurrentEnvironmentUpdateGet(update)
        );
    }
}