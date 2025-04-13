import java.awt.image.BufferedImage;
import javax.swing.*;

public class PanelTypes extends JFrame {

    public BufferedImage backgroundimage;

    public PanelTypes() {
        this.setTitle("Leave!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(960, 420);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setscene(BufferedImage backgroundimage) {
        this.backgroundimage = backgroundimage;
    }
}