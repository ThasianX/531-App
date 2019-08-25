package com.example.a531app.settingsnavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a531app.R;
import com.example.a531app.architecture.LiftModel;

import java.util.List;

public class SelectableAdapter extends RecyclerView.Adapter<SelectableAdapter.SelectableViewHolder>{

    private List<LiftModel> liftModels;
    private final Context mContext;

    public static final String LOG_TAG = SelectableAdapter.class.getSimpleName();

    private SelectableAdapterClickListener clickListener;


    public interface SelectableAdapterClickListener{
        void onClick(LiftModel lift, int position);
    }

    public SelectableAdapter(Context context, SelectableAdapterClickListener listener, List<LiftModel> lifts){
        mContext = context;
        clickListener = listener;
        liftModels = lifts;
    }

    @NonNull
    @Override
    public SelectableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.maxes_row, viewGroup, false);

        return new SelectableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectableViewHolder selectableViewHolder, int position) {
        LiftModel lift = liftModels.get(position);
        String name = lift.getName();
        selectableViewHolder.mLiftLabel.setText(name);
        String max = "" + lift.getTraining_max();
        selectableViewHolder.mTrainingMax.setText(max);

    }

    @Override
    public int getItemCount() {
        return liftModels.size();
    }

    public void changeLift(LiftModel lift, int position){
        Log.d(LOG_TAG, "Changing lift");
        liftModels.set(position, lift);
        notifyItemChanged(position);
    }

    public class SelectableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mLiftLabel;
        private TextView mTrainingMax;

        public SelectableViewHolder(View view){
            super(view);
            mLiftLabel = view.findViewById(R.id.lift_label);
            mTrainingMax = view.findViewById(R.id.training_max_tv);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            LiftModel lift = liftModels.get(position);
            clickListener.onClick(lift, position);
        }
    }
}
