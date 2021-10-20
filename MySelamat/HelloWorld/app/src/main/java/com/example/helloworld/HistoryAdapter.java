package com.example.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.db.AppDatabase;
import com.example.helloworld.db.USER_PLACE;
import com.example.helloworld.db.UserDao;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HisViewHolder> {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<USER_PLACE> mHistory;

    public HistoryAdapter(Context context) {
        layoutInflater= LayoutInflater.from(context);
        mContext= context;
    }

    public  void setHisList(List<USER_PLACE> mHistory){
        this.mHistory =mHistory;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public HisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= layoutInflater.inflate(R.layout.history_item,parent,false);
        HisViewHolder viewHolder= new HisViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HisViewHolder holder, int position) {


        AppDatabase db =AppDatabase.getDBInstance(mContext.getApplicationContext());
        UserDao userDao = db.userDao();

        holder.his_place.setText(userDao.searchplace(this.mHistory.get(position).PID));
        holder.his_time.setText(this.mHistory.get(position).VisitTime);
        holder.his_date.setText(this.mHistory.get(position).VisitDate);

    }

    @Override
    public int getItemCount() {

        return this.mHistory.size();
    }

    public class HisViewHolder extends  RecyclerView.ViewHolder {

        private TextView his_place,his_time,his_date;
        private int mPosition;

        public HisViewHolder(@NonNull View itemView) {
            super(itemView);
            his_place= itemView.findViewById(R.id.his_place);
            his_date=itemView.findViewById(R.id.his_date);
            his_time=itemView.findViewById(R.id.his_time);
        }
    }
}
