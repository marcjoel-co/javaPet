import java.awt.*;
import java.util.Random;
import javax.swing.*;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        setBackgroundImage(imagePath);
    }

    public void setBackgroundImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class Chapter1 extends JPanel {
    private Player player;
    private Weapon weapon;
    private Companion companion;
    private BackgroundPanel backgroundPanel;
    private JTextArea logArea;
    private JPanel actionPanel;
    private JLabel playerStatusLabel;
    private JLabel companionStatusLabel;
    private JFrame topFrame;
    private JLabel enemyImageLabel;
    private Enemy currentEnemy;

    public Chapter1(Player player, Weapon weapon) {
        this.player = player;
        this.weapon = weapon;
        topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
    
        // Main screen (background panel)
        backgroundPanel = new BackgroundPanel("img/intro_background.png");
        add(backgroundPanel, BorderLayout.CENTER);
    
        // Log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        
        // Action selection panel
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
    
        // Status panel
        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        playerStatusLabel = new JLabel();
        companionStatusLabel = new JLabel();
        statusPanel.add(playerStatusLabel);
        statusPanel.add(companionStatusLabel);
        add(statusPanel, BorderLayout.NORTH);
    
        // Panel combining log area and action selection panel
        JPanel logAndActionPanel = new JPanel();
        logAndActionPanel.setLayout(new BorderLayout());
        logAndActionPanel.add(actionPanel, BorderLayout.NORTH);
        logAndActionPanel.add(scrollPane, BorderLayout.CENTER);
        logAndActionPanel.setPreferredSize(new Dimension(400, 150));
        add(logAndActionPanel, BorderLayout.SOUTH);
    
        // Enemy image label
        enemyImageLabel = new JLabel();
        enemyImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
        updateStatus();
    
        createMenuBar();
    
        appendLog("The game will now begin. First, let's choose a companion.");
        selectCompanion();
        startFirstBattle();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu optionsMenu = new JMenu("Options");
        JMenuItem statusItem = new JMenuItem("Status");
        JMenuItem itemsItem = new JMenuItem("Items");
        JMenuItem saveItem = new JMenuItem("Save");

        statusItem.addActionListener(e -> showStatus());
        itemsItem.addActionListener(e -> showItems());
        saveItem.addActionListener(e -> saveGame());

        optionsMenu.add(statusItem);
        optionsMenu.add(itemsItem);
        optionsMenu.add(saveItem);

        menuBar.add(optionsMenu);

        if (topFrame != null) {
            topFrame.setJMenuBar(menuBar);
        }
    }

    private void showStatus() {
        String status = "Player HP: " + player.getHp() + " / MP: " + player.getMp() +
                        "\nAttack Power: " + player.getAttackPower() +
                        "\nExperience: " + player.getExperience();
        if (companion != null) {
            status += "\nCompanion: " + companion.getName() + "\nSkill: " + companion.getSkill();
        }
        appendLog("Status:\n" + status);
    }

    private void showItems() {
        String items = "1. Potion\n2. Elixir\n3. Silver Sword";
        appendLog("Items:\n" + items);
    }

    private void saveGame() {
        appendLog("Game saved.");
    }

    private void selectCompanion() {
        String[] options = {"Warrior Ryan", "Wizard Eris", "Thief Carlos"};
        ImageIcon[] icons = {
            new ImageIcon("img/ryan.png"),
            new ImageIcon("img/eris.png"),
            new ImageIcon("img/carlos.png")
        };
        JPanel panel = new JPanel(new GridLayout(1, 3));

        for (int i = 0; i < options.length; i++) {
            panel.add(new JLabel(options[i], icons[i], SwingConstants.CENTER));
        }

        int choice = JOptionPane.showOptionDialog(this, panel, "Please choose a companion",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                companion = new Companion("Ryan", "Takes on attacks",100);
                break;
            case 1:
                companion = new Companion("Eris", "Deals ice damage to all enemies",100);
                break;
            case 2:
                companion = new Companion("Carlos", "Detects traps",100);
                break;
            default:
                companion = new Companion("Ryan", "Takes on attacks",100);
                break;
        }
        updateStatus();
    }

    private void startFirstBattle() {
        changeBackground("img/battle_background.png");
        setEnemyImage("img/enemy1.png");
        appendLog("A monster appeared! The battle begins.");
    
        // Display battle progress UI
        showBattleUI();
        currentEnemy = new Enemy("Monster", 30, 5);
    }

    private void showBattleUI() {
        actionPanel.removeAll();
        String[] options = {"Attack", "Magic", "Item", "Escape"};
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> handleBattleAction(option));
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void handleBattleAction(String action) {
        switch (action) {
            case "Attack":
                attackEnemy(currentEnemy);
                break;
            case "Magic":
                useMagic(currentEnemy);
                break;
            case "Item":
                useItem();
                break;
            case "Escape":
                escape();
                break;
        }
        if (!checkBattleOutcome(currentEnemy)) {
            enemyAttack(currentEnemy);
            checkBattleOutcome(currentEnemy);
        }
    }

    private void attackEnemy(Enemy enemy) {
        int damage = weapon.getPower();
        enemy.setHp(enemy.getHp() - damage);
        appendLog("Player's attack! Enemy HP: " + enemy.getHp());

        if (companion != null && "Eris".equals(companion.getName())) {
            damage = 10; // Additional damage from Eris's skill
            enemy.setHp(enemy.getHp() - damage);
            appendLog("Eris's skill activated! Enemy HP: " + enemy.getHp());
        }
        if (enemy.getHp() <= 0) {
            appendLog("Defeated the enemy!");
            changeBackground("img/rootSelect_background.png");
            removeEnemyImage();
            selectPath();
        }
    }

    private void useMagic(Enemy enemy) {
        String[] spells = {"Fire", "Ice"};
        actionPanel.removeAll();
        for (String spell : spells) {
            JButton button = new JButton(spell);
            button.addActionListener(e -> {
                int damage = 0;
                if ("Fire".equals(spell)) {
                    damage = 20;
                    player.setMp(player.getMp() - 10);
                } else if ("Ice".equals(spell)) {
                    damage = 30;
                    player.setMp(player.getMp() - 20);
                }
                enemy.setHp(enemy.getHp() - damage);
                appendLog("Used magic! Enemy HP: " + enemy.getHp());
                showBattleUI();
                if (!checkBattleOutcome(enemy)) {
                    enemyAttack(enemy);
                    checkBattleOutcome(enemy);
                }
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void useItem() {
        String[] items = {"Potion", "Elixir"};
        actionPanel.removeAll();
        for (String item : items) {
            JButton button = new JButton(item);
            button.addActionListener(e -> {
                if ("Potion".equals(item)) {
                    player.setHp(player.getHp() + 20);
                    appendLog("Used a potion. Player HP: " + player.getHp());
                } else if ("Elixir".equals(item)) {
                    player.setHp(100);
                    player.setMp(100);
                    appendLog("Used an elixir. Player HP: " + player.getHp() + ", MP: " + player.getMp());
                }
                showBattleUI();
                if (!checkBattleOutcome(currentEnemy)) {
                    enemyAttack(currentEnemy);
                    checkBattleOutcome(currentEnemy);
                }
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void escape() {
        int chance = new Random().nextInt(100);
        if (chance < 50) {
            appendLog("Successfully escaped!");
            changeBackground("img/rootSelect_background.png");
            removeEnemyImage();
            selectPath();
        } else {
            appendLog("Failed to escape!");
            enemyAttack(currentEnemy);
            checkBattleOutcome(currentEnemy);
        }
    }

    private void enemyAttack(Enemy enemy) {
        int damage = enemy.getAttackPower();
        player.setHp(player.getHp() - damage);
        appendLog("Enemy's attack! Player HP: " + player.getHp());
    }

    private boolean checkBattleOutcome(Enemy enemy) {
        if (player.getHp() <= 0) {
            appendLog("Player has been defeated...");
            changeBackground("img/game_over_background.png");
            removeEnemyImage();
            return true;
        } else if (enemy.getHp() <= 0) {
            appendLog("Defeated the enemy!");
            changeBackground("img/rootSelect_background.png");
            removeEnemyImage();
            selectPath();
            return true;
        }
        return false;
    }

    private void selectPath() {
        actionPanel.removeAll();
        String[] options = {"Left Path", "Right Path", "Go Straight"};
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                appendLog("Chose " + option + ".");
                handlePathSelection(option);
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    
    private void handlePathSelection(String path) {
        switch (path) {
            case "Left Path":
                appendLog("Took too long and encountered an enemy.");
                startBattle(new Enemy("Monster", 50, 10));
                break;
            case "Right Path":
                handleRightPath();
                break;
            case "Go Straight":
                appendLog("Gained experience and items!");
                player.setExperience(500);
                player.addItem("Silver Sword");
                updateStatus();
                break;
        }
    }
    
    private void handleRightPath() {
        if (companion != null && "Carlos".equals(companion.getName())) {
            int chance = new Random().nextInt(100);
            if (chance < 70) {
                appendLog("Carlos detected a trap. Opening the treasure chest!");
                player.addItem("Treasure Chest Contents");
                updateStatus();
            } else {
                appendLog("Carlos failed to detect the trap.");
                handleTrap();
            }
        } else {
            handleTrap();
        }
    }
    
    private void handleTrap() {
        String[] options = {"Carefully disarm the trap", "Disarm the trap with magic", "Ignore the trap and open the treasure chest"};
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> {
                appendLog("Attempting to " + option + ".");
                handleTrapOption(option);
            });
            actionPanel.add(button);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    
    private void handleTrapOption(String option) {
        switch (option) {
            case "Carefully disarm the trap":
                int chance = new Random().nextInt(100);
                if (chance < 50) {
                    appendLog("Successfully disarmed the trap. Opening the treasure chest!");
                    openTreasureChest();
                } else {
                    appendLog("The trap activated. The entire party takes 30 damage.");
                    player.setHp(player.getHp() - 30);
                    if (companion != null) {
                        companion.setHp(companion.getHp() - 30);
                    }
                    updateStatus();
                }
                break;
            case "Disarm the trap with magic":
                if (companion != null && "Princess Riana".equals(companion.getName())) {
                    chance = new Random().nextInt(100);
                    if (chance < 50) {
                        appendLog("Successfully disarmed the trap with magic. Opening the treasure chest!");
                        openTreasureChest();
                    } else {
                        appendLog("The magic malfunctioned. The trap activated, but the treasure chest can be opened.");
                        player.setHp(player.getHp() - 10);
                        if (companion != null) {
                            companion.setHp(companion.getHp() - 10);
                        }
                        player.addItem("Magic Ring");
                        player.addItem("Elixir");
                        player.addItem("Silver Key");
                        updateStatus();
                    }
                } else {
                    appendLog("Cannot disarm the trap with magic because Princess Riana is not present.");
                }
                break;
            case "Ignore the trap and open the treasure chest":
                appendLog("The trap activated. Took 30 damage, but opening the treasure chest.");
                player.setHp(player.getHp() - 30);
                if (companion != null) {
                    companion.setHp(companion.getHp() - 30);
                }
                openTreasureChest();
                break;
        }
    }
    
    private void openTreasureChest() {
        player.addItem("Magic Ring");
        player.addItem("Elixir");
        player.addItem("Silver Key");
        appendLog("Opened the treasure chest! Gained the following items:\n- Magic Ring\n- Elixir\n- Silver Key");
        updateStatus();
    }
    
    private void startBattle(Enemy enemy) {
        currentEnemy = enemy;
        changeBackground("img/battle_background.png");
        setEnemyImage("img/enemy.png");
        appendLog(enemy.getName() + " appeared! The battle begins.");
        showBattleUI();
    }
    

    private void appendLog(String message) {
        logArea.append(message + "\n");
    }

    private void updateStatus() {
        playerStatusLabel.setText("Player - HP: " + player.getHp() + " MP: " + player.getMp());
        if (companion != null) {
            companionStatusLabel.setText("Companion - " + companion.getName() + " Skill: " + companion.getSkill());
        }
    }

    private void changeBackground(String imagePath) {
        backgroundPanel.setBackgroundImage(imagePath);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }

    private void setEnemyImage(String imagePath) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
    
        int newWidth = 200; 
        int newHeight = -1;
    
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
    
        enemyImageLabel.setIcon(resizedIcon);
    
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1; 
        centerPanel.add(enemyImageLabel, gbc);
    
        // Add an empty label at the top
        JLabel emptyLabel = new JLabel();
        gbc.gridy = 0; 
        centerPanel.add(emptyLabel, gbc);
    
        backgroundPanel.removeAll();
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }

    private void removeEnemyImage() {
        backgroundPanel.removeAll();
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }
}
