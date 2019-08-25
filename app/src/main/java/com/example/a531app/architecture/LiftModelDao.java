package com.example.a531app.architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.a531app.utilities.Lift;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface LiftModelDao {

    // ORDER BY day ASC

    @Query("select * from LiftModel")
    List<LiftModel> getAllLifts();

    @Query("select * from LiftModel where exercise_id = :id")
    LiftModel getLiftById(int id);

    @Query("select * from LiftModel where name=:name")
    LiftModel getLiftByName(String name);

    @Insert(onConflict = IGNORE)
    long addLift(LiftModel liftModel);

    @Query("UPDATE LiftModel SET round_to=:round WHERE exercise_id=:id")
    void updateRoundTo(double round, int id);

    @Query("UPDATE LIFTMODEL SET personal_record=:personal_record WHERE exercise_id=:id")
    void updatePr(int personal_record, int id);

    @Query("UPDATE LiftModel SET progression=:progression WHERE exercise_id=:id")
    void updateProgression(double progression, int id);

    @Query("UPDATE LIFTMODEL SET training_max=:max WHERE exercise_id=:id")
    void updateMax(double max, int id);


}
