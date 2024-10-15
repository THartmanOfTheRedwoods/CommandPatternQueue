import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

class ResizeImage implements Serializable {
    private byte[] imageBytes; // Serialized image data
    private int originalWidth;
    private int originalHeight;
    private int newWidth;
    private int newHeight;
    private String outPath;

    public ResizeImage(BufferedImage image, int newWidth, int newHeight, String outPath) throws IOException {
        this.imageBytes = bufferedImageToByteArray(image);
        this.originalWidth = image.getWidth();
        this.originalHeight = image.getHeight();
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.outPath = outPath;
    }

    public void resize() throws IOException {
        BufferedImage image = byteArrayToBufferedImage(imageBytes);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        imageBytes = bufferedImageToByteArray(resizedImage);
        System.out.println("Image resized to " + newWidth + "x" + newHeight);
    }

    public void write() throws IOException {
        ImageIO.write(byteArrayToBufferedImage(imageBytes), "jpg", new File(this.outPath));
    }

    public void restoreOriginalSize() throws IOException {
        BufferedImage image = byteArrayToBufferedImage(imageBytes);
        BufferedImage originalImage = new BufferedImage(originalWidth, originalHeight, image.getType());
        Graphics2D g = originalImage.createGraphics();
        g.drawImage(image, 0, 0, originalWidth, originalHeight, null);
        g.dispose();
        imageBytes = bufferedImageToByteArray(originalImage);
        System.out.println("Image restored to original size: " + originalWidth + "x" + originalHeight);
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

    private BufferedImage byteArrayToBufferedImage(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return ImageIO.read(bais);
    }
}
