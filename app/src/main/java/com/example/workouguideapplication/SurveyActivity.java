package com.example.workouguideapplication;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SurveyActivity extends AppCompatActivity {
    private RadioGroup gender;
    // Khai bao cac string trung gian
    String NAME, AGE, HEIGHT, WEIGHT, GENDER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khai bao cac button va input edit text
        TextInputEditText name = findViewById(R.id.textInputName);
        TextInputEditText age = findViewById(R.id.textInputAge);
        TextInputEditText height = findViewById(R.id.textInputHeight);
        TextInputEditText weight = findViewById(R.id.textInputWeight);
        RadioGroup gender = findViewById(R.id.radioGroup1);
        Button submit = findViewById(R.id.buttonSubmit);

        // Nhan gia tri cho cac string
        NAME = Objects.requireNonNull(name.getText()).toString();
        AGE = Objects.requireNonNull(age.getText()).toString();
        HEIGHT = Objects.requireNonNull(height.getText()).toString();
        WEIGHT = Objects.requireNonNull(weight.getText()).toString();


        // Code ham xu ly
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);
                if (checkedId != -1)
                    GENDER =  radioButton.getText().toString();

            }
        });





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NAME!=null && AGE!=null && HEIGHT!=null && WEIGHT!=null && GENDER!=null)
                {

                        if (Integer.parseInt(age.getText().toString())<=0||Float.parseFloat(height.getText().toString())<=0||Float.parseFloat(weight.getText().toString())<=0)
                        {
                            Toast.makeText(getBaseContext(),"You can not input negative numbers in Age - Height - Weight fields.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Redirecting to Training Activity", Toast.LENGTH_LONG).show();
                            Intent completeintent = new Intent(SurveyActivity.this, TrainingActivity.class);
                            // TODO: inset query code here
                            startActivity(completeintent);
                        }

                }
                else
                {
                    if (NAME==null||AGE==null||HEIGHT==null||WEIGHT==null||GENDER==null)
                    {
                        Toast.makeText(getBaseContext(),"You can not leave any field blank.\nPlease fill in all fields.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}