package eriksearle.model;

import lombok.Getter;

@Getter
public class Team {
    String name;
    String rival;
    Team rivalTeam;
    double[] weeklyScores;
    int division;

    public Team(String name, String rival, double[] weeklyScores, int division){
        this.name = name;
        this.rival = rival;
        this.weeklyScores = weeklyScores;
        this.division = division;
        this.rivalTeam = null;
    }

    public void setRivalTeam(Team rivalTeam) {
        this.rivalTeam = rivalTeam;
    }
}
