package com.lyj.permissions;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;


@RequiresApi(api = Build.VERSION_CODES.M)
public class LYJPermissionAct extends AppCompatActivity{

    private int requestCode = 9;
    /**
     * 단일 권한요청
     * @param permission
     */
    public void checkPermission(String permission){

        int granted = ActivityCompat.checkSelfPermission(this, permission);
        // 권한 X
        if(granted != PackageManager.PERMISSION_GRANTED){
            //권한 요청
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
        Log.e("YJ", "Permission : " + permission);
    }

    /**
     * 멀티 권한 요청

     * @param permissions
     */
    public void checkPermissions(String[] permissions){

        ArrayList<String> tempData = new ArrayList<>();
        for(int i = 0; i < permissions.length; i++){
            int granted = ActivityCompat.checkSelfPermission(this, permissions[i]);
            if(granted != PackageManager.PERMISSION_GRANTED){
                tempData.add(permissions[i]);
            }
        }
        // 허용하지 않은 권한이 있다면
        if(tempData.size() > 0){

            String[] reqPermissions = new String[tempData.size()];
            for(int i = 0 ; i < tempData.size(); i++){
                reqPermissions[i] = tempData.get(i);
            }
            // 권한을 요청
            ActivityCompat.requestPermissions(this, reqPermissions, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(this.requestCode == requestCode){
            finish();
        }
    }
}
