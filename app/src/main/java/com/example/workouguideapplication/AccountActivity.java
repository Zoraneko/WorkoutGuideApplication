package com.example.workouguideapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.acc_contstraintlayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button optionLogoutButton = findViewById(R.id.buttonOptionLogout);

        Button buttonTraining = findViewById(R.id.buttonTraining2);
        Button buttonExercises = findViewById(R.id.buttonExercises2);

        optionLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_logout = new Intent(AccountActivity.this, OptionLogSignActivity.class);
                // TODO: code them ocd dawng xuat

                startActivity(intent_logout);
                finish();
            }
        });


        buttonTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });

        buttonExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, ExercisesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Người dùng nhấn nút quay lại
            BacktoTraining();
            return true; // Đánh dấu sự kiện đã được xử lý
        }
        return super.onKeyDown(keyCode, event);
    }

    private void BacktoTraining()
    {
        Intent intent_account = new Intent(AccountActivity.this, TrainingActivity.class);
        startActivity(intent_account);
        finish();
    }
}