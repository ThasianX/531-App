package com.example.a531app.settingsnavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a531app.R;
import com.example.a531app.utilities.Lift;

import java.util.List;

public class SelectableAdapter extends RecyclerView.Adapter<SelectableAdapter.SelectableViewHolder>{

    private final List<Lift> mLifts;
    private final Context mContext;

    private SelectableAdapterClickListener clickListener;


    public interface SelectableAdapterClickListener{
        void onClick(Lift lift, int position);
    }

    public SelectableAdapter(Context context, SelectableAdapterClickListener listener, List<Lift> lifts){
        mContext = context;
        clickListener = listener;
        mLifts = lifts;
    }

    @NonNull
    @Override
    public SelectableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.maxes_row, viewGroup, false);

        return new SelectableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectableViewHolder selectableViewHolder, int position) {
        Lift lift = mLifts.get(position);
        String name = lift.getName();
        selectableViewHolder.mLiftLabel.setText(name);
        String max = "" + lift.getTraining_max();
        selectableViewHolder.mTrainingMax.setText(max);

    }

    @Override
    public int getItemCount() {
        return mLifts.size();
    }

    public List<Lift> getmLifts() {
        return mLifts;
    }

    public void changeLift(double max, int position){
        mLifts.get(position).setTraining_max(max);
        notifyDataSetChanged();
    }

    public void changeRoundTo(double round){
        for(Lift lift : mLifts){
            lift.setRound_to(round);
        }
        notifyDataSetChanged();
    }

    public void changeProgression(double progression, int position){
        mLifts.get(position).setProgression(progression);
        notifyDataSetChanged();
    }



    public class SelectableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mLiftLabel;
        private TextView mTrainingMax;

        public SelectableViewHolder(View view){
            super(view);
            mLiftLabel = (TextView) view.findViewById(R.id.lift_label);
            mTrainingMax = (TextView) view.findViewById(R.id.training_max_tv);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Lift lift = mLifts.get(position);
            clickListener.onClick(lift, position);
        }
    }
}
