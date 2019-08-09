package com.lyj.permissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;


@RequiresApi(api = Build.VERSION_CODES.M)
public class LYJPermissionAct extends AppCompatActivity{

    private int requestCode = 0;
    private int REQUEST_CODE_SYSTEM_OVERLAY = 101;

    // 요청 퍼미션 리스트
    private String[] rejectionPermissions;

    // 콜백 인터페이스
    private PermissionDelegate permissionDelegate;

    // 설정정보
    private Config mConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        // 콜백 인터페이스 체크
        if(LYJPermission.permissionStacks.size() > 0) {
            Log.e("YJ", "");
            permissionDelegate = LYJPermission.permissionStacks.pop();
            Log.e("YJ", "permission hash : " + permissionDelegate.hashCode());
        }else{
            Toast.makeText(this, "권한 요청중 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

        GetConfig();
    }
/*
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if( permissionDelegate != null ){ta
            LYJPermission.permissionStacks = null;
        }
    }

*/

    /**
     * 설정정보 얻기
     */
    void GetConfig(){

        this.mConfig = (Config) getIntent().getSerializableExtra("config");
        // 권한 요청
        RequestPermission();
    }

    /**
     *  싱글/그룹 요청 권한 체크
     */
    void RequestPermission(){

        String[] tempPermissions = this.mConfig.getPermissions();
        boolean isPermissionCheck = tempPermissions.length > 1 ? checkPermissions(tempPermissions) : checkPermission(tempPermissions[0]);
        if(isPermissionCheck) { // 권한 승인이 안된 상태.
            ActivityCompat.requestPermissions(this, tempPermissions, mConfig.getRequestCode());
        }else{ // 권한이 모드 승인된 상태
            permissionDelegate.permissionCompleted(tempPermissions);
            finish();
        }
    }

    /**
     * 단일 권한 체크
     * @param permission
     */
    public boolean checkPermission(String permission){

        int granted = ActivityCompat.checkSelfPermission(this, permission);
        // 권한 체크
        if(granted != PackageManager.PERMISSION_GRANTED){

           return true;
        }
        Log.e("YJ", "Permission :: " + permission);
        return false;
    }

    /**
     * 멀티 권한 체크
     * @param permissions
     */
    public boolean checkPermissions(String[] permissions){

        ArrayList<String> tempData = new ArrayList<>();
        for(int i = 0; i < permissions.length; i++){
            int granted = ActivityCompat.checkSelfPermission(this, permissions[i]);
            if(granted != PackageManager.PERMISSION_GRANTED){
                tempData.add(permissions[i]);
            }
        }
        // 허용하지 않은 권한이 있다면
        if(tempData.size() > 0){

            rejectionPermissions = new String[tempData.size()];
            for(int i = 0 ; i < tempData.size(); i++){
                rejectionPermissions[i] = tempData.get(i);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(this.mConfig.getRequestCode() == requestCode){
            Log.e("YJ", "reqeust!!!!!");
            if(permissions.length > 0){
                boolean isGranted = true;
                for(int i = 0; i < permissions.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        isGranted = false;
                    }
                }
                if(permissionDelegate != null){
                    Log.e("YJ", "permission hash : " + permissionDelegate.hashCode());
                }
                // 사용자가 권한을 허용했을 경우.
                if(isGranted){
                    // 시스템 오버레이 권한 요청이 있다면
                    if(this.mConfig.isSystemOverlay()){
                        startOverlayWindowService();
                    }else{
                        permissionDelegate.permissionCompleted(permissions);
                    }
                }else{
                    // 사용자가 권한을 허용하지 않았을 경우.
                    permissionDelegate.permissionFailed(permissions);
                }
            }
        }
        if(!mConfig.isSystemOverlay())finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SYSTEM_OVERLAY){
            if(checkCanOverlay()){
                permissionDelegate.permissionCompleted(mConfig.getPermissions());
            }else{
                permissionDelegate.permissionFailed(mConfig.getPermissions());
            }
        }
        finish();
    }

    /**
     * 시스템 오버레이가 활성화되어 있는지 체크
     * @return
     */
    protected boolean checkCanOverlay(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        }
        return false;
    }
    /**
     * 다른 앱 위에 그리기 퍼미션 체크
     * 권한 체크되어 있지 않다면 권한 설정 화면으로 이동.
     */
    public void startOverlayWindowService() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE_SYSTEM_OVERLAY);
    }
}
