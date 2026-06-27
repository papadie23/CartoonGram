package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class LikeButton extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Icon createHeartIcon(boolean filled) {
        int size = 36;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // enable anti-aliasing for smooth edges/refinarea la buffered image cu anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // define colors /definire culoare
        g2.setColor(filled ? Color.RED : Color.BLACK);

        // draw the heart shape using Bezier curves/folosirea curbei Bezier pentru a face inima
        Path2D.Double path = new Path2D.Double();
        path.moveTo(size / 2, size * 0.85); // Adjusted starting point
        path.curveTo(size, size * 0.55, size * 0.7, size * 0.25, size / 2, size * 0.45);
        path.curveTo(size * 0.3, size * 0.25, 0, size * 0.55, size / 2, size * 0.85);
        g2.fill(path);

        g2.dispose();
        return new ImageIcon(image);
    }

    private Icon emptyHeartIcon;
    private Icon filledHeartIcon;
    private boolean isLiked = false;

    public LikeButton() {
        super();
        emptyHeartIcon = createHeartIcon(false);
        filledHeartIcon = createHeartIcon(true);
        setIcon(emptyHeartIcon);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleLike();
            }
        });
    }

    public boolean isLiked() {
        return isLiked;
    }

    private void toggleLike() {
        if (isLiked) {
            setIcon(emptyHeartIcon);
        } else {
            setIcon(filledHeartIcon);
        }
        isLiked = !isLiked;
    }
}
