package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Match;
import java.com.footballtournament.domain.model.Result;
import java.com.footballtournament.domain.model.Tournament;
import java.util.ArrayList;
import java.util.HashMap;

public class TournamentTest {
    private Tournament testtournament;
    private Club testclub1;
    private Club testclub2;
    private Club testclub3;
    private Club testclub4;
    private Match testmatch1;
    private Match testmatch2;
    private Match testmatch3;
    private Result testresult1;
    private Result testresult2;
    private Result testresult3;

    @BeforeEach
    void runBefore() {
        testtournament = new Tournament("UEFA Champions League", new ArrayList<>(), new HashMap<>(), new HashMap<>());
        testclub1 = new Club("FC Barcelona", 1899, "Joan Laporta", "Camp Nou", 27, 5, new ArrayList<>());
        testclub2 = new Club("Manchester City", 1880, "Shiek Mansour", "Etihad", 8, 1, new ArrayList<>());
        testclub3 = new Club("Real Madrid", 1902, "Florentino Perez", "Santiago Bernabeu", 36, 15, new ArrayList<>());
        testclub4 = new Club("Manchester United", 1878, "Sir Jim Racliffe", "Old Trafford", 13, 3, new ArrayList<>());
        testtournament.addclub(testclub1);
        testtournament.addclub(testclub2);
        testtournament.addclub(testclub3);

        testmatch1 = new Match(testclub1, testclub3, "Camp Nou");
        testresult1 = new Result(5, 0);
        testmatch2 = new Match(testclub2, testclub3, "Santiago Bernabeu");
        testresult2 = new Result(1, 1);
        testmatch3 = new Match(testclub1, testclub2, "Etihad");
        testresult3 = new Result(0, 1);
    }

    @Test
    void testConstructor() {
        assertEquals("UEFA Champions League", testtournament.getname());
        assertTrue(testtournament.getparticipatingClubs().contains(testclub1));
        assertTrue(testtournament.getparticipatingClubs().contains(testclub2));
        assertTrue(testtournament.getparticipatingClubs().contains(testclub3));
        assertEquals(3, testtournament.getparticipatingClubs().size());

        assertEquals(0, testtournament.getclubPoints().get(testclub1));
        assertEquals(0, testtournament.getclubPoints().get(testclub2));
        assertEquals(0, testtournament.getclubPoints().get(testclub3));

        assertTrue(testtournament.getmatchResult().isEmpty());
    }

    @Test
    void testaddclub() {
        testtournament.addclub(testclub4);
        assertTrue(testtournament.getparticipatingClubs().contains(testclub4));
        assertEquals(0, testtournament.getclubPoints().get(testclub4));
    }

    @Test
    void testaddMatchResult() {
        testtournament.addmatchResult(testmatch1, testresult1);
        assertEquals(3, testtournament.getclubPoints().get(testclub1));
        assertEquals(0, testtournament.getclubPoints().get(testclub3));

        testtournament.addmatchResult(testmatch2, testresult2);
        assertEquals(1, testtournament.getclubPoints().get(testclub2));
        assertEquals(1, testtournament.getclubPoints().get(testclub3));

        testtournament.addmatchResult(testmatch3, testresult3);
        assertEquals(3, testtournament.getclubPoints().get(testclub1));
        assertEquals(4, testtournament.getclubPoints().get(testclub2));
    }

    @Test
    void testToJson() {
        testtournament.addmatchResult(testmatch1, testresult1);
        JSONObject jsonObject = testtournament.toJson();
        assertEquals("UEFA Champions League", jsonObject.getString("name"));

        // test participating clubs
        JSONArray participatingClubsJson = jsonObject.getJSONArray("participatingClubs");
        assertEquals(3, participatingClubsJson.length());
        assertEquals(testclub1.toJson().toString(), participatingClubsJson.getJSONObject(0).toString());
        assertEquals(testclub2.toJson().toString(), participatingClubsJson.getJSONObject(1).toString());
        assertEquals(testclub3.toJson().toString(), participatingClubsJson.getJSONObject(2).toString());

        // test clubPoints
        assertEquals(3, jsonObject.getJSONObject("clubPoints").getInt("FC Barcelona"));
        assertEquals(0, jsonObject.getJSONObject("clubPoints").getInt("Real Madrid"));

        // test matchResults
        assertEquals(1, jsonObject.getJSONArray("matchResults").length());
        assertEquals("FC Barcelona", jsonObject.getJSONArray("matchResults").getJSONObject(0).getString("homeClub"));
        assertEquals("Real Madrid", jsonObject.getJSONArray("matchResults").getJSONObject(0).getString("awayClub"));
        assertEquals("Camp Nou", jsonObject.getJSONArray("matchResults").getJSONObject(0).getString("stadium"));
        assertEquals(5,
                jsonObject.getJSONArray("matchResults").getJSONObject(0).getInt("homeGoals"));
        assertEquals(0,
                jsonObject.getJSONArray("matchResults").getJSONObject(0).getInt("awayGoals"));
    }

}
