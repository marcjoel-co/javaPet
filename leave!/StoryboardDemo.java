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
    private Map<String, String> cardAudioMap; // Map < key, value> basically library from python

    public StoryboardDemo() {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        cardAudioMap = new HashMap<>();

        
        // starting audio

        PlayMusic("src/audiofiles/effects/clocktick.WAV");
        PlayMusic("src/audiofiles/ambience/ambience_low..WAV");

        // Card 1 
        // theres prolly abtter way to do this, but hahahaha
        JPanel card1 = new JPanel();
        card1.setLayout(new GridBagLayout());
        JLabel introduction = new JLabel(" Its 11:43 PM");
        introduction.setFont(new Font("Times New Roman", Font.BOLD, 24));
        introduction.setForeground(Color.WHITE);
        introduction.setHorizontalAlignment(SwingConstants.CENTER);
        introduction.setVerticalAlignment(SwingConstants.CENTER);
        card1.add(introduction);
        card1.setBackground(Color.BLACK);
        card1.setName("CARD1"); // Set the card name
        cardAudioMap.put("CARD1", "src/audiofiles/effects/clocktick.WAV"); // Associate audio with card1 although does not work because im stoopid
        // cardAudioMap.put("CARD1_AMBIENCE", "src/audiofiles/ambience/ambience.WAV"); // Associate audio with card1


        // Card 2
        JPanel youhear = new JPanel();
        youhear.setLayout(new GridBagLayout());
        JLabel youhearLabel = new JLabel("You hear someone knocking");
        youhearLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        youhearLabel.setForeground(Color.WHITE);
        youhearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        youhearLabel.setVerticalAlignment(SwingConstants.CENTER);
        youhear.add(youhearLabel);
        youhear.setBackground(Color.BLACK);

        youhear.add(new JButton(" Open the door?"));
        youhear.setName("youhear"); 
        /**
         * This sets the key of the key balue pair to be youhear, so you ca play the audio later 
         */
        // cardAudioMap.put("youhear", "src/audiofiles/effects/ring.WAV"); // Associate audio with card2

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
        theyare.setName("theyare"); // Set the card name
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
                    }
                }
            }
        });

        this.setLayout(new BorderLayout());
        this.add(container, BorderLayout.CENTER);
        this.add(switchButton, BorderLayout.SOUTH);
    }

    private Component getCurrentCard(JPanel parent) {
        for (Component comp : parent.getComponents()) {
            if (comp.isVisible()) {
                return comp;
            }
        }
        return null; // Add this line
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
    // medjo AI this and hindi pa tested so beware 
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

