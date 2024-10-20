package com.alejandrobel.prograquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<ResultModel> resultsList;

    public LeaderboardAdapter(List<ResultModel> resultsList) {
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leadboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultModel result = resultsList.get(position);
        holder.textViewEmail.setText(result.getEmail());
        holder.textViewScore.setText(String.valueOf(result.getScore()));
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEmail, textViewScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewScore = itemView.findViewById(R.id.textViewScore);
        }
    }
}

