package com.example.a531app.cycles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public WeeksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.weeks_row, viewGroup, false);

        return new WeeksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeksViewHolder weeksViewHolder, int i) {
        String week = weekLabels[i];
        weeksViewHolder.weekLabel.setText(week);
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

        public WeeksViewHolder(View view){
            super(view);
            weekLabel = view.findViewById(R.id.week_label);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.onClick(position+1);
        }
    }

}
