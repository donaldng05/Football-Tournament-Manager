package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Match;
import java.util.ArrayList;

public class MatchTest {
    private Match testmatch;
    private Club testhomeClub;
    private Club testawayClub;

    @BeforeEach
    void runBefore() {
        testhomeClub = new Club("FC Barcelona", 1899, "Joan Laporta", "Camp Nou", 27, 5, new ArrayList<>());
        testawayClub = new Club("Real Madrid", 1902, "Florentino Perez", "Santiago Bernabeu", 36, 15,
                new ArrayList<>());
        testmatch = new Match(testhomeClub, testawayClub, "Santiago Bernabeu");
    }

    @Test
    void testConstructor() {
        assertEquals(testhomeClub, testmatch.gethomeClub());
        assertEquals(testmatch.getawayClub(), testawayClub);
        assertEquals(testmatch.getstadium(), "Santiago Bernabeu");
    }

    @Test
    void testToJson() {
        JSONObject json = testmatch.toJson();
        assertEquals(testhomeClub.toJson().toString(), json.getJSONObject("homeClub").toString());
        assertEquals(testawayClub.toJson().toString(), json.getJSONObject("awayClub").toString());
        assertEquals("Santiago Bernabeu", json.getString("stadium"));
    }
}
