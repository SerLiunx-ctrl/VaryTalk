package github.serliunx.varytalk.common.event;

import github.serliunx.varytalk.project.system.entity.SystemUser;
import org.springframework.context.ApplicationEvent;

public class UserUpdateEvent{

    private final SystemUser systemUser;

    public UserUpdateEvent(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public SystemUser getUser() {
        return systemUser;
    }
}
