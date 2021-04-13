
/**
 * ideas
 *
 * 1) error test by what happends if a user enters a wrong userName and or Token
 * 2) ADD NEW buttone for add commit delete
 * 3) add scrolability for the text file panal
 */

import github.tools.client.GitHubApiClient;
import github.tools.client.QueryParams;
import github.tools.responseObjects.GetRepoFileResponse;
import github.tools.responseObjects.ListBranchesInRepoResponse;
import github.tools.responseObjects.ListReposResponse;
import github.tools.responseObjects.RepoFileContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public static RepoFileContent ClassInfo;
    public static ArrayList<RepoFileContent> classes;
    public static GetRepoFileResponse FileResponce;
    public Main(){

        OpenFile = new JButton("Open Selected File");
        OpenFile.setBounds(220,400, 200,50);
        LoggedIn = false;

        int FrameX =500, FrameY =500;
        DarkLight = false;
        Branches = new List();
        Classes = new List();
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
         RepoChoice = new JComboBox<String>();
        RepoChoice.setBounds(40,200,200,40);
        RepoChoice.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String CurRepo = ((String) RepoChoice.getSelectedItem());
                //now i have to get all of the classes for the repo
                if(CurRepo.split("/").length>1) {
                    //System.out.println(CurRepo.split("/").length);
                    QueryParams qry = new QueryParams();
                    qry.addParam("type","owner");
                    Owner = CurRepo.split("/")[0];
                    RepoN = CurRepo.split("/")[1];
                    ListBranchesInRepoResponse res=  Client.listBranchesInRepo(CurRepo.split("/")[0], CurRepo.split("/")[1], qry);

                    Branches.removeAll();
                    for(int i = 0 ; i < res.getJson().size();i++) {
                        Branches.add( res.getJson().get(i).getAsJsonObject().get("name").getAsString());
                    }




                    updateBranch();

            }
        }});

        BranchChoice = new JComboBox<String>();
        BranchChoice.setBounds(260,200,200,40);
        BranchChoice.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {// this is going to be the call to allow me to get the classes in that branch
                 classes = new ArrayList<>();
                if(BranchChoice.getSelectedItem()!= null){
                    Branch = (String)BranchChoice.getSelectedItem();
                    classes = Client.getAllFilesInRepo(Owner,RepoN,(String)BranchChoice.getSelectedItem());
                    String[] classNames = new String[classes.size()];
                    int counter = 0;
                    for(RepoFileContent RFC :classes){
                        classNames[counter++] = RFC.getFileName();
                    }




                    ClassChoice.removeAllItems();
                    for(String FN: classNames){
                        ClassChoice.addItem(FN);
                    }

                }


            }});
        ClassChoice = new JComboBox<String>();
        ClassChoice.setBounds(300,300,200,50);
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

                JEditorPane Editer = new JEditorPane("text",FileResponce.getText());

                JScrollPane scrollPane = new JScrollPane(Editer);
                JScrollBar bars = scrollPane.getVerticalScrollBar();
                JMenuBar menBar = new JMenuBar();
                JMenu Send = new JMenu("File");
                JMenuItem SaveAndSend = new JMenuItem("Upload");
                String finalFilePath = FilePath;
                SaveAndSend.addActionListener(b -> {
                        Client.updateFile(Owner,RepoN, finalFilePath,Branch,Editer.getText(),"sent through GitHub's new enchanced GUI");
                        }
                        );
                menBar.add(Send);
                Send.add(SaveAndSend);
                ED.setJMenuBar(menBar);


                ED.add(bars,BorderLayout.EAST);
                ED.add(scrollPane,BorderLayout.CENTER);
                ED.setSize(600, 700);
                ED.add(Editer,BorderLayout.CENTER);
                ED.setVisible(true);
            }
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
        frame.setIconImage(IMG.getImage());



        frame.setVisible(true);
    }
    public void LoginPopUp(){
        JFrame loginFrame = new JFrame("Login");
        ImageIcon githubicon = new ImageIcon("GitHubLogo.png");
        loginFrame.setSize(400,200);
        loginFrame.setLayout(null);
        //loginFrame.setDefaultCloseOperation(3);
        JLabel Image = new JLabel(githubicon);
        Image.setBounds(0,0,100,100);
        JLabel UsrNameLabel = new JLabel("UserName");
        JLabel PwrdLabel = new JLabel("Auth Token");
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
                QueryParams qry = new QueryParams();
                qry.addParam("type","owner");
                ListReposResponse rep = Client.listRepos(new QueryParams());
                //github.tools.responseObjects.GetRepoInfoResponse res =  Client.getRepoInfo(rep.getJson().get(i).getAsJsonObject().get("full_name").getAsString(),"GitHubEnhancement");
                //System.out.println(res.getRepoFullName());
                //setReposResponse(rep);
                List Repos = new List();
                for(int i = 0 ; i < rep.getJson().size();i++) {
                    Repos.add( rep.getJson().get(i).getAsJsonObject().get("full_name").getAsString());
                }

                setRepo(Repos);
                LoggedIn = true;
                updateRepo();
                //need to make sure that the user is logged in and is the correct user
                loginFrame.dispose();

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





    public static void main(String[] args){

        Main Run = new Main();
    }
}
