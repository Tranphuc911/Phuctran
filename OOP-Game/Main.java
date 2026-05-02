import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Plants vs Zombies - Full Features");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GameManager());
        
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
}