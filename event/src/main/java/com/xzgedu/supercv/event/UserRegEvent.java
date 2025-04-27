package com.xzgedu.supercv.event;

import org.springframework.context.ApplicationEvent;

public class UserRegEvent extends ApplicationEvent {
    private long userId;

    public UserRegEvent(long userId) {
        super("user-reg-event");
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}