package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.com.footballtournament.domain.model.Club;
import java.com.footballtournament.domain.model.Player;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// The ClubDetailsPanel class displays details of a club and its players in a panel
public class ClubDetailsPanel extends JPanel {
    private JLabel clubNameLabel;
    private JLabel yearLabel;
    private JLabel presidentLabel;
    private JLabel stadiumLabel;
    private JLabel domesticChampionsLabel;
    private JLabel continentalChampionsLabel;
    private JList<String> playerList;

    // EFFECTS: constructs a new ClubDetailsPanel and initializes components
    public ClubDetailsPanel(Club club) {
        setLayout(new BorderLayout());
        JPanel clubInfoPanel = new JPanel(new GridLayout(6, 1));
        clubNameLabel = new JLabel();
        yearLabel = new JLabel();
        presidentLabel = new JLabel();
        stadiumLabel = new JLabel();
        domesticChampionsLabel = new JLabel();
        continentalChampionsLabel = new JLabel();

        clubInfoPanel.add(clubNameLabel);
        clubInfoPanel.add(yearLabel);
        clubInfoPanel.add(presidentLabel);
        clubInfoPanel.add(stadiumLabel);
        clubInfoPanel.add(domesticChampionsLabel);
        clubInfoPanel.add(continentalChampionsLabel);

        add(clubInfoPanel, BorderLayout.NORTH);

        playerList = new JList<>();
        add(new JScrollPane(playerList), BorderLayout.CENTER);

        if (club != null) {
            updateClub(club);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the panel with the details of the given club
    public void updateClub(Club club) {
        clubNameLabel.setText("Club: " + club.getName());
        yearLabel.setText("Founded: " + club.getyearOfFoundation());
        presidentLabel.setText("President: " + club.getcurrentPresident());
        stadiumLabel.setText("Stadium: " + club.getcurrentStadium());
        domesticChampionsLabel.setText("Domestic Champions: " + club.getdomesticChampions());
        continentalChampionsLabel.setText("Continental Champions: " + club.getcontinentalChampions());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Player player : club.getplayers()) {
            listModel.addElement(player.getname() + " - " + player.getposition() + " - " + player.getnationality());
        }
        playerList.setModel(listModel);
    }
}
