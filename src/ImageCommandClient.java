import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// Client
public class ImageCommandClient {
    public static void main(String[] args) throws Exception {
        // Load image (replace "image.jpg" with your actual file path)
        // BufferedImage image = ImageIO.read(new File("/Users/trevorhartman/Downloads/DecoratorExample/CommandPatternQueue/images/space_beer.jpg"));
        BufferedImage image = ImageIO.read(new File("images/space_beer.jpg"));

        // Invoker to queue and execute commands
        CommandQueueInvoker invoker = new CommandQueueInvoker();

        // Prepare receiver
        ResizeImage resizeImage = new ResizeImage(image, 640, 480, "images_out/space_beer.jpg"); // Resizing to 640x480
        // Create command
        ImageCommand resizeCommand = new ResizeImageCommand(resizeImage);
        // Send resizeCommand to remote queue
        invoker.sendCommand(resizeCommand);

        // Receive and execute commands (simulating a distributed environment), this
        // would normally happen on a different host
        invoker.receiveAndExecuteCommand();

        // Now let's apply a border to the resized image.
        // TODO: Implement a resize and border MACRO command to do both.

        image = ImageIO.read(new File("images_out/space_beer.jpg"));
        // Prepare receiver
        BorderImage borderImage = new BorderImage(image, Color.RED, 20, "images_out/space_beer.jpg"); // 10px black border
        // Create command
        ImageCommand borderCommand = new BorderImageCommand(borderImage);
        // Execute resize and border commands
        invoker.sendCommand(borderCommand);
        invoker.receiveAndExecuteCommand();

        // Undo commands
        //invoker.undoCommand(borderCommand);
        //invoker.undoCommand(resizeCommand);

        // Save the manipulated image (for demo purposes)
        ImageIO.write(borderImage.getImage(), "jpg", new File("images/output_image.jpg"));
    }
}
