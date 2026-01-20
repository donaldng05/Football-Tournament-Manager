package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Match;
import java.com.footballtournament.domain.model.Player;
import java.com.footballtournament.domain.model.Result;
import java.com.footballtournament.domain.model.Tournament;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class JsonReaderTest extends JsonTest {
    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Tournament tournament = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyTournament() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTournament.json");
        try {
            Tournament tournament = reader.read();
            checkTournament("UEFA Champions League", tournament);
            assertEquals(0, tournament.getparticipatingClubs().size());
            assertEquals(new HashMap<>(), tournament.getclubPoints());
            assertEquals(new HashMap<>(), tournament.getmatchResult());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralTournament() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTournament.json");
        try {
            Tournament tournament = reader.read();
            verifyTournament(tournament);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void verifyTournament(Tournament tournament) {
        checkTournament("Premier League", tournament);
        assertEquals(3, tournament.getparticipatingClubs().size());
        // test the 1st club
        Club firstClub = tournament.getparticipatingClubs().get(0);
        List<Player> players1 = firstClub.getplayers();
        checkClub("Man City", 1900, "Mansour", "Etihad", 8, 1, players1, firstClub);
        checkPlayer("Haaland", "Forward", "British", 24, players1.get(0));
        checkPlayer("Foden", "Winger", "British", 24, players1.get(1));
        // test the 2nd club
        Club secondClub = tournament.getparticipatingClubs().get(1);
        List<Player> players2 = secondClub.getplayers();
        checkClub("Man United", 1899, "Racliffe", "Old Trafford", 13, 3, players2, secondClub);
        checkPlayer("Mainoo", "Midfielder", "British", 20, players2.get(0));
        // test the 3rd club
        Club thirdClub = tournament.getparticipatingClubs().get(2);
        checkClub("Arsenal", 1901, "Arteta", "Emirates", 3, 0, new ArrayList<>(), thirdClub);
        // test club points
        Map<Club, Integer> clubPoints = tournament.getclubPoints();
        assertEquals(3, clubPoints.size());
        assertEquals(4, clubPoints.get(firstClub));
        assertEquals(4, clubPoints.get(secondClub));
        assertEquals(0, clubPoints.get(thirdClub));
        // test match results
        verityMatchResults(tournament.getmatchResult());
    }

    private void verityMatchResults(Map<Match, Result> matchResults) {
        assertEquals(3, matchResults.size());
        for (Match match : matchResults.keySet()) {
            Result result = matchResults.get(match);
            if (match.gethomeClub().getName().equals("Man City")
                    && match.getawayClub().getName().equals("Man United")) {
                checkMatch("Man City", "Man United", "Etihad", match);
                checkResult(0, 0, result);
            } else if (match.gethomeClub().getName().equals("Man City")
                    && match.getawayClub().getName().equals("Arsenal")) {
                checkMatch("Man City", "Arsenal", "Emirates", match);
                checkResult(1, 0, result);
            } else if (match.gethomeClub().getName().equals("Arsenal")
                    && match.getawayClub().getName().equals("Man United")) {
                checkMatch("Arsenal", "Man United", "Old Trafford", match);
                checkResult(1, 2, result);
            }
        }
    }
}
