package java.com.footballtournament.domain.model;

import org.json.JSONObject;

// The match class represnets a football match between 2 clubs, home club and away club, and the stadium that will be 
// used to hold the match. Assume that every match in the tournament is placed at either the homeClub or the awayClub's 
// stadium
public class Match {

    private Club homeClub;
    private Club awayClub;
    private String stadium;

    public Match(Club homeClub, Club awayClub, String stadium) {
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.stadium = stadium;
    }

    public Club gethomeClub() {
        return homeClub;
    }

    public Club getawayClub() {
        return awayClub;
    }

    public String getstadium() {
        return stadium;
    }

    // EFFECTS: return the match to a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("homeClub", homeClub.toJson());
        json.put("awayClub", awayClub.toJson());
        json.put("stadium", stadium);
        return json;
    }
}
