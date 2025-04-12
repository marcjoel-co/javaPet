import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelTypes extends JFrame {

    public BufferedImage backgroundimage;

    public PanelTypes() {
        this.setTitle("square");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420, 420);
    }
}