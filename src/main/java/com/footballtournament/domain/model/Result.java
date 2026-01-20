package java.com.footballtournament.domain.model;

import org.json.JSONObject;

// the Result class represents the outcome of a football match, with the goals scored by the home club and the away 
// club. If the home club has more goals than the away club, the result of the match is "Home", claiming the home club's
// victory. If the home club has fewer goals than the away club, the result of the match is "Away" instead, showing the
// away club is the winner of the 2. If both club have the same number of goals, the result of the match is "Draw".

public class Result {
    private int homeGoals; // the goals that the home club scores
    private int awayGoals; // the goals that the away club scores

    public Result(int homeGoals, int awayGoals) {
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public int gethomeGoals() {
        return homeGoals;
    }

    public int getawayGoals() {
        return awayGoals;
    }

    // EFFECTS: return "Home" if the home club won, "Away" if the away club won,
    // else "Draw"
    public String determineWinner() {
        if (homeGoals > awayGoals) {
            return "Home";
        } else if (homeGoals < awayGoals) {
            return "Away";
        } else {
            return "Draw";
        }
    }

    // EFFECTS: return the result as a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("homeGoals", homeGoals);
        json.put("awayGoals", awayGoals);
        return json;
    }

}
