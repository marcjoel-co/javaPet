import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WeaponSelectionScreen extends JPanel {
    private JFrame frame;

    public WeaponSelectionScreen(JFrame frame) {
        this.frame = frame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Selectionweapon", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        JPanel weaponPanel = new JPanel();
        weaponPanel.setLayout(new GridLayout(1, 3)); 

        weaponPanel.add(createWeaponPanel("Sword bu hera", "img/sword.png", 15));
        weaponPanel.add(createWeaponPanel("asewesometstick", "img/stick.png", 20));
        weaponPanel.add(createWeaponPanel("minecraft  bow", "img/bow.png", 10));

        //add(weaponPanel, BorderLayout.CENTER);
    }

    private JPanel createWeaponPanel(String weaponName, String imagePath, int weaponPower) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel(weaponName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(nameLabel, BorderLayout.NORTH);

        ImageIcon weaponIcon = loadAndResizeImage(imagePath, 200, 200); 
        JLabel imageLabel = new JLabel(weaponIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectWeapon(weaponName, weaponPower);
            }
        });
        panel.add(imageLabel, BorderLayout.CENTER);

        return panel;
    }

    private ImageIcon loadAndResizeImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void selectWeapon(String weaponName, int weaponPower) {
        Player player = GameManager.getInstance().getPlayer();
        player.setWeapon(weaponName);
        Weapon weapon = new Weapon(weaponName, weaponPower);
        GameManager.getInstance().setWeapon(weapon);

        // 次のチャプターに遷移
        if (frame instanceof Main) {
            ((Main) frame).switchToChapter1();
        }
    }
}
