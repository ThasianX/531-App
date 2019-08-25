package com.example.a531app.architecture;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class LiftListViewModel extends AndroidViewModel {

    //    private final List<LiftModel> liftList;
    private LiftModelDao liftModelDao;


    public LiftListViewModel(@NonNull Application application) {
        super(application);
        liftModelDao = AppDatabase.getDatabase(this.getApplication()).liftDao();
    }


    public List<LiftModel> getLiftModels() {
        return liftModelDao.getAllLifts();
    }

    public void updateRoundTo(LiftModel liftModel) {

        liftModelDao.updateRoundTo(liftModel.getRound_to(), liftModel.exercise_id);

    }

    public void updatePr(LiftModel liftModel) {
        liftModelDao.updatePr(liftModel.getPersonal_record(), liftModel.exercise_id);

    }

    public void updateMax(LiftModel liftModel) {
        liftModelDao.updatePr(liftModel.getPersonal_record(), liftModel.exercise_id);
    }

    public void updateProgression(LiftModel liftModel) {
        liftModelDao.updateProgression(liftModel.getProgression(), liftModel.exercise_id);

    }

    public void increaseLifts() {
        for (LiftModel lift : getLiftModels()) {
            liftModelDao.updateMax(lift.getTraining_max() + lift.getProgression(), lift.exercise_id);
        }

    }

    public void resetPrs() {
        for (LiftModel lift : getLiftModels()) {
            liftModelDao.updatePr(0, lift.exercise_id);
        }

    }

    public LiftModel getLiftById(int id) {
        return liftModelDao.getLiftById(id);
    }

    public LiftModel getLiftByName(String name) {
        return liftModelDao.getLiftByName(name);
    }
}
