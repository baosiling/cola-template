package com.baosiling.cola.domain;

import com.alibaba.cola.event.DomainEventI;

/**
 * DomainEventServiceI 领域事件服务
 *
 */
public interface DomainEventServiceI {

    /**
     * 发布领域事件
     * 具体的事件处理机制，由应用自己实现，在cola-extension中，由MetaQ的实现
     * @param domainEvent
     */
    void publish(DomainEventI domainEvent);
}
