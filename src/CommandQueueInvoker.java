import org.zeromq.ZMQ;
import java.io.*;
class CommandQueueInvoker {
    private ZMQ.Context context;
    private ZMQ.Socket sender;
    private ZMQ.Socket receiver;

    public CommandQueueInvoker() {
        context = ZMQ.context(1);
        sender = context.socket(ZMQ.PUSH);
        sender.bind("tcp://*:5555");
        receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://localhost:5555");
    }

    public void sendCommand(ImageCommand command) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(command);
        objOut.close();

        byte[] commandBytes = byteOut.toByteArray();
        sender.send(commandBytes);
    }

    public void receiveAndExecuteCommand() throws IOException, ClassNotFoundException {
        byte[] receivedBytes = receiver.recv();

        ByteArrayInputStream byteIn = new ByteArrayInputStream(receivedBytes);
        ObjectInputStream objIn = new ObjectInputStream(byteIn);
        ImageCommand command = (ImageCommand) objIn.readObject();
        objIn.close();

        try {
            command.execute();
        } catch (IOException e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }

    public void close() {
        sender.close();
        receiver.close();
        context.term();
    }
}