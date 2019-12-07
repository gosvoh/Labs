package ga.gosvoh.server.utils;

@SuppressWarnings("WeakerAccess")
public class PacketOverflowException extends RuntimeException {
    public PacketOverflowException(String msg) {
        super(msg);
    }
}
