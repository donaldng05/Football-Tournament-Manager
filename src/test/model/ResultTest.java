package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.com.footballtournament.domain.model.Result;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResultTest {
    private Result testresult1;
    private Result testresult2;
    private Result testresult3;

    @BeforeEach
    void runBefore() {
        testresult1 = new Result(4, 0);
        testresult2 = new Result(0, 1);
        testresult3 = new Result(0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals(4, testresult1.gethomeGoals());
        assertEquals(0, testresult1.getawayGoals());
        assertEquals(0, testresult2.gethomeGoals());
        assertEquals(1, testresult2.getawayGoals());
        assertEquals(0, testresult3.gethomeGoals());
        assertEquals(0, testresult3.getawayGoals());
    }

    @Test
    void testdetermineWinner() {
        assertEquals("Home", testresult1.determineWinner());
        assertEquals("Away", testresult2.determineWinner());
        assertEquals("Draw", testresult3.determineWinner());
    }

    @Test
    void testToJson() {
        JSONObject json1 = testresult1.toJson();
        assertEquals(4, json1.getInt("homeGoals"));
        assertEquals(0, json1.getInt("awayGoals"));

        JSONObject json2 = testresult2.toJson();
        assertEquals(0, json2.getInt("homeGoals"));
        assertEquals(1, json2.getInt("awayGoals"));

        JSONObject json3 = testresult3.toJson();
        assertEquals(0, json3.getInt("homeGoals"));
        assertEquals(0, json3.getInt("awayGoals"));
    }

}
