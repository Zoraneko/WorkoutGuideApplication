package com.example.workouguideapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercises);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] exercises = {"Push Up", "Deadlift", "Jumping Jack"};
        LinearLayout layout = findViewById(R.id.Container_exercises);

        for (String exercise : exercises) {
            // Tạo Button mới cho mỗi bài tập
            LinearLayout exerciseLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 32); // Khoảng cách giữa các ô
            exerciseLayout.setLayoutParams(params);
            exerciseLayout.setOrientation(LinearLayout.VERTICAL);



            // Tạo TextView cho tên bài tập
            Button exerciseButton = new Button(this);
            exerciseButton.setText(exercise);
            exerciseButton.setTextSize(32f);
            exerciseButton.setTextColor(Color.WHITE);
            exerciseButton.setPadding(16, 16, 16, 32);
            exerciseButton.setBackgroundColor(Color.BLACK);
            exerciseButton.setClickable(true);

            exerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExercisesActivity.this, InstructionActivity.class);
                    intent.putExtra("EXTRA_MESSAGE","Exercises");
                    startActivity(intent);
                }
            });



            // Thêm các TextView vào exerciseLayout
            exerciseLayout.addView(exerciseButton);

            // Thêm exerciseLayout vào layout chính
            layout.addView(exerciseLayout);
        }
    }
}