package com.example.homeworkoutapp.ui.exercises;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkoutapp.Database_Helper;
import com.example.homeworkoutapp.R;
import com.example.homeworkoutapp.StartActivity;
import com.example.homeworkoutapp.databinding.FragmentNewExerciseBinding;
import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.objects.Rutine_Exercise;

// clase para agregar un nuevo ejercicio a la rutina
public class NewExerciseFragment extends Fragment {
    // variables de la clase
    Context context;
    FragmentNewExerciseBinding binding;
    Database_Helper database_helper;

    StartActivity activity;

    Rutine_Exercise pasableRE;
    Exercise pasableExercise;
    Rutine_Exercise actualExercise;
    int workTimeHours;
    int workTimeMinutes;
    int workTimeSeconds;
    int _workTimeHours;
    int _workTimeMinutes;
    int _workTimeSeconds;
    int repeats;

    TextView title;
    TextView exerciseName;
    TextView workTime;
    TextView restTime;
    TextView repetitions;

    AppCompatButton select;
    AppCompatButton accept;
    AppCompatButton cancel;

    //Constructor
    public NewExerciseFragment() {

    }

    // cargar las variable si iniciales al crear la clase
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(actualExercise != null){
            database_helper = new Database_Helper(getActivity());
            database_helper.getExercise(actualExercise.exercise_id);
            setWorkTime(actualExercise.work_time);
            setRestTime(actualExercise.rest_time);
            repeats = actualExercise.repeats;
        }
        else {
            workTimeHours = 0;
            workTimeMinutes = 0;
            workTimeSeconds = 0;

            _workTimeHours = 0;
            _workTimeMinutes = 0;
            _workTimeSeconds = 0;

            repeats = 0;
        }
    }

    // para convertir los segundos de trabajo en hora/minutos/segundos
    public void setWorkTime(int time){
        workTimeHours = time / 3600;
        workTimeMinutes = time % 3600 / 60;
        workTimeSeconds = time % 3600 % 60;
    }

    // para convertir los segundos de descanso en hora/minutos/segundos
    public void setRestTime(int time){
        _workTimeHours = time / 3600;
        _workTimeMinutes = time % 3600 / 60;
        _workTimeSeconds = time % 3600 % 60;
    }

    // para cuando se resume despues de elegir un ejercicio
    @Override
    public void onResume() {
        super.onResume();
        // si se selecciono no deberia de estar nula la variable
        if(activity.getPasableExercise() != null){
            //y cargaria el dato del ejercicio
            pasableExercise = activity.getPasableExercise();
            exerciseName.setText(pasableExercise.name);
            activity.setPasableExercise(null);
        }
    }

    // inicializacion de la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (StartActivity) getActivity();
        binding = FragmentNewExerciseBinding.inflate(inflater, container, false);
        context = getContext();
        View root = binding.getRoot();

        title = root.findViewById(R.id.frag_title);
        exerciseName = root.findViewById(R.id.selected_exercise);
        workTime = root.findViewById(R.id.exercise_work_time);
        restTime = root.findViewById(R.id.exercise_rest_time);
        repetitions = root.findViewById(R.id.exercise_repetitions);
        select = root.findViewById(R.id.select_exercise);
        accept = root.findViewById(R.id.add_excercise);
        cancel = root.findViewById(R.id.cancel_exercise);

        title.setText("Añadir ejercicio");

        // cargar los datos en los textview si tienen algo
        if(actualExercise != null){
            pasableExercise = database_helper.getExercise(actualExercise.exercise_id);
            exerciseName.setText(actualExercise.exercise_name);
        }

        if(pasableExercise != null){
            exerciseName.setText(pasableExercise.name);
        }

        if (workTimeHours != 0 || workTimeMinutes != 0 || workTimeSeconds != 0){
            workTime.setText(String.format("%1$02d:%2$02d:%3$02d", workTimeHours, workTimeMinutes, workTimeSeconds));
        }
        if (_workTimeHours != 0 || _workTimeMinutes != 0 || _workTimeSeconds != 0){
            restTime.setText(String.format("%1$02d:%2$02d:%3$02d", _workTimeHours, _workTimeMinutes, _workTimeSeconds));
        }
        if (repeats != 0){
            if(repeats == 1){
                repetitions.setText(String.format(repeats + " vez"));
            }
            else {
                repetitions.setText(String.format(repeats + " veces"));
            }
        }

        //eventos de los campos
        workTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        restTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker2();
            }
        });

        repetitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRepeatsPicker();
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // para abrir la vista de seleccion de ejercicio
                // se le pasa un true para que los ejercicios devuelvan el ejercicio cuando lo seleciones
                ExercisesFragment exercisesFragment = new ExercisesFragment(true);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.nav_host_fragment_content_start,exercisesFragment);
                fragmentTransaction.commit();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se validan los campos
                if (validateDataInserted()){
                    // se devuelve el ejercicio con los timepos y repeticiones
                    Log.d("VALID","Datos validos");
                    int worktimeSeconds = converTimeSeconds(workTimeHours,workTimeMinutes,workTimeSeconds);
                    int restimeSeconds = converTimeSeconds(_workTimeHours,_workTimeMinutes,_workTimeSeconds);
                    pasableRE = new Rutine_Exercise(pasableExercise.exercise_id,0,0, pasableExercise.name, worktimeSeconds,restimeSeconds, repeats);

                    activity.setPasableRE(pasableRE);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return root;
    }

    // metodo para validar los campos
    private boolean validateDataInserted(){
        // sale un mensaje con el campo faltante de datos
        if(pasableExercise == null){
            makeToast(Toast.makeText(context,"Seleccione un ejercicio.",Toast.LENGTH_SHORT));
            return false;
        }
        else if (workTimeHours == 0 && workTimeMinutes == 0 && workTimeSeconds == 0){
            makeToast(Toast.makeText(context,"Ingrese el tiempo de ejercicio.",Toast.LENGTH_SHORT));
            return false;
        }
        else if (_workTimeHours == 0 && _workTimeMinutes == 0 && _workTimeSeconds == 0){
            makeToast(Toast.makeText(context,"Ingrese el tiempo de descanso.",Toast.LENGTH_SHORT));
            return false;
        }
        else if (repeats == 0){
            makeToast(Toast.makeText(context,"Ingrese las repeticiones.",Toast.LENGTH_SHORT));
            return false;
        }
        return true;
    }

    // metodo para mostrar un mensaje personalizado
    public void makeToast(Toast _toast){
        Toast toast = _toast;
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.container_toast);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }

    // metodo para mostrar el selector de tiempo para ejercitarse
    public void showTimePicker(){
        Dialog timePickerDialog = new Dialog(context);
        timePickerDialog.setContentView(R.layout.dialog_time);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton cancel = timePickerDialog.findViewById(R.id.cancel);
        AppCompatButton ok = timePickerDialog.findViewById(R.id.ok);
        NumberPicker numberPickerHour = timePickerDialog.findViewById(R.id.numpicker_hours);
        NumberPicker numberPickerMinutes = timePickerDialog.findViewById(R.id.numpicker_minutes);
        NumberPicker numberPickerSeconds = timePickerDialog.findViewById(R.id.numpicker_seconds);

        numberPickerHour.setMaxValue(23);
        numberPickerMinutes.setMaxValue(59);
        numberPickerSeconds.setMaxValue(59);

        numberPickerHour.setValue(workTimeHours);
        numberPickerMinutes.setValue(workTimeMinutes);
        numberPickerSeconds.setValue(workTimeSeconds);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // workTime.setText(numberPickerHour.getValue() + ":" + numberPickerMinutes.getValue() + ":" + numberPickerSeconds.getValue());
                workTimeHours = numberPickerHour.getValue();
                workTimeMinutes = numberPickerMinutes.getValue();
                workTimeSeconds = numberPickerSeconds.getValue();

                if (workTimeHours != 0 || workTimeMinutes != 0 || workTimeSeconds != 0){
                    workTime.setText(String.format("%1$02d:%2$02d:%3$02d", workTimeHours, workTimeMinutes, workTimeSeconds));
                }

                timePickerDialog.dismiss();
            }
        });
        timePickerDialog.show();
    }

    // metodo para mostrar el selector de tiempo para descansar
    public void showTimePicker2(){
        Dialog timePickerDialog = new Dialog(context);
        timePickerDialog.setContentView(R.layout.dialog_time);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton cancel = timePickerDialog.findViewById(R.id.cancel);
        AppCompatButton ok = timePickerDialog.findViewById(R.id.ok);
        NumberPicker numberPickerHour = timePickerDialog.findViewById(R.id.numpicker_hours);
        NumberPicker numberPickerMinutes = timePickerDialog.findViewById(R.id.numpicker_minutes);
        NumberPicker numberPickerSeconds = timePickerDialog.findViewById(R.id.numpicker_seconds);

        numberPickerHour.setMaxValue(23);
        numberPickerMinutes.setMaxValue(59);
        numberPickerSeconds.setMaxValue(59);

        numberPickerHour.setValue(_workTimeHours);
        numberPickerMinutes.setValue(_workTimeMinutes);
        numberPickerSeconds.setValue(_workTimeSeconds);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // workTime.setText(numberPickerHour.getValue() + ":" + numberPickerMinutes.getValue() + ":" + numberPickerSeconds.getValue());
                _workTimeHours = numberPickerHour.getValue();
                _workTimeMinutes = numberPickerMinutes.getValue();
                _workTimeSeconds = numberPickerSeconds.getValue();

                if (_workTimeHours != 0 || _workTimeMinutes != 0 || _workTimeSeconds != 0){
                    restTime.setText(String.format("%1$02d:%2$02d:%3$02d", _workTimeHours, _workTimeMinutes, _workTimeSeconds));
                }

                timePickerDialog.dismiss();
            }
        });
        timePickerDialog.show();
    }

    // metodo para mostrar el selector de repeticiones
    public void showRepeatsPicker(){
        Dialog repeatsPickerDialog = new Dialog(context);
        repeatsPickerDialog.setContentView(R.layout.dialog_repeats);
        repeatsPickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton cancel = repeatsPickerDialog.findViewById(R.id.cancel);
        AppCompatButton ok = repeatsPickerDialog.findViewById(R.id.ok);
        NumberPicker numberPickerRepeats = repeatsPickerDialog.findViewById(R.id.numpicker_repeats);

        numberPickerRepeats.setMaxValue(20);

        numberPickerRepeats.setValue(repeats);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatsPickerDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // workTime.setText(numberPickerHour.getValue() + ":" + numberPickerMinutes.getValue() + ":" + numberPickerSeconds.getValue());
                repeats = numberPickerRepeats.getValue();

                if( repeats != 0){
                    if(repeats == 1){
                        repetitions.setText(String.format(repeats + " vez"));
                    }
                    else {
                        repetitions.setText(String.format(repeats + " veces"));
                    }
                }
                repeatsPickerDialog.dismiss();
            }
        });

        repeatsPickerDialog.show();
    }
    //convertidor de hh:mm:ss a puros segundos
    public int converTimeSeconds(int hours, int minutes, int seconds){
        int totalSeconds = hours * 3600;
        totalSeconds += minutes * 60;
        totalSeconds += seconds;
        return totalSeconds;
    }
}