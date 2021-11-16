package com.example.homeworkoutapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.objects.Filter;
import com.example.homeworkoutapp.objects.Rutine;
import com.example.homeworkoutapp.objects.Rutine_Exercise;
import com.example.homeworkoutapp.objects.Step;

import java.util.ArrayList;
import java.util.Optional;

public class Database_Helper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "home_workout.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EXCERCISE = "exercise";
    private static final String EXERCISE_ID = "id";
    private static final String EXERCISE_TYPE_ID = "type_id";
    private static final String EXERCISE_ACTUAL_TYPE_ID = "actual_type_id";
    private static final String EXERCISE_NAME = "name";
    private static final String EXERCISE_DESCRIPTION = "description";
    private static final String EXERCISE_GIF_LOCATION = "gif_location";
    private static final String EXERCISE_TIPS = "tips";

    private static final String TABLE_RUTINE = "rutine";
    private static final String RUTINE_ID = "id";
    private static final String RUTINE_NAME = "name";
    private static final String RUTINE_DESCRIPTION = "description";
    private static final String RUTINE_EXERCISES = "exercises";
    private static final String RUTINE_DURATION = "duration";

    private static final String TABLE_STEP = "step";
    private static final String STEP_ID = "id";
    private static final String STEP_EXERCISE_ID = "exercise_id";
    private static final String STEP_DESCRIPTION = "description";

    private static final String TABLE_RE = "rutine_exercise";
    private static final String RE_RUTINE_ID = "rutine_id";
    private static final String RE_EXERCISE_ID = "exercise_id";
    private static final String RE_POSITION = "position";
    private static final String RE_REPS = "reps";
    private static final String RE_WORK_TIME = "work_time";
    private static final String RE_REST_TIME = "rest_time";
    private static final String RE_REPEATS = "repeats";

    private static  final String TABLE_TYPE = "type";
    private static final String TYPE_ID = "id";
    private static final String TYPE_CLASIFICATION = "clasification";
    private static final String TYPE_IMAGE = "location";

    public Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String qry_create_type = "CREATE TABLE " + TABLE_TYPE + " (" +
                TYPE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                TYPE_CLASIFICATION + " TEXT NOT NULL UNIQUE," +
                TYPE_IMAGE + " TEXT NOT NULL);";

        String qry_create_exercise = "CREATE TABLE " + TABLE_EXCERCISE + "(" +
                EXERCISE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                EXERCISE_TYPE_ID + " INTEGER NOT NULL DEFAULT 1," +
                EXERCISE_ACTUAL_TYPE_ID + " INTEGER NOT NULL DEFAULT 1," +
                EXERCISE_NAME + " TEXT NOT NULL," +
                EXERCISE_DESCRIPTION + " TEXT," +
                EXERCISE_GIF_LOCATION + " TEXT NOT NULL," +
                EXERCISE_TIPS + " TEXT," +
                "FOREIGN KEY(" + EXERCISE_TYPE_ID + ") REFERENCES " + TABLE_TYPE + "(" + TYPE_ID + "));";

        String qry_create_table_step = "CREATE TABLE " + TABLE_STEP + "(" +
                STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                STEP_EXERCISE_ID + " INTEGER NOT NULL," +
                STEP_DESCRIPTION + " TEXT," +
                "FOREIGN KEY(" + STEP_EXERCISE_ID + ") REFERENCES " + TABLE_EXCERCISE + "(" + EXERCISE_ID +"));";

        String qry_create_rutine = "CREATE TABLE " + TABLE_RUTINE +"(" +
                RUTINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RUTINE_NAME + " TEXT NOT NULL," +
                RUTINE_DESCRIPTION + " TEXT," +
                RUTINE_EXERCISES + " INTEGER," +
                RUTINE_DURATION + " INTEGER);";

        String qry_create_rutine_exercise = "CREATE TABLE " + TABLE_RE + "(" +
                RE_RUTINE_ID + " INTEGER NOT NULL," +
                RE_EXERCISE_ID + " INTEGER NOT NULL," +
                RE_POSITION + " INTEGER NOT NULL," +
                RE_REPS + " INTEGER," +
                RE_WORK_TIME + " INTEGER," +
                RE_REST_TIME + " INTEGER NOT NULL," +
                RE_REPEATS + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + RE_RUTINE_ID + ") REFERENCES " + TABLE_RUTINE + "(" + RUTINE_ID + ")," +
                "FOREIGN KEY(" + RE_EXERCISE_ID + ") REFERENCES " + TABLE_EXCERCISE + "(" + EXERCISE_ID + "));";

        db.execSQL(qry_create_type);
        db.execSQL(qry_create_exercise);
        db.execSQL(qry_create_rutine);
        db.execSQL(qry_create_table_step);
        db.execSQL(qry_create_rutine_exercise);

        String qry_insert_types = "INSERT INTO " + TABLE_TYPE + " VALUES" +
                " (1,'Completo','ic_body')," +
                " (2,'Cuello','ic_neck')," +
                " (3,'Pecho','ic_chest_true')," +
                " (4,'Abdomen','ic_abs')," +
                " (5,'Piernas','ic_legs')," +
                " (6,'Brazo','ic_arm')," +
                " (7,'Espalda','ic_back')," +
                " (8,'Oculto','ic_hidden');";

        // id, tipo_ejercicio_original, tipo_ejercicio_actual, nombre, Descripcion,Tips
        String qry_insert_exercises = "INSERT INTO " + TABLE_EXCERCISE + " VALUES" +
                " (1,5,5,'Sentadilla','Ejercicio para fortalecer las piernas.','gif_homer','Mantén un equilibrio apoyándote de los brazos.')," +

                " (2,4,4,'Plancha','Ejercicio para fortalecer el abdomen.','gif_homer','Mantén los brazos separados y no bajes la pelvis.')," +

                " (3,4,4,'Plancha lateral derecha','Ejercicio para fortalecer el abdomen oblicuo.','gif_homer','Posiciona un pie al frente para mantener el equilibrio.')," +

                " (4,4,4,'Plancha lateral izquierda','Ejercicio para fortalecer el abdomen oblicuo.','gif_homer','Posiciona un pie al frente para mantener el equilibrio.')," +

                " (5,1,1,'Saltos de Tijera','Ejercicio de cardio.','gif_homer','Estira completamente los brazos y no abras demasiado las piernas en el salto.')," +

                " (6,5,5,'Zancada frontal','Ejercicio para fortalecer las piernas.','gif_homer','Mantente siempre erguido mirando hacia el frente.')," +

                " (7,4,4,'Abdominales','Ejercicio para fortalecer el abdomen.','gif_homer','Procura tocar las rodillas con los codos, en caso de no ser posible intenta llegar lo más cerca posible.')," +

                " (8,6,6,'Flexiones Diamante','Ponte en posición como si hicieras flexiones “comunes” pero, en lugar de colocar los brazos a los costados del cuerpo, debes apoyar las manos por delante del pecho. \n" +
                "Los dedos índice y los pulgares se deben tocar (formando un corazón o un diamante, de allí el nombre del ejercicio).\n" +
                "\n" +
                "\n','gif_homer',' Inicia empujando el piso hasta poder levantar tu peso, repite hasta lograr una repetición y así hasta lograr la meta')," +

                " (9,6,6,'Flexiones','Coloca las manos alineadas a la altura de los hombros, justo debajo. \n" +
                "Separa los dedos de la mano para que puedas soportar mejor tu propio peso. \n','gif_homer','Cuando realices la flexión, rota las manos hacia afuera para que el hombro también sienta la rotación\n')," +

                " (10,4,4,'Abdominales con piernas elevadas','Acostarse en el piso,elevar las piernas hasta un ángulo de 90° , sin flexionar\n" +
                "Bajarlas lentamente hasta casi tocar el piso,\n" +
                "Repetir\n','gif_homer','No dejar que los talones toquen el piso para hacer más fuerza con el abdomen');";

        String qry_insert_steps = "INSERT INTO " + TABLE_STEP + " VALUES" +
                "(1,1,'Debes mantener la cabeza horizontal.')," +
                "(2,1,'Los pies deben colocarse al ancho de los hombros.')," +
                "(3,1,'La rodilla, durante la flexión, no debe sobrepasar la punta de los pies.')," +
                "(4,1,'No se debe flexionar demasiado.')," +
                "(5,2,'Ponte en posición de flexión de brazos, con los codos por debajo de los hombros y los pies separados a lo ancho de las caderas.')," +
                "(6,2,'Flexione los codos y apoye el peso en los antebrazos y en los dedos de los pies, manteniendo el cuerpo en línea recta.')," +
                "(7,2,'Aguanta el mayor tiempo posible.')," +
                "(8,3,'Apoya la mano derecha en el suelo.')," +
                "(9,3,'Coloca la mano izquierda pegada al cuerpo.')," +
                "(10,3,'Elévate hasta que sólo tengas apoyado un brazo y los pies en el suelo.')," +
                "(11,4,'Apoya la mano izquierda en el suelo.')," +
                "(12,4,'Coloca la mano derecha pegada al cuerpo.')," +
                "(13,4,'Elévate hasta que sólo tengas apoyado un brazo y los pies en el suelo.')," +
                "(14,5,'Párate derecho con los pies juntos y las manos a los lados.')," +
                "(15,5,'Salta, abra los pies y ponga ambas manos juntas sobre su cabeza.')," +
                "(16,5,'Saltar de nuevo y volver a la posición inicial.')," +
                "(17,5,'Repite hasta que el juego esté completo.')," +
                "(18,6,'Baja la cadera hasta que el cuádriceps queda paralelo al suelo.')," +
                "(19,6,'La pierna de atrás se flexiona hasta que casi tocamos el suelo con la rodilla que forma un ángulo de 90 grados.')," +
                "(20,6,'Volvemos a posición inicial y para hacerlo nos impulsamos con la pierna adelantada, apoya bien la planta del pie en el suelo para conseguir la fuerza.')," +
                "(21,6,'Alternar piernas.')," +

                "(22,6,'')," +
                "(123434343443434,5,'Debes mantener la cabeza horizontal.')";

        String qry_insert_routines = "INSERT INTO " + TABLE_RUTINE + " VALUES" +
                " (1,'Rutina mañanera', 'Para las mañanas hermosas', 5,360)," +
                " (2,'Rutina mediodia', 'Porque sí.', 0,0)," +
                " (3,'Rutina noche', 'Para dormir mejor.', 3,180);";

        String qry_insert_routines_exercises = "INSERT INTO " + TABLE_RE + " VALUES" +
                " (1, 1,1,null,30,15,4)," +
                " (1, 2,2,null,30,15,4)," +
                " (1, 3,3,null,30,15,4)," +
                " (1, 4,4,null,30,15,4)," +
                " (1, 5,5,null,30,15,4)," +
                " (3, 6,6,null,30,15,4)," +
                " (3, 7,7,null,30,15,4)," +
                " (3, 8,8,null,30,15,4);";


        db.execSQL(qry_insert_types);
        db.execSQL(qry_insert_exercises);
        db.execSQL(qry_insert_steps);
        db.execSQL(qry_insert_routines);
        db.execSQL(qry_insert_routines_exercises);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop_step = "DROP TABLE IF EXISTS " + TABLE_STEP + ";";
        String drop_rutine_exercise = "DROP TABLE IF EXISTS " + TABLE_RE + ";";
        String drop_exercise = "DROP TABLE IF EXISTS " + TABLE_EXCERCISE + ";";
        String drop_rutine = "DROP TABLE IF EXISTS " + TABLE_RUTINE + ";";

        db.execSQL(drop_step);
        db.execSQL(drop_rutine_exercise);
        db.execSQL(drop_exercise);
        db.execSQL(drop_rutine);
        onCreate(db);
    }

    public long getHiddenFilterId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{TYPE_ID,TYPE_CLASIFICATION};
        String selection =   TYPE_CLASIFICATION + " = " +  "'Oculto'";
        Cursor cursor = db.query(TABLE_TYPE,
                columns,
                selection,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        int type_id_hidden = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE_ID));
        return type_id_hidden;
    }

    public ArrayList<Filter> getFilters(){
        ArrayList<Filter> filters = new ArrayList<Filter>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[]{TYPE_ID,TYPE_CLASIFICATION,TYPE_IMAGE};

        Cursor cursor = db.query(TABLE_TYPE,
                columns,
                null,
                null,
                null,
                null,
                null);

        Filter nofilter = new Filter(0,"Todos", "ic_todo");
        filters.add(nofilter);
        while(cursor.moveToNext()) {
            int filter_id = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE_ID));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_CLASIFICATION));
            String image_path = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_IMAGE));

            Filter filter = new Filter(filter_id, description, image_path);
            filters.add(filter);
        }
        db.close();

        return filters;
    }

    public void hideExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        long type_id_hidden = getHiddenFilterId();

        ContentValues cv = new ContentValues();
        cv.put(EXERCISE_ACTUAL_TYPE_ID,type_id_hidden);
        String where = EXERCISE_ID+" = " + exercise.exercise_id;
        db.update(TABLE_EXCERCISE, cv, where,null);
        db.close();
    }

    public void unhideExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXERCISE_ACTUAL_TYPE_ID,exercise.type_id);
        String where = EXERCISE_ID+" = " + exercise.exercise_id;
        db.update(TABLE_EXCERCISE, cv, where,null);
        db.close();
    }

    public ArrayList<Step> getSteps(Exercise exercise){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Step> step_list= new ArrayList<Step>();



        String[] columns = new String[]{STEP_ID,STEP_EXERCISE_ID,STEP_DESCRIPTION};
        String selection = STEP_EXERCISE_ID + " = " + exercise.exercise_id;

        Cursor cursor = db.query(TABLE_STEP,
                columns,
                selection,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()) {
            int step_id = cursor.getInt(cursor.getColumnIndexOrThrow(STEP_ID));
            int exercise_id = cursor.getInt(cursor.getColumnIndexOrThrow(STEP_EXERCISE_ID));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(STEP_DESCRIPTION));

            Step step = new Step(step_id, exercise_id, description);
            step_list.add(step);
        }
        db.close();

        return step_list;
    }

    public ArrayList<Rutine_Exercise> getExercises(long rutine_id){
        ArrayList<Rutine_Exercise> exercises = new ArrayList<Rutine_Exercise>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection =   TABLE_RE + "." + RE_EXERCISE_ID + " = " + TABLE_EXCERCISE + "." + EXERCISE_ID + " AND " + TABLE_RE + "." + RE_RUTINE_ID + " = " +  rutine_id;
        String orderBy = RE_POSITION + " ASC";

        String[] columns = new String[]{
                TABLE_RE+"."+RE_RUTINE_ID,
                TABLE_RE+"."+RE_EXERCISE_ID,
                TABLE_RE+"."+RE_POSITION,
                TABLE_EXCERCISE+"."+EXERCISE_NAME,
                TABLE_RE+"."+RE_WORK_TIME,
                TABLE_RE+"."+RE_REST_TIME,
                TABLE_RE+"."+RE_REPEATS};

        Cursor cursor = db.query(TABLE_RE + ","+ TABLE_EXCERCISE,
                columns,
                selection,
                null,
                null,
                null,
                orderBy);

        int count = 1;
        while(cursor.moveToNext()) {
            int _rutine_id = cursor.getInt(cursor.getColumnIndexOrThrow(RE_RUTINE_ID));
            int exercise_id = cursor.getInt(cursor.getColumnIndexOrThrow(RE_EXERCISE_ID));
            int position = count;
            String exercise_name = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_NAME));
            int workTime = cursor.getInt(cursor.getColumnIndexOrThrow(RE_WORK_TIME));
            int restTime = cursor.getInt(cursor.getColumnIndexOrThrow(RE_REST_TIME));
            int repeats = cursor.getInt(cursor.getColumnIndexOrThrow(RE_REPEATS));
            count++;
            Rutine_Exercise rutine = new Rutine_Exercise( exercise_id, _rutine_id, position, exercise_name,workTime, restTime,repeats);
            exercises.add(rutine);
        }
        db.close();

        return exercises;
    }

    public ArrayList<Rutine> getRutines(){
        ArrayList<Rutine> routines = new ArrayList<Rutine>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[]{RUTINE_ID,RUTINE_NAME,RUTINE_DESCRIPTION,RUTINE_EXERCISES,RUTINE_DURATION};

        Cursor cursor = db.query(TABLE_RUTINE,
                columns,
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(RUTINE_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(RUTINE_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(RUTINE_DESCRIPTION));
            int exercises = cursor.getInt(cursor.getColumnIndexOrThrow(RUTINE_EXERCISES));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(RUTINE_DURATION));

            Rutine rutine = new Rutine(id, name, description, exercises,duration);
            routines.add(rutine);
        }
        db.close();

        return routines;
    }

    public long insertRutine(Rutine routine){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RUTINE_NAME,routine.name);
        values.put(RUTINE_DESCRIPTION,routine.Description);
        values.put(RUTINE_EXERCISES,routine.Exercises);
        values.put(RUTINE_DURATION,routine.Duration);

        long id = db.insert(TABLE_RUTINE,null,values);

        db.close();
        return id;
    }

    public void updateRutine(Rutine routine,ArrayList<Rutine_Exercise> exercises){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(RUTINE_NAME,routine.name);
        cv.put(RUTINE_DESCRIPTION,routine.Description);
        cv.put(RUTINE_EXERCISES,routine.Exercises);
        cv.put(RUTINE_DURATION,routine.Duration);
        String where = RUTINE_ID + " = " + routine.id;

        // Delete all related data of routine
        db.delete(TABLE_RE,RE_RUTINE_ID + " = " + routine.id,null);


        String[] whereArgs = new String[]{String.valueOf(routine.id)};

        for (Rutine_Exercise exercise: exercises) {
            cv = new ContentValues();
            cv.put(RE_RUTINE_ID,routine.id);
            cv.put(RE_EXERCISE_ID,exercise.exercise_id);
            cv.put(RE_REPEATS,exercise.repeats);
            cv.put(RE_POSITION,exercise.position);
            cv.put(RE_REST_TIME,exercise.rest_time);
            cv.put(RE_WORK_TIME,exercise.work_time);

            long resultado = db.insert(TABLE_RE, null, cv);
            if(resultado == -1){
                Log.d("TAG", "Error insertando dato");
            }
        }

        db.close();
    }

    public Exercise getExercise(int exerciseId){
        Exercise exercise;
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[]{EXERCISE_ID,EXERCISE_TYPE_ID,EXERCISE_ACTUAL_TYPE_ID,EXERCISE_NAME, EXERCISE_DESCRIPTION,EXERCISE_GIF_LOCATION,EXERCISE_TIPS};
        String selection = EXERCISE_ID + " = " + exerciseId;

        cursor = db.query(TABLE_EXCERCISE,
                columns,
                selection,
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        int exercise_id = cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_ID));
        int _type_id = cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_TYPE_ID));
        int actual_type_id = cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_ACTUAL_TYPE_ID));
        String namecursor = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_NAME));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_DESCRIPTION));
        String gifLocation = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_GIF_LOCATION));
        String tips = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_TIPS));
        exercise = new Exercise(exercise_id, _type_id,actual_type_id, namecursor, description, gifLocation, tips);

        return exercise;
    }

    public ArrayList<Exercise> getFilteredExcercises(int type_id , String name){
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String selection = "";

        String[] columns = new String[]{EXERCISE_ID,EXERCISE_TYPE_ID,EXERCISE_ACTUAL_TYPE_ID,EXERCISE_NAME, EXERCISE_DESCRIPTION,EXERCISE_GIF_LOCATION,EXERCISE_TIPS};

        String orderBy = EXERCISE_NAME + " ASC";

        if (type_id == 0 && (name == null || name.equals(""))){
            selection = null;
        }
        else if (type_id == 0 && !(name == null || name.equals(""))){
            selection = EXERCISE_NAME + " LIKE '%" + name + "%'";
        }
        else if (type_id != 0 && (name == null || name.equals(""))){
            selection =   EXERCISE_ACTUAL_TYPE_ID + " = " +  type_id;
        }
        else if(type_id != 0 && !(name == null || name.equals(""))) {
            selection =   EXERCISE_ACTUAL_TYPE_ID + " = " +  type_id + " AND "+ EXERCISE_NAME + " LIKE '%" + name + "%'";
        }

        cursor = db.query(TABLE_EXCERCISE,
                columns,
                selection,
                null,
                null,
                null,
                orderBy);

        while(cursor.moveToNext()) {
            int exercise_id = cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_ID));
            int _type_id = cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_TYPE_ID));
            int actual_type_id = cursor.getInt(cursor.getColumnIndexOrThrow(EXERCISE_ACTUAL_TYPE_ID));
            String namecursor = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_DESCRIPTION));
            String gifLocation = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_GIF_LOCATION));
            String tips = cursor.getString(cursor.getColumnIndexOrThrow(EXERCISE_TIPS));
            Exercise exercise = new Exercise(exercise_id, _type_id,actual_type_id, namecursor, description, gifLocation, tips);
            exercises.add(exercise);
        }
        db.close();

        return exercises;
    }

    public void insertExercise (long routine_id, Rutine_Exercise exercise){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RE_RUTINE_ID,routine_id);
        values.put(RE_EXERCISE_ID,exercise.exercise_id);
        values.put(RE_POSITION,exercise.position);
        values.put(RE_WORK_TIME,exercise.work_time);
        values.put(RE_REST_TIME,exercise.rest_time);
        values.put(RE_REPEATS,exercise.repeats);

        db.insert(TABLE_RE,null,values);
        db.close();
    }

    public void duplicateRoutine(Rutine routine){
        ArrayList<Rutine_Exercise> list_exercises = getExercises(routine.id);
        Rutine newroutine = routine;
        newroutine.name += " copia";
        newroutine.id = (int)insertRutine(newroutine);
        for (Rutine_Exercise exercise: list_exercises) {
            insertExercise(newroutine.id, exercise);
        }
    }

    public void deleteRoutine(long RoutineID){
        SQLiteDatabase db = getWritableDatabase();
        String whereRE = RE_RUTINE_ID + " = " + RoutineID;
        String whereROUTINE = RUTINE_ID + " = " + RoutineID;
        db.delete(TABLE_RE, whereRE, null);
        db.delete(TABLE_RUTINE, whereROUTINE, null);
    }
}
