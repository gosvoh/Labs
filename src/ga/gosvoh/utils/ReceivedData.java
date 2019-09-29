package ga.gosvoh.utils;

import java.nio.ByteBuffer;

public class ReceivedData {
    private int countOfUniverses, universeKey;
    private int countOfPackets, currentPacketNumber;
    private ByteBuffer data;

    public ReceivedData() {
    }

    public ReceivedData(int countOfUniverses, int universeKey, int countOfPackets, int currentPacketNumber, ByteBuffer data) {
        this.countOfUniverses = countOfUniverses;
        this.universeKey = universeKey;
        this.countOfPackets = countOfPackets;
        this.currentPacketNumber = currentPacketNumber;
        this.data = data;
    }

    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
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

    public int getCountOfPackets() {
        return countOfPackets;
    }

    public void setCountOfPackets(int countOfPackets) {
        this.countOfPackets = countOfPackets;
    }

    public int getCurrentPacketNumber() {
        return currentPacketNumber;
    }

    public void setCurrentPacketNumber(int currentPacketNumber) {
        this.currentPacketNumber = currentPacketNumber;
    }
}
