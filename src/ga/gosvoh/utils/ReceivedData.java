package ga.gosvoh.utils;

public class ReceivedData {
    private int countOfUniverses, universeKey;
    private byte[] data;

    public ReceivedData() {
    }

    public ReceivedData(int countOfUniverses, int universeKey, byte[] data) {
        this.countOfUniverses = countOfUniverses;
        this.universeKey = universeKey;
        this.data = data;
    }

    public boolean isFullData() {
        return (data != null);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getCountOfUniverses() {
        return countOfUniverses;
    }

    public void setCountOfUniverses(int countOfUniverses) {
        this.countOfUniverses = countOfUniverses;
    }

    public int getUniverseKey() {
        return universeKey;
    }

    public void setUniverseKey(int universeKey) {
        this.universeKey = universeKey;
    }
}
