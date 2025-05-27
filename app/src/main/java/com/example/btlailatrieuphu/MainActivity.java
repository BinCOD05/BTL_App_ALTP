package com.example.btlailatrieuphu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button btnPlay = (Button) findViewById(R.id.btnPlay);
        Button btnExit = (Button) findViewById(R.id.btnExit);
        Button btnReset = findViewById(R.id.btnReset);
        TextView txtMoney = findViewById(R.id.txtMoney);
        SharedPreferences pref = getSharedPreferences("game_data" , MODE_PRIVATE);
        int money = pref.getInt("Total_Money" , 0);
        txtMoney.setText("Thành tựu: " + money + "VND");
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this , DisplayGame.class);
                startActivity(it);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("game_data" , MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("Total_Money" , 0) ;
                editor.apply();
                txtMoney.setText("Thành tựu: " + pref.getInt("Total_Money" , 0) + "VND");
            }
        });
    }
}