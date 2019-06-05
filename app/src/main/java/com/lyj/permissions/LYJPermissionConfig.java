package com.lyj.permissions;

import android.content.Context;
import android.util.Log;

public class LYJPermissionConfig extends Builder{

    private Context mContext;
    private PermissionDelegate mPermissionDelegate;
    private String[] mPermissions;

    public LYJPermissionConfig(Context context){
        this.mContext = context;
    }

    @Override
    public void requestPermission(String[] permission) {
        super.requestPermission(permission);
        if(permission != null){
            this.mPermissions = permission;
            for(String name : permission){
                Log.e("YJ", " ------> " + name);
            }
        }
    }

    @Override
    public void setOnPermissionDelegate(PermissionDelegate delegate) {
        super.setOnPermissionDelegate(delegate);
        this.mPermissionDelegate = delegate;
    }
}
