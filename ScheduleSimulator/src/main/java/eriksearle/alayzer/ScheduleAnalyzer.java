package eriksearle.alayzer;

import eriksearle.model.Team;
import eriksearle.model.TeamSchedule;
import java.util.ArrayList;
import java.util.List;

public class ScheduleAnalyzer {

    private final Team team;
    private final List<TeamSchedule> teamSchedules;
    private int longestWinStreak;
    private ArrayList<TeamSchedule> longestWinStreakSeasons;
    private int longestLosingStreak;
    private ArrayList<TeamSchedule> longestLosingStreakSeasons;
    private int mostWins;
    private ArrayList<TeamSchedule> mostWinsSeasons;
    private int leastWins;
    private ArrayList<TeamSchedule> leastWinsSeasons;
    private final int[] seasonsWithWins;
    private final double[] winPercentages;
    private int mostCommonWins;

    private static final String LINE_BREAK = "\n--------------------------------------------------\n";

    public ScheduleAnalyzer(Team team, List<TeamSchedule> teamSchedules){
        this.team = team;
        this.teamSchedules = teamSchedules;
        longestWinStreak = -1;
        longestLosingStreak = -1;
        mostWins = -1;
        leastWins = 15;
        mostCommonWins = 0;
        longestLosingStreakSeasons = new ArrayList<>();
        longestLosingStreakSeasons = new ArrayList<>();
        mostWinsSeasons = new ArrayList<>();
        leastWinsSeasons = new ArrayList<>();
        seasonsWithWins = new int[15];
        winPercentages = new double[15];
        analyze();
    }

    private void analyze(){
        for(TeamSchedule teamSchedule: teamSchedules){
            int wins = 0;
            int currentWinStreak = 0;
            int currentLossStreak = 0;
            int highWinStreak = 0;
            int highLossStreak = 0;
            for(int i=0; i<14; i++){
                boolean win = teamSchedule.getSchedule()[i].getWeeklyScores()[i] < team.getWeeklyScores()[i];
                String score = String.format("(%.2f-%.2f)", team.getWeeklyScores()[i], teamSchedule.getSchedule()[i].getWeeklyScores()[i]);
                if(win){
                    wins++;
                    currentWinStreak++;
                    currentLossStreak = 0;
                    if(currentWinStreak > highWinStreak) highWinStreak = currentWinStreak;
                    teamSchedule.getResults()[i] = "W " + score;
                }
                else{
                    currentLossStreak++;
                    currentWinStreak = 0;
                    if(currentLossStreak > highLossStreak) highLossStreak = currentLossStreak;
                    teamSchedule.getResults()[i] = "L " + score;
                }
            }
            seasonsWithWins[wins]++;
            if(wins <= leastWins){
                if(wins < leastWins){
                    leastWins = wins;
                    leastWinsSeasons = new ArrayList<>();
                }
                leastWinsSeasons.add(teamSchedule);
            }
            if(wins >= mostWins){
                if(wins > mostWins){
                    mostWins = wins;
                    mostWinsSeasons = new ArrayList<>();
                }
                mostWinsSeasons.add(teamSchedule);
            }
            if(highLossStreak >= longestLosingStreak){
                if(highLossStreak > longestLosingStreak){
                    longestLosingStreak = highLossStreak;
                    longestLosingStreakSeasons = new ArrayList<>();
                }
                longestLosingStreakSeasons.add(teamSchedule);
            }
            if(highWinStreak >= longestWinStreak){
                if(highWinStreak > longestWinStreak){
                    longestWinStreak = highWinStreak;
                    longestWinStreakSeasons = new ArrayList<>();
                }
                longestWinStreakSeasons.add(teamSchedule);
            }
        }
        for(int i=0; i<15; i++){
            if(seasonsWithWins[i] > seasonsWithWins[mostCommonWins]) mostCommonWins = i;
            winPercentages[i] = ((double) seasonsWithWins[i]/ (double) teamSchedules.size())*100;
        }
    }

    public String toString(){
        String returnable = team.getName();
        returnable += LINE_BREAK;
        returnable += String.format("Best Record: %d-%d  Worst Record: %d-%d  Longest Winning Streak: %d  Longest Losing Streak: %d  Most Common Record: %d-%d  Frequency Of Most Common Record: %.3f",
                mostWins, 14-mostWins, leastWins, 14-leastWins, longestWinStreak, longestLosingStreak, mostCommonWins, 14-mostCommonWins, winPercentages[mostCommonWins]);
        returnable += LINE_BREAK;
        returnable += "Example Best Record\n" + mostWinsSeasons.get(0);
        returnable += LINE_BREAK;
        returnable += "Example Worst Record\n" + leastWinsSeasons.get(0);
        returnable += LINE_BREAK;
        returnable += "Example Long Win Streak\n" + longestWinStreakSeasons.get(0);
        returnable += LINE_BREAK;
        returnable += "Example Long Loss Streak\n" + longestLosingStreakSeasons.get(0);
        returnable += LINE_BREAK;
        returnable += "Record Frequencies\n";
        for(int i=0; i<15; i++){
            returnable += i + "-" + (14-i) + ": " + seasonsWithWins[i] + " (" + String.format("%.3f",winPercentages[i]) + "%)\n";
        }
        returnable += "\n\n";
        return returnable;
    }
}
