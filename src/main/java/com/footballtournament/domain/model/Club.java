package java.com.footballtournament.domain.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Event;
import model.EventLog;

// the Club class represents a football club with its name, its year of foundation, its current president, its current
// stadium, its number of domestic and continental league titles won (both of which can be incremented by 1 after a
// a tournament as long as the club wins it), and its list of (arbitrary number of) players. For example, if the club 
// is super confident about winning a league, it may only need 11 players, which is extremely rare, if not 
// unprecedented in football history. 
public class Club {
    private String name;
    private int yearOfFoundation;
    private String currentPresident;
    private String currentStadium;
    private int domesticChampions;
    private int continentalChampions;
    private List<Player> players;

    public Club(String name, int yearOfFoundation, String currentPresident, String currentStadium,
            int domesticChampions, int continentalChampions, List<Player> players) {
        this.name = name;
        this.yearOfFoundation = yearOfFoundation;
        this.currentPresident = currentPresident;
        this.currentStadium = currentStadium;
        this.domesticChampions = domesticChampions;
        this.continentalChampions = continentalChampions;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public int getyearOfFoundation() {
        return yearOfFoundation;
    }

    public String getcurrentPresident() {
        return currentPresident;
    }

    // EFFECTS: return the name of the current stadium
    public String getcurrentStadium() {
        return currentStadium;
    }

    public int getdomesticChampions() {
        return domesticChampions;
    }

    public int getcontinentalChampions() {
        return continentalChampions;
    }

    // REQUIRES: dchampionsToAdd > 0
    // MODIFIES: this
    // EFFECTS: add the specified number of domestic champions to the club's total
    // number
    public void adddomesticChampions(int dchampionsToAdd) {
        this.domesticChampions += dchampionsToAdd;
    }

    // REQUIRES: cchampionsToAdd > 0
    // MODIFIES: this
    // EFFECTS: add the specified number of continental champions to the club's
    // total number
    public void addcontinentalChampions(int cchampionsToAdd) {
        this.continentalChampions += cchampionsToAdd;
    }

    // REQUIRES: player != null
    // MODIFIES: this
    // EFFECTS: add a player to the club's list of players and logs the event
    public void addPlayer(Player player) {
        players.add(player);
        EventLog.getInstance().logEvent(new Event("Player " + player.getname() + " added to club " + this.getName()));
    }

    // EFFECTS: return the club's list of players
    public List<Player> getplayers() {
        return players;
    }

    // EFFECTS: return the club as a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("yearOfFoundation", yearOfFoundation);
        json.put("currentPresident", currentPresident);
        json.put("currentStadium", currentStadium);
        json.put("domesticChampions", domesticChampions);
        json.put("continentalChampions", continentalChampions);
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS: return a list of players to a JSON array
    public JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player player : players) {
            jsonArray.put(player.toJson());
        }
        return jsonArray;
    }

}
