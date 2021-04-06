import javax.swing.*;
public class Main {

    public Main(){//ini
        JFrame frame = new JFrame("GitHub Edhancements");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(300,300);//setting width and height of the frame
        JButton DarkLightBool = new JButton("TOGGLE DARKMODE");
        frame.setVisible(true);
    }
    public static void main(String[] args){
        Main Run = new Main();
    }
}
