package com.lyj.permissions;

import android.content.Context;
import android.util.Log;

public class LYJPermissionConfig{

    private Context mContext;
    private PermissionDelegate mPermissionDelegate;
    private String[] mPermissions;

    private static LYJPermissionConfig permissionConfig;

    public static synchronized LYJPermissionConfig getInstance(){
        if(permissionConfig == null){
            permissionConfig = new LYJPermissionConfig();
        }
        return permissionConfig;
    }

    public LYJPermissionConfig setContext(Context context){
        this.mContext = context;
        return permissionConfig;
    }

    public LYJPermissionConfig requestPermission(String[] permission) {
        if(permission != null){
            this.mPermissions = permission;
            for(String name : permission){
                //
                Log.e("YJ", " 1------> " + name);
            }
        }
        return permissionConfig;
    }

    public LYJPermissionConfig setOnPermissionDelegate(PermissionDelegate delegate) {
        this.mPermissionDelegate = delegate;
        for(String name : mPermissions){
            //
            Log.e("YJ", " 2------> " + name);
        }
        return permissionConfig;
    }
}
