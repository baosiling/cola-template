package com.baosiling.cola.event;

import com.baosiling.cola.dto.Response;
import com.baosiling.cola.event.EventI;

/**
 * EventBus interface
 */
public interface EventBusI {

    /**
     * sent event to EventBus
     * @param event
     * @return
     */
    Response fire(EventI event);

    /**
     * fire all handler which registered the event
     * @param event
     */
    void fireAll(EventI event);

    /**
     * Async fire all handlers
     * @param event
     */
    void asyncFire(EventI event);
}
