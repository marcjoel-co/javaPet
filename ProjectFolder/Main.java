import javax.swing.JFrame; // header for creating a frame for the window
import javax.swing.ImageIcon; //for adding icons to the frame
import java.awt.Image; // preriqusite for the ImageIcon

//main  entry point
class Main {
    public static void main(String[] args) {
        JFrame gameWindow = new JFrame(); // instance of the game window
        PanelMod panel = new PanelMod(); // instance of the panel(contents of the window)

        ImageIcon icon = new ImageIcon("Icon.png"); //instance of a ImageIcon (pre-loads an image to code)
        Image windIcon = icon.getImage(); //an instance of an image(represents graphical images)

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setIconImage(windIcon);
        gameWindow.setResizable(false);
        gameWindow.setTitle("LEAVE: A pick your Story Adventure Game!");

        gameWindow.add(panel);
        gameWindow.pack(); //keyword that syncs the height and width of the frame and panel


        gameWindow.setLocationRelativeTo(null); //sets frame position to center
        gameWindow.setVisible(true);
        
    }
}
