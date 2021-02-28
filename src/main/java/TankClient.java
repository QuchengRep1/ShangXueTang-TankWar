import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    int x = 50 ,y = 50;
    Image offScreenImage = null; //定义offScreenImage为基板图片，鉴于repaint方法为update+paint方法的组合，
                                 // 所以修改update为每次写完基版才会刷新
    public void launchFrame() {
        setLocation(400,300);
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setTitle("TankWar");
        setBackground(Color.GREEN);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new KeyMonitor());

        setResizable(false);
        setVisible(true);

        PaintThread paintThread = new PaintThread();
        new Thread(paintThread).start();
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,30,30);
        g.setColor(c);
        //y += 5;
    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) offScreenImage = createImage(800, 600);
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    private  class PaintThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                repaint();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("OK");
            int key =  e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_RIGHT :  x += 5; break;
                case KeyEvent.VK_LEFT:    x -= 5; break;
                case KeyEvent.VK_UP :     y -= 5; break;
                case KeyEvent.VK_DOWN :   y += 5; break;

            }
        }
    }


    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
    }
}


