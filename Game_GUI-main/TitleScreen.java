import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JPanel {
    private JFrame frame;

    public TitleScreen(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("光と闇の冒険者達", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        add(titleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.PLAIN, 24));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCharacterSelection();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.PLAIN, 24));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startCharacterSelection() {
        frame.getContentPane().removeAll();
        CharacterSelectionScreen characterSelectionScreen = new CharacterSelectionScreen(frame);
        frame.add(characterSelectionScreen);
        frame.revalidate();
        frame.repaint();
    }
}
