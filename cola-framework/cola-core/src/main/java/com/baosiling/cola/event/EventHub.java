package com.baosiling.cola.event;

import com.baosiling.cola.event.EventI;
import com.baosiling.cola.exception.framework.ColaException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 事件控制中枢
 * @author: wangzhx
 * @create: 2020-08-22 20:50
 */
@Component
public class EventHub {

    @Setter
    @Getter
    private ListMultimap<Class, EventHandlerI> eventRepository = ArrayListMultimap.create();

    @Getter
    private Map<Class, Class> responseRepository = new HashMap<>();

    public List<EventHandlerI> getEventHandler(Class eventClass){
        List<EventHandlerI> eventHandlerIList = findHandler(eventClass);
        if(CollectionUtils.isEmpty(eventHandlerIList)){
            throw new ColaException(eventClass + "is not registered in eventHub, please register first");
        }
        return eventHandlerIList;
    }

    /**
     * 注册事件
     * @param eventClz
     * @param eventHandler
     */
    public void register(Class<? extends EventI> eventClz, EventHandlerI eventHandler){
        eventRepository.put(eventClz, eventHandler);
    }

    private List<EventHandlerI> findHandler(Class<? extends EventI> eventClass){
        return eventRepository.get(eventClass);
    }

}