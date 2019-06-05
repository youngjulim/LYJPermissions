package com.lyj.permissions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LYJPermissionConfig{

    private Context mContext;
    private PermissionDelegate mPermissionDelegate;
    private String[] mPermissions;
    private int requestCode = 0;

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

    public LYJPermissionConfig requestPermission(String[] permission, int reqCode) {
        if(permission != null){
            this.mPermissions = permission;
            this.requestCode = reqCode;
            for(String name : permission){
                //
                Log.e("YJ", " 11333333------> " + name);
            }
        }
        return permissionConfig;
    }

    public LYJPermissionConfig setOnPermissionDelegate(PermissionDelegate delegate) {
        this.mPermissionDelegate = delegate;
        for(String name : mPermissions){
            //
            Log.e("YJ", " 22------> " + name);
        }
        return permissionConfig;
    }

    public void excute(){
        Intent intent = new Intent(mContext, LYJPermissionAct.class);
        intent.putExtra(LYJPermission.REQUEST_PERMISSIONS, mPermissions);
        intent.putExtra(LYJPermission.REQUEST_CODE, requestCode);
        mContext.startActivity(intent);
    }

    public PermissionDelegate getPermissionDelegate(){
        return mPermissionDelegate;
    }
}
