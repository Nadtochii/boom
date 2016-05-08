package com.example.sasha.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.models.TeamScore;

import java.util.ArrayList;

/**
 * Created by Sasha on 08.05.2016.
 */
public class TeamScoreAdapter extends ArrayAdapter<TeamScore> {

    private static class ViewHolder {
        public TextView teamName;
        public TextView teamScore;
    }

    public TeamScoreAdapter(Context context, ArrayList<TeamScore> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeamScore score = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_team_score, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.teamName = (TextView) convertView.findViewById(R.id.team_name);
            holder.teamScore = (TextView) convertView.findViewById(R.id.team_score);
            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.teamName.setText(String.valueOf(score.getTeam().getName()));
        holder.teamScore.setText(String.valueOf(score.getScore()));

        return convertView;
    }
}
