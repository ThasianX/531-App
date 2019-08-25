package com.example.a531app.cyclenavigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a531app.R;

public class WeeksAdapter extends RecyclerView.Adapter<WeeksAdapter.WeeksViewHolder> {

    private final String[] weekLabels;
    private final Context mContext;
    private WeeksAdapterClickListener clickListener;

    public WeeksAdapter(Context context, WeeksAdapterClickListener listener){
        mContext = context;
        clickListener = listener;
        weekLabels = new String[]{"Week one - 65/75/85%", "Week two - 70/80/90%", "Week three - 75/85/95%", "Week four - Deload"};
    }

    @NonNull
    @Override
    public WeeksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.weeks_row, viewGroup, false);

        return new WeeksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeksViewHolder weeksViewHolder, int position) {
        String week = weekLabels[position];
        weeksViewHolder.weekLabel.setText(week);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CycleManager.SP_NAME, Context.MODE_PRIVATE);

        int value;
        int progress;
        switch(position){
            case 0:
                value = sharedPreferences.getInt(CurrentCycleFragment.WEEK_1_KEY, 0);
                weeksViewHolder.progressBar.setProgress(value);
                progress = (int) Math.rint(value/52.0*100);
                weeksViewHolder.progressStatus.setText(""+progress+"%");
                break;
            case 1:
                value=sharedPreferences.getInt(CurrentCycleFragment.WEEK_2_KEY, 0);
                weeksViewHolder.progressBar.setProgress(value);
                progress = (int) Math.rint(value/52.0*100);
                weeksViewHolder.progressStatus.setText(""+progress+"%");
                break;
            case 2:
                value= sharedPreferences.getInt(CurrentCycleFragment.WEEK_3_KEY, 0);
                weeksViewHolder.progressBar.setProgress(value);
                progress = (int) Math.rint(value/52.0*100);
                weeksViewHolder.progressStatus.setText(""+progress+"%");
                break;
            case 3:
                value = sharedPreferences.getInt(CurrentCycleFragment.WEEK_4_KEY, 0);
                weeksViewHolder.progressBar.setProgress(value);
                progress = (int) Math.rint(value/52.0*100);
                weeksViewHolder.progressStatus.setText(""+progress+"%");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return weekLabels.length;
    }

    public interface WeeksAdapterClickListener{
        void onClick(int week);
    }

    public class WeeksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView weekLabel;
        private ProgressBar progressBar;
        private TextView progressStatus;

        public WeeksViewHolder(View view){
            super(view);
            weekLabel = view.findViewById(R.id.week_label);
            progressBar = view.findViewById(R.id.progressBar);
            progressStatus  = view.findViewById(R.id.tv_progress_status);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.onClick(position+1);
        }
    }

}
