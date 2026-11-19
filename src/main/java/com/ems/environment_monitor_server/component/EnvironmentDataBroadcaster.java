package com.ems.environment_monitor_server.component;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.ems.environment_monitor_server.pojo.CurrentEnvironmentUpdate;
import com.ems.environment_monitor_server.pojo.EnvironmentDataPoint;
import com.ems.environment_monitor_server.pojo.EnvironmentHistoryUpdate;

@Component
public class EnvironmentDataBroadcaster {

    private final SimpMessagingTemplate messaging;

    public EnvironmentDataBroadcaster(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCurrentEnvironmentUpdate(CurrentEnvironmentUpdate update) {
        messaging.convertAndSend(
            "/topic/" + update.getMonitorDeviceUsername() + "/current",
            new EnvironmentDataPoint(update)
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEnvironmentHistoryUpdate(EnvironmentHistoryUpdate update) {
        messaging.convertAndSend(
            "/topic/" + update.getMonitorDeviceUsername() + "/history",
            new EnvironmentDataPoint(update)
        );
    }
}