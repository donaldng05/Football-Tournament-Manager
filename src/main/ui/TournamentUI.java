package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Match;
import java.com.footballtournament.domain.model.Player;
import java.com.footballtournament.domain.model.Result;
import java.com.footballtournament.domain.model.Tournament;
import java.io.FileNotFoundException;
import java.io.IOException;

import persistence.*;

// The TournamentUI class handles user interactions for managing tournaments, including creating tournaments, adding 
// clubs, adding players to clubs, recording match results, and viewing tournament details. It uses a console-based 
// interface to prompt the user for input and display information.

public class TournamentUI {
    private static final String JSON_STORE = "./data/tournamentFORGUI.json";
    private Tournament tournament;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public TournamentUI() {
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: create a tournament, with a name first
    public void createTournament() {
        System.out.println("Enter tournament name: ");
        String name = scanner.nextLine();
        tournament = new Tournament(name, new ArrayList<>(), new HashMap<>(), new HashMap<>());
        System.out.println("Tournament created: " + name);
    }

    // EFFECTS: add club to a tournament
    // if no tournament exists, user has to add the tournament before moving on to
    // the add Club function
    // if a tournament exists, add the club with its details
    public void addclub() {
        if (tournament == null) {
            System.out.println("No tournament created. Please create a tournament first!");
            return;
        }

        Club club = getClubDetailsFromUser();
        tournament.addclub(club);
        System.out.println("Club added: " + club.getName());
    }

    // EFFECTS: add player to a club in a tournament
    // a tournament and a club must exist for the user to be able to add player
    // otherwise, add a tournament and a club first
    public void addPlayerToClub() {
        if (tournament == null) {
            System.out.println("No tournament created. Please create a tournament first!");
            return;
        }

        Club club = findClubByName(null);
        if (club == null) {
            System.out.println("Club not found!");
            return;
        }
        addPlayerToClub(club);
    }

    // EFFECTS: helps add details of a player to a club
    private void addPlayerToClub(Club club) {
        System.out.println("Enter player name: ");
        String name = scanner.nextLine();
        System.out.println("Enter player position: ");
        String position = scanner.nextLine();
        System.out.println("Enter player nationality: ");
        String nationality = scanner.nextLine();
        System.out.println("Enter player age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        club.addPlayer(new Player(name, position, nationality, age));
        System.out.println("Player added: " + name);
    }

    // EFFECTS: record a match result, assume that a tournament and 2 clubs have
    // already existed
    public void recordMatchResult() {
        if (tournament == null) {
            System.out.println("No tournament created. Please create a tournament first!");
            return;
        }

        Club homeClub = findClubByName("Enter home club name: ");
        Club awayClub = findClubByName("Enter away club name: ");

        if (homeClub == null || awayClub == null) {
            System.out.println("One or both clubs not found!");
            return;
        }

        System.out.println("Enter match stadium: ");
        String stadium = scanner.nextLine();
        System.out.println("Enter home club score: ");
        int homeGoals = scanner.nextInt();
        System.out.println("Enter away club score: ");
        int awayGoals = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Match match = new Match(homeClub, awayClub, stadium);
        Result result = new Result(homeGoals, awayGoals);
        tournament.addmatchResult(match, result);
        System.out.println("Match result recorded!");
    }

    // EFFECTS: view the details of a tournament (name, participating clubs, match
    // results),
    // assume that a tournament has already been created
    public void viewTournamentDetails() {
        if (tournament == null) {
            System.out.println("No tournament created. Please create a tournament first!");
            return;
        }

        System.out.println("Tournament: " + tournament.getname());
        System.out.println("Participating clubs:");
        for (Club club : tournament.getparticipatingClubs()) {
            System.out.println("- " + club.getName() + " (Points: " + tournament.getclubPoints().get(club) + ")");
        }
        System.out.println("Match Results:");
        for (Match match : tournament.getmatchResult().keySet()) {
            Result result = tournament.getmatchResult().get(match);
            System.out.println("- " + match.gethomeClub().getName() + " vs " + match.getawayClub().getName() + ": "
                    + result.gethomeGoals() + "-" + result.getawayGoals() + " at " + match.getstadium());
        }
    }

    // EFFECTS: save the tournament to a file
    public void saveTournament() {
        if (tournament == null) {
            System.out.println("No tournament created. Create a tournament first");
            return;
        }

        try {
            jsonWriter.open();
            jsonWriter.write(tournament);
            jsonWriter.close();
            System.out.println("Saved" + tournament.getname() + "to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: load a tournament from a file
    public void loadTournament() {
        try {
            tournament = jsonReader.read();
            System.out.println("Loaded" + tournament.getname() + "from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: help get the attributes of a club from the user
    private Club getClubDetailsFromUser() {
        String name = getClubName();
        int yearOfFoundation = getYearOfFoundation();
        String currentPresident = getCurrentPresident();
        String currentStadium = getCurrentStadium();
        int domesticChampions = getDomesticChampions();
        int continentalChampions = getContinentalChampions();
        List<Player> players = getPlayers();

        return new Club(name, yearOfFoundation, currentPresident, currentStadium,
                domesticChampions, continentalChampions, players);
    }

    private String getClubName() {
        System.out.println("Enter club name: ");
        return scanner.nextLine();
    }

    private int getYearOfFoundation() {
        System.out.println("Enter year of foundation: ");
        return scanner.nextInt();
    }

    private String getCurrentPresident() {
        System.out.println("Enter club current president: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    private String getCurrentStadium() {
        System.out.println("Enter club current stadium: ");
        return scanner.nextLine();
    }

    private int getDomesticChampions() {
        System.out.println("Enter club domestic champions: ");
        return scanner.nextInt();
    }

    private int getContinentalChampions() {
        System.out.println("Enter club continental champions: ");
        return scanner.nextInt();
    }

    private List<Player> getPlayers() {
        System.out.println("Enter club list of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(getPlayerDetails());
        }
        return players;
    }

    // EFFECTS: help get the details of a player from the user
    private Player getPlayerDetails() {
        System.out.println("Enter player name: ");
        String playername = scanner.nextLine();
        System.out.println("Enter player position: ");
        String position = scanner.nextLine();
        System.out.println("Enter player nationality: ");
        String nationality = scanner.nextLine();
        System.out.println("Enter player age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        return new Player(playername, position, nationality, age);
    }

    // EFFECTS: find a club by its name
    private Club findClubByName(String prompt) {
        System.out.print(prompt);
        String name = scanner.nextLine();
        for (Club club : tournament.getparticipatingClubs()) {
            if (club.getName().equalsIgnoreCase(name)) {
                return club;
            }
        }
        return null;
    }
}
