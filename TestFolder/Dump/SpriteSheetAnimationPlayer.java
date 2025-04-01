package Dump;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheetAnimationPlayer extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int ANIMATION_DELAY = 100; // milliseconds
    
    // Sprite sheet configuration for 16x16 sprites
    private BufferedImage spriteSheet;
    private int spriteWidth = 20;  // Width of each sprite in pixels
    private int spriteHeight = 16; // Height of each sprite in pixels
    private int numRows = 20;      // Number of rows in the sprite sheet
    private int numCols =10;      // Number of columns in the sprite sheet
    private int totalFrames = 0;   // Total number of frames (calculated from sprite sheet)
    
    private int currentFrame = 0;
    private Timer animationTimer;
    private boolean isPlaying = false;
    private int scale = 10; // Default scale factor (increased for better visibility of small sprites)
    
    private JPanel animationPanel;
    private JButton playPauseButton;
    private JButton resetButton;
    private JSlider speedSlider;
    private JSlider scaleSlider;
    private JButton loadButton;
    private JButton prevFrameButton;
    private JButton nextFrameButton;
    private JLabel frameCountLabel;
    
    public SpriteSheetAnimationPlayer() {
        super("16x16 Sprite Animation Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        // Create placeholder sprite sheet
        createPlaceholderSpriteSheet();
        
        // Set up animation timer
        animationTimer = new Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalFrames > 0) {
                    currentFrame = (currentFrame + 1) % totalFrames;
                    updateFrameCountLabel();
                    animationPanel.repaint();
                }
            }
        });
        
        // Create UI
        setupUI();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void createPlaceholderSpriteSheet() {
        // Create a placeholder sprite sheet for demonstration
        spriteSheet = new BufferedImage(spriteWidth * numCols, spriteHeight * numRows, 
                                      BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = spriteSheet.createGraphics();
        
        // Fill with transparent background
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, spriteSheet.getWidth(), spriteSheet.getHeight());
        g2d.setComposite(AlphaComposite.SrcOver);
        
        // Draw grid lines (very light for visibility)
        g2d.setColor(new Color(200, 200, 200, 100));
        for (int i = 0; i <= numRows; i++) {
            g2d.drawLine(0, i * spriteHeight, spriteWidth * numCols, i * spriteHeight);
        }
        for (int i = 0; i <= numCols; i++) {
            g2d.drawLine(i * spriteWidth, 0, i * spriteWidth, spriteHeight * numRows);
        }
        
        // Draw simple animated sprites suitable for 16x16 dimensions
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int frameNum = row * numCols + col;
                int x = col * spriteWidth;
                int y = row * spriteHeight;
                
                // Animation pattern based on frame number
                int animPhase = frameNum % 8;
                
                // Draw a simple creature-like sprite that moves/animates
                g2d.setColor(new Color(50, 50, 50));
                
                // Body
                g2d.fillRect(x + 4, y + 6, 8, 6);
                
                // Head
                g2d.fillOval(x + 3, y + 3, 10, 8);
                
                // Eyes
                g2d.setColor(Color.WHITE);
                g2d.fillRect(x + 5, y + 5, 2, 2);
                g2d.fillRect(x + 9, y + 5, 2, 2);
                
                // Legs (with animation)
                g2d.setColor(new Color(50, 50, 50));
                switch (animPhase) {
                    case 0:
                        // Legs position 1
                        g2d.drawLine(x + 5, y + 12, x + 3, y + 14);
                        g2d.drawLine(x + 11, y + 12, x + 13, y + 14);
                        break;
                    case 1:
                        // Legs position 2
                        g2d.drawLine(x + 5, y + 12, x + 4, y + 15);
                        g2d.drawLine(x + 11, y + 12, x + 12, y + 15);
                        break;
                    case 2:
                        // Legs position 3
                        g2d.drawLine(x + 5, y + 12, x + 5, y + 15);
                        g2d.drawLine(x + 11, y + 12, x + 11, y + 15);
                        break;
                    case 3:
                        // Legs position 4
                        g2d.drawLine(x + 5, y + 12, x + 6, y + 15);
                        g2d.drawLine(x + 11, y + 12, x + 10, y + 15);
                        break;
                    case 4:
                        // Legs position 5
                        g2d.drawLine(x + 5, y + 12, x + 7, y + 14);
                        g2d.drawLine(x + 11, y + 12, x + 9, y + 14);
                        break;
                    case 5:
                        // Legs position 6
                        g2d.drawLine(x + 5, y + 12, x + 6, y + 15);
                        g2d.drawLine(x + 11, y + 12, x + 10, y + 15);
                        break;
                    case 6:
                        // Legs position 7
                        g2d.drawLine(x + 5, y + 12, x + 5, y + 15);
                        g2d.drawLine(x + 11, y + 12, x + 11, y + 15);
                        break;
                    case 7:
                        // Legs position 8
                        g2d.drawLine(x + 5, y + 12, x + 4, y + 15);
                        g2d.drawLine(x + 11, y + 12, x + 12, y + 15);
                        break;
                }
            }
        }
        
        g2d.dispose();
        totalFrames = numRows * numCols;
    }
    
    private void loadSpriteSheet(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                spriteSheet = ImageIO.read(file);
                calculateTotalFrames();
                currentFrame = 0;
                updateFrameCountLabel();
                animationPanel.repaint();
                JOptionPane.showMessageDialog(this, "Sprite sheet loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "File not found: " + path, 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading sprite sheet: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void calculateTotalFrames() {
        // Calculate how many complete sprites fit in the sprite sheet
        int sheetWidth = spriteSheet.getWidth();
        int sheetHeight = spriteSheet.getHeight();
        
        numCols = sheetWidth / spriteWidth;
        numRows = sheetHeight / spriteHeight;
        totalFrames = numRows * numCols;
    }
    
    private void updateFrameCountLabel() {
        if (frameCountLabel != null) {
            frameCountLabel.setText("Frame: " + (currentFrame + 1) + " / " + totalFrames);
        }
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Animation panel with grid background for better visibility of small sprites
        animationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Draw a checkered background
                int cellSize = 20;
                for (int row = 0; row < getHeight() / cellSize + 1; row++) {
                    for (int col = 0; col < getWidth() / cellSize + 1; col++) {
                        if ((row + col) % 2 == 0) {
                            g2d.setColor(new Color(240, 240, 240));
                        } else {
                            g2d.setColor(new Color(220, 220, 220));
                        }
                        g2d.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                    }
                }
                
                if (spriteSheet != null && totalFrames > 0) {
                    // Calculate the row and column for the current frame
                    int row = currentFrame / numCols;
                    int col = currentFrame % numCols;
                    
                    // Calculate the source coordinates in the sprite sheet
                    int sx1 = col * spriteWidth;
                    int sy1 = row * spriteHeight;
                    int sx2 = sx1 + spriteWidth;
                    int sy2 = sy1 + spriteHeight;
                    
                    // Calculate the destination coordinates (centered in the panel)
                    int scaledWidth = spriteWidth * scale;
                    int scaledHeight = spriteHeight * scale;
                    int dx1 = (getWidth() - scaledWidth) / 2;
                    int dy1 = (getHeight() - scaledHeight) / 2;
                    int dx2 = dx1 + scaledWidth;
                    int dy2 = dy1 + scaledHeight;
                    
                    // Enable high-quality scaling for small pixels
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                         RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                    
                    // Draw the current sprite
                    g2d.drawImage(spriteSheet, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
                    
                    // Draw a border around the sprite for visibility
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(dx1 - 1, dy1 - 1, scaledWidth + 1, scaledHeight + 1);
                    
                    // Display pixel grid for the sprite (when scale is large enough)
                    if (scale >= 8) {
                        g2d.setColor(new Color(200, 200, 200, 100));
                        // Vertical lines
                        for (int i = 1; i < spriteWidth; i++) {
                            int x = dx1 + i * scale;
                            g2d.drawLine(x, dy1, x, dy2 - 1);
                        }
                        // Horizontal lines
                        for (int i = 1; i < spriteHeight; i++) {
                            int y = dy1 + i * scale;
                            g2d.drawLine(dx1, y, dx2 - 1, y);
                        }
                    }
                }
            }
        };
        animationPanel.setBackground(Color.WHITE);
        add(animationPanel, BorderLayout.CENTER);
        
        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1, 5, 5));
        
        // Animation controls
        JPanel animControls = new JPanel(new FlowLayout());
        
        // Add previous and next frame buttons
        prevFrameButton = new JButton("◀");
        prevFrameButton.addActionListener(e -> {
            if (totalFrames > 0) {
                currentFrame = (currentFrame - 1 + totalFrames) % totalFrames;
                updateFrameCountLabel();
                animationPanel.repaint();
            }
        });
        
        playPauseButton = new JButton("▶");
        playPauseButton.addActionListener(e -> togglePlayPause());
        
        nextFrameButton = new JButton("▶");
        nextFrameButton.addActionListener(e -> {
            if (totalFrames > 0) {
                currentFrame = (currentFrame + 1) % totalFrames;
                updateFrameCountLabel();
                animationPanel.repaint();
            }
        });
        
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            currentFrame = 0;
            updateFrameCountLabel();
            animationPanel.repaint();
        });
        
        frameCountLabel = new JLabel("Frame: 1 / " + totalFrames);
        
        animControls.add(prevFrameButton);
        animControls.add(playPauseButton);
        animControls.add(nextFrameButton);
        animControls.add(resetButton);
        animControls.add(frameCountLabel);
        
        // Settings controls
        JPanel settingsControls = new JPanel(new FlowLayout());
        
        JLabel speedLabel = new JLabel("Speed: ");
        speedSlider = new JSlider(JSlider.HORIZONTAL, 10, 500, ANIMATION_DELAY);
        speedSlider.setInverted(true); // Lower value = faster animation
        speedSlider.addChangeListener(e -> {
            int value = speedSlider.getValue();
            animationTimer.setDelay(value);
        });
        
        JLabel scaleLabel = new JLabel("Scale: ");
        scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, scale);
        scaleSlider.addChangeListener(e -> {
            scale = scaleSlider.getValue();
            animationPanel.repaint();
        });
        
        loadButton = new JButton("Load Sprite Sheet");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadSpriteSheet(selectedFile.getAbsolutePath());
            }
        });
        
        JButton createPlaceholderButton = new JButton("Create Example");
        createPlaceholderButton.addActionListener(e -> {
            createPlaceholderSpriteSheet();
            updateFrameCountLabel();
            animationPanel.repaint();
        });
        
        settingsControls.add(speedLabel);
        settingsControls.add(speedSlider);
        settingsControls.add(scaleLabel);
        settingsControls.add(scaleSlider);
        settingsControls.add(loadButton);
        settingsControls.add(createPlaceholderButton);
        
        controlPanel.add(animControls);
        controlPanel.add(settingsControls);
        
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void togglePlayPause() {
        if (isPlaying) {
            animationTimer.stop();
            playPauseButton.setText("▶");
        } else {
            animationTimer.start();
            playPauseButton.setText("⏸");
        }
        isPlaying = !isPlaying;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Use system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SpriteSheetAnimationPlayer();
        });
    }
}