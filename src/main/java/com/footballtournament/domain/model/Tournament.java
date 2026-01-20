package java.com.footballtournament.domain.model;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

// the Tournament class represents a football tournament with its name, its list of participating clubs, a map of clubs 
// with their points earned, and a map of matches with their results. 
public class Tournament {
    private String name;
    private List<Club> participatingClubs;
    private Map<Club, Integer> clubPoints;
    private Map<Match, Result> matchResults;

    public Tournament(String name, List<Club> participatingClubs,
            Map<Club, Integer> clubPoints, Map<Match, Result> matchResults) {
        this.name = name;
        this.participatingClubs = participatingClubs;
        this.clubPoints = clubPoints;
        this.matchResults = matchResults;
    }

    public String getname() {
        return name;
    }

    // REQUIRES: club != null and not in the tournament
    // MODIFIES: this
    // EFFECTS: add a club to the list of participating clubs
    public void addclub(Club club) {
        participatingClubs.add(club);
        clubPoints.put(club, 0);
    }

    public List<Club> getparticipatingClubs() {
        return participatingClubs;
    }

    // REQUIRES: match != null and both participating clubs are in the tournament
    // MODIFIES: this
    // EFFECTS: records the result of a match and add it to the matchResults
    public void addmatchResult(Match match, Result result) {
        matchResults.put(match, result);
        updatePoints(match, result);
    }

    public Map<Match, Result> getmatchResult() {
        return new HashMap<>(matchResults);
    }

    // MODIFIES: this
    // EFFECTS: update the points for each club based on match result
    public void updatePoints(Match match, Result result) {
        Club homeClub = match.gethomeClub();
        Club awayClub = match.getawayClub();
        int homePoints = clubPoints.get(homeClub);
        int awayPoints = clubPoints.get(awayClub);

        if (result.gethomeGoals() > result.getawayGoals()) {
            // homeClub win
            clubPoints.put(homeClub, homePoints + 3);
        } else if (result.gethomeGoals() < result.getawayGoals()) {
            // awayClub win
            clubPoints.put(awayClub, awayPoints + 3);
        } else {
            // Draw
            clubPoints.put(homeClub, homePoints + 1);
            clubPoints.put(awayClub, awayPoints + 1);
        }
    }

    public Map<Club, Integer> getclubPoints() {
        return Map.copyOf(clubPoints);
    }

    // EFFECTS: return the tournament as a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("participatingClubs", clubsToJson());
        json.put("clubPoints", clubPointsToJson());
        json.put("matchResults", matchResultsToJson());
        return json;
    }

    // EFFECTS: return a list of participating clubs as a JSON array
    private JSONArray clubsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Club club : participatingClubs) {
            jsonArray.put(club.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: return a map of clubs to their earned points to a JSON object
    private JSONObject clubPointsToJson() {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<Club, Integer> entry : clubPoints.entrySet()) {
            jsonObject.put(entry.getKey().getName(), entry.getValue());
        }
        return jsonObject;
    }

    // EFFECTS: return a map of matches to their results to a JSON array
    private JSONArray matchResultsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Match, Result> entry : matchResults.entrySet()) {
            JSONObject json = new JSONObject();
            json.put("homeClub", entry.getKey().gethomeClub().getName());
            json.put("awayClub", entry.getKey().getawayClub().getName());
            json.put("stadium", entry.getKey().getstadium());
            json.put("homeGoals", entry.getValue().gethomeGoals());
            json.put("awayGoals", entry.getValue().getawayGoals());
            jsonArray.put(json);
        }
        return jsonArray;
    }
}
