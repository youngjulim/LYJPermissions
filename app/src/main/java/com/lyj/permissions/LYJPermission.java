package com.lyj.permissions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class LYJPermission {

    public static final String REQUEST_CODE = "req_code";
    public static final String REQUEST_PERMISSIONS = "req_permission";
    public static final String REQUEST_SYSTEM_OVERLAY = "req_system_overlay";


    private Context mContext;
    public PermissionDelegate mPermissionDelegate;
    private String[] mPermissions;
    private int requestCode = 0;
    private boolean isSystemOverlay = false;

    private LYJPermission(Builder builder){

        this.mContext = builder.context;
        this.mPermissionDelegate = builder.mPermissionDelegate;
        this.mPermissions = builder.permissionsList;
        this.requestCode = builder.requestCode;
        this.isSystemOverlay = builder.isSystemOverlay;
        this.goPermission();
    }

    /**
     * 퍼미션 요청 Activity으로 이동
     */
    private void goPermission(){
        ArrayList<PermissionDelegate> permissionDelegateArrayList = new ArrayList<>();
        permissionDelegateArrayList.add(mPermissionDelegate);
        Intent intent = new Intent(mContext, LYJPermissionAct.class);
        intent.getExtras().putSerializable("config", new Config(mPermissions, isSystemOverlay, requestCode, mPermissionDelegate));
        //intent.getExtras().pintent.putExtra()
        mContext.startActivity(intent);
    }

    /**
     * 빌더 패턴 클래스
     */
    public static class Builder {

        private Context context;
        private int requestCode = 0;
        private String[] permissionsList;
        private boolean isSystemOverlay = false;
        private PermissionDelegate mPermissionDelegate;

        public Builder(Context ctx){
            this.context = ctx;
        }

        /**
         * 요청 퍼미션과, 요청 코드를 받는다.
         * @param permission
         * @param reqCode
         * @return
         */
        public Builder requestPermission(String[] permission, int reqCode) {
            if(permission != null){
                this.permissionsList = permission;
                this.requestCode = reqCode;
                for(String name : permission){
                    Log.e("LYJ", "요청 퍼미션 : " + name);
                }
            }
            return this;
        }

        /**
         * 해당 앱이 시스템 오버레이 권한도 함께 요청했는지 값을 받는다.
         * @param value
         * @return
         */
        public Builder setSystemOverlay(boolean value){
            this.isSystemOverlay = value;
            return this;
        }

        /**
         * 퍼미션 요청, 수락 결과 수신받을 콜백 설정
         * @param delegate
         * @return
         */
        public Builder setOnPermissionDlegate(PermissionDelegate delegate) {
            this.mPermissionDelegate = delegate;
            return this;
        }

        public LYJPermission build(){
            return new LYJPermission(this);
        }
    }

}
