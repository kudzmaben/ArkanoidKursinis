package ArkanoidKursinis;
import javax.swing.JFrame;

public class StartGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        DrawGameplay gamePlay = new DrawGameplay();
        frame.setBounds(0,0, 700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(gamePlay);
        frame.setVisible(true);
    }
}