package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.com.footballtournament.domain.model.Player;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

// the PlayerDialog class is a dialog for entering player details
public class PlayerDialog extends JDialog {
    private JTextField nameField;
    private JTextField positionField;
    private JTextField nationalityField;
    private JTextField ageField;
    private Player player;

    // EFFECTS: constructs a new PlayerDialog and initializes components
    public PlayerDialog(JFrame parent) {
        super(parent, "Add Player", true);
        setupDialog(parent);
        createFormFields();
        createAddButton();
    }

    // MODIFIES: this
    // EFFECTS: sets up the dialog properties
    private void setupDialog(JFrame parent) {
        setLayout(new GridLayout(5, 2));
        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    // MODIFIES: this
    // EFFECTS: creates the form fields and adds them to the dialog
    private void createFormFields() {
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Position:"));
        positionField = new JTextField();
        add(positionField);

        add(new JLabel("Nationality: "));
        nationalityField = new JTextField();
        add(nationalityField);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);
    }

    // MODIFIES: this
    // EFFECTS: creates the add button and adds it to the dialog
    private void createAddButton() {
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String position = positionField.getText();
                String nationality = nationalityField.getText();
                int age = Integer.parseInt(ageField.getText());
                player = new Player(name, position, nationality, age);
                setVisible(false);
            }
        });
        add(addButton);
    }

    // EFFECTS: returns the player created by the dialog
    public Player getPlayer() {
        return player;
    }
}
