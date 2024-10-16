import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MACROCommand implements ImageCommand {
    private List<ImageCommand> commands;

    public MACROCommand(List<ImageCommand> commands) {
        this.commands = commands;
    }
    @Override
    public void execute() throws IOException {
        for(ImageCommand c : this.commands) {
            c.execute();
        }
    }

    @Override
    public void undo() throws IOException {
        for(ImageCommand c : this.commands) {
            c.undo();
        }
    }
}
