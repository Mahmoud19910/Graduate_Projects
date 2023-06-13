package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.StopwatchTimer;
import dev.mah.nassa.gradu_ptojects.Fragments.Fragment_StartTraining_CaloriesGoal;
import dev.mah.nassa.gradu_ptojects.Fragments.Fragment_StartTraining_FreeGoal;
import dev.mah.nassa.gradu_ptojects.Fragments.Fragment_StartTraining_TimeGoal;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityStartTrainingBinding;

public class StartTraining_Activity extends AppCompatActivity {
    private ActivityStartTrainingBinding binding;
    private int indexFrag;
    private Sports_Exercises sports_exercises;
    private String goalValue, uid;
    boolean isGoalAchived = false;
    private String goal, achivmentGoal;
    private StopwatchTimer timer;
    boolean musicIsPlay = true;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartTrainingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mediaPlayer =MediaPlayer.create(StartTraining_Activity.this , R.raw.sport_music);

        LanguageUtils.changeLanguage(StartTraining_Activity.this, "en");

        timer = new StopwatchTimer();
        Intent intent = getIntent();
        indexFrag = intent.getIntExtra("indexFrag", 1);
        goalValue = intent.getStringExtra("value");// قيمة الهدف الذي تم تحديديه سواء كان سعرات حرارية أو وقت زمني
        uid = intent.getStringExtra("uid");
        sports_exercises = (Sports_Exercises) intent.getSerializableExtra("obj");
        replace(indexFrag, sports_exercises);

        binding.titleStartTraining.setText(sports_exercises.getName());

        binding.music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (musicIsPlay) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.setLooping(true);
                        mediaPlayer.setDataSource(StartTraining_Activity.this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sport_music));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        binding.music.setImageDrawable(getDrawable(R.drawable.music_note_24));
                        musicIsPlay = false;
                    } else {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        binding.music.setImageDrawable(getDrawable(R.drawable.music_off_24));
                        musicIsPlay = true;
                    }
                } catch (IOException e) {
                    Toast.makeText(StartTraining_Activity.this, e.getMessage().toString()+"", Toast.LENGTH_SHORT).show();

                }




            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // is used to replace fragment
    private void replace(int indexFrag, Sports_Exercises sports_exercises) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj", sports_exercises);
        bundle.putString("value", goalValue);
        bundle.putString("uid", uid);

        switch (indexFrag) {
            case 0:
                Fragment_StartTraining_TimeGoal timeGoal = new Fragment_StartTraining_TimeGoal();
                timeGoal.setArguments(bundle);
                transaction.replace(R.id.framStartTraining, timeGoal);
                transaction.commit();
                break;
            case 1:
                Fragment_StartTraining_CaloriesGoal caloGoal = new Fragment_StartTraining_CaloriesGoal();
                caloGoal.setArguments(bundle);
                transaction.replace(R.id.framStartTraining, caloGoal);
                transaction.commit();
                break;
            case 2:
                Fragment_StartTraining_FreeGoal freeGoal = new Fragment_StartTraining_FreeGoal();
                freeGoal.setArguments(bundle);
                transaction.replace(R.id.framStartTraining, freeGoal);
                transaction.commit();
                break;
        }

    }


}
