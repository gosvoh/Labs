package ga.gosvoh.utils;

import java.net.DatagramPacket;

@SuppressWarnings("WeakerAccess")
public class ReceivedData {
    private int countOfUniverses, universeKey;
    private int countOfPackets, currentPacketNumber;
    //private byte[] data;
    private DatagramPacket data;

    public ReceivedData() {
    }

    public ReceivedData(int countOfUniverses, int universeKey, int countOfPackets, int currentPacketNumber, DatagramPacket data) {
        this.countOfUniverses = countOfUniverses;
        this.universeKey = universeKey;
        this.countOfPackets = countOfPackets;
        this.currentPacketNumber = currentPacketNumber;
        this.data = data;
    }

    public boolean isFullData() {
        return (data != null);
    }

    /*public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }*/

    public DatagramPacket getData() {
        return data;
    }

    public void setData(DatagramPacket data) {
        this.data = data;
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
