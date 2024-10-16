import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

class BorderImage implements Serializable {
    private final String newImagePath;
    private final String imagePath;
    private transient BufferedImage image;
    private final Color borderColor;
    private final int borderSize;

    public BorderImage(String imagePath, String newImagePath, Color borderColor, int borderSize) {
        this.borderColor = borderColor;
        this.borderSize = borderSize;
        this.imagePath = imagePath;
        this.newImagePath = newImagePath;
    }

    public BorderImage(String imagePath, Color borderColor, int borderSize) {
        this(imagePath, imagePath, borderColor, borderSize);
    }

    public void load() throws IOException {
        this.image = ImageIO.read(new File(this.imagePath));
    }

    public void addBorder() throws IOException {
        if(this.image == null) { this.load(); }

        int newWidth = this.image.getWidth() + 2 * borderSize;
        int newHeight = this.image.getHeight() + 2 * borderSize;
        BufferedImage borderedImage = new BufferedImage(newWidth, newHeight, this.image.getType());
        Graphics2D g = borderedImage.createGraphics();
        g.setColor(borderColor);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(this.image, borderSize, borderSize, null);
        g.dispose();
        ImageIO.write(borderedImage, "jpg", new File(this.newImagePath));
        System.out.println("Border added with color " + borderColor + " and size " + borderSize);
    }

    //TODO: For this to work, the original image path would need to be saved somewhere
    public void removeBorder() throws IOException {
        if(this.image == null) { this.load(); }
        int originalWidth = this.image.getWidth() - 2 * borderSize;
        int originalHeight = this.image.getHeight() - 2 * borderSize;
        BufferedImage originalImage = this.image.getSubimage(borderSize, borderSize, originalWidth, originalHeight);
        ImageIO.write(originalImage, "jpg", new File(this.imagePath));
        System.out.println("Border removed");
    }
}
