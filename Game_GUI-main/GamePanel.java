import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    private String text;
    private int charIndex = 0;
    private boolean showEnterMessage = false;
    private Timer timer;

    public GamePanel() {
        Player player = GameManager.getInstance().getPlayer();
        this.text = "かつて、平和な王国「エルデンリア」は光の力によって繁栄していました。しかし、闇の勢力が復活し、王国に混乱と恐怖が広がり始めます。" 
                    + player.getName() + "は「選ばれし者」として、光と闇のバランスを取り戻すための冒険に出ます。";
        initPanel();
        startTextDisplay();
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

    private void startTextDisplay() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < text.length()) {
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
        for (int i = 0; i < charIndex; i++) {
            char c = text.charAt(i);
            g.drawString(String.valueOf(c), x, y);
            x += 15;
            if (x >= getWidth() - 50) {
                x = 50;
                y += 30;
            }
        }
    }

    private void drawEnterMessage(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.YELLOW);
        String message = "エンターキーを押してください";
        int x = getWidth() - g.getFontMetrics().stringWidth(message) - 20;
        int y = getHeight() - 30;
        g.drawString(message, x, y);
    }

    private void transitionToNextScreen() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame instanceof Main) {
            ((Main) topFrame).switchToWeaponSelection();
        }
    }
}
