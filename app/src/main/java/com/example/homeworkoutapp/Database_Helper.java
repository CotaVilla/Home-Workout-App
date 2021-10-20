package com.example.homeworkoutapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database_Helper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "home_workout.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EXCERCISE = "exercise";
    private static final String EXERCISE_ID = "id";
    private static final String EXERCISE_TYPE_ID = "type_id";
    private static final String EXERCISE_NAME = "name";
    private static final String EXERCISE_DESCRIPTION = "description";
    private static final String EXERCISE_GIF_LOCATION = "gif_location";
    private static final String EXERCISE_TIPS = "tips";

    private static final String TABLE_RUTINE = "rutine";
    private static final String RUTINE_ID = "id";
    private static final String RUTINE_NAME = "name";
    private static final String RUTINE_DESCRIPTION = "description";

    private static final String TABLE_STEP = "step";
    private static final String STEP_ID = "id";
    private static final String STEP_EXERCISE_ID = "exercise_id";
    private static final String STEP_DESCRIPTION = "description";

    private static final String TABLE_RE = "rutine_exercise";
    private static final String RE_RUTINE_ID = "rutine_id";
    private static final String RE_EXERCISE_ID = "exercise_id";
    private static final String RE_REPS = "reps";
    private static final String RE_WORK_TIME = "work_time";
    private static final String RE_REST_TIME = "rest_time";
    private static final String RE_REPEATS = "repeats";

    private static  final String TABLE_TYPE = "type";
    private static final String TYPE_ID = "id";
    private static final String TYPE_CLASIFICATION = "clasification";

    public Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String qry_create_type = "CREATE TABLE " + TABLE_TYPE + " (" +
                TYPE_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                TYPE_CLASIFICATION + " TEXT NOT NULL UNIQUE);";

        String qry_create_exercise = "CREATE TABLE " + TABLE_EXCERCISE + "(" +
                EXERCISE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                EXERCISE_TYPE_ID + " INTEGER NOT NULL DEFAULT 1," +
                EXERCISE_NAME + " TEXT NOT NULL," +
                EXERCISE_DESCRIPTION + " TEXT," +
                EXERCISE_GIF_LOCATION + " TEXT NOT NULL," +
                EXERCISE_TIPS + " TEXT," +
                "FOREIGN KEY(" + EXERCISE_TYPE_ID + ") REFERENCES " + TABLE_TYPE + "(" + TYPE_ID + "));";

        String qry_create_rutine = "CREATE TABLE " + TABLE_RUTINE +"(" +
                RUTINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RUTINE_NAME + " TEXT NOT NULL," +
                RUTINE_DESCRIPTION + " TEXT);";

        String qry_create_table_step = "CREATE TABLE " + TABLE_STEP + "(" +
                STEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                STEP_EXERCISE_ID + " INTEGER NOT NULL," +
                STEP_DESCRIPTION + " TEXT," +
                "FOREIGN KEY(" + STEP_EXERCISE_ID + ") REFERENCES " + TABLE_EXCERCISE + "(" + EXERCISE_ID +"));";

        String qry_create_rutine_exercise = "CREATE TABLE " + TABLE_RE + "(" +
                RE_RUTINE_ID + " INTEGER NOT NULL," +
                RE_EXERCISE_ID + " INTEGER NOT NULL," +
                RE_REPS + " INTEGER," +
                RE_WORK_TIME + " TEXT," +
                RE_REST_TIME + " TEXT NOT NULL," +
                RE_REPEATS + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + RE_RUTINE_ID + ") REFERENCES " + TABLE_RUTINE + "(" + RUTINE_ID + ")," +
                "FOREIGN KEY(" + RE_EXERCISE_ID + ") REFERENCES " + TABLE_EXCERCISE + "(" + EXERCISE_ID + "));";

        db.execSQL(qry_create_type);
        db.execSQL(qry_create_exercise);
        db.execSQL(qry_create_rutine);
        db.execSQL(qry_create_table_step);
        db.execSQL(qry_create_rutine_exercise);

        String qry_insert_types = "INSERT INTO " + TABLE_TYPE + " VALUES" +
                " (1,'full_body')," +
                " (2,'neck')," +
                " (3,'chest')," +
                " (4,'abdomen')," +
                " (5,'legs')," +
                " (6,'arms');";

        String qry_insert_exercises = "INSERT INTO " + TABLE_EXCERCISE + " VALUES" +
                " (1,5,'Sentadilla',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (2,4,'Plancha',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (3,4,'Plancha lateral derecha',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (4,4,'Plancha lateral izquierda',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (5,1,'Saltos de Tijera',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (6,5,'Zancada frontal',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (7,4,'Abdominales',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (8,6,'Flexiones Diamante',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (9,6,'Flexiones',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL)," +
                " (10,4,'Abdominales con piernas elevadas',NULL,'app/src/main/res/drawable/exercises_img/default_img.jpg',NULL);";


        db.execSQL(qry_insert_types);
        db.execSQL(qry_insert_exercises);
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
}
