package com.example.workouguideapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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

public class InstructionActivity extends AppCompatActivity {
    Map<String, Object> data = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instruction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận Intent từ các bên khác gửi đến
        // Đồng thời nhận các giá trị thuộc tính khác như tên bài tập, ID

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        String ExerciseID = intent.getStringExtra("ExerciseID");


        // Khai báo các thành phần giao diện
        Button buttonDone = findViewById(R.id.buttonDone);
        TextView textViewExerciseName = findViewById(R.id.textViewWorkoutName);
        TextView textViewMuscle = findViewById(R.id.textViewMUSCLE2);
        TextView textViewInst = findViewById(R.id.textViewINST2);
        TextView textViewPrep = findViewById(R.id.textViewPREP2);
        TextView textViewTip = findViewById(R.id.textViewTIP2);
        VideoView videoGuide = (VideoView) findViewById(R.id.videoView3);

        //


        db.collection("Exercises")
                .whereEqualTo("Id", ExerciseID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data.putAll(document.getData());
                                textViewExerciseName.setText((CharSequence) data.get("Name"));
                                textViewMuscle.setText((CharSequence) data.get("Muscle"));
                                textViewPrep.setText((CharSequence) data.get("Prepare"));
                                String Ins = Objects.requireNonNull(data.get("Instruction")).toString();
                                String[] Inst = Ins.split(":");
                                StringBuilder Instruction = new StringBuilder();
                                for (String s : Inst) {
                                    Instruction.append(s).append("\r\n");
                                }
                                textViewInst.setText(Instruction.toString());
                                String tips = Objects.requireNonNull(data.get("Tips")).toString();
                                String[] stips = tips.split("\\.");
                                StringBuilder Tips = new StringBuilder();
                                for(String s : stips){
                                    Tips.append(s).append("\r\n");
                                }

                                textViewTip.setText(Tips.toString());
                                Uri uri = Uri.parse(Objects.requireNonNull(data.get("VideoLink")).toString());
                                videoGuide.setVideoURI(uri);
                            }
                        }
                    }
                });





        // Set VIDEO
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoGuide);
        videoGuide.setMediaController(mediaController);
        videoGuide.requestFocus();


        // Bắt đầu phát video
        videoGuide.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoGuide.start();
            }
        });
        videoGuide.setOnCompletionListener(mp -> videoGuide.start());

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}