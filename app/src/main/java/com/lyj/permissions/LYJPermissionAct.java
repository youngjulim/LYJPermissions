package com.lyj.permissions;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;


@RequiresApi(api = Build.VERSION_CODES.M)
public class LYJPermissionAct extends AppCompatActivity{

    private int requestCode = 0;
    // 사용자에게 요구 권한 목록
    private String[] requestPermissions;
    // 사용자가 거부한 권한 목록
    private String[] rejectionPermissions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Log.e("YJ", "1234566711199990000009808018080880");
        GetConfig();
    }

    void GetConfig(){

        this.requestCode = getIntent().getIntExtra(LYJPermission.REQUEST_CODE, 0);
        this.requestPermissions = getIntent().getStringArrayExtra(LYJPermission.REQUEST_PERMISSIONS);
        // 권한 요청
        RequestPermission();
    }

    /**
     *  싱글/그룹 요청 권한 체크
     */
    void RequestPermission(){

        String[] tempPermissions = this.requestPermissions;
        boolean isPermissionCheck = tempPermissions.length > 1 ? checkPermissions(tempPermissions) : checkPermission(tempPermissions[0]);
        if(isPermissionCheck) { // 권한 승인이 안된 상태.
            ActivityCompat.requestPermissions(this, tempPermissions, this.requestCode);
        }else{ // 권한이 모드 승인된 상태
            LYJPermissionConfig.getInstance().getPermissionDelegate().permissionCompleted(tempPermissions);
        }
    }

    /**
     * 단일 권한 체
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
        if(this.requestCode == requestCode){
            if(permissions.length > 0){
                boolean isGranted = true;
                for(int i = 0; i < permissions.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        isGranted = false;
                    }
                }
                // 사용자가 권한을 허용했을 경우.
                if(isGranted){
                    LYJPermissionConfig.getInstance().getPermissionDelegate().permissionCompleted(permissions);
                }else{
                    // 사용자가 권한을 허용하지 않았을 경우.
                    LYJPermissionConfig.getInstance().getPermissionDelegate().permissionFailed(permissions);
                }
            }
        }
        finish();
    }
}
