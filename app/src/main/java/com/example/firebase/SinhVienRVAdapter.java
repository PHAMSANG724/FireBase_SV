package com.example.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SinhVienRVAdapter extends RecyclerView.Adapter<SinhVienRVAdapter.ViewHolder> {
    private ArrayList<SinhVienRVModal> sinhVienRVModalArrayList;
    private Context context;
    private SinhVienClickInterface sinhVienClickInterface;

    public SinhVienRVAdapter(ArrayList<SinhVienRVModal> sinhVienRVModalArrayList, Context context, SinhVienClickInterface sinhVienClickInterface) {
        this.sinhVienRVModalArrayList = sinhVienRVModalArrayList;
        this.context = context;
        this.sinhVienClickInterface = sinhVienClickInterface;
    }

    @NonNull
    @Override
    public SinhVienRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sinhvien_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SinhVienRVAdapter.ViewHolder holder, int position) {
        SinhVienRVModal sinhVienRVModal = sinhVienRVModalArrayList.get(position);
        holder.svNameTV.setText(sinhVienRVModal.getSvName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinhVienClickInterface.onSinhVienClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sinhVienRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView svNameTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            svNameTV = itemView.findViewById(R.id.idTVName);
        }
    }

    public interface SinhVienClickInterface{
        void onSinhVienClick(int position);

        boolean onCreateOptionMenu(Menu menu);
    }
}
