package ui;

import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Player;
import java.com.footballtournament.domain.model.Tournament;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

// Constructs the MainGUI and initializes components
// Sets up events logging on application close
public class MainGUI extends JFrame {
    public static final String JSON_STORE = "./data/tournamentFORGUI.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Tournament tournament;
    private ClubDetailsPanel clubDetailsPanel;
    private JTextArea filteredPlayersTextArea;
    private Image backgroundImage;

    // EFFECTS: constructs the main GUI and initializes components
    public MainGUI() {
        super("Football Tournament Manager");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        try {
            tournament = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to load from file: " + e.getMessage());
        }
        loadBackgroundImage();
        setupMainGUI();
        createMenuBar();
        createToolBar();
        createClubDetailsPanel();
        createFilteredPlayersPanel();

        // add this listener to handle application closing and print the event log
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // super.windowClosing(e);
                // printEventLog();
                printLogEvents();
            }
        });
    }

    // EFFECTS: prints all events in the EventLog to the console
    // private void printEventLog() {
    // EventLog eventLog = EventLog.getInstance();
    // for (model.Event event: eventLog) {
    // System.out.println(event.toString());
    // }
    // }

    // EFFECTS: prints all events in the log upon application closure
    private void printLogEvents() {
        EventLog log = EventLog.getInstance();
        for (Event event : log) {
            System.out.println(event);
        }
        log.clear();
    }

    // MODIFIES: this
    // EFFECTS: sets up the main window
    private void setupMainGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: loads the background image
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("./data/stadium.jpg"));
            if (backgroundImage == null) {
                System.out.println("Image not found or could not be loaded");
            } else {
                System.out.println("Image loaded successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the menu bar
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new SaveAction());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new LoadAction());
        fileMenu.add(loadItem);
    }

    // MODIFIES: this
    // EFFECTS: creates the tool bar
    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton addPlayerButton = new JButton("Add Player");
        addPlayerButton.addActionListener(e -> addPlayerToClub());
        toolBar.add(addPlayerButton);

        JButton filterPlayersButton = new JButton("Filter Players");
        filterPlayersButton.addActionListener(e -> filterPlayersByPosition());
        toolBar.add(filterPlayersButton);

        JButton removePlayerButton = new JButton("Remove Player");
        removePlayerButton.addActionListener(e -> removePlayerFromClub());
        toolBar.add(removePlayerButton);

        add(toolBar, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: creates the club details panel
    private void createClubDetailsPanel() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    // System.out.println("checked");
                    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
                }
            }
        };

        clubDetailsPanel = new ClubDetailsPanel(null);
        backgroundPanel.add(clubDetailsPanel, BorderLayout.CENTER);

        filteredPlayersTextArea = new JTextArea();
        filteredPlayersTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(filteredPlayersTextArea);
        JPanel filteredPlayersPanel = new JPanel(new BorderLayout());
        filteredPlayersPanel.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.add(filteredPlayersPanel, BorderLayout.SOUTH);

        add(backgroundPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates the filtered players panel
    private void createFilteredPlayersPanel() {
        filteredPlayersTextArea = new JTextArea(10, 50);
        add(new JScrollPane(filteredPlayersTextArea), BorderLayout.SOUTH);
    }

    private class SaveAction implements ActionListener {
        // EFFECTS: saves the current state of the tournament to a file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(tournament);
                jsonWriter.close();
                JOptionPane.showMessageDialog(MainGUI.this, "Saved tournament to " + JSON_STORE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(MainGUI.this, "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    private class LoadAction implements ActionListener {
        // EFFECTS: loads the state of the tournament from a file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                tournament = jsonReader.read();
                JOptionPane.showMessageDialog(MainGUI.this, "Loaded tournament from " + JSON_STORE);
                if (!tournament.getparticipatingClubs().isEmpty()) {
                    clubDetailsPanel.updateClub(tournament.getparticipatingClubs().get(0));
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(MainGUI.this, "Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // EFFECTS: launches the main GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
    }

    // MODIFIES: this
    // EFFECTS: adds a player to the selected club
    private void addPlayerToClub() {
        Club selectedClub = getSelectedClub();
        if (selectedClub != null) {
            PlayerDialog playerDialog = new PlayerDialog(this);
            playerDialog.setVisible(true);
            Player player = playerDialog.getPlayer();
            if (player != null) {
                selectedClub.addPlayer(player);
                clubDetailsPanel.updateClub(selectedClub);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a club first!");
        }
    }

    // MODIFIES: this
    // EFFECTS: filters players by position for the selected club
    private void filterPlayersByPosition() {
        Club selectedClub = getSelectedClub();
        if (selectedClub != null) {
            FilterPlayersDialog filterPlayersDialog = new FilterPlayersDialog(this);
            filterPlayersDialog.setVisible(true);
            String position = filterPlayersDialog.getPosition();
            if (position != null) {
                List<Player> filteredPlayers = selectedClub.getplayers().stream()
                        .filter(player -> player.getposition().equalsIgnoreCase(position)).collect(Collectors.toList());
                displayFilteredPlayers(filteredPlayers);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a club first!");
        }
        EventLog.getInstance().logEvent(new Event("Player filtered based on position"));
    }

    // MODIFIES: this
    // EFFECTS: displays the filtered players in the JTextArea
    private void displayFilteredPlayers(List<Player> players) {
        filteredPlayersTextArea.setText(""); // clear existing text
        for (Player player : players) {
            filteredPlayersTextArea.append(player.getname() + " - " + player.getposition() + " - "
                    + player.getnationality() + " - " + player.getage() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a player from the selected club
    private void removePlayerFromClub() {
        Club selectedClub = getSelectedClub();
        if (selectedClub == null) {
            JOptionPane.showMessageDialog(this, "Please select a club first!");
            return;
        }
        String playerName = JOptionPane.showInputDialog(null, "Enter the name of the player to remove:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            removePlayerByName(selectedClub, playerName);
        }
    }

    // MODIFIES: this
    // EFFECTS: finds and removes the player with the given name from the club
    private void removePlayerByName(Club club, String playerName) {
        Player playerToRemove = findPlayerByName(club, playerName);
        if (playerToRemove != null) {
            club.getplayers().remove(playerToRemove);
            clubDetailsPanel.updateClub(club);
            EventLog.getInstance().logEvent(new Event("Player removed from club: " + playerName));
        } else {
            System.out.println("Player not found");
        }
    }

    // EFFECTS: returns the player with the given name if found, null otherwise
    private Player findPlayerByName(Club club, String playerName) {
        for (Player player : club.getplayers()) {
            if (player.getname().equalsIgnoreCase(playerName)) {
                return player;
            }
        }
        return null;
    }

    // EFFECTS: returns the selected club
    private Club getSelectedClub() {
        // we'll just return the 1st club in the list
        return tournament.getparticipatingClubs().isEmpty() ? null : tournament.getparticipatingClubs().get(0);
    }
}
