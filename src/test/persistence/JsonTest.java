package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Match;
import java.com.footballtournament.domain.model.Player;
import java.com.footballtournament.domain.model.Result;
import java.com.footballtournament.domain.model.Tournament;
import java.util.List;

public class JsonTest {
    protected void checkTournament(String name, Tournament tournament) {
        assertEquals(name, tournament.getname());
    }

    protected void checkClub(String name, int yearOfFoundation, String currentPresident, String currentStadium,
            int domesticChampions, int continentalChampions, List<Player> players, Club club) {
        assertEquals(name, club.getName());
        assertEquals(yearOfFoundation, club.getyearOfFoundation());
        assertEquals(currentPresident, club.getcurrentPresident());
        assertEquals(currentStadium, club.getcurrentStadium());
        assertEquals(domesticChampions, club.getdomesticChampions());
        assertEquals(continentalChampions, club.getcontinentalChampions());
        assertEquals(players.size(), club.getplayers().size());
    }

    protected void checkPlayer(String name, String position, String nationality, int age, Player player) {
        assertEquals(name, player.getname());
        assertEquals(position, player.getposition());
        assertEquals(nationality, player.getnationality());
        assertEquals(age, player.getage());
    }

    protected void checkMatch(String homeClub, String awayClub, String stadium, Match match) {
        assertEquals(homeClub, match.gethomeClub().getName());
        assertEquals(awayClub, match.getawayClub().getName());
        assertEquals(stadium, match.getstadium());
    }

    protected void checkResult(int homeGoals, int awayGoals, Result result) {
        assertEquals(homeGoals, result.gethomeGoals());
        assertEquals(awayGoals, result.getawayGoals());
    }
}
