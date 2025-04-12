import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics; // a header that allows to draw shapes to a component(panel)
import java.awt.Dimension; //a header that unites width and frame values to one variable
import java.awt.Color; //defines basic colors

public class PanelMod extends JPanel{ // a class that is a child class to JPanel

    JLabel label = new JLabel(); //instance of a JPanel
    ContrlMod ctrl = new ContrlMod(); //an instance of the mouselistener class

    int tileSize = 16; // size of the character
    int scale = 5; //scaling

    int screenRows = 48;
    int screenCols = 30;

    int screenHeight = (tileSize * screenRows);
    int screenWidth = (tileSize * screenCols);

    PanelMod() { //A constructot for the class

        this.setPreferredSize(new Dimension(screenHeight, screenWidth));
        this.setBackground(Color.LIGHT_GRAY);

        //sets JLabel Values
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setPreferredSize(new Dimension(100, 100));
        label.addMouseListener(ctrl);
        
        this.add(label);
        this.setFocusable(true);
    }

    public void paintComponent(Graphics g) { //A method that draws a shape to the panel itself
        super.paintComponent(g); //defining paintcomponent to JPanel

    }

    
}
