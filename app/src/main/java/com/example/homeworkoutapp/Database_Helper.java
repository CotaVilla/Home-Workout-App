package com.example.homeworkoutapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database_Helper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "home_workout.db";
    private static final int DATABASE_VERSION = 1;

    public Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String qry_create_exercise = "CREATE TABLE IF NOT EXISTS 'exercise' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'name' TEXT NOT NULL," +
                "'description' TEXT," +
                "'gif_location' TEXT NOT NULL," +
                "'tips' TEXT);";

        String qry_create_rutine = "CREATE TABLE 'rutine' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'name'TEXT NOT NULL," +
                "'description' TEXT);";

        String qry_create_table_step = "CREATE TABLE 'step' (" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'exercise_id' INTEGER NOT NULL," +
                "'step_description' TEXT," +
                "FOREIGN KEY('exercise_id') REFERENCES 'exercise'('id'));";

        String qry_create_rutine_exercise = "CREATE TABLE 'rutine_exercise' (" +
                "'rutine_id' INTEGER NOT NULL," +
                "'excersice_id' INTEGER NOT NULL," +
                "'reps' INTEGER," +
                "'work_time' TEXT," +
                "'rest_time' TEXT NOT NULL," +
                "'repeats' INTEGER NOT NULL," +
                "FOREIGN KEY('rutine_id') REFERENCES 'rutine'('id')," +
                "FOREIGN KEY('excersice_id') REFERENCES 'exercise'('id'));";

        db.execSQL(qry_create_exercise);
        db.execSQL(qry_create_rutine);
        db.execSQL(qry_create_table_step);
        db.execSQL(qry_create_rutine_exercise);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop_step = "DROP TABLE IF EXISTS 'step';";
        String drop_rutine_exercise = "DROP TABLE IF EXISTS 'rutine_exercise';";
        String drop_exercise = "DROP TABLE IF EXISTS 'exercise';";
        String drop_rutine = "DROP TABLE IF EXISTS 'rutine';";

        db.execSQL(drop_step);
        db.execSQL(drop_rutine_exercise);
        db.execSQL(drop_exercise);
        db.execSQL(drop_rutine);

        onCreate(db);
    }
}
