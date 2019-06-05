package com.lyj.permissions;

public abstract class Builder {

    /**
     * 요청 퍼미션
     * @param permission
     */
    public void requestPermission(String[] permission){}

    public void setOnPermissionDelegate(PermissionDelegate delegate){}
}
