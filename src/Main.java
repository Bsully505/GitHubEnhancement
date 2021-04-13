
/**
 * ideas
 *test
 * 1) include a log in feature that will alloo a user to login onto their account
 * 2) create a new page that allows them to grab a file from git
 * 3) ADD NEW buttone for add commit delete
 */

import github.tools.client.GitHubApiClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public boolean DarkLight; //dark = true light = false;
    public static GitHubApiClient Client;
    public Main(){

        int FrameX =500 ;
        int FrameY = 500;
        DarkLight = false;
        //initialize the JFrame
        ImageIcon IMG = new ImageIcon("GitHubLogo.png");

        JFrame frame = new JFrame("GitHub Edhancements");

        JMenuBar bar = new JMenuBar();
        JMenu  File = new JMenu("File");
        bar.add(File);
        JMenuItem DarkMode = new JMenuItem("DarkMode");
        JMenuItem EnterLogin = new JMenuItem("Login");
        File.add(EnterLogin);
        File.add(DarkMode);
        frame.setJMenuBar(bar);
        EnterLogin.addActionListener(e -> {
            LoginPopUp();

        });


        DarkMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DarkLight == false){
                    DarkLight = true;
                    frame.getContentPane().setBackground(Color.BLACK);
                    DarkMode.setText("LightMode");

                }
                else if (DarkLight == true){
                    DarkLight = false;
                    frame.getContentPane().setBackground(Color.WHITE);
                    DarkMode.setText("DarkMode");
                }
            }
        });

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        frame.setLayout(null);
        frame.setSize(FrameX,FrameY);//setting width and height of the frame

        JPanel loginPan = new JPanel(null);
        loginPan.setBounds(FrameX/2-100,FrameY/2-50,200,100);


        JTextField UserNameLogin = new JTextField();
        JTextField PasswordLogin = new JTextField();
        JButton Login = new JButton("Login");
        UserNameLogin.setBounds(15,20, 75,20);






        JButton DarkLightBool = new JButton("TOGGLE DARKMODE");
        DarkLightBool.setBounds(25,25,200,25);

        JButton add  = new JButton("ADD");






        frame.add(DarkLightBool);
        frame.add(add);




        frame.setIconImage(IMG.getImage());



        frame.setVisible(true);
    }
    public static void LoginPopUp(){
        JFrame loginFrame = new JFrame("Login");
        ImageIcon githubicon = new ImageIcon("GitHubLogo.png");
        loginFrame.setSize(400,200);
        loginFrame.setLayout(null);
        //loginFrame.setDefaultCloseOperation(3);
        JLabel Image = new JLabel(githubicon);
        Image.setBounds(0,0,100,100);
        JLabel UsrNameLabel = new JLabel("UserName");
        JLabel PwrdLabel = new JLabel("Password");
        JTextField UserName = new JTextField();
        JTextField Password = new JTextField();
        JButton Login = new JButton("Login");
        Login.setBounds(225,60, 100,40);

        UsrNameLabel.setBounds(10,20,100,50);
        PwrdLabel.setBounds(10,80, 100,50);
        UserName.setBounds(78,38,100,20);
        Password.setBounds(78,96,100,20);

        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String UserNme = UserName.getText();
                String Passwrd = Password.getText();
                GitHubApiClient Client = new GitHubApiClient(UserNme, Passwrd);
                Client.setUser(UserNme);
                setAuth(Client);
                //need to make sure that the user is logged in and is the correct user


            }
        });

        loginFrame.add(Login);
        loginFrame.add(UsrNameLabel);
        loginFrame.add(PwrdLabel);
        loginFrame.add(Image);
        loginFrame.add(UserName);
        loginFrame.add(Password);

        loginFrame.setVisible(true);


    }
    public static void setAuth(GitHubApiClient auth){
        Main.Client = auth;
    }




    public static void main(String[] args){

        Main Run = new Main();
    }
}
