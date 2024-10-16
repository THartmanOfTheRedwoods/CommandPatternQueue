import java.io.IOException;

// Concrete Command for resizing an image
class ImageUploadCommand implements ImageCommand {
    private UploadImage uploadImage;

    public ImageUploadCommand(UploadImage uploadImage) {
        this.uploadImage = uploadImage;
    }

    @Override
    public void execute() throws IOException {
        uploadImage.write();
    }

    @Override
    public void undo() throws IOException {
        uploadImage.delete();
    }
}
