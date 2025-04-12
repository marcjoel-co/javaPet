import java.awt.event.MouseEvent; //records and sends the mouse action to other compnents
import java.awt.event.MouseListener; //listens to mouse actions

public class ContrlMod implements MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked!"); //if mouse clicked
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Clicked(Hold!)"); //if mouse hold press

        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Click(release!)"); //if mouse release

        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.err.println("Mouse inside the label"); // if mouse is inside a component

    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.err.println("mouse outside the label"); // if mousr is outside a component

    }
    
}
