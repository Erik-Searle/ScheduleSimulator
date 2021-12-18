package eriksearle.parsers;

import eriksearle.model.Team;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TeamParser {

    public ArrayList<Team> parseTeams(){
        ArrayList<Team> teams = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray teamsJson = (JSONArray) parser.parse(new FileReader("/Users/erik.searle/workspace/personal/ScheduleSimulator/src/teams.json"));
            Iterator<JSONObject> iterator = teamsJson.iterator();
            while(iterator.hasNext()) {
                JSONObject teamJson = iterator.next();
                double[] scores = new double[14];
                int i=0;
                for(Object score: (JSONArray) teamJson.get("scores")){
                    scores[i] = (double) score;
                    i++;
                }
                teams.add(new Team((String)teamJson.get("name"), (String)teamJson.get("rival"), scores, ((Long)teamJson.get("division")).intValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teams;
    }
}
