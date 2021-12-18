package eriksearle.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TeamScheduleTest {

    Team[] inDiv = new Team[]{
            new Team("A", "B", new double[0], 1),
            new Team("C", "D", new double[0], 1),
            new Team("E", "F", new double[0], 1),
            new Team("G", "H", new double[0], 1),
            new Team("I", "J", new double[0], 1),
            new Team("K", "L", new double[0], 1),
    };
    Team[] outDiv = new Team[]{
            new Team( "B", "A", new double[0], 1),
            new Team("D","C", new double[0], 1),
            new Team("F","E", new double[0], 1),
            new Team("H","G",  new double[0], 1),
            new Team("J","I", new double[0], 1),
            new Team("L","K", new double[0], 1),
    };

    @Test
    public void getScheduleReturnsCorrectly(){
        Team thisTeam = new Team("M", "N", new double[0], 1);
        thisTeam.setRivalTeam(new Team("N", "M", new double[0], 1));
        TeamSchedule schedule = new TeamSchedule(thisTeam, inDiv, outDiv, 12);
        assertThat(schedule.toString()).isEqualTo("N, A, C, E, B, D, F, H, J, G, I, K, L, N");
    }
}
