package com.lyj.permissions;


import java.io.Serializable;


public class Config implements Serializable {

    private PermissionDelegate delegate;
    private String[] permissions;
    private int requestCode = 0;
    private boolean isSystemOverlay = false;

    private PermissionDelegate permissionDelegate;

    public Config(){}

    public Config(String[] permissions, boolean isSystemOverlay, int reqCode, PermissionDelegate delegate){
      this.delegate = delegate;
      this.permissions = permissions;
      this.isSystemOverlay = isSystemOverlay;
      this.requestCode = reqCode;
    }

    public PermissionDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(PermissionDelegate delegate) {
        this.delegate = delegate;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public boolean isSystemOverlay() {
        return isSystemOverlay;
    }

    public void setSystemOverlay(boolean systemOverlay) {
        isSystemOverlay = systemOverlay;
    }

    public PermissionDelegate getPermissionDelegate() {
        return permissionDelegate;
    }

    public void setPermissionDelegate(PermissionDelegate permissionDelegate) {
        this.permissionDelegate = permissionDelegate;
    }
}
