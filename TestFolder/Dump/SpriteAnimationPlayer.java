
package Dump;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteAnimationPlayer extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int SPRITE_SIZE = 64;
    private static final int ANIMATION_DELAY = 100; // milliseconds
    
    private List<BufferedImage> sprites;
    private int currentFrame = 0;
    private Timer animationTimer;
    private boolean isPlaying = false;
    private int scale = 3; // Scale factor for the sprites
    
    private JPanel animationPanel;
    private JButton playPauseButton;
    private JButton resetButton;
    private JSlider speedSlider;
    private JSlider scaleSlider;
    
    public SpriteAnimationPlayer() {
        super("Sprite Animation Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        // Load sprites
        loadSprites();
        
        // Set up animation timer
        animationTimer = new Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame = (currentFrame + 1) % sprites.size();
                animationPanel.repaint();
            }
        });
        
        // Create UI
        setupUI();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void loadSprites() {
        sprites = new ArrayList<>();
        
        // We'll create placeholder sprites since we can't directly load the sprites from the image
        // In a real application, you would load these from files or other sources
        
        // Create 50 mock sprites representing the animation frames
        for (int i = 0; i < 50; i++) {
            BufferedImage sprite = createPlaceholderSprite(i);
            sprites.add(sprite);
        }
    }
    
    private BufferedImage createPlaceholderSprite(int index) {
        // Create a placeholder sprite with a number indicating its position in the sequence
        BufferedImage img = new BufferedImage(SPRITE_SIZE, SPRITE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Draw a simple shape that changes with the index to simulate animation
        g2d.setColor(new Color(50, 50, 50, 200));
        
        // Alternate between different shapes to simulate your animation
        int phase = index % 4;
        switch (phase) {
            case 0:
                g2d.fillOval(10, 10, SPRITE_SIZE - 20, SPRITE_SIZE - 20);
                break;
            case 1:
                g2d.fillOval(15, 15, SPRITE_SIZE - 30, SPRITE_SIZE - 30);
                break;
            case 2:
                g2d.fillOval(20, 20, SPRITE_SIZE - 40, SPRITE_SIZE - 40);
                break;
            case 3:
                g2d.fillOval(15, 15, SPRITE_SIZE - 30, SPRITE_SIZE - 30);
                break;
        }
        
        // Draw frame number
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(String.valueOf(index), SPRITE_SIZE / 2 - 5, SPRITE_SIZE / 2 + 5);
        
        g2d.dispose();
        return img;
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Animation panel
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!sprites.isEmpty()) {
                    BufferedImage sprite = sprites.get(currentFrame);
                    Graphics2D g2d = (Graphics2D) g;
                    
                    // Draw at the center of the panel
                    int x = (getWidth() - SPRITE_SIZE * scale) / 2;
                    int y = (getHeight() - SPRITE_SIZE * scale) / 2;
                    
                    g2d.drawImage(sprite, x, y, SPRITE_SIZE * scale, SPRITE_SIZE * scale, null);
                    
                    // Display frame information
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("Frame: " + (currentFrame + 1) + " / " + sprites.size(), 10, 20);
                }
            }
        };
        animationPanel.setBackground(Color.WHITE);
        add(animationPanel, BorderLayout.CENTER);
        
        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        playPauseButton = new JButton("Play");
        playPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePlayPause();
            }
        });
        
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame = 0;
                animationPanel.repaint();
            }
        });
        
        JLabel speedLabel = new JLabel("Speed: ");
        speedSlider = new JSlider(JSlider.HORIZONTAL, 50, 500, ANIMATION_DELAY);
        speedSlider.setInverted(true); // Lower value = faster animation
        speedSlider.addChangeListener(e -> {
            int value = speedSlider.getValue();
            animationTimer.setDelay(value);
        });
        
        JLabel scaleLabel = new JLabel("Scale: ");
        scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, scale);
        scaleSlider.addChangeListener(e -> {
            scale = scaleSlider.getValue();
            animationPanel.repaint();
        });
        
        controlPanel.add(playPauseButton);
        controlPanel.add(resetButton);
        controlPanel.add(speedLabel);
        controlPanel.add(speedSlider);
        controlPanel.add(scaleLabel);
        controlPanel.add(scaleSlider);
        
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void togglePlayPause() {
        if (isPlaying) {
            animationTimer.stop();
            playPauseButton.setText("Play");
        } else {
            animationTimer.start();
            playPauseButton.setText("Pause");
        }
        isPlaying = !isPlaying;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SpriteAnimationPlayer();
        });
    }
}