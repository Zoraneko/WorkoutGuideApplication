package com.example.workouguideapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorLong;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity {
    Map<String, Object> data = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Map<String, Object>> list = new ArrayList<>();

    private ActivityResultLauncher<Intent> resultLauncher;
    String FilterMuscle = "", FilterEquip = "", FilterGetAll = "";
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
        ImageButton buttonFilter = findViewById(R.id.imageButtonSort);

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExercisesActivity.this, FilterActivity.class);
                resultLauncher.launch(intent);
            }
        });
        // Đây là mảng kí tự chứa tên các bài tập


        LinearLayout layout = findViewById(R.id.Container_exercises);


        db.collection("Exercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = new HashMap<>(document.getData());

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

                                exerciseButton.setTextSize(28f);
                                exerciseButton.setTextColor(Color.WHITE);
                                exerciseButton.setPadding(16, 16, 16, 32);
                                exerciseButton.setBackgroundColor(Color.BLACK);
                                exerciseButton.setClickable(true);

                                list.add(data);

                                exerciseButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ExercisesActivity.this, InstructionActivity.class);
                                        // Ở đây chúng ta sẽ đặt thêm các Extra Message khác cho Intent được gửi cho InstructionActivity


                                        intent.putExtra("EXTRA_MESSAGE", "Exercises");


                                        for (Map<String, Object> l : list) {
                                            if (l.get("Name") == exerciseButton.getText()) {
                                                intent.putExtra("ExerciseID", (String) l.get("Id"));
                                                break;
                                            }
                                        }


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

        // ----------------------------------------------------------------
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{

        });
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            FilterMuscle = data.getStringExtra("Muscle");
                            FilterEquip = data.getStringExtra("Equip");
                            FilterGetAll = data.getStringExtra("GetAll");
                            // Cos thể code tại đây vì đây là hàm thực hiện sau khi Result đc trả về
                            if (Objects.equals(FilterGetAll, "Yes")) {
                                layout.removeAllViews();
                                db.collection("Exercises")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Map<String, Object> data = new HashMap<>(document.getData());

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

                                                        exerciseButton.setTextSize(28f);
                                                        exerciseButton.setTextColor(Color.WHITE);
                                                        exerciseButton.setPadding(16, 16, 16, 32);
                                                        exerciseButton.setBackgroundColor(Color.BLACK);
                                                        exerciseButton.setClickable(true);

                                                        list.add(data);

                                                        exerciseButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent intent = new Intent(ExercisesActivity.this, InstructionActivity.class);
                                                                // Ở đây chúng ta sẽ đặt thêm các Extra Message khác cho Intent được gửi cho InstructionActivity


                                                                intent.putExtra("EXTRA_MESSAGE", "Exercises");


                                                                for (Map<String, Object> l : list) {
                                                                    if (l.get("Name") == exerciseButton.getText()) {
                                                                        intent.putExtra("ExerciseID", (String) l.get("Id"));
                                                                        break;
                                                                    }
                                                                }


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
                                        })
                                ;

                            }
                        }
                        if (!Objects.equals(FilterGetAll, "Yes")){
                            layout.removeAllViews();

                            db.collection("Exercises")
                                    .whereEqualTo("Equipment", FilterEquip)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Map<String, Object> data = new HashMap<>(document.getData());

                                                    String muscle = Objects.requireNonNull(data.get("Muscle")).toString();
                                                    String[] muscles = muscle.split(",");

                                                    boolean checked = false;
                                                    for (String s : muscles) {
                                                        if (Objects.equals(s, FilterMuscle)) {
                                                            checked = true;
                                                            break;
                                                        }
                                                    }
                                                    list.add(data);
                                                    if (checked) {
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

                                                        exerciseButton.setTextSize(28f);
                                                        exerciseButton.setTextColor(Color.WHITE);
                                                        exerciseButton.setPadding(16, 16, 16, 32);
                                                        exerciseButton.setBackgroundColor(Color.BLACK);
                                                        exerciseButton.setClickable(true);
                                                        exerciseButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent intent = new Intent(ExercisesActivity.this, InstructionActivity.class);
                                                                // Ở đây chúng ta sẽ đặt thêm các Extra Message khác cho Intent được gửi cho InstructionActivity


                                                                intent.putExtra("EXTRA_MESSAGE", "Exercises");


                                                                for (Map<String, Object> l : list) {
                                                                    if (l.get("Name") == exerciseButton.getText()) {
                                                                        intent.putExtra("ExerciseID", (String) l.get("Id"));
                                                                        break;
                                                                    }
                                                                }


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
                                        }
                                    })
                            ;
                        }

                    }
                })
        ;


    }
}