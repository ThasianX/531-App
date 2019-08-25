package com.example.a531app.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {LiftModel.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "lifts.db";
    private static final Object LOCK = new Object();

    public abstract LiftModelDao liftDao();

    public synchronized static AppDatabase getDatabase(Context context){
        if(INSTANCE ==null){
            INSTANCE = buildDatabase(context);
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context){
        AppDatabase db =  Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                //A problem with the database creation. First execution always leads to a crash. Second execution is fine.
                //Oncreate method definitely has a problem

                Log.d(LOG_TAG, "Building the database instance");

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "Prepopulating database with lifts");
                        LiftModelDao dao = getDatabase(context).liftDao();
                        dao.addLift(new LiftModel("Overhead press", 5, 0, 5, 0, 1, "Lat work"));
                        dao.addLift(new LiftModel( "Deadlift",  10, 0, 5, 0, 2, "Ab work"));
                        dao.addLift(new LiftModel( "Bench press", 5, 0, 5, 0, 4, "Lat work"));
                        dao.addLift(new LiftModel( "Squat", 10, 0, 5, 0, 5, "Ab work"));
                    }
                });
            }
        }).build();

        db.beginTransaction();
        db.endTransaction();

        return db;
    }

}
