import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

class BorderImage implements Serializable {
    private byte[] imageBytes;
    private Color borderColor;
    private int borderSize;
    private String outPath;

    public BorderImage(BufferedImage image, Color borderColor, int borderSize, String outPath) throws IOException {
        this.imageBytes = bufferedImageToByteArray(image);
        this.borderColor = borderColor;
        this.borderSize = borderSize;
        this.outPath = outPath;
    }

    public void addBorder() throws IOException {
        BufferedImage image = byteArrayToBufferedImage(imageBytes);
        int newWidth = image.getWidth() + 2 * borderSize;
        int newHeight = image.getHeight() + 2 * borderSize;
        BufferedImage borderedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = borderedImage.createGraphics();
        g.setColor(borderColor);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(image, borderSize, borderSize, null);
        g.dispose();
        imageBytes = bufferedImageToByteArray(borderedImage);
        System.out.println("Border added with color " + borderColor + " and size " + borderSize);
    }

    public void write() throws IOException {
        ImageIO.write(byteArrayToBufferedImage(imageBytes), "jpg", new File(this.outPath));
    }

    public void removeBorder() throws IOException {
        BufferedImage image = byteArrayToBufferedImage(imageBytes);
        int originalWidth = image.getWidth() - 2 * borderSize;
        int originalHeight = image.getHeight() - 2 * borderSize;
        BufferedImage originalImage = image.getSubimage(borderSize, borderSize, originalWidth, originalHeight);
        imageBytes = bufferedImageToByteArray(originalImage);
        System.out.println("Border removed");
    }

    public BufferedImage getImage() throws IOException {
        return byteArrayToBufferedImage(imageBytes);
    }

    // Helper methods to convert between BufferedImage and byte array
    private byte[] bufferedImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    public BufferedImage byteArrayToBufferedImage(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return ImageIO.read(bais);
    }
}
