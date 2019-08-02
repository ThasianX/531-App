package com.example.a531app.cycles;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a531app.R;

import java.util.Date;
import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder> {

    private final List<String> mDates;
    private final Context mContext;

    private ManagerAdapterClickListener clickListener;

    public interface ManagerAdapterClickListener{
        void onClick();
    }


    @NonNull
    @Override
    public ManagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.cycle_manager_row, viewGroup, false);

        return new ManagerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerViewHolder managerViewHolder, int i) {
        String date = mDates.get(i);
        managerViewHolder.cycleDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return mDates.size();
    }



    public ManagerAdapter(List<String> mDates, ManagerAdapterClickListener listener, Context mContext) {
        this.mDates = mDates;
        clickListener = listener;
        this.mContext = mContext;
    }

    public void addDate(String date){
        if(!mDates.contains(date)){
            mDates.add(date);
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "WeekCycle already exists for today's date. To create a new one for today's date, delete the existing weekcycle.", Toast.LENGTH_LONG);
        }
    }



    public class ManagerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView cycleDate;

        public ManagerViewHolder(View view){
            super(view);
            cycleDate = view.findViewById(R.id.cycle_date_length);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick();
        }
    }
}
