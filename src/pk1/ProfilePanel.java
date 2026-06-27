package pk1;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ProfilePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private String postImagePath;
    private CommentSection commentSection;
    private boolean isCommentSectionVisible = false;
    private Timer slideTimer;
    private int targetHeight;
    private PhotoLabel photoLabel;
    private JLabel likesLabel;
    private int likeCount;
    private Random rng = new Random();

    public ProfilePanel(String characterName, String userName, String profileImagePath, String photoText, String postImagePath, String captionText, String descriptionText, FollowersWindow followersWindow, FollowSubject followSubject) {
        this.postImagePath = postImagePath;
        this.likeCount = rng.nextInt(200) + 5;

        setLayout(null);
        setPreferredSize(new Dimension(400, 420));
        setBackground(new Color(255, 255, 255, 0));

        ProfileLabel profileLabel = new ProfileLabel(characterName, profileImagePath);
        profileLabel.setBounds(8, 8, 36, 36);
        add(profileLabel);

        UserNameLabel nameLabel = new UserNameLabel(userName);
        nameLabel.setBounds(50, 10, 160, 30);
        add(nameLabel);

        JButton optionsBtn = new JButton("\u22EF");
        optionsBtn.setFont(new Font("Arial", Font.BOLD, 18));
        optionsBtn.setBounds(340, 4, 50, 36);
        optionsBtn.setFocusPainted(false);
        optionsBtn.setBorderPainted(false);
        optionsBtn.setContentAreaFilled(false);
        optionsBtn.setForeground(Color.DARK_GRAY);
        add(optionsBtn);

        FollowButton followButton = new FollowButton(followSubject, followersWindow, characterName);
        followButton.setBounds(230, 10, 95, 30);
        add(followButton);

        Follower follower = new Follower(characterName, followersWindow);
        followSubject.attach(follower);

        LikeButton likeButton = new LikeButton();

        photoLabel = new PhotoLabel(photoText, postImagePath, likeButton);
        photoLabel.setBounds(0, 50, 400, 260);
        add(photoLabel);

        int btnY = 320;

        likeButton.setBounds(8, btnY, 32, 32);
        add(likeButton);

        likeButton.addActionListener(e -> {
            if (likeButton.isLiked()) {
                likeCount++;
            } else {
                likeCount--;
            }
            likesLabel.setText(likeCount + " likes");
        });

        JButton commentIconBtn = new JButton("\uD83D\uDCAC");
        commentIconBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        commentIconBtn.setBounds(48, btnY - 1, 32, 32);
        commentIconBtn.setFocusPainted(false);
        commentIconBtn.setBorderPainted(false);
        commentIconBtn.setContentAreaFilled(false);
        commentIconBtn.addActionListener(e -> toggleCommentSection());
        add(commentIconBtn);

        JButton shareBtn = new JButton("\u27A8");
        shareBtn.setFont(new Font("Arial", Font.BOLD, 14));
        shareBtn.setBounds(86, btnY - 1, 32, 32);
        shareBtn.setFocusPainted(false);
        shareBtn.setBorderPainted(false);
        shareBtn.setContentAreaFilled(false);
        shareBtn.setForeground(Color.DARK_GRAY);
        add(shareBtn);

        JButton bookmarkBtn = new JButton("\u2661");
        bookmarkBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        bookmarkBtn.setBounds(355, btnY - 1, 36, 32);
        bookmarkBtn.setFocusPainted(false);
        bookmarkBtn.setBorderPainted(false);
        bookmarkBtn.setContentAreaFilled(false);
        bookmarkBtn.setForeground(Color.DARK_GRAY);
        bookmarkBtn.addActionListener(e -> {
            bookmarkBtn.setText(bookmarkBtn.getText().equals("\u2661") ? "\u2665" : "\u2661");
        });
        add(bookmarkBtn);

        int infoY = btnY + 30;
        likesLabel = new JLabel(likeCount + " likes");
        likesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        likesLabel.setBounds(12, infoY, 200, 16);
        likesLabel.setForeground(new Color(38, 38, 38));
        add(likesLabel);

        JLabel captionLabel = new JLabel("<html><b>" + userName + "</b> " + captionText + "</html>");
        captionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        captionLabel.setBounds(12, infoY + 18, 370, 28);
        add(captionLabel);

        JLabel timestamp = new JLabel(getRandomTimestamp());
        timestamp.setFont(new Font("Arial", Font.PLAIN, 10));
        timestamp.setForeground(new Color(142, 142, 142));
        timestamp.setBounds(12, infoY + 46, 200, 14);
        add(timestamp);

        commentSection = new CommentSection();
        commentSection.setBounds(10, 430, 380, 150);
        commentSection.setVisible(false);
        add(commentSection);
    }

    private String getRandomTimestamp() {
        String[] times = {"2 minutes ago", "15 minutes ago", "1 hour ago", "3 hours ago", "5 hours ago", "8 hours ago", "12 hours ago", "1 day ago"};
        return times[rng.nextInt(times.length)];
    }

    private void toggleCommentSection() {
        isCommentSectionVisible = !isCommentSectionVisible;
        commentSection.setVisible(isCommentSectionVisible);
        targetHeight = isCommentSectionVisible ? 600 : 440;
        if (slideTimer != null && slideTimer.isRunning()) {
            slideTimer.stop();
        }
        slideTimer = new Timer(10, e -> slideAnimation());
        slideTimer.start();
    }

    private void slideAnimation() {
        Dimension currentSize = getPreferredSize();
        int currentHeight = currentSize.height;
        if (isCommentSectionVisible && currentHeight < targetHeight) {
            setPreferredSize(new Dimension(getWidth(), currentHeight + 10));
        } else if (!isCommentSectionVisible && currentHeight > targetHeight) {
            setPreferredSize(new Dimension(getWidth(), currentHeight - 10));
        } else {
            slideTimer.stop();
        }
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        g2.setColor(new Color(219, 219, 219));
        g2.setStroke(new BasicStroke(0.8f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        g2.dispose();
    }

    public String getPostImagePath() {
        return postImagePath;
    }
}
