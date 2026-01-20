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

public class JsonWriterTest extends JsonTest {
    @Test
    public void testWriterInvalidFile() {
        try {
            Tournament tournament = new Tournament("Bundesliga", new ArrayList<>(), new HashMap<>(), new HashMap<>());
            JsonWriter writer = new JsonWriter(".data/myillegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyTournament() {
        try {
            Tournament tournament = new Tournament("UEFA Champions League", new ArrayList<>(), new HashMap<>(),
                    new HashMap<>());
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTournament.json");
            writer.open();
            writer.write(tournament);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTournament.json");
            tournament = reader.read();
            assertEquals("UEFA Champions League", tournament.getname());
            assertEquals(0, tournament.getparticipatingClubs().size());
            assertEquals(new HashMap<>(), tournament.getclubPoints());
            assertEquals(new HashMap<>(), tournament.getmatchResult());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralTournament() {
        try {
            Tournament tournament = createSampleTournament();
            JsonWriter writer = new JsonWriter("./data/tournament.json");
            writer.open();
            writer.write(tournament);
            writer.close();

            JsonReader reader = new JsonReader("./data/tournament.json");
            tournament = reader.read();
            verifyTournament(tournament);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private Tournament createSampleTournament() {
        Tournament tournament = new Tournament("Premier League", new ArrayList<>(), new HashMap<>(),
                new HashMap<>());

        List<Player> players1 = new ArrayList<>();
        players1.add(new Player("Haaland", "Forward", "British", 24));
        players1.add(new Player("Foden", "Winger", "British", 24));
        List<Player> players2 = new ArrayList<>();
        players2.add(new Player("Mainoo", "Midfielder", "British", 20));

        tournament.addclub(new Club("Man City", 1900, "Mansour", "Etihad", 8, 1, players1));
        tournament.addclub(new Club("Man United", 1899, "Racliffe", "Old Trafford", 13, 3, players2));
        tournament.addclub(new Club("Arsenal", 1901, "Arteta", "Emirates", 3, 0, new ArrayList<>()));

        tournament.addmatchResult(new Match(tournament.getparticipatingClubs().get(0),
                tournament.getparticipatingClubs().get(1), "Etihad"), new Result(0, 0));
        tournament.addmatchResult(new Match(tournament.getparticipatingClubs().get(0),
                tournament.getparticipatingClubs().get(2), "Emirates"), new Result(1, 0));
        tournament.addmatchResult(new Match(tournament.getparticipatingClubs().get(2),
                tournament.getparticipatingClubs().get(1), "Old Trafford"), new Result(1, 2));

        return tournament;
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
