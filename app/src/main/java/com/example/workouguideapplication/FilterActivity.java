package com.example.workouguideapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FilterActivity extends AppCompatActivity {
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private ToggleButton ButtonALL;
    String Muscle="",Equip="",GetAll="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonSendResult = findViewById(R.id.buttonSendResult);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        ButtonALL = findViewById(R.id.ButtonALL);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);
                if (checkedId != -1)
                    Muscle =  radioButton.getText().toString();

            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);
                if (checkedId != -1)
                    Equip = radioButton.getText().toString();

            }
        });
        ButtonALL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    radioGroup1.clearCheck();
                    radioGroup2.clearCheck();
                    for (int i = 0; i < radioGroup1.getChildCount(); i++) {
                        radioGroup1.getChildAt(i).setEnabled(false);
                    }
                    for (int i = 0; i < radioGroup2.getChildCount(); i++) {
                        radioGroup2.getChildAt(i).setEnabled(false);
                    }
                    Muscle = "";
                    Equip = "";
                    GetAll = "Yes";
                }
                else {
                    GetAll="";
                    for (int i = 0; i < radioGroup1.getChildCount(); i++) {
                        radioGroup1.getChildAt(i).setEnabled(true);
                    }
                    for (int i = 0; i < radioGroup2.getChildCount(); i++) {
                        radioGroup2.getChildAt(i).setEnabled(true);
                    }
                }
            }
        });


        buttonSendResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Muscle", Muscle);
                intent.putExtra("Equip",Equip);
                intent.putExtra("GetAll",GetAll);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}