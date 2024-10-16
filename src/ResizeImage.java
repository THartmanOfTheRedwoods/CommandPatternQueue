import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

class ResizeImage implements Serializable {
    private transient BufferedImage image;  // transient skips variable during serialization
    private final String imagePath;
    private final String newImagePath;
    private int originalWidth;
    private int originalHeight;
    private final int newWidth;
    private final int newHeight;

    public ResizeImage(String imagePath, String newImagePath, int newWidth, int newHeight) {
        this.imagePath = imagePath;
        this.newImagePath = newImagePath;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    public ResizeImage(String imagePath, int newWidth, int newHeight) {
        this(imagePath, imagePath, newWidth, newHeight);
    }

    public void load() throws IOException {
        this.image = ImageIO.read(new File(imagePath));
        this.originalWidth = image.getWidth();
        this.originalHeight = image.getHeight();
    }

    public void resize() throws IOException {
        if(this.image == null) { this.load(); }  // Load the image if it isn't already

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, this.image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(this.image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        ImageIO.write(resizedImage, "jpg", new File(this.newImagePath));
        System.out.println("Image resized to " + newWidth + "x" + newHeight);
    }

    //TODO: For this to work, the original image path would need to be saved somewhere
    public void restoreOriginalSize() throws IOException {
        if(this.image == null) { this.load(); }  // Load the image if it isn't already

        BufferedImage originalImage = new BufferedImage(originalWidth, originalHeight, image.getType());
        Graphics2D g = originalImage.createGraphics();
        g.drawImage(image, 0, 0, originalWidth, originalHeight, null);
        g.dispose();
        ImageIO.write(originalImage, "jpg", new File(this.imagePath));
        System.out.println("Image restored to original size: " + originalWidth + "x" + originalHeight);
    }
}
