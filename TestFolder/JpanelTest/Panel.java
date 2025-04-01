package JpanelTest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


public class Panel extends JPanel implements Runnable{
    
    final int ogTileSize = 16;
    final int scale = 3;

    final int tileSize = ogTileSize * scale;
    final int screenCol = 16;
    final int screenRow = 12;

    final int screenWidth = tileSize * screenCol;
    final int screenHeight = tileSize * screenRow;

    int fps = 60;

    KeyInput keyInput = new KeyInput();
    Thread testThread;

    int pX = 100;
    int pY = 100;
    int plSp = 5;

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLUE);
        this.addKeyListener(keyInput);
        this.setFocusable(true);

    }

    public void startThread() {

        testThread = new Thread(this);
        testThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps;
        double nextDrawtime = System.nanoTime() + drawInterval;

        while(testThread != null) {

            update();
            repaint();
            
            try {
                double remainTime = nextDrawtime - System.nanoTime();
                remainTime = remainTime / 1000000000;

                if(remainTime < 0) {
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime);
                nextDrawtime += drawInterval;
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {

        if(keyInput.upPress == true) {
            pY -= plSp;
        }

        else if(keyInput.downPress == true) {
            pY += plSp;
        }

        else if(keyInput.leftPress == true) {
            pX -= plSp;
        }
        else if(keyInput.rightPress == true) {
            pX += plSp;
        }


    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D conv2D = (Graphics2D)g;

        conv2D.setColor(Color.BLACK);
        conv2D.fillRect(pX, pY, tileSize, tileSize);
        conv2D.dispose();
    }
}
