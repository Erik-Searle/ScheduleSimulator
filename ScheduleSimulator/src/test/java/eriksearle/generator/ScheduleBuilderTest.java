package eriksearle.generator;

import static org.assertj.core.api.Assertions.assertThat;

import eriksearle.model.Team;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;


public class ScheduleBuilderTest {

    @Test
    public void generateDivisionPermutationsDoesAsSuch(){
        Team[] teams = new Team[]{new Team("Erik", "Tyler", new double[0], 1),
                new Team("Tyler", "Erik", new double[0], 1),
                new Team("James", "Brendan", new double[0], 1),
                new Team("Brendan", "James", new double[0], 1),
                new Team("Kenny", "Deion", new double[0], 1)};
        ScheduleBuilder builder = new ScheduleBuilder(teams);
        List<List<Team>> schedules = builder.generateDivisionPermutations(new ArrayList<Team>(), new ArrayList<Team>(List.of(teams)));
        assertThat(schedules.size()).isEqualTo(120);
        boolean listsAreUnique = true;
        outer: for(int i=0; i<schedules.size()-1; i++){
            for(int j=i+1; j<schedules.size(); j++){
                if(!listsAreDifferent(schedules.get(i), schedules.get(j))){
                    listsAreUnique = false;
                    break outer;
                }
            }
        }
        assertThat(listsAreUnique).isTrue();
    }

    private boolean listsAreDifferent(List<Team> list1, List<Team> list2){
        for(int i=0; i<list1.size(); i++){
            if(!list1.get(i).equals(list2.get(i))) return true;
        }
        return false;
    }

}
