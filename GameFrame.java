import javax.swing.JFrame;

public class GameFrame extends JFrame{
    
    GameFrame(){
        
        GamePanel panel = new GamePanel();
        this.add(panel);

        this.setTitle("Saap Game.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}