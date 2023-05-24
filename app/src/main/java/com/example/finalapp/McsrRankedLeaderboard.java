package com.example.finalapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class McsrRankedLeaderboard {

    private String uuid;
    private String nickname;
    private String badge;
    private String elo_rate;
    private String elo_rank;

    public McsrRankedLeaderboard(){};

    public McsrRankedLeaderboard(String uuid, String nickname, String badge, String elo_rate, String elo_rank){
        this.uuid  = uuid;
        this.nickname = nickname;
        this.badge = badge;
        this.elo_rate = elo_rate;
        this.elo_rank = elo_rank;
    }

    public String getUuid(){
        return uuid;
    }

    public String getNickname(){
        return nickname;
    }

    public String getBadge(){
        return badge;
    }

    public String getElo_rate(){
        return elo_rate;
    }

    public String getElo_rank(){
        return elo_rank;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setBadge(String badge){
        this.badge = badge;
    }

    public void setElo_rate(String elo_rate){
        this.elo_rate = elo_rate;
    }

    public void setElo_rank(String elo_rank){
        this.elo_rank = elo_rank;
    }

    public static McsrRankedLeaderboard parseJSONObject(JSONObject object){
        McsrRankedLeaderboard leaderboard = new McsrRankedLeaderboard();

        try{
            if(object.has("uuid")){
                leaderboard.setUuid(object.getString("uuid"));
            }

            if(object.has("nickname")){
                leaderboard.setNickname(object.getString("nickname"));
            }

            if(object.has("badge")){
                leaderboard.setBadge(object.getString("badge"));
            }

            if(object.has("elo_rate")){
                leaderboard.setElo_rate(object.getString("elo_rate"));
            }

            if(object.has("elo_rank")){
                leaderboard.setElo_rank(object.getString("elo_rank"));
            }

        } catch (Exception e){

        }

        return leaderboard;
    }

    public static LinkedList<McsrRankedLeaderboard> parseJSONArray(JSONArray array){
        LinkedList<McsrRankedLeaderboard> list = new LinkedList<>();

        try {
            for(int i = 0; i < array.length(); i++){
                McsrRankedLeaderboard leaderboard = parseJSONObject(array.getJSONObject(i));
                list.add(leaderboard);
            }
        } catch (Exception e){

        }
        return list;
    }
}
