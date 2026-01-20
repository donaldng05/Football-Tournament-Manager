package persistence;

// import model.*;

import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Match;
import java.com.footballtournament.domain.model.Player;
import java.com.footballtournament.domain.model.Result;
import java.com.footballtournament.domain.model.Tournament;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.*;

// represents a reader that reads tournament from JSON data stored in file
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads tournament from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Tournament read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parseTournament(json);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses tournament from JSON object and returns it
    public Tournament parseTournament(JSONObject json) {
        String name = json.getString("name");
        List<Club> participatingClubs = parseparticipatingClubs(json.getJSONArray("participatingClubs"));
        Map<Club, Integer> clubPoints = parseClubPoints(json.getJSONObject("clubPoints"), participatingClubs);
        Map<Match, Result> matchResults = parseMatchResults(json.getJSONArray("matchResults"), participatingClubs);
        return new Tournament(name, participatingClubs, clubPoints, matchResults);
    }

    // EFFECTS: parses participatingClubs from JSON array, adds them to a list of
    // participating participatingClubs and
    // returns the list
    public List<Club> parseparticipatingClubs(JSONArray jsonArray) {
        List<Club> participatingClubs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String name = json.getString("name");
            int year = json.getInt("yearOfFoundation");
            String president = json.getString("currentPresident");
            String stadium = json.getString("currentStadium");
            int domesticChampions = json.getInt("domesticChampions");
            int continentalChampions = json.getInt("continentalChampions");
            List<Player> players = parsePlayers(json.getJSONArray("players"));
            participatingClubs.add(new Club(name, year, president, stadium, domesticChampions, continentalChampions,
                    players));
        }
        return participatingClubs;
    }

    // EFFECTS: parses players from JSON array, adds them to a list of players and
    // returns the list
    public List<Player> parsePlayers(JSONArray jsonArray) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String name = json.getString("playername");
            String position = json.getString("position");
            String nationality = json.getString("nationality");
            int age = json.getInt("age");
            players.add(new Player(name, position, nationality, age));
        }
        return players;
    }

    // EFFECTS: parses club points from JSON object and returns a map of
    // participatingClubs with their earned points
    public Map<Club, Integer> parseClubPoints(JSONObject jsonObject, List<Club> participatingClubs) {
        Map<Club, Integer> clubPoints = new HashMap<>();
        for (Club club : participatingClubs) {
            String name = club.getName();
            if (jsonObject.has(name)) {
                clubPoints.put(club, jsonObject.getInt(name));
            }
        }
        return clubPoints;
    }

    // EFFECTS: parses match results from a JSON array and returns a map of matches
    // to their results
    public Map<Match, Result> parseMatchResults(JSONArray jsonArray, List<Club> participatingClubs) {
        Map<Match, Result> matchResults = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            Club homeClub = findClubByName(participatingClubs, json.getString("homeClub"));
            Club awayClub = findClubByName(participatingClubs, json.getString("awayClub"));
            String stadium = json.getString("stadium");
            int homeGoals = json.getInt("homeGoals");
            int awayGoals = json.getInt("awayGoals");
            Match match = new Match(homeClub, awayClub, stadium);
            Result result = new Result(homeGoals, awayGoals);
            matchResults.put(match, result);
        }
        return matchResults;
    }

    // EFFECTS: finds club by its name
    public Club findClubByName(List<Club> participatingClubs, String name) {
        for (Club club : participatingClubs) {
            if (club.getName().equals(name)) {
                return club;
            }
        }
        return null;
    }
}
