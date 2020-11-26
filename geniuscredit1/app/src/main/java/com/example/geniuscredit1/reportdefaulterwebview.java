package com.example.geniuscredit1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class reportdefaulterwebview extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE = 10000;
    WebView webView;


    String url = "https://drive.google.com/uc?export=download&id=1caI0QVNEDTIG50djExQSumvlw08bTyo_";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportdefaulterwebview);

        Toast toast = Toast.makeText(reportdefaulterwebview.this,"Downloading  has started",Toast.LENGTH_LONG);
        toast.show();

       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
           if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

               String[] permission ={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission,PERMISSION_STORAGE_CODE);
           }
           else{
        startdownloading();
           }
       }
       else{
        startdownloading();
       }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:{
               if(grantResults.length> 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                  startdownloading();
               }
               else{
                   Toast toast = Toast.makeText(reportdefaulterwebview.this,
                           "Permission Denied !",
                           Toast.LENGTH_LONG);

                   toast.show();
               }
            }
        }
    }
    public void startdownloading(){
        DownloadManager.Request request= new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("CA Certificate Format");
        request.setDescription("Downloading CA Certificate");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, ""+ System.currentTimeMillis());
        DownloadManager manager =(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

}