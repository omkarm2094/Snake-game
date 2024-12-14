import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 75;
    static final int[] X = new int[GAME_UNITS];
    static final int[] Y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addKeyListener(new MKeyAdapter());
        startGame();
    }
    public void startGame(){

        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start(); 

    }

    public void draw(Graphics g){

        if (running) {  

            // for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {

            //         g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            //         g.drawLine(0, i * UNIT_SIZE, i * SCREEN_WIDTH, i * UNIT_SIZE);
            //     }

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {

                if (i == 0) {

                    g.setColor(Color.GREEN);
                    g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
            
                } else {
                    
                    g.setColor(new Color(88, 190, 37));
                    g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);

                }
                
            g.setColor(Color.red);
            g.setFont(new Font("Comic Sans", Font.BOLD, 30));
            g.drawString("Score: " + applesEaten, 20, 25);
            
            if ((applesEaten % 5 == 0) && DELAY > 25 && applesEaten != 0) {
                timer.setDelay(DELAY - 5);
            }
            }
        } else {
            gameOver(g);
        }

    }

    public void newApple(){
 
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

 
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        draw(g);

    }

    public void move(){

        for (int i = bodyParts; i > 0; i--) {
            
            X[i] = X[i - 1];
            Y[i] = Y[i - 1];

        }

        switch(direction){

            case 'U':
                Y[0] =  Y[0] - UNIT_SIZE;
                break;
            case 'D':
                Y[0] =  Y[0] + UNIT_SIZE;
                break;
            case 'L':
                X[0] =  X[0] - UNIT_SIZE;
                break;
            case 'R':
                X[0] =  X[0] + UNIT_SIZE;
                break;
            
        }
    }

    public void checkApple(){

        if ((X[0] == appleX) && (Y[0] == appleY)) {

            bodyParts++;
            applesEaten++;
            newApple();
            
        }
    }

    public void checkCollision(){

        for (int i = bodyParts; i > 0; i--) {

            if ((X[0] == X[i]) && (Y[0] == Y[i])) {
                
                running = false;
            }
        }
        //if touches left
        if (X[0] < 0)
            running = false;
        //if touches right
        if (X[0] > SCREEN_WIDTH)
            running = false;
        //if touches top
        if (Y[0] < 0)
            running = false;
        //if touches bottom
        if (Y[0] > SCREEN_HEIGHT)
            running = false;

        if (!running)
            timer.stop();
    }

    public void gameOver(Graphics g){

        g.setColor(Color.red);
        g.setFont(new Font("Comic Sans", Font.BOLD, 70));
        g.drawString("Game Over", 100, 300);
        g.setFont(new Font("Comic Sans", Font.BOLD, 30));
        g.drawString("Score: " + applesEaten, 100, 230);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {

            move();
            checkApple();
            checkCollision();

        }

        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){

            switch (e.getKeyCode()) {

                case KeyEvent.VK_UP:
                    if (direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                        direction = 'D';
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                        direction = 'R';
                    break;
            }
        }

    }

    public class MKeyAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){

            switch (e.getKeyChar()) {

                case 'w':
                    if (direction != 'D')
                        direction = 'U';
                    break;
                case 's':
                    if (direction != 'U')
                        direction = 'D';
                    break;
                case 'a':
                    if (direction != 'R')
                        direction = 'L';
                    break;
                case 'd':
                    if (direction != 'L')
                        direction = 'R';
                    break;
            }
        }

    }
    
}