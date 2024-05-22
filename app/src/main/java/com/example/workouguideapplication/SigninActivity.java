package com.example.workouguideapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SigninActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonSignin = findViewById(R.id.buttonSignin);

        List<Map<String, Object>> listdata = new ArrayList<>();

        checked = false;
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

        buttonSignin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView errorText = (TextView) findViewById(R.id.textView13);
                TextView inputusername = (TextView) findViewById(R.id.username);
                TextView inputpwd = (TextView) findViewById(R.id.pwd);
                TextView inputverifypwd = (TextView) findViewById(R.id.pwd2);

                String username = Objects.requireNonNull(inputusername.getText()).toString();
                String password = Objects.requireNonNull(inputpwd.getText()).toString();
                String verifypassword = Objects.requireNonNull(inputverifypwd.getText()).toString();



                if(username.isEmpty())
                {
                    errorText.setText("Username cannot be empty");
                    errorText.setVisibility(View.VISIBLE);
                }
                else
                {
                    if(password.isEmpty())
                    {
                        errorText.setText("Password cannot be empty");
                        errorText.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        checked = false;
                        for (Map<String, Object> data : listdata)
                        {
                            if (data.get("Username").toString().equals(username))
                            {
                                checked = true;
                            }
                        }

                        if(!checked)
                        {
                            if (!password.equals(verifypassword))
                            {
                                errorText.setText("Verify password is incorrect");
                                errorText.setVisibility(View.VISIBLE);
                                inputverifypwd.setText("");
                                inputpwd.setText("");
                                inputverifypwd.clearFocus();
                            }
                            else
                            {
                                if (password.length() < 8) {
                                    errorText.setText("Password must have at least 8 characters");
                                    errorText.setVisibility(View.VISIBLE);
                                    inputverifypwd.setText("");
                                    inputpwd.setText("");
                                    inputverifypwd.clearFocus();
                                }
                                else
                                {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Username", username);
                                    data.put("Password", password);
                                    db.collection("Account").document(username).set(data);
                                    Intent intent_signin = new Intent(SigninActivity.this, OptionLogSignActivity.class);
                                    startActivity(intent_signin);
                                }
                            }
                        }
                        else
                        {
                            errorText.setText("Username already exists");
                            errorText.setVisibility(View.VISIBLE);
                            inputverifypwd.setText("");
                            inputpwd.setText("");
                            inputusername.setText("");
                            inputverifypwd.clearFocus();
                        }


                    }

                }




            }
        });
    }
}


