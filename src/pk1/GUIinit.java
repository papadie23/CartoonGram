package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List; // Add this import statement
import java.util.Map;

public class GUIinit extends JFrame {
    private static final long serialVersionUID = 1L;
    private FollowSubject followSubject;
    private FollowersWindow followersWindow;
    private JScrollPane scrollPane;

    public GUIinit() {
        super("CartoonGram");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        followSubject = new FollowSubject();
        followersWindow = new FollowersWindow(this); // pass the main window reference

        JPanel contentPanel = createContentPanel();
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        BottomNavBar bottomNavBar = new BottomNavBar(followSubject, followersWindow, this);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomNavBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        String basePath = "src/pk1/";
        Object[][] characters = {
            {"Tom", "tomthecat", basePath + "tom/pf.jpg", 4, "Description by Tom"},
            {"Jake", "jake_adventuretime", basePath + "jake/pf.jpg", 3, "Description by Jake"},
            {"Princess Bubblegum", "princessgumb", basePath + "princessgumb/pf.jpg", 3, "Description by Bubblegum"},
            {"Gumball", "gumball_waterson", basePath + "gumball/pf.jpg", 3, "Description by Gumball"},
            {"Darwin", "darwin_fishbuddy", basePath + "darwin/pf.jpg", 3, "Description by Darwin"},
        };
        //hashmap pentru fiecare caracter
        Map<String, FollowSubject> followSubjects = new HashMap<>();
        for (Object[] character : characters) {
            String characterName = (String) character[0];
            String userName = (String) character[1];
            String profileImagePath = (String) character[2];
            int postCount = (int) character[3];
            List<Integer> postIndices = new ArrayList<>(); // ensure List is imported
            for (int i = 1; i <= postCount; i++) {
                postIndices.add(i);
            }
            Collections.shuffle(postIndices);

            FollowSubject followSubject = followSubjects.computeIfAbsent(userName, k -> new FollowSubject());
            for (int i = 0; i < 2 && i < postCount; i++) {
                int postIndex = postIndices.get(i);
                String postImagePath = profileImagePath.replace("pf.jpg", postIndex + ".jpg");
                ProfilePanel profilePanel = new ProfilePanel(characterName, userName, profileImagePath, "Photo Posted", postImagePath, "", (String) character[4], followersWindow, followSubject);
                contentPanel.add(profilePanel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        return contentPanel;
    }

    public void showFollowersWindow() 
    {
    	//ive implemented this because when they are both visible the followerwindow doesnt get updatd fsr
        setVisible(false); // hide the main window
        followersWindow.setVisible(true); // show the followers window
    }

    public void showMainWindow() {
        followersWindow.setVisible(false); // Hide the followers window
        setVisible(true); // Show the main window
    }

    public void scrollToTop() {
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
                int currentValue = verticalBar.getValue();
                if (currentValue > 0) {
                    int scrollAmount = Math.max(1, (int) (currentValue * 0.1));
                    verticalBar.setValue(currentValue - scrollAmount);
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new GUIinit();
    }
}
