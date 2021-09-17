package com.example.botaoflutuante;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WidgetService extends Service {
    int layout_flag;
    View mFloatingView;
    WindowManager windowManager;
    ImageView imageClose;
    TextView tvWidget;
    TextView MenosBtn;
    TextView MaisBtn;
    float heigth,width;

    private int contador_ofc;

    private void contar() {
         View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.menos:
                        menosContador();
                        break;
                    case R.id.mais:
                        maisContador();
                        break;

                }

            }
        };
    }







    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            layout_flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else{
            layout_flag = WindowManager.LayoutParams.TYPE_PHONE;
        }

        //inflar o widget

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_widget, null);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                layout_flag,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);


        //posiçao incial
        layoutParams.gravity = Gravity.TOP|Gravity.RIGHT;
        layoutParams.x = 0;
        layoutParams.y = 100;



        //paramentro para o botao de fechar
        WindowManager.LayoutParams imageParams = new WindowManager.LayoutParams(140,
               140,
                layout_flag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        imageParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
        imageParams.y = 100;

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        imageClose = new ImageView(this);
        imageClose.setImageResource(R.drawable.close_treco);
        imageClose.setVisibility(View.INVISIBLE);
        windowManager.addView(imageClose,imageParams);
        windowManager.addView(mFloatingView,layoutParams);
        mFloatingView.setVisibility(View.VISIBLE);

        heigth = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();

        tvWidget = (TextView)mFloatingView.findViewById(R.id.text_widget);

        MenosBtn = (TextView)mFloatingView.findViewById(R.id.menos);

        MaisBtn = (TextView)mFloatingView.findViewById(R.id.mais2);

        MaisBtn.setOnTouchListener(new View.OnTouchListener() {
            int initialX,initialY;
            float initialTouchX,initialTouchY;
            long startclickTime;

            int max_clic_dura = 200;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:

                        startclickTime = Calendar.getInstance().getTimeInMillis();
                        //imageClose.setVisibility(View.VISIBLE);

                        initialX = layoutParams.x;
                        initialY = layoutParams.y;

                        //posicao do toque

                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        return  true;
                    case MotionEvent.ACTION_UP:
                        long clickDuration = Calendar.getInstance().getTimeInMillis()-startclickTime;
                        //imageClose.setVisibility(View.GONE);

                        layoutParams.x = initialX + (int)(initialTouchX-event.getRawX());
                        layoutParams.y = initialY + (int)(event.getRawY()-initialTouchY);

                        if(clickDuration<max_clic_dura){
                            //Toast.makeText(WidgetService.this, "Time: "+tvWidget.getText().toString(), Toast.LENGTH_SHORT).show();
                            maisContador();
                            contar();
                        }else {
                            //remove widget
                            if(layoutParams.y>(heigth*0.6)){
                                stopSelf();
                            }

                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //calcular as cordenadas x e y do movimento

                        layoutParams.x = initialX + (int) (initialTouchX-event.getRawX());
                        layoutParams.y = initialY + (int) (event.getRawY()-initialTouchY);

                        //atualizar as novas cordenadas!!!

                        windowManager.updateViewLayout(mFloatingView,layoutParams);

                        if(layoutParams.y>(heigth*0.6)){
                            imageClose.setImageResource(R.drawable.close_treco);
                        }else {
                            imageClose.setImageResource(R.drawable.close_treco);
                        }




                        return true;
                }


                return false;
            }
        });




        MenosBtn.setOnTouchListener(new View.OnTouchListener() {
            int initialX,initialY;
            float initialTouchX,initialTouchY;
            long startclickTime;

            int max_clic_dura = 200;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:

                        startclickTime = Calendar.getInstance().getTimeInMillis();
                        //imageClose.setVisibility(View.VISIBLE);

                        initialX = layoutParams.x;
                        initialY = layoutParams.y;

                        //posicao do toque

                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        return  true;
                    case MotionEvent.ACTION_UP:
                        long clickDuration = Calendar.getInstance().getTimeInMillis()-startclickTime;
                        //imageClose.setVisibility(View.GONE);

                        layoutParams.x = initialX + (int)(initialTouchX-event.getRawX());
                        layoutParams.y = initialY + (int)(event.getRawY()-initialTouchY);

                        if(clickDuration<max_clic_dura){
                            //Toast.makeText(WidgetService.this, "Time: "+tvWidget.getText().toString(), Toast.LENGTH_SHORT).show();
                            menosContador();
                            contar();
                        }else {
                            //remove widget
                            if(layoutParams.y>(heigth*0.6)){
                                stopSelf();
                            }

                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //calcular as cordenadas x e y do movimento

                        layoutParams.x = initialX + (int) (initialTouchX-event.getRawX());
                        layoutParams.y = initialY + (int) (event.getRawY()-initialTouchY);

                        //atualizar as novas cordenadas!!!

                        windowManager.updateViewLayout(mFloatingView,layoutParams);

                        if(layoutParams.y>(heigth*0.6)){
                            imageClose.setImageResource(R.drawable.close_treco);
                        }else {
                            imageClose.setImageResource(R.drawable.close_treco);
                        }




                        return true;
                }


                return false;
            }
        });


        //logica do flutuante aqui

        Handler hendler = new Handler();
        hendler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    contador_ofc = 0;
                    tvWidget.setText(contador_ofc+"");
                   // hendler.postDelayed(this,1000);
            }
        }, 10);




        //MOVIMENTO DE ARRASTAR DRAG MOVE

        tvWidget.setOnTouchListener(new View.OnTouchListener() {
            int initialX,initialY;
            float initialTouchX,initialTouchY;
            long startclickTime;

            int max_clic_dura = 200;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:

                        startclickTime = Calendar.getInstance().getTimeInMillis();
                        imageClose.setVisibility(View.VISIBLE);

                        initialX = layoutParams.x;
                        initialY = layoutParams.y;

                        //posicao do toque

                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        return  true;
                    case MotionEvent.ACTION_UP:
                        long clickDuration = Calendar.getInstance().getTimeInMillis()-startclickTime;
                        imageClose.setVisibility(View.GONE);

                        layoutParams.x = initialX + (int)(initialTouchX-event.getRawX());
                        layoutParams.y = initialY + (int)(event.getRawY()-initialTouchY);

                        if(clickDuration<max_clic_dura){
                            //Toast.makeText(WidgetService.this, "Time: "+tvWidget.getText().toString(), Toast.LENGTH_SHORT).show();
                            maisContador();
                            contar();
                        }else {
                            //remove widget
                            if(layoutParams.y>(heigth*0.6)){
                                stopSelf();
                            }

                        }

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //calcular as cordenadas x e y do movimento

                        layoutParams.x = initialX + (int) (initialTouchX-event.getRawX());
                        layoutParams.y = initialY + (int) (event.getRawY()-initialTouchY);

                        //atualizar as novas cordenadas!!!

                        windowManager.updateViewLayout(mFloatingView,layoutParams);

                        if(layoutParams.y>(heigth*0.6)){
                            imageClose.setImageResource(R.drawable.close_treco);
                        }else {
                            imageClose.setImageResource(R.drawable.close_treco);
                        }




                        return true;
                }


                return false;
            }
        });


        return START_STICKY;
    }


    private void maisContador() {
        contador_ofc++;
        tvWidget.setText(contador_ofc+"");

    }


    private void menosContador() {
        contador_ofc --;
        tvWidget.setText(contador_ofc+"");

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFloatingView!=null){
            windowManager.removeView(mFloatingView);
        }

        if(imageClose!=null){
            windowManager.removeView(imageClose);
        }
    }
}
