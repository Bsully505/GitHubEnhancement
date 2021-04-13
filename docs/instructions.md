# **Instructions**

Here you will learn how to setup and navigate through our program.

## **How to Setup:**

Utilizing the program through our repo from GitHub, you can simply copy the link from the code and run
the command *git clone* into your computer's terminal. From there you may open that repo in an IDE such
as Eclipse or IntelliJ. For this project, programmers utilized both IDE's.

In addition, two external libraries will need to be added to the project:
- GitSubprocessClient: allows for running Git commands from a Java program instead of the terminal
- GitHubApiClientTool: allows for sending API calls to GitHub.

### **To Download Java Packages:**

1. After clicking the link to each packages' repo, click on *Releases* located on the right side of the screen.
2. Click on on the .jar file of the most recent version of the repo, under *Assets*.
3. Once clicked, the file will be downloaded onto your computer.

### **To install in Eclipse:**

1. Having the Java project open in Eclipse, right click on the project, click on *Build Path*,
then *Add External Archives*.
2. Select the jar file you wish to add and click *Open*.
3. Once imported, you should find the added library under *Referenced Libraries*.

### **To install in IntelliJ:**

1. Having the Java project open in IntelliJ, right click on the project and click on *Open Module Settings*.
2. Click on *Dependencies*.
3. Click the *+* located on the right side of the screen.
4. A menu will drop down and select *JARS or directories*.
5. Select the jar file you wish to add and click *OK*.
6. Once imported, you should find the added library in *Dependencies* or the *External Libraries* tab.

## **How to use:**

Once the project is imported into your IDE:

1. Run the program from the *Main.java* file and the GUI window should pop up.

![Gui](./assets/images/gui.jpeg)

2. Click *File* in the top left corner, you will be given two options:
    - DarkMode: allows you to the switch the theme/color of the application from light to dark
    - Login: allows you to log onto your GitHub account using a token for the password
      > You can find a token by going to your GitHub account, in Settings, scroll down to Developer Settings and click on Personal Access Tokens.

3. Once you complete your login, you will see three boxes which will allow you to select a *repository*,
a *branch*, and a *file* you wish to edit.
4. Click the *Open Selected File* button.
5. Make the desired changes to the file you selected.
6. Click *File*, then *Upload*.
7. You should be able to see the changes made in your repository on GitHub.

Click [here](./index) to return to the home page.
