package com.lyj.permissions;

public class LYJPermission {

    public static final String REQUEST_CODE = "req_code";
    public static final String REQUEST_PERMISSIONS = "req_permission";
    /*
            주석처리!!!!!!
     */
    public static LYJPermissionConfig getConfig(){

        return LYJPermissionConfig.getInstance();
    }

}
