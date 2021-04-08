//mport com.sun.webkit.ThemeClient;
/**
 * ideas
 *
 * 1) include a log in feature that will alloo a user to login onto their account
 * 2) create a new page that allows them to grab a file from git
 * 3) ADD NEW buttone for add commit delete
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public boolean DarkLight; //dark = true light = false;
    public Main(){//ini
        DarkLight = false;
        //initialize the JFrame
        ImageIcon IMG = new ImageIcon("GitHubLogo.png");

        JFrame frame = new JFrame("GitHub Edhancements");

        JPanel loginPan = new JPanel(null);
        loginPan.setBounds(200,200,200,100);

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        frame.setLayout(null);
        frame.setSize(500,500);//setting width and height of the frame



        JTextField UserNameLogin = new JTextField();
        JTextField PasswordLogin = new JTextField();

        loginPan.setBackground(Color.green);
        loginPan.setSize(200,100);





        JButton DarkLightBool = new JButton("TOGGLE DARKMODE");
        DarkLightBool.setBounds(25,25,100,25);

        JButton add  = new JButton("ADD");





        frame.add(DarkLightBool);
        frame.add(add);

        frame.add(loginPan);


        frame.setIconImage(IMG.getImage());
        //frame.add(DarkLightBool);
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ee) {
            // handle exception
        }
        DarkLightBool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DarkLight == false){
                    DarkLight = true;
                    System.out.println("set to true/ dark theme");
                    //set theme to dark
                    frame.setForeground(Color.BLACK);
                    frame.update(frame.getGraphics());
                    frame.getContentPane().setBackground(Color.BLACK);
                    SwingUtilities.updateComponentTreeUI(frame);
                    //frame.pack();


                }
                else if (DarkLight == true){
                    DarkLight = false;
                    frame.getContentPane().setBackground(Color.WHITE);

                    System.out.println("set to false/ light theme");
                }
            }
        });
        frame.setVisible(true);
    }


    public static void main(String[] args){
        Main Run = new Main();
    }
}
