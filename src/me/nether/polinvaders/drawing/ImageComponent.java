package me.nether.polinvaders.drawing;

import me.nether.polinvaders.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class ImageComponent extends AbstractComponent {

    protected Image img;
    private String directory;

    public boolean doNotCancel;

    public ImageComponent(float x, float y, int width, int height, String directory) {
        super(x, y, width, height);
        this.directory = directory;
        ImageIcon icon = new ImageIcon("assets/" + this.directory);
        img = icon.getImage();
    }

    public void draw(Graphics2D g, AffineTransform t) {
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.drawImage(this.img,
                (int) (this.x - this.width / 2),
                (int) (this.y - this.height / 2),
                (int) this.width,
                (int) this.height,
        null);
    }

    public void draw(Graphics2D g) {
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawImage(this.img,
                (int) (this.x - this.width / 2),
                (int) (this.y - this.height / 2),
                (int) this.width,
                (int) this.height,
                null);
    }

    public void draw(Graphics2D g, float x, float y, float width, float height) {
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawImage(this.img,
                (int) x,
                (int) y,
                (int) width,
                (int) height,
                null);
    }

    /**
     * The order is
     * 1     2
     * <p>
     * <p>
     * 3     4
     *
     * @return all 4 points of the rectangle hitbox
     */
    public Point2D[] getBounds() {
        return new Point2D[]{
                new Point2D.Float(this.x - this.width / 2, this.y - this.height / 2),
                new Point2D.Float(this.x + this.width / 2, this.y - this.height / 2),
                new Point2D.Float(this.x - this.width / 2, this.y + this.height / 2),
                new Point2D.Float(this.x + this.width / 2, this.y + this.height / 2)
        };
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public boolean isOutOfBorders() {
        return !doNotCancel && (x + width / 2 < 0 || x - width / 2 > Main.WIDTH || y + height / 2 < 0 || y - height / 2 > Main.HEIGHT);
    }
}
