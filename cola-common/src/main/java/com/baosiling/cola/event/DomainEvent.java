package com.baosiling.cola.event;

/**
 * Domain Event (领域事件)
 *
 * 命名规则：实体名+动词的过去时态+Event
 *
 * 比如CustomerCreated 表示创建完客户发送出的领域事件
 * ContactAdded 表示添加完联系人发送出来的领域事件
 * OpportunityTransferred 表示机会转移完发送出来的领域事件
 *
 */
public interface DomainEvent extends EventI{

}
