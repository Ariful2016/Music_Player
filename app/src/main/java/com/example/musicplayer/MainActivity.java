package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTimePerm();
    }

    private void runTimePerm() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                        getData();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings

                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void getData() {

        String[] projection =          {
                            MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DATE_MODIFIED,
                            MediaStore.Audio.Media.BUCKET_DISPLAY_NAME,
                            MediaStore.Audio.Media.DISPLAY_NAME

                    };

        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = getContentResolver().query(contentUri,projection,null,null,null);

        if (cursor!=null){
            cursor.moveToPosition(0);

            Log.i("TAG", "getData: "+   cursor.getCount());


            while (true){

                long id= cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                Uri audioUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);

                Audio audio = new Audio(name,audioUri);

                Log.i("TAG", "getData: " + audio.toString());

                if (cursor.isLast()){
                    break;
                } else{
                    cursor.moveToNext();
                }

            }

            cursor.close();
        }
    }
}