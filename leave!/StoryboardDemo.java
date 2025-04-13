
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import javax.swing.*;

public class StoryboardDemo extends JPanel {

    private CardLayout cardLayout;
    private JPanel container;
    private Clip currentClip;
    private Map<String, String> cardAudioMap;
    private JFrame parentFrame;

    public StoryboardDemo(JFrame frame) {
        this.parentFrame = frame;
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        cardAudioMap = new HashMap<>();

        // Starting audio
        PlayMusic("src/audiofiles/effects/clocktick.WAV");
        PlayMusic("src/audiofiles/ambience/ambience_low..WAV");

        // Card 1
        JPanel card1 = new JPanel();
        card1.setLayout(new GridBagLayout());
        JLabel introduction = new JLabel(" Its 11:43 PM");
        introduction.setFont(new Font("Times New Roman", Font.BOLD, 24));
        introduction.setForeground(Color.WHITE);
        introduction.setHorizontalAlignment(SwingConstants.CENTER);
        introduction.setVerticalAlignment(SwingConstants.CENTER);
        card1.add(introduction);
        card1.setBackground(Color.BLACK);
        card1.setName("CARD1");
        cardAudioMap.put("CARD1", "src/audiofiles/effects/clocktick.WAV");

        // Card 2
        JPanel youhear = new JPanel();
        youhear.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        JLabel youhearLabel = new JLabel("You hear someone knocking");
        youhearLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        youhearLabel.setForeground(Color.WHITE);
        youhearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        youhearLabel.setVerticalAlignment(SwingConstants.CENTER);
        youhear.add(youhearLabel, gbc);
        youhear.setBackground(Color.BLACK);
        
        JButton open = new JButton("Open the door?");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked!");
                showBlackPanel();
            }
        });
        youhear.add(open, gbc);
        youhear.setName("youhear");
        
        // Card 3
        JPanel theyare = new JPanel();
        theyare.setLayout(new GridBagLayout());
        JLabel theyareLabel = new JLabel("They are coming");
        theyareLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        theyareLabel.setForeground(Color.WHITE);
        theyareLabel.setHorizontalAlignment(SwingConstants.CENTER);
        theyareLabel.setVerticalAlignment(SwingConstants.CENTER);
        theyare.add(theyareLabel);
        theyare.setBackground(Color.BLACK);
        theyare.setName("theyare");
        cardAudioMap.put("theyare", "src/audiofiles/effects/ring.WAV");

        container.add(card1, "CARD1");
        container.add(youhear, "youhear");
        container.add(theyare, "theyare");

        JButton switchButton = new JButton("next");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the next card when clicked
                cardLayout.next(container);

                // Get the current card name
                Component currentCard = getCurrentCard(container);
                if (currentCard != null) {
                    String cardName = currentCard.getName();

                    // Play audio associated with the new card
                    if (cardName != null && cardAudioMap.containsKey(cardName)) {
                        stopCurrentClip();
                        currentClip = PlayMusic(cardAudioMap.get(cardName));
                    }

                    // Check if it's the third card ("theyare")
                    if ("theyare".equals(cardName)) {
                        // Create a timer to show the black panel after a brief delay
                        Timer timer = new Timer(2000, event -> {
                            showBlackPanel();
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
                }
            }
        });

        // Add a KeyListener to handle space key press
        this.setFocusable(true);
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    // Switch to the next card
                    cardLayout.next(container);

                    // Get the current card name
                    Component currentCard = getCurrentCard(container);
                    if (currentCard != null) {
                        String cardName = currentCard.getName();

                        // Play audio associated with the new card
                        if (cardName != null && cardAudioMap.containsKey(cardName)) {
                            stopCurrentClip();
                            currentClip = PlayMusic(cardAudioMap.get(cardName));
                        }

                        // Check if it's the third card ("theyare")
                        if ("theyare".equals(cardName)) {
                            // Create a timer to show the black panel after a brief delay
                            Timer timer = new Timer(2000, event -> {
                                showBlackPanel();
                            });
                            timer.setRepeats(false);
                            timer.start();
                        }
                    }
                }
            }
        });

        this.setLayout(new BorderLayout());
        this.add(container, BorderLayout.CENTER);
        this.add(switchButton, BorderLayout.SOUTH);
    }

    // Method to show black panel and handle transition
    private void showBlackPanel() {
        stopCurrentClip();
        
        // Create a new JFrame for the pure black panel
        JFrame blackFrame = new JFrame("Darkness");
        blackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a pure black panel
        JPanel blackPanel = new JPanel();
        blackPanel.setBackground(Color.BLACK);
        
        // Optional: Add a hidden message that appears after a delay
        JLabel hiddenMessage = new JLabel("There is no escape");
        hiddenMessage.setForeground(Color.RED);
        hiddenMessage.setFont(new Font("Serif", Font.BOLD, 36));
        hiddenMessage.setVisible(false);
        blackPanel.setLayout(new GridBagLayout());
        blackPanel.add(hiddenMessage);
        
        // Add the black panel to the frame
        blackFrame.add(blackPanel);
        
        // Set frame to full screen
        blackFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        blackFrame.setUndecorated(true); // Remove window decorations for true full screen
        
        // Make the frame visible
        blackFrame.setVisible(true);
        
        // Play a scary sound if available
        Clip scarySound = PlayMusic("src/audiofiles/effects/ring.WAV");
        
        // Timer to show hidden message after a delay
        Timer messageTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hiddenMessage.setVisible(true);
            }
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
        
        // Close the original frame after a short delay
        Timer closeTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentFrame != null) {
                    parentFrame.dispose();
                }
            }
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
        
        // Optional: Add a key listener to close the black panel on any key press
        blackPanel.setFocusable(true);
        blackPanel.requestFocus();
        blackPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.exit(0);
            }
        });
    }

    private Component getCurrentCard(JPanel parent) {
        for (Component comp : parent.getComponents()) {
            if (comp.isVisible()) {
                return comp;
            }
        }
        return null;
    }

    private void stopCurrentClip() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
    }

    public static Clip PlayMusic(String location) {
        return PlayMusic(location, false, 0); // Call the more general method with defaults
    }

    public static Clip PlayMusic(String location, boolean loop, int delay) {
        Clip clip = null;
        try {
            // Try to load the audio file as a resource
            URL url = StoryboardDemo.class.getClassLoader().getResource(location);
            if (url == null) {
                // If not found as resource, try as a file
                File audioFile = new File(location);
                if (!audioFile.exists()) {
                    System.err.println("Audio file not found: " + location);
                    JOptionPane.showMessageDialog(null, "Audio file not found: " + location,
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                url = audioFile.toURI().toURL();
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Create timer for delayed playback
            final Clip finalClip = clip; // Create a final copy for the Timer
            Timer timer = new Timer(delay, e -> {
                if (loop) {
                    finalClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop indefinitely
                } else {
                    finalClip.start(); // Regular playback
                }
            });
            timer.setRepeats(false); // One-time trigger
            timer.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing audio: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error playing audio: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return clip;
    }
}