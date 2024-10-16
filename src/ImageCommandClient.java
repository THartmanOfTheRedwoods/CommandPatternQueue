import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

// Client
public class ImageCommandClient {
    public static void withMacro(CommandQueueInvoker invoker) throws Exception {
        // Still create the concrete receivers
        ResizeImage resizeImage = new ResizeImage("images_out/space_beer.jpg", 640, 480); // Resizing to 640x480
        BorderImage borderImage = new BorderImage("images_out/space_beer.jpg", Color.RED, 20); // 20px red border

        List<ImageCommand> commands = new ArrayList<>();
        commands.add(new ResizeImageCommand(resizeImage));
        commands.add(new BorderImageCommand(borderImage));
        // Prepare the macro
        ImageCommand macro = new MACROCommand(commands);
        // Send macro command to remote queue
        invoker.sendCommand(macro);
        /*------------------------------------------------------------------------------------------------------*/
        // Receive and execute commands (simulating a distributed environment), this
        // would normally happen on a different host
        invoker.receiveAndExecuteCommand();
        /*------------------------------------------------------------------------------------------------------*/
    }

    public static void noMacro(CommandQueueInvoker invoker) throws Exception {

        // Prepare receiver
        ResizeImage resizeImage = new ResizeImage("images_out/space_beer.jpg", 640, 480); // Resizing to 640x480
        // Create command
        ImageCommand resizeCommand = new ResizeImageCommand(resizeImage);
        // Send resizeCommand to remote queue
        invoker.sendCommand(resizeCommand);

        /*------------------------------------------------------------------------------------------------------*/
        // Receive and execute commands (simulating a distributed environment), this
        // would normally happen on a different host
        invoker.receiveAndExecuteCommand();
        /*------------------------------------------------------------------------------------------------------*/

        // Now let's apply a border to the resized image.
        // TODO: Implement a resize and border MACRO command to do both.

        // Prepare receiver
        BorderImage borderImage = new BorderImage("images_out/space_beer.jpg", Color.RED, 20); // 20px red border
        // Create command
        ImageCommand borderCommand = new BorderImageCommand(borderImage);
        // Send border command to remote queue
        invoker.sendCommand(borderCommand);
        /*------------------------------------------------------------------------------------------------------*/
        // Receive and execute commands (simulating a distributed environment), this
        // would normally happen on a different host
        invoker.receiveAndExecuteCommand();
        /*------------------------------------------------------------------------------------------------------*/
    }

    public static void main(String[] args) throws Exception {
        // Load image (replace "image.jpg" with your actual file path)
        // BufferedImage image = ImageIO.read(new File("/Users/trevorhartman/Downloads/DecoratorExample/CommandPatternQueue/images/space_beer.jpg"));
        BufferedImage image = ImageIO.read(new File("images/space_beer.jpg"));

        // Invoker to queue and execute commands
        CommandQueueInvoker invoker = new CommandQueueInvoker();

        // Prepare the receiver
        UploadImage uploadImage = new UploadImage(image, "images_out/space_beer.jpg");
        // Create command
        ImageUploadCommand uploadCommand = new ImageUploadCommand(uploadImage);
        // Send uploadCommand to remote queue
        invoker.sendCommand(uploadCommand);
        /*------------------------------------------------------------------------------------------------------*/
        // Receive and execute commands (simulating a distributed environment), this
        // would normally happen on a different host
        invoker.receiveAndExecuteCommand();
        /*------------------------------------------------------------------------------------------------------*/

        // Send the resize and border commands outside a macro
        // noMacro(invoker);
        withMacro(invoker);

        // Undo commands
        //invoker.undoCommand(borderCommand);
        //invoker.undoCommand(resizeCommand);
    }
}
