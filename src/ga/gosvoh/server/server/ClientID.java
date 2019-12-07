package ga.gosvoh.server.server;

import ga.gosvoh.server.utils.ReceivedData;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("WeakerAccess")
public class ClientID {
    private InetAddress address;
    private int port, receivedPackets, receivedDatas;
    private boolean isProcessing, isLoadImport;
    private static CopyOnWriteArrayList<ClientID> ids = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<ReceivedData> receivedData;

    private ClientID(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public static ClientID getClientID(InetAddress address, int port) {
        ClientID clientID = new ClientID(address, port);
        if (!ids.contains(clientID)) {
            ids.add(clientID);
        }
        return ids.get(ids.indexOf(clientID));
    }

    public static CopyOnWriteArrayList<ClientID> getIds() {
        return ids;
    }

    public int getPort() {
        return port;
    }

    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(address, port);
    }

    public boolean isProcessing() {
        return isProcessing;
    }

    public boolean isLoadImport() {
        return isLoadImport;
    }

    public void startProcessing() {
        isProcessing = true;
    }

    public void startLoadImport() {
        isLoadImport = true;
    }

    public void stopLoadImport() {
        isLoadImport = false;
    }

    public void stopProcessing() {
        isProcessing = false;
    }

    public static void clearAll() {
        for (ClientID clientID : ids) {
            if (!clientID.isProcessing)
                ids.remove(clientID);
        }
    }

    public void resetPacketsCount() {
        receivedPackets = 0;
    }

    public void resetReceivedDatasCout() {
        receivedDatas = 0;
    }

    public void incReceivedPackets() {
        receivedPackets++;
    }

    public void incReceivedDatasCount() {
        receivedDatas++;
    }

    public int getReceivedPackets() {
        return receivedPackets;
    }

    public int getReceivedDatas() {
        return receivedDatas;
    }

    public void setReceivedData(CopyOnWriteArrayList<ReceivedData> receivedData) {
        this.receivedData = receivedData;
    }

    public CopyOnWriteArrayList<ReceivedData> getReceivedData() {
        if (receivedData != null)
            return receivedData;
        else return null;
    }

    @Override
    public String toString() {
        return "IP клиента: " + address + ", порт: " + port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientID clientID = (ClientID) o;
        return port == clientID.port &&
                isProcessing == clientID.isProcessing &&
                address.equals(clientID.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port, isProcessing);
    }
}
