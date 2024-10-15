import java.io.IOException;
import java.io.Serializable;

// Command Interface
interface ImageCommand extends Serializable {
    void execute() throws IOException;
    void undo() throws IOException;
}