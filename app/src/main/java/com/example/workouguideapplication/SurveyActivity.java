package com.example.workouguideapplication;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapp.utils.GlobalSingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;



public class SurveyActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        Intent intent = getIntent();
        String Username = intent.getStringExtra("Username");
        String Password = intent.getStringExtra("Password");

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

        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    age.requestFocus();
                    return true;
                }
                return false;
            }
        });

        age.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    gender.requestFocus();
                    return true;
                }
                return false;
            }
        });
        height.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    weight.requestFocus();
                    return true;
                }
                return false;
            }
        });

        weight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(weight.getWindowToken(), 0);
                    submit.performClick();
                    return true;
                }
                return false;
            }
        });

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
                            NAME = Objects.requireNonNull(name.getText()).toString();
                            AGE = Objects.requireNonNull(age.getText()).toString();
                            HEIGHT = Objects.requireNonNull(height.getText()).toString();
                            WEIGHT = Objects.requireNonNull(weight.getText()).toString();
                            Intent completeintent = new Intent(SurveyActivity.this, TrainingActivity.class);

                            HashMap<String, Object> data = new HashMap<>();
                            data.put("Username", Username);
                            data.put("Password", Password);
                            data.put("Name", NAME);
                            data.put("Age", AGE);
                            data.put("Height", HEIGHT);
                            data.put("Weight", WEIGHT);
                            data.put("Gender", GENDER);

                            db.collection("Account").document(Username).set(data);

                            completeintent.putExtra("Username", Username);
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