import java.io.IOException;

// Concrete Command for resizing an image
class NullCommand implements ImageCommand {
    public NullCommand() {
    }

    @Override
    public void execute() throws IOException {
    }

    @Override
    public void undo() throws IOException {
    }
}
