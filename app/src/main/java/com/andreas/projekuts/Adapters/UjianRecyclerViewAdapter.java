package com.andreas.projekuts.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.andreas.projekuts.R;
import com.andreas.projekuts.Models.Ujian;
import com.andreas.projekuts.UpdateFragment;

import java.util.List;

public class UjianRecyclerViewAdapter  extends RecyclerView.Adapter<UjianRecyclerViewAdapter.UserViewHolder> {
    private Context context;
    private List<Ujian> result;


    public UjianRecyclerViewAdapter(Context context, List<Ujian> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Ujian ujian = result.get(position);
        holder.jadwal.setText(ujian.getTanggal());
        holder.mapel.setText(ujian.getMapel());

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder  {
        private TextView jadwal , mapel;

        public UserViewHolder(@NonNull View view){
            super(view);
            jadwal = view.findViewById(R.id.tv_ujian);
            mapel = view.findViewById(R.id.tv_mapel);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Ujian ujian = result.get(getAdapterPosition());
                    Bundle data = new Bundle();
                    data.putSerializable("ujian",ujian);
                    UpdateFragment updateFragment= new UpdateFragment();
                    updateFragment.setArguments(data);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout,updateFragment)
                            .commit();
                }
            });

        }


    }

}

