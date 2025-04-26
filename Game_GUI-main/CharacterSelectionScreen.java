import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelectionScreen extends JPanel {

    private JFrame frame;
    private JTextField nameField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;

    public CharacterSelectionScreen(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Character Selection", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        formPanel.add(genderLabel, gbc);

        gbc.gridx = 1;
        JPanel genderPanel = new JPanel();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        formPanel.add(genderPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Start game button
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Next");
        startButton.setFont(new Font("Serif", Font.PLAIN, 24));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startGame() {
        String name = nameField.getText();
        String gender = maleButton.isSelected() ? "Male" : "Female";
        
        if (name.isEmpty() || (!maleButton.isSelected() && !femaleButton.isSelected())) {
            JOptionPane.showMessageDialog(this, "Please enter a name and select a gender.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Player player = new Player(name, 100, 50, 20);
        player.setGender(gender);
        GameManager.getInstance().setPlayer(player);

        frame.getContentPane().removeAll();
        GamePanel GamePanel = new GamePanel();
        frame.add(GamePanel);
        frame.revalidate();
        frame.repaint();
    }
}
