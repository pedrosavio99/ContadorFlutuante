package com.example.botaoflutuante;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonaddWid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonaddWid = (Button)findViewById(R.id.buton_widget);

        getPermission();

        buttonaddWid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!Settings.canDrawOverlays(MainActivity.this)){
                        getPermission();
                    }else{
                        Intent intent = new Intent(MainActivity.this,WidgetService.class);
                        startService(intent);
                        finish();
                    }
            }
        });
    }

    public void getPermission(){
        //ver se o celular aceita o alert window

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
                !Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 ){
                if(!Settings.canDrawOverlays(MainActivity.this)){
                    Toast.makeText(this,"aaaaaaaaa permissao negada", Toast.LENGTH_SHORT).show();
                }
        }
    }
}