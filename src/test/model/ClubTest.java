package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Player;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClubTest {
    private Club testclub;

    @BeforeEach
    void runBefore() {
        testclub = new Club("FC Barcelona", 1899, "Joan Laporta", "Camp Nou", 27, 5, new ArrayList<>());
    }

    @Test
    void testConstructor() {
        assertEquals("FC Barcelona", testclub.getName());
        assertEquals(1899, testclub.getyearOfFoundation());
        assertEquals("Joan Laporta", testclub.getcurrentPresident());
        assertEquals("Camp Nou", testclub.getcurrentStadium());
        assertEquals(27, testclub.getdomesticChampions());
        assertEquals(5, testclub.getcontinentalChampions());
        assertEquals(new ArrayList<>(), testclub.getplayers());
    }

    @Test
    void testaddPlayer() {
        Player messi = new Player("Messi", "Striker", "Argentinian", 37);
        testclub.addPlayer(messi);
        assertTrue(testclub.getplayers().contains(messi));
    }

    @Test
    void testaddDomesticChampions() {
        testclub.adddomesticChampions(1);
        assertEquals(28, testclub.getdomesticChampions());
    }

    @Test
    void testaddContinentalChampions() {
        testclub.addcontinentalChampions(1);
        assertEquals(6, testclub.getcontinentalChampions());
    }

    @Test
    void testToJson() {
        JSONObject json = testclub.toJson();
        assertEquals("FC Barcelona", json.getString("name"));
        assertEquals(1899, json.getInt("yearOfFoundation"));
        assertEquals("Joan Laporta", json.getString("currentPresident"));
        assertEquals("Camp Nou", json.getString("currentStadium"));
        assertEquals(27, json.getInt("domesticChampions"));
        assertEquals(5, json.getInt("continentalChampions"));
        assertEquals(0, json.getJSONArray("players").length());
    }

    @Test
    void testPlayersToJson() {
        testclub.addPlayer(new Player("Messi", "Striker", "Argentinian", 37));
        JSONArray jsonArray = testclub.playersToJson();
        assertEquals(1, jsonArray.length());
        // test player
        assertEquals("Messi", jsonArray.getJSONObject(0).getString("playername"));
        assertEquals("Striker", jsonArray.getJSONObject(0).getString("position"));
        assertEquals("Argentinian", jsonArray.getJSONObject(0).getString("nationality"));
        assertEquals(37, jsonArray.getJSONObject(0).getInt("age"));
    }
}
