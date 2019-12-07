package ga.gosvoh.client.utils;

@SuppressWarnings("WeakerAccess")
public class ReceivedData {
    private int totalCount;
    private byte commandCode;
    private byte[] data;
    private boolean isDone = false;

    public ReceivedData() {
    }

    public ReceivedData(byte commandCode, int totalCount, byte[] data) {
        this.totalCount = totalCount;
        this.commandCode = commandCode;
        this.data = data;
        isDone = true;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public byte getCommandCode() {
        return commandCode;
    }

    public boolean haveData() {
        return data != null;
    }

    public byte[] getData() {
        return data;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
