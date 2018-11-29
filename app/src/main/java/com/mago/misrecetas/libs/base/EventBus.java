package com.mago.misrecetas.libs.base;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
