package com.example.workouguideapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExercisesActivity extends AppCompatActivity {
    Map<String, Object> data = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        Button buttonTraining = findViewById(R.id.buttonTraining2);
        Button buttonAccount = findViewById(R.id.buttonAccount2);

        // Đây là mảng kí tự chứa tên các bài tập


        LinearLayout layout = findViewById(R.id.Container_exercises);


        db.collection("Exercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data.putAll(document.getData());
                                // Tạo Button mới cho mỗi bài tập
                                LinearLayout exerciseLayout = new LinearLayout(ExercisesActivity.this);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                params.setMargins(0, 0, 0, 32); // Khoảng cách giữa các ô
                                exerciseLayout.setLayoutParams(params);
                                exerciseLayout.setOrientation(LinearLayout.VERTICAL);



                                // Tạo TextView cho tên bài tập
                                Button exerciseButton = new Button(ExercisesActivity.this);

                                exerciseButton.setText((CharSequence) data.get("Name")); // TODO: đặt tên theo tên đang đc duyệt

                                exerciseButton.setTextSize(32f);
                                exerciseButton.setTextColor(Color.WHITE);
                                exerciseButton.setPadding(16, 16, 16, 32);
                                exerciseButton.setBackgroundColor(Color.BLACK);
                                exerciseButton.setClickable(true);

                                // Tạm thời khởi tạo finalI để lấy giá trị hiện tại của i
                                // finalI sẽ được sử dụng làm biến đếm để putExtra có thể truy cập và gửi giá trị ID trong mảng cho InstructionActivity

                                exerciseButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ExercisesActivity.this, InstructionActivity.class);
                                        // Ở đây chúng ta sẽ đặt thêm các Extra Message khác cho Intent được gửi cho InstructionActivity


                                        intent.putExtra("EXTRA_MESSAGE","Exercises");


                                        intent.putExtra("ExerciseID",(String)data.get("Id"));


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
                });





        // TODO: tạo thêm 1 button mới để lọc + tạo event listener với nhưng exercise có tag ko match sẽ bị ẩn

        buttonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExercisesActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        buttonTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExercisesActivity.this, TrainingActivity.class);
                startActivity(intent);
            }
        });
    }
}