package com.saille.ogzq;

import java.util.Map;
import java.util.Hashtable;

public class OgzqURL {
    public static String URL = "http://f7.ogzq.xdgame.cn";
    public static final String TASK = "/TaskInfo.aspx";
    public static final String MATCH_TRAINING = "/MatchList/TrainMatch/TrainMatch.aspx";
    public static final String MIDDLE_MAN = "/MiddleMan.aspx";
    public static final String ARENA = "/MatchList/Arena/Arena.aspx";
    public static final String TRAINING = "/Training.aspx";
    public static final String TEAMGAME = "/MatchList/TeamGame/TeamGame.aspx";
    public static final String VIEW_PLAYER = "/TeamAndPlayer/Player.aspx";
    public static final String VIEW_TEAM = "/TeamAndPlayer/Team.aspx";
    public static final String TEAM_PLAY = "/MatchList/TeamPlay/TeamPlay.aspx";
    public static final String BAGS = "/Bag.aspx";
    public static final String TRADE = "/Prop/Trade.aspx";
    public static final String LEAGUE = "/MatchList/SeasonMatch/SeasonMatch.aspx";
    public static final String CHALLENGE = "/MatchList/ChallengeMatch/ChallengeList.aspx";
    public static final String CHALLENGE_2 = "/MatchList/ChallengeMatch/Challenge1.aspx";
    public static final String TRAININGBASE = "/TrainingBase.aspx";
    public static final String CHAMPIONCUP = "/ChampionCup.aspx";
    public static final String TACTIC = "/Tactics.aspx";
    public static final String TRAININGBASE2 = "/TrainingBase2.aspx";
    public static final String TACTICAL = "/Tactical.aspx";
    public static final String WORLDCUP = "/WorldCup.aspx";

    public static Map<String, String> ITEMCODE = new Hashtable<String, String>();

    static {
        ITEMCODE.put("1201", "����1��");
        ITEMCODE.put("1202", "����2��");
        ITEMCODE.put("1203", "����3��");
        ITEMCODE.put("1204", "����4��");
        ITEMCODE.put("1205", "����5��");
        ITEMCODE.put("1301", "��ͧ1��");
        ITEMCODE.put("1302", "��ͧ2��");
        ITEMCODE.put("1303", "��ͧ3��");
        ITEMCODE.put("1304", "��ͧ4��");
        ITEMCODE.put("1305", "��ͧ5��");
        ITEMCODE.put("1401", "�ɻ�1��");
        ITEMCODE.put("1402", "�ɻ�2��");
        ITEMCODE.put("1403", "�ɻ�3��");
        ITEMCODE.put("1404", "�ɻ�4��");
        ITEMCODE.put("1405", "�ɻ�5��");
    }
}