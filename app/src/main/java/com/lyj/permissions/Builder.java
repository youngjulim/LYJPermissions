package com.lyj.permissions;

public abstract class Builder {

    /**
     * 요청 퍼미션
     * @param permission
     */
    void requestPermission(String[] permission){}

    void setOnPermissionDelegate(PermissionDelegate delegate){}
}
