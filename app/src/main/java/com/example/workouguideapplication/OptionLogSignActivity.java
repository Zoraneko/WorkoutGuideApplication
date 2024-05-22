package com.example.workouguideapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OptionLogSignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_option_log_sign);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button buttonLogin = findViewById(R.id.buttonChooseLogin);
        Button buttonSignup = findViewById(R.id.buttonChooseSignup);

        Intent intentSignupComplete = getIntent();
        String message = intentSignupComplete.getStringExtra("Complete");

        // Hiển thị Toast thông báo nội dung nhận được
        if (message != null) {
            Toast.makeText(this, "You have just completed creating your account!\n Please login now.", Toast.LENGTH_LONG).show();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(OptionLogSignActivity.this, LoginActivity.class);

                startActivity(intentLogin);
            }
        });
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignin = new Intent(OptionLogSignActivity.this, SigninActivity.class);

                startActivity(intentSignin);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Người dùng nhấn nút quay lại
            showExitDialog();
            return true; // Đánh dấu sự kiện đã được xử lý
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Người dùng xác nhận thoát
                    exitApp(); // Kết thúc Activity
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void exitApp() {
        finishAffinity(); // Kết thúc tất cả các Activity trong ứng dụng
        System.exit(0); // Thoát hoàn toàn khỏi ứng dụng
    }
}