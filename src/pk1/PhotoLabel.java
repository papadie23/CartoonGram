package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PhotoLabel extends JLabel {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_IMAGE_PATH = "C:\\path\\to\\placeholder.jpg";
    private String photoPath;
    private String photoText;
    private LikeButton likeButton;
    private BufferedImage originalImage;

    public PhotoLabel(String photoText, String photoPath, LikeButton likeButton) {
        this.photoText = photoText;
        this.photoPath = photoPath;
        this.likeButton = likeButton;

        if (photoPath != null) {
            try {
                File file = new File(photoPath);
                if (file.exists()) {
                    originalImage = ImageIO.read(file);
                } else {
                    System.err.println("Image not found at path: " + photoPath);
                    loadDefaultImage();
                }
            } catch (IOException e) {
                System.err.println("Error loading image at path: " + photoPath);
                e.printStackTrace();
                loadDefaultImage();
            }
        } else {
            loadDefaultImage();
        }

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

    public void updateBounds(int containerWidth) {
        if (originalImage != null) {
            int imgW = containerWidth;
            int imgH = (int) (originalImage.getHeight(null) * ((double) containerWidth / originalImage.getWidth(null)));
            if (imgH > 350) imgH = 350;
            Image scaledImage = getScaledImage(originalImage, imgW, imgH);
            setIcon(new ImageIcon(scaledImage));
            setBounds(0, 56, containerWidth, imgH);
        }
    }

    private void loadDefaultImage() {
        try {
            BufferedImage placeholder = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
            if (placeholder != null) {
                originalImage = placeholder;
            }
        } catch (IOException e) {
            setText(photoText);
            setHorizontalAlignment(SwingConstants.CENTER);
            setBackground(Color.WHITE);
            setOpaque(true);
        }
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}
