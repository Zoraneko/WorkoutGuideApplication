package com.example.workouguideapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import android.provider.CalendarContract;
import android.widget.Toast;
import com.example.myapp.utils.GlobalSingleton;

public class TrainingActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_training);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        GlobalSingleton singleton = GlobalSingleton.getInstance();
        singleton.setGlobalVariable(intent.getStringExtra("Username"));

        // Khai báo các button
        Button buttonChest = findViewById(R.id.buttonChestworkout);
        Button buttonBack = findViewById(R.id.buttonBackworkout);
        Button buttonArm = findViewById(R.id.buttonArmworkout);
        Button buttonLeg = findViewById(R.id.buttonLegworkout);

        Button buttonExercises = findViewById(R.id.buttonExercises2);
        Button buttonAccount = findViewById(R.id.buttonAccount2);
        ImageButton buttonReminder = findViewById(R.id.imageButton);

        // Load ảnh từ tài nguyên drawable
        Bitmap originalBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.chestimage);
        Bitmap originalBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.backimage);
        Bitmap originalBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.armimage);
        Bitmap originalBitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.legimage);

// Resize ảnh
        int width = 210; // Chiều rộng mới
        int height = 210; // Chiều cao mới
        Bitmap resizedBitmap1 = Bitmap.createScaledBitmap(originalBitmap1, width, height, false);
        Bitmap resizedBitmap2 = Bitmap.createScaledBitmap(originalBitmap2, width, height, false);
        Bitmap resizedBitmap3 = Bitmap.createScaledBitmap(originalBitmap3, width, height, false);
        Bitmap resizedBitmap4 = Bitmap.createScaledBitmap(originalBitmap4, width, height, false);

        // Thiết lập ảnh cho drawableleft của button
        buttonChest.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getResources(), resizedBitmap1), null, null, null);
        buttonBack.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getResources(), resizedBitmap2), null, null, null);
        buttonArm.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getResources(), resizedBitmap3), null, null, null);
        buttonLeg.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getResources(), resizedBitmap4), null, null, null);

        // Triển khai onclick eventlisstener cho lịch hẹn
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần +1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String startTime = String.format("%04d-%02d-%02dT%02d:%02d:%02d", year, month, day, hour, minute, second);
        String endTime = String.format("%04d-%02d-%02dT%02d:%02d:%02d", year, month, day, hour + 4, minute, second);

        // Parsing the date and time
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date mStartTime = null;
        Date mEndTime = null;
        try {
            mStartTime = mSimpleDateFormat.parse(startTime);
            mEndTime = mSimpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // When Button is clicked, Intent started
        // to create an event with given time
        Date finalMStartTime = mStartTime;
        Date finalMEndTime = mEndTime;
        buttonReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_EDIT);
                mIntent.setType("vnd.android.cursor.item/event");
                mIntent.putExtra("beginTime", finalMStartTime.getTime());
                mIntent.putExtra("time", true);
                mIntent.putExtra("rule", "FREQ=WEEKLY");
                mIntent.putExtra("endTime", finalMEndTime.getTime());
                mIntent.putExtra("title", "Workout Reminder");
                startActivity(mIntent);
            }
        });

        // Triển khai onclick eventlistener cho 4 button
        buttonChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChest = new Intent(TrainingActivity.this, ChestworkoutActivity.class);

                startActivity(intentChest);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(TrainingActivity.this, BackworkoutActivity.class);
                startActivity(intentBack);
            }
        });
        buttonArm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentArm = new Intent(TrainingActivity.this, ArmworkoutActivity.class);
                startActivity(intentArm);
            }
        });
        buttonLeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLeg = new Intent(TrainingActivity.this, LegworkoutActivity.class);
                startActivity(intentLeg);
            }
        });

        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        buttonExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingActivity.this, ExercisesActivity.class);
                startActivity(intent);
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