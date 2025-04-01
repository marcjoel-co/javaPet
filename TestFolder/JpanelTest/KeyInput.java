package JpanelTest;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

    boolean upPress, downPress, leftPress, rightPress = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPress = true;

        }

        if(code == KeyEvent.VK_A) {
            leftPress = true;

        }

        if(code == KeyEvent.VK_S) {

            downPress = true;
            
        }

        if(code == KeyEvent.VK_D) {
            rightPress = true;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPress = false;

        }

        if(code == KeyEvent.VK_A) {
            leftPress = false;

        }

        if(code == KeyEvent.VK_S) {
            downPress = false;
            
        }

        if(code == KeyEvent.VK_D) {
            rightPress = false;
            
        }

    }
}
