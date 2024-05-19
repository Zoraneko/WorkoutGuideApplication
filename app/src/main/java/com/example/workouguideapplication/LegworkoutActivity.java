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

public class LegworkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_legworkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonReturn = findViewById(R.id.buttonReturn);
        // TODO: FIX FIX FIX
        /*Button B301 = findViewById(R.id.buttonA301);
        Button B302 = findViewById(R.id.buttonA302);
        Button B303 = findViewById(R.id.buttonA303);
        Button B304 = findViewById(R.id.buttonA304);

        B301.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentINST = new Intent(LegworkoutActivity.this,InstructionActivity.class);
                intentINST.putExtra("ExerciseID","301");
                startActivity(intentINST);
            }
        });
        B302.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentINST = new Intent(LegworkoutActivity.this,InstructionActivity.class);
                intentINST.putExtra("ExerciseID","302");
                startActivity(intentINST);
            }
        });
        B303.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentINST = new Intent(LegworkoutActivity.this,InstructionActivity.class);
                intentINST.putExtra("ExerciseID","303");
                startActivity(intentINST);
            }
        });
        B304.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentINST = new Intent(LegworkoutActivity.this,InstructionActivity.class);
                intentINST.putExtra("ExerciseID","304");
                startActivity(intentINST);
            }
        });
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}