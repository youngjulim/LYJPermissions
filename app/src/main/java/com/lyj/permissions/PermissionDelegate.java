package com.lyj.permissions;

import java.io.Serializable;

public interface PermissionDelegate {

    void permissionCompleted(String[] permission);
    void permissionFailed(String[] permission);

}
