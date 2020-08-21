package com.baosiling.cola.event;

public interface MessageQueueEventI extends EventI {
    public String getEventType();
    public String getEventTopic();
}
