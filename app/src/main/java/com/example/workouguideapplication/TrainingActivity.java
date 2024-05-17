package com.example.workouguideapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TrainingActivity extends AppCompatActivity {

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

        // Khai báo các button
        Button buttonChest = findViewById(R.id.buttonChestworkout);
        Button buttonBack = findViewById(R.id.buttonBackworkout);
        Button buttonArm = findViewById(R.id.buttonArmworkout);
        Button buttonLeg = findViewById(R.id.buttonLegworkout);

        Button buttonExercises = findViewById(R.id.buttonExercises2);
        Button buttonAccount = findViewById(R.id.buttonAccount2);

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
}