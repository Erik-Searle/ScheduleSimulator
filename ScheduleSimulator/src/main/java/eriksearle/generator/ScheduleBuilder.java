package eriksearle.generator;

import eriksearle.model.Team;
import eriksearle.model.TeamSchedule;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScheduleBuilder {

    private Team[] allTeams;
    private final List<Team> div1;
    private final List<Team> div2;
    private final int[] possibleInterDevWeeks = new int[]{1,2,3,9,10,11,12};

    public ScheduleBuilder(Team[] teams){
        allTeams = teams;
        div1 = Stream.of(teams).filter(team -> team.getDivision() == 1).collect(Collectors.toList());
        div2 = Stream.of(teams).filter(team -> team.getDivision() == 2).collect(Collectors.toList());
    }

    public ArrayList<TeamSchedule> generateAllSchedules(Team team){
        ArrayList<TeamSchedule> schedules = new ArrayList<>();
        ArrayList<Team> localDiv = getLocalDiv(team);
        ArrayList<Team> rivalDiv = getRivalDiv(team);
        List<List<Team>> interDivSchedules = generateDivisionPermutations(rivalDiv);
        List<List<Team>> intraDivSchedules = generateDivisionPermutations(localDiv);
        for(int week: possibleInterDevWeeks){
            for(List<Team> interDivSchedule: interDivSchedules){
                for(List<Team> intraDivSchedule: intraDivSchedules){
                    Team[] interDivArray = interDivSchedule.toArray(new Team[0]);
                    Team[] intraDivArray = intraDivSchedule.toArray(new Team[0]);
                    schedules.add(new TeamSchedule(team, intraDivArray, interDivArray, week));
                }
            }
        }
        return schedules;
    }

    public List<List<Team>> generateDivisionPermutations(List<Team> division){
        return generateDivisionPermutations(new ArrayList<>(), division);
    }

    public List<List<Team>> generateDivisionPermutations(List<Team> currentList, List<Team> remainingTeams){
        if(remainingTeams.isEmpty()){
            ArrayList<List<Team>> returnable = new ArrayList<>();
            returnable.add(currentList);
            return returnable;
        }
        ArrayList<List<Team>> returnable = new ArrayList<>();
        for(Team team: remainingTeams){
            ArrayList<Team> copyOfRemaining = new ArrayList<>();
            for(Team remainingTeam: remainingTeams){
                if(!remainingTeam.getName().equals(team.getName())) copyOfRemaining.add(remainingTeam);
            }
            ArrayList<Team> copyOfCurrent = new ArrayList<>(currentList);
            copyOfCurrent.add(team);
            returnable.addAll(generateDivisionPermutations(copyOfCurrent, copyOfRemaining));
        }
        return returnable;
    }

    private ArrayList<Team> getLocalDiv(Team team){
        if(div1.contains(team)) return getDivCopyMinus(div1, team);
        return getDivCopyMinus(div2, team);
    }

    private ArrayList<Team> getRivalDiv(Team team){
        if(div1.contains(team.getRivalTeam())) return getDivCopyMinus(div1, team.getRivalTeam());
        return getDivCopyMinus(div2, team.getRivalTeam());
    }

    private ArrayList<Team> getDivCopyMinus(List<Team> div, Team team){
        ArrayList<Team> returnable = new ArrayList<>();
        for(Team team1: div) if(!team1.equals(team)) returnable.add(team1);
        return returnable;
    }
}
