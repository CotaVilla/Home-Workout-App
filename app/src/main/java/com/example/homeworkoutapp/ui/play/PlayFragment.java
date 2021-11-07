package com.example.homeworkoutapp.ui.play;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.Recyclers.PlayerRecycler;
import com.example.homeworkoutapp.objects.Rutine;
import com.example.homeworkoutapp.objects.Rutine_Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlayFragment extends Fragment {
    private Rutine rutine;
    private ArrayList<Rutine_Exercise> list_exercises;
    private RecyclerView recycler;
    PlayerRecycler adapter;

    Database_Helper database_helper;

    SeekBar seekBar;
    TextView position;
    TextView exerciseName;
    TextView progress;
    TextView timeText;
    TextView totalTimeText;
    TextView fase;
    FloatingActionButton buttonPlay;
    FloatingActionButton buttonPrevious;
    FloatingActionButton buttonNext;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    Rutine_Exercise rutine_exercise;
    int timeToPlay;
    int actuaRepeat;
    int repeats;
    boolean rest;

    Boolean counterIsActive = false;
    Boolean playclicked = false;

    public PlayFragment (Rutine rutine){
        this.rutine = rutine;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*container.removeAllViews();*/
        View root = inflater.inflate(R.layout.fragment_play, container, false);
        database_helper = new Database_Helper(getActivity());

        // RecyclerView Rutinas
        recycler = (RecyclerView) root.findViewById(R.id.recyler_player);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                setNewExcercise();
            }
        });

        // Get routines from database
        Database_Helper database_helper = new Database_Helper(getActivity());
        list_exercises = database_helper.getExercises(rutine.id);

        adapter = new PlayerRecycler(list_exercises,((FragmentActivity)getContext()).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_start));
        recycler.setAdapter(adapter);
        // END RecyclerView

        buttonPlay = root.findViewById(R.id.player_play);
        buttonPrevious = root.findViewById(R.id.player_previous);
        buttonNext = root.findViewById(R.id.player_next);
        seekBar = root.findViewById(R.id.player_seekbar);
        position = root.findViewById(R.id.position);
        exerciseName = root.findViewById(R.id.exercise_name);
        progress = root.findViewById(R.id.repeats);
        fase = root.findViewById(R.id.fase);
        timeText = root.findViewById(R.id.actual_time);
        totalTimeText = root.findViewById(R.id.total_time);
        buttonPlay = root.findViewById(R.id.player_play);
        mediaPlayer = MediaPlayer.create(root.getContext(), R.raw.bongo_alarm_huawei_ringtones);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPlay.setClickable(false);
                start_timer();
                buttonPlay.setClickable(true);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonNext.setClickable(false);
                nextButtonClick();
                buttonNext.setClickable(true);
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateOnSeekbarChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(counterIsActive){
                    countDownTimer.cancel();
                    buttonPlay.setClickable(false);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(counterIsActive){
                    countDownTimer = setTimer(seekBar.getMax()-seekBar.getProgress());
                    countDownTimer.start();
                    counterIsActive = true;
                    buttonPlay.setClickable(true);
                }
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    public CountDownTimer setTimer(int seconds) {

        return new CountDownTimer(seconds*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateOnCounterTick(seekBar.getMax(),(int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                if((actuaRepeat != repeats) || !rest){
                    nextTimer();
                }
                else {
                    nextItem();
                    // TODO: change to next exercise.
                }
            }
        };
    }

    public void start_timer() {

        if(!counterIsActive){
            counterIsActive = true;
            buttonPlay.setImageResource(R.drawable.ic_pause);
            countDownTimer = setTimer(timeToPlay - seekBar.getProgress());
            countDownTimer.start();
        }else{
            counterIsActive = false;
            buttonPlay.setImageResource(R.drawable.ic_play);
            countDownTimer.cancel();
        }
    }

    public void setNewExcercise(){
        rutine_exercise = adapter.getSelectedValues();
        exerciseName.setText(rutine_exercise.exercise_name);
        position.setText("#"+rutine_exercise.position);
        actuaRepeat = 0;
        rest = false;
        nextTimer();
    }

    public void nextButtonClick(){

        if(!(actuaRepeat >= repeats) || rest){
            if(rest){
                timeToPlay = rutine_exercise.rest_time;
                updateValues();
                rest=false;
            }
            else {
                actuaRepeat++;
                timeToPlay = rutine_exercise.work_time;
                updateValues();
                rest=true;
            }
            if(counterIsActive){
                countDownTimer.cancel();
                countDownTimer = setTimer(timeToPlay);
                countDownTimer.start();
            }
        }
        else {
            if(rutine_exercise.position < adapter.getItemCount()){
                adapter.changeSelected();
                setNewExcercise();
            }
            else {
                counterIsActive=false;
                adapter.resetSelection();
                setNewExcercise();

                buttonPlay.setImageResource(R.drawable.ic_play);
            }
        }
    }

    public void nextTimer(){

        if(rest){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.rest_sound);
            timeToPlay = rutine_exercise.rest_time;
            updateValues();
            rest=false;
        }
        else {
            actuaRepeat++;
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.work_sound);
            timeToPlay = rutine_exercise.work_time;
            updateValues();
            rest=true;
        }
        if(counterIsActive){
            countDownTimer.cancel();
            countDownTimer = setTimer(timeToPlay);
            countDownTimer.start();
            if (actuaRepeat != 1 || !rest)
                mediaPlayer.start();
        }
    }

    public void updateValues(){
        if(rest){
            fase.setText(R.string.descansar);
        }
        else{
            fase.setText(R.string.ejercistarse);
        }
        repeats = rutine_exercise.repeats;
        String _progress = actuaRepeat + "/" + repeats;
        progress.setText(_progress);
        totalTimeText.setText(formatTime(timeToPlay));
        seekBar.setMax(timeToPlay);
        seekBar.setProgress(0);
    }
    public void selectedItemChanged(){
        if(countDownTimer!= null){
            countDownTimer.cancel();
            countDownTimer = null;
            counterIsActive =false;
            buttonPlay.setImageResource(R.drawable.ic_play);
            setNewExcercise();
        }
    }
    public void nextItem(){
        if(rutine_exercise.position < adapter.getItemCount()){
            adapter.changeSelected();
            setNewExcercise();
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.change_exercise_sound);
            mediaPlayer.start();
        }
        else {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.rutine_finished);
            mediaPlayer.start();
            counterIsActive=false;
            adapter.resetSelection();
            setNewExcercise();

            buttonPlay.setImageResource(R.drawable.ic_play);
        }
    }



    private void updateOnSeekbarChange(int time){
        timeText.setText(formatTime(time));
    }

    private void updateOnCounterTick(int max, int time){
        int progress = max-time;
        seekBar.setProgress(progress);
    }

    public String formatTime(int seconds){
        // Divide progress by 60 to get the minutes
        int min = seconds / 60;
        // Divide progress by 60 and get the remainder for seconds
        int sec = seconds % 60;
        String secondsFinal;
        // If the value of seconds is less than or equal to 9,
        // print a leading zero if you want to show seconds in 2 digits format
        if(sec <= 9){
            secondsFinal = "0" + sec;
        }else{
            secondsFinal = "" + sec;
        }
        return "" + min + ":" + secondsFinal;
    }

    // In onPause() and onDestroy(), if the counterIsActive flag is true,
    // cancel countDownTimer.
    @Override
    public void onPause() {
        super.onPause();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }
}
