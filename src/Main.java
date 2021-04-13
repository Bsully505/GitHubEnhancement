
/**
 *Author Bryan Sullivan
 * ideas / todo list
 * fix error for having a path that has a space in it
 * Implement the bring up of th website when clicked in the panal
 */

import github.tools.client.GitHubApiClient;
import github.tools.client.QueryParams;
import github.tools.responseObjects.GetRepoFileResponse;
import github.tools.responseObjects.ListBranchesInRepoResponse;
import github.tools.responseObjects.ListReposResponse;
import github.tools.responseObjects.RepoFileContent;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {
    public boolean DarkLight; //dark = true light = false;
    public boolean LoggedIn;
    public static GitHubApiClient Client;
    public static ListReposResponse rep;
    public static List Repos;
    public static List Branches;
    public static List Classes;
    public static JComboBox<String> RepoChoice;
    public static JComboBox<String> BranchChoice;
    public static JComboBox<String> ClassChoice;
    public static JButton OpenFile;
    public static String Owner;
    public static String RepoN;
    public static String Branch;
    public static ArrayList<RepoFileContent> classes;
    public static GetRepoFileResponse FileResponce;
    public static int Rx = 10, Ry= 40, Rw= 200, Rh= 40;// initialize the dimensions
    public static Color DarkColor = Color.DARK_GRAY;
    public static Color Orig;
    public static JTextField Instruc;
    public Main() throws URISyntaxException {
        int FrameX =700, FrameY =400;

        OpenFile = new JButton("Open Selected File");
        OpenFile.setBounds(220,200, 200,50);
        LoggedIn = false;


        DarkLight = false;
        Branches = new List();
        Classes = new List();
        //initialize the JFrame
        ImageIcon IMG = new ImageIcon("GitHubLogo.png");//the image is to large so the image does not show
        JFrame frame = new JFrame("GitHub Edhancements");
        Orig = frame.getBackground();

        JLabel RepoInstrc = new JLabel("Select Repository");
        JLabel BranchInstrc = new JLabel("Select Branch");
        JLabel FileInstruct = new JLabel("Select File");


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
        RepoChoice = new JComboBox<String>();
        RepoChoice.setBounds(Rx,Ry,Rw,Rh);
        RepoChoice.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String CurRepo = ((String) RepoChoice.getSelectedItem());
                if(CurRepo.split("/").length>1) {
                    QueryParams qry = new QueryParams();
                    qry.addParam("type","owner");
                    Owner = CurRepo.split("/")[0];
                    RepoN = CurRepo.split("/")[1];
                    ListBranchesInRepoResponse res=  Client.listBranchesInRepo(CurRepo.split("/")[0], CurRepo.split("/")[1], qry);
                    Branches.removeAll();

                    for(int i = 0 ; i < res.getJson().size();i++) {
                        String brch =( res.getJson().get(i).getAsJsonObject().get("name").getAsString().replaceAll(" ","%20"));
                        System.out.println(brch);
                        Branches.add(brch);
                    }
                    updateBranch();
            }
        }
        });

        BranchChoice = new JComboBox<String>();
        BranchChoice.setBounds(RepoChoice.getX()+RepoChoice.getWidth()+10,RepoChoice.getY(),200,40);
        BranchChoice.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {// this is going to be the call to allow me to get the classes in that branch
                 classes = new ArrayList<>();
                if(BranchChoice.getSelectedItem()!= null){
                    Branch = (String)BranchChoice.getSelectedItem();
                    try{
                    classes = Client.getAllFilesInRepo(Owner,RepoN,((String)BranchChoice.getSelectedItem()).replaceAll(" ","%20"));
                    String[] classNames = new String[classes.size()];
                    int counter = 0;
                    for(RepoFileContent RFC :classes){
                        classNames[counter++] = RFC.getFileName().replaceAll(" ","%20");
                    }
                    ClassChoice.removeAllItems();
                    for(String FN: classNames){
                        ClassChoice.addItem(FN);
                    }}
                    catch(Exception z){
                        System.out.println("error with branchs");
                        z.printStackTrace();

                    }
                }
            }
        });
        ClassChoice = new JComboBox<String>();
        ClassChoice.setBounds(BranchChoice.getX()+BranchChoice.getWidth()+20,RepoChoice.getY(),200,40);
        frame.add(ClassChoice);
        frame.add(BranchChoice);
        frame.add(RepoChoice);
        frame.add(OpenFile);
        OpenFile.addActionListener(e-> {
            if(LoggedIn == true) {
                JFrame ED = new JFrame("Edit");
                String FilePath = "";
                String classFileName = (String) ClassChoice.getSelectedItem();
                for(RepoFileContent c: classes){
                    if( c.getFileName().equals(classFileName)){
                         FilePath = c.getFilePath();
                         FileResponce = Client.getRepoFile(Owner,RepoN,c.getFilePath(),Branch);
                    }
                }
                try {
                    JEditorPane Editer = new JEditorPane("text", FileResponce.getText());
                    Editer.setPreferredSize(new Dimension(800, 900));

                    JMenuBar menBar = new JMenuBar();
                    JMenu Send = new JMenu("File");
                    JMenuItem SaveAndSend = new JMenuItem("Upload");
                    String finalFilePath = FilePath;
                    SaveAndSend.addActionListener(b -> {
                        try {
                            Client.updateFile(Owner, RepoN, finalFilePath, Branch, Editer.getText(), "sent through GitHub's new enchanced GUI");
                            JOptionPane.showMessageDialog(ED, "File updated successfully.");
                            ED.dispose();
                        } catch (Exception z) {
                            JOptionPane.showMessageDialog(ED, "Failed to update file.");
                        }

                    });
                    menBar.add(Send);
                    Send.add(SaveAndSend);
                    ED.setJMenuBar(menBar);


                    if (DarkLight == true) {
                        Editer.setForeground(Orig);
                        Editer.setBackground(DarkColor);
                    }
                    ED.setSize(600, 700);
                    JScrollPane scrollPane = new JScrollPane(Editer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setPreferredSize(new Dimension(800, 900));

                    ED.add(scrollPane, BorderLayout.CENTER);
                    ED.setVisible(true);
                }catch (Exception z){
                    System.out.println("this is not the right path due to an error with a branch containing a space which did not allow the \n class to update their names");
                }
            }
        });



        DarkMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DarkLight == false){
                    DarkLight = true;
                    frame.getContentPane().setBackground(DarkColor);
                    RepoInstrc.setBackground(DarkColor);
                    RepoInstrc.setForeground(Orig);
                    BranchInstrc.setBackground(DarkColor);
                    BranchInstrc.setForeground(Orig);
                    FileInstruct.setBackground(DarkColor);
                    FileInstruct.setForeground(Orig);
                    Instruc.setBackground(DarkColor);
                    Instruc.setForeground(Orig);
                    DarkMode.setText("LightMode");


                }
                else if (DarkLight == true){
                    DarkLight = false;
                    frame.getContentPane().setBackground(Orig);
                    RepoInstrc.setBackground(Orig);
                    RepoInstrc.setForeground(DarkColor);
                    BranchInstrc.setBackground(Orig);
                    BranchInstrc.setForeground(DarkColor);
                    FileInstruct.setBackground(Orig);
                    FileInstruct.setForeground(DarkColor);
                    Instruc.setBackground(Orig);
                    Instruc.setForeground(DarkColor);
                    DarkMode.setText("DarkMode");
                }
            }
        });
         URI target = new URI("https://bsully505.github.io/GitHubEnhancement/");

         Instruc = new JTextField("For help or more information please visit \n" + target.toString());
        Instruc.setEditable(false);
        Instruc.setBorder( new MatteBorder(0,0,1,0,new Color(0,0,0,0)));
        Instruc.setBackground(Orig);
        //JLabel Instruc = new JLabel("For help or more information please visit " );
        Instruc.setBounds((FrameX/2)-250, FrameY-(FrameY/4),500, 100);

        Instruc.addMouseListener(new MouseListener() {
                public boolean in = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(in) {
                    try {
                        Desktop.getDesktop().browse(target);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                in =true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
               in = false;
            }


        });

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        RepoInstrc.setBounds(Rx+(Rw/2)-(int)(Rw/(2*1.8)),0,(int) (Rw/1.2),Rh);
        BranchInstrc.setBounds(20+ Rx+Rw+Rx+(Rw/2)-(int)(Rw/(2*1.8)),0,(int) (Rw/1.2),Rh);
        FileInstruct.setBounds(20+ (2*(Rx+Rw))+Rx+(Rw/2)-(int)(Rw/(2*1.8)),0,(int) (Rw/1.2),Rh);
        frame.add(Instruc);
        frame.add(RepoInstrc);
        frame.add(BranchInstrc);
        frame.add(FileInstruct);
        frame.setLayout(null);
        frame.setSize(FrameX,FrameY);//setting width and height of the frame
        frame.setIconImage(IMG.getImage());



        frame.setVisible(true);
    }
    public void LoginPopUp(){

        JFrame loginFrame = new JFrame("Login");
        ImageIcon githubicon = new ImageIcon("GitHubLogo.png");
        loginFrame.setSize(400,200);
        loginFrame.setLayout(null);
        if(DarkLight){
            loginFrame.setBackground(DarkColor);

        }
        JLabel Image = new JLabel(githubicon);
        Image.setBounds(0,0,100,100);
        JLabel UsrNameLabel = new JLabel("UserName");
        JLabel PwrdLabel = new JLabel("Auth Token");
        JTextField UserName = new JTextField();
        JTextField Password = new JTextField();
        JButton Login = new JButton("Login");
        Login.setBounds(225,60, 100,40);

        UsrNameLabel.setBounds(5,20,100,50);
        PwrdLabel.setBounds(5,80, 100,50);
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
                QueryParams qry = new QueryParams();
                qry.addParam("type","owner");
                try {
                    ListReposResponse rep = Client.listRepos(new QueryParams());
                    List Repos = new List();
                    for (int i = 0; i < rep.getJson().size(); i++) {
                        Repos.add(rep.getJson().get(i).getAsJsonObject().get("full_name").getAsString());
                    }
                    setRepo(Repos);
                    LoggedIn = true;
                    updateRepo();
                    loginFrame.dispose();
                }
                catch(Exception z){
                    UserName.setText("");
                    Password.setText("");
                    JOptionPane.showMessageDialog(loginFrame,"you have entered a incorrect userName or Authentication token try again");
                }
            }
        });

        loginFrame.add(Login);
        loginFrame.add(UsrNameLabel);
        loginFrame.add(PwrdLabel);
        loginFrame.add(Image);
        loginFrame.add(UserName);
        loginFrame.add(Password);
        if(DarkLight){
            loginFrame.getContentPane().setBackground(DarkColor);
            UsrNameLabel.setForeground(Orig);
            PwrdLabel.setForeground(Orig);

        }

        loginFrame.setVisible(true);


    }
    public static void setAuth(GitHubApiClient auth){
        Main.Client = auth;
    }
    public void setReposResponse(ListReposResponse rep){
        this.rep = rep;
    }
    public void setRepo(List repo){
        this.Repos = repo;
    }
    public void setbranches(List Branch){
        this.Branches = Branch;
    }
    public void updateRepo(){
        RepoChoice.removeAllItems();
        for(String i: Repos.getItems()) {
            RepoChoice.addItem(i);
        }
    }
    public void updateBranch(){
        BranchChoice.removeAllItems();
        for(String i: Branches.getItems()) {
            BranchChoice.addItem(i);
        }
    }





    public static void main(String[] args) throws URISyntaxException {

        Main Run = new Main();
    }
}
