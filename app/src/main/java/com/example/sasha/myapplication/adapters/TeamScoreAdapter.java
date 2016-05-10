package com.example.sasha.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.game.Game;
import com.example.sasha.myapplication.models.Team;

/**
 * Created by Sasha on 08.05.2016.
 */
public class TeamScoreAdapter extends BaseAdapter {

    private static class ViewHolder {
        public TextView teamName;
        public TextView teamScore;
    }

    private Context mContext;

    public TeamScoreAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return Game.getCurrentGame().getNumTeams();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_team_score, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.teamName = (TextView) convertView.findViewById(R.id.team_name);
            holder.teamScore = (TextView) convertView.findViewById(R.id.team_score);
            convertView.setTag(holder);
        }

        Game activeGame = Game.getCurrentGame();
        Team team = activeGame.getTeam(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.teamName.setText(String.valueOf(team.getName()));
        holder.teamScore.setText(String.valueOf(activeGame.getTeamScore(team)));

        return convertView;
    }
}
