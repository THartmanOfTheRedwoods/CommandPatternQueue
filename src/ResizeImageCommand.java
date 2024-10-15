import java.io.IOException;

// Concrete Command for resizing an image
class ResizeImageCommand implements ImageCommand {
    private ResizeImage resizeImage;

    public ResizeImageCommand(ResizeImage resizeImage) {
        this.resizeImage = resizeImage;
    }

    @Override
    public void execute() throws IOException {
        resizeImage.resize();
        resizeImage.write();
    }

    @Override
    public void undo() throws IOException {
        resizeImage.restoreOriginalSize();
    }
}
