package com.example.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2000; // Splash ekranın gösterileceği süre (2 saniye)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        // Splash ekranın gösterildikten sonra ana aktiviteye geçmek için bir zamanlayıcı kullanın
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ana aktiviteye geçiş
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Bu aktiviteyi kapat
            }
        }, SPLASH_DELAY);
    }
}