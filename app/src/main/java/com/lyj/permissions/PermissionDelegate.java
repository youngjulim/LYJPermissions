package com.lyj.permissions;

public interface PermissionDelegate{

    void permissionCompleted(String[] permission);
    void permissionFailed(String[] permission);

}
