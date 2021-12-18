package eriksearle.model;

import lombok.Value;

@Value
public class TeamSchedule {
    Team team;
    Team[] inDivision;
    Team[] outDivision;
    int outDivSpecialWeek;
    String[] results = new String[14];
    public Team[] getSchedule(){
        Team[] schedule = new Team[14];
        schedule[0] = team.getRivalTeam();
        schedule[13] = team.getRivalTeam();
        int indiv = 0;
        int outdiv = 0;
        for(int i=1; i<4; i++){
            if(i == outDivSpecialWeek) {
                schedule[i] = outDivision[outdiv];
                outdiv++;
            }
            else{
                schedule[i] = inDivision[indiv];
                indiv++;
            }
        }
        for(int i=4; i<9; i++){
            schedule[i] = outDivision[outdiv];
            outdiv++;
        }
        for(int i=9; i<13; i++){
            if(i == outDivSpecialWeek) {
                schedule[i] = outDivision[outdiv];
                outdiv++;
            }
            else{
                schedule[i] = inDivision[indiv];
                indiv++;
            }
        }
        return schedule;
    }

    public String toString(){
        String schedString = "";
        Team[] schedule = getSchedule();
        for(int i=0; i<14; i++){
            schedString += schedule[i].getName() + " " + results[i] + ", ";
        }
        return schedString.substring(0, schedString.length()-2);
    }
}
