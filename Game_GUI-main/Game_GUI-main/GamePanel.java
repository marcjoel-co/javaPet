import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    private String text;
    private int charIndex = 0;
    private boolean showEnterMessage = false;
    private Timer timer;
    static String [] Cstrings;
    MessageIntro message = new MessageIntro();
    private int messageInt = 0;
    String[] cMsg;
    public GamePanel() {
        Player player = GameManager.getInstance().getPlayer();
        
        
        MessageIntro msg = new MessageIntro();
        System.out.println();
        cMsg = msg.compiled_lines;
        
        initPanel();


        // for (int i = 0; i < 23; i++)
        // {
            startTextDisplay(msg.compiled_lines[0]);    
        // }
        
    }

    private void initPanel() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enterPressed");
        getActionMap().put("enterPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showEnterMessage) {
                    transitionToNextScreen();
                }
            }
        });
    }

    private void startTextDisplay(String arr) {
        timer = new Timer(1000, new ActionListener() {

            
            @Override
            public void actionPerformed(ActionEvent e) {
               
                    if (charIndex < cMsg[messageInt].length()) {
                        charIndex++;
                        repaint();
                    } else {
                        showEnterMessage = true;
                        timer.stop();
                        repaint();
                    }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacterInfo(g);
        if (showEnterMessage) {
            drawEnterMessage(g);
        }
    }

    private void drawCharacterInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        int x = 50;
        int y = 50;
        
            
            for (int j = 0; j < charIndex; j++) {
                char c = cMsg[messageInt].charAt(j);
                g.drawString(String.valueOf(c), x, y);
                x += 15;
                if (x >= getWidth() - 50) 
                {
                    x = 50;
                    y += 30;
                }
            }
            
            drawEnterMessage(g);
            if (charIndex >= cMsg[messageInt].length())
            {
                g.setColor(Color.black);
                g.fillRect(0, 0, getWidth(), getHeight());
                messageInt ++;
                charIndex = 0;
                startTextDisplay(cMsg[messageInt]);
               
            }

        
    }

    private void drawEnterMessage(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.YELLOW);
        String message = "Enter to next scene";
        int x = getWidth() - g.getFontMetrics().stringWidth(message) - 20;
        int y = getHeight() - 30;
        g.drawString(message, x, y);
    }

    private void transitionToNextScreen() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame instanceof Main) {
            ((Main) topFrame).switchToChapter1();
        }
    }

    public void setMessageVal(String message)
    {

    }
}
