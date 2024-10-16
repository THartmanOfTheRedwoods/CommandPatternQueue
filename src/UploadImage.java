import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

class UploadImage implements Serializable {
    private final byte[] imageBytes; // Serialized image data
    private final String outPath;

    public UploadImage(BufferedImage image, String outPath) throws IOException {
        this.imageBytes = bufferedImageToByteArray(image);
        this.outPath = outPath;
    }

    public void write() throws IOException {
        ImageIO.write(byteArrayToBufferedImage(imageBytes), "jpg", new File(this.outPath));
    }

    public void delete() {
        File file = new File(this.outPath);
        file.delete();
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
