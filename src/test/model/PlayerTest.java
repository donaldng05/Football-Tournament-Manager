package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.com.footballtournament.domain.model.Player;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Player testplayer;

    @BeforeEach
    void runBefore() {
        testplayer = new Player("Cristiano Ronaldo", "Forward", "Portugese", 39);
    }

    @Test
    void testConstructor() {
        assertEquals("Cristiano Ronaldo", testplayer.getname());
        assertEquals("Forward", testplayer.getposition());
        assertEquals("Portugese", testplayer.getnationality());
        assertEquals(39, testplayer.getage());
    }

    @Test
    void testToJson() {
        JSONObject json = testplayer.toJson();
        assertEquals("Cristiano Ronaldo", json.getString("playername"));
        assertEquals("Forward", json.getString("position"));
        assertEquals("Portugese", json.getString("nationality"));
        assertEquals(39, json.getInt("age"));
    }

}
