import java.io.IOException;

// Concrete Command for adding/removing border
class BorderImageCommand implements ImageCommand {
    private BorderImage borderImage;

    public BorderImageCommand(BorderImage borderImage) {
        this.borderImage = borderImage;
    }

    @Override
    public void execute() throws IOException {
        borderImage.addBorder();
        borderImage.write();
    }

    @Override
    public void undo() throws IOException {
        borderImage.removeBorder();
    }
}
