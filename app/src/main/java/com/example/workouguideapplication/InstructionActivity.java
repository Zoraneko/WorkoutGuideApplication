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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InstructionActivity extends AppCompatActivity {

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
        // TODO sẽ có 1 lần duyệt để match theo ID đc intent gửi sang, sau đó truy xuất các thuộc tính kahsc
        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        String ExerciseID = intent.getStringExtra("ExerciseID");

        // TODO: truy vấn here
        String ExerciseName="Name";
        String ExerciseVideo = "[URL_here]";
        String ExerciseMuscle = "[MUSCLE]\n[MUSCLE]\n[MUSCLE]";
        String ExercisePrep = "[PREP]\n[PREP]\n[PREP]";
        String ExerciseInst = "[INST]\n[INST]\n[INST]";
        String ExerciseTip = "[TIP]\n[TIP]\n[TIP]";

        // Khai báo các thành phần giao diện
        Button buttonDone = findViewById(R.id.buttonDone);
        TextView textViewExerciseName = findViewById(R.id.textViewWorkoutName);
        TextView textViewMuscle = findViewById(R.id.textViewMUSCLE2);
        TextView textViewInst = findViewById(R.id.textViewINST2);
        TextView textViewPrep = findViewById(R.id.textViewPREP2);
        TextView textViewTip = findViewById(R.id.textViewTIP2);
        VideoView videoGuide = (VideoView) findViewById(R.id.videoView3);


        // Set text 5 thành phần NAME - MUSCLE - PREP - INST - TIP
        textViewExerciseName.setText(ExerciseName);
        textViewMuscle.setText(ExerciseMuscle);
        textViewPrep.setText(ExercisePrep);
        textViewInst.setText(ExerciseInst);
        textViewTip.setText(ExerciseTip);



        // Set VIDEO
        // FIXME: đây là minh họa video, sau này chúng ta sẽ sửa Uri.parse bằng string ExerciseVideo
        String videoUrl = "https://drive.google.com/uc?export=download&id=1M-OOPl3aqjr26b7harAUzaX8hOY4jk3B";
        Uri uri = Uri.parse(videoUrl);
        videoGuide.setVideoURI(uri);
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


        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}