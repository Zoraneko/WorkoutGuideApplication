package com.example.workouguideapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapp.utils.GlobalSingleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.myapp.utils.GlobalSingleton;

public class AccountActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        GlobalSingleton singleton = GlobalSingleton.getInstance();
        String username = singleton.getGlobalVariable();

        TextView name = findViewById(R.id.textViewName);
        TextView age = findViewById(R.id.textViewAge);
        TextView gender = findViewById(R.id.textViewGender);
        TextView weight = findViewById(R.id.textViewWeight);
        TextView height = findViewById(R.id.textViewHeight);

        db.collection("Account")
                .whereEqualTo("Username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               for (QueryDocumentSnapshot document : task.getResult()) {
                                                   Map<String, Object> data = (HashMap<String, Object>) document.getData();
                                                   name.setText(Objects.requireNonNull(data.get("Name")).toString());
                                                   age.setText(Objects.requireNonNull(data.get("Age")).toString());
                                                   gender.setText(Objects.requireNonNull(data.get("Gender")).toString());
                                                   weight.setText(Objects.requireNonNull(data.get("Weight")).toString());
                                                   height.setText(Objects.requireNonNull(data.get("Height")).toString());
                                               }
                                           }
                                       });

        Button optionLogoutButton = findViewById(R.id.buttonOptionLogout);
        Button gotoSurvey = findViewById(R.id.buttonSurvey);
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
        gotoSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_survey = new Intent(AccountActivity.this, SurveyActivity.class);
                intent_survey.putExtra("Username", username);
                startActivity(intent_survey);
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