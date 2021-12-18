import eriksearle.alayzer.ScheduleAnalyzer;
import eriksearle.generator.ScheduleBuilder;
import eriksearle.model.Team;
import eriksearle.model.TeamSchedule;
import eriksearle.parsers.TeamParser;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] cheese){
        ArrayList<Team> teams = new TeamParser().parseTeams();
        for(Team team: teams){
            team.setRivalTeam(teams.stream().filter(rivalTeam -> rivalTeam.getName().equals(team.getRival())).findFirst().get());
        }
        for(Team team: teams) {
            ScheduleBuilder builder = new ScheduleBuilder(teams.toArray(new Team[0]));
            List<TeamSchedule> schedules = builder.generateAllSchedules(team);
            System.out.println(new ScheduleAnalyzer(team, schedules));
        }
    }
}
