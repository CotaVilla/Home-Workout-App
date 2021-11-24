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
import com.example.homeworkoutapp.databinding.FragmentEditExerciseBinding;
import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.objects.Rutine_Exercise;

public class EditExerciseFragment extends Fragment {
    // variables de la clase
    Context context;
    FragmentEditExerciseBinding binding;
    Database_Helper database_helper;

    StartActivity activity;

    Rutine_Exercise pasableRE;
    Exercise pasableExercise;
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

    //Constructor, se le pasa el ejercicio a editar de la rutina
    public EditExerciseFragment(Rutine_Exercise exercise) {
        pasableRE = exercise;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // cuando se crea se inicializan los valores de la clase
        database_helper = new Database_Helper(getActivity());
        pasableExercise = database_helper.getExercise(pasableRE.exercise_id);
        setWorkTime(pasableRE.work_time);
        setRestTime(pasableRE.rest_time);
        repeats = pasableRE.repeats;
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

    // para cuando volvamos a abrir esta pantalla desde elegir ejercicio
    @Override
    public void onResume() {
        super.onResume();
        //checamos si se eleigio un ejercio para cambiar el actual
        if(activity.getPasableExercise() != null){
            pasableExercise = activity.getPasableExercise();
            exerciseName.setText(pasableExercise.name);
            activity.setPasableExercise(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (StartActivity) getActivity();
        binding = FragmentEditExerciseBinding.inflate(inflater, container, false);
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

        title.setText("Editar ejercicio");

        exerciseName.setText(pasableRE.exercise_name);

        //Si hay datos para los campos se carga, si no se deja vacio
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

        // eventos de los objetos
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
                // para cargar el fragmento para seleccionar ejercicio
                // se le pasa el true para que cuando hagamos clic sobre los ejercicios se seleccionen
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

                if (validateDataInserted()){
                    // si no falta algun dato se pasa el ejercio a travez de una variable en el activity
                    Log.d("VALID","Datos validos");
                    int worktimeSeconds = converTimeSeconds(workTimeHours,workTimeMinutes,workTimeSeconds);
                    int restimeSeconds = converTimeSeconds(_workTimeHours,_workTimeMinutes,_workTimeSeconds);

                    pasableRE.exercise_id =pasableExercise.exercise_id;
                    pasableRE.exercise_name = pasableExercise.name;
                    pasableRE.work_time = worktimeSeconds;
                    pasableRE.rest_time = restimeSeconds;
                    pasableRE.repeats = repeats;
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

    // Metodo para validar los datos
    private boolean validateDataInserted(){
        //se usan los toast para mostrar que falta algo
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

    // para mostrar un mensaje customizado al fondo de la pantalla
    public void makeToast(Toast _toast){
        Toast toast = _toast;
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.container_toast);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }

    // menu para seleccionar el tiempo de trabajo
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

    // Menu para seleccionar el tiempo de descanso
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

    // menu para seleccionar las repeticiones
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
    public int converTimeSeconds(int hours, int minutes, int seconds){
        int totalSeconds = hours * 3600;
        totalSeconds += minutes * 60;
        totalSeconds += seconds;
        return totalSeconds;
    }
}