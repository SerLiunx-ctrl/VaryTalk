package com.serliunx.varytalk.system.event;

import com.serliunx.varytalk.system.entity.SystemUser;

public class UserUpdateEvent{

    private final SystemUser systemUser;

    public UserUpdateEvent(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public SystemUser getUser() {
        return systemUser;
    }
}
