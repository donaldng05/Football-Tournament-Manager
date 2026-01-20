package java.com.footballtournament.domain.model;

import org.json.JSONObject;

// the Player class represents a football player with attributes such as the player's name, position (goalkeeper, 
// centre-back, full-back, left-back, right-back, defensive/ central/ attacking/ left/ right midfielder, left/ right
// winger, centre forward), nationality, and age
public class Player {
    private String playername;
    private String position;
    private String nationality;
    private int age;

    public Player(String playername, String position, String nationality, int age) {
        this.playername = playername;
        this.position = position;
        this.nationality = nationality;
        this.age = age;
    }

    // EFFECTS: return the player's name
    public String getname() {
        return playername;
    }

    // EFFECTS: return the player's position
    public String getposition() {
        return position;
    }

    // EFFECTS: return the player's nationality
    public String getnationality() {
        return nationality;
    }

    // EFFECTS: return the player's age
    public int getage() {
        return age;
    }

    // EFFECTS: return the player as a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playername", playername);
        json.put("position", position);
        json.put("nationality", nationality);
        json.put("age", age);
        return json;
    }

}
