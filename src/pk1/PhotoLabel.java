package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PhotoLabel extends JLabel {
    private static final long serialVersionUID = 1L;

    public PhotoLabel(String photoText, String photoPath, LikeButton likeButton) {
        boolean loaded = false;
        if (photoPath != null) {
            try {
                File file = new File(photoPath);
                if (file.exists()) {
                    BufferedImage img = ImageIO.read(file);
                    if (img != null) {
                        Image scaled = img.getScaledInstance(380, 250, Image.SCALE_SMOOTH);
                        setIcon(new ImageIcon(scaled));
                        loaded = true;
                    }
                }
            } catch (IOException e) {
                // fall through to text
            }
        }
        if (!loaded) {
            setText(photoText != null ? photoText : "");
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createLineBorder(new Color(219, 219, 219)));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    likeButton.doClick();
                }
            }
        });
    }
}
