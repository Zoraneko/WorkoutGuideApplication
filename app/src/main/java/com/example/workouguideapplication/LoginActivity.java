package com.example.workouguideapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.KeyEvent;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Map<String, Object>> listdata = new ArrayList<>();
    boolean checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button loginButton = findViewById(R.id.buttonLogin);
        Intent intent = getIntent();
        String Username = intent.getStringExtra("Username");
        String Password = intent.getStringExtra("Password");

        db.collection("Account")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listdata.add(document.getData());
                            }
                        }
                    }
                });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                checked = false;

                TextInputEditText InputUsername = (TextInputEditText) findViewById(R.id.username);
                TextInputEditText InputPassword = (TextInputEditText) findViewById(R.id.pwd);
                TextView errorText = findViewById(R.id.textView3);

                String username = Objects.requireNonNull(InputUsername.getText()).toString();
                String password = Objects.requireNonNull(InputPassword.getText()).toString();

                for(Map<String, Object> item : listdata)
                {
                    if(Objects.equals(item.get("Username"), username) && Objects.equals(item.get("Password"), password)  &&  Objects.equals(Username, username))
                    {
                        checked = true;
                        break;
                    }
                }

                if(checked)
                {
                    if(Objects.equals(Username, username))
                    {
                        Intent intent_login = new Intent(LoginActivity.this, SurveyActivity.class);
                        intent_login.putExtra("Username", Username);
                        intent_login.putExtra("Password", Password);
                        startActivity(intent_login);

                    }
                    else
                    {
                        errorText.setText("Username or password is invalid");
                        errorText.setVisibility(View.VISIBLE);
                        InputPassword.setText("");
                        InputUsername.setText("");
                        InputPassword.clearFocus();
                    }
                }
                else
                {
                    Intent intent_login = new Intent(LoginActivity.this, TrainingActivity.class);
                    intent_login.putExtra("Username", username);
                    startActivity(intent_login);
                }
            }
        });
    }
}