package com.baosiling.cola.domain;

/**
 * 领域工厂
 */
public interface DomainFactoryI<T extends EntityObject> {

    T create();

}
