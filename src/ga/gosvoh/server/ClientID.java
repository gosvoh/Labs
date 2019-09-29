package ga.gosvoh.server;

import java.net.InetAddress;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("WeakerAccess")
public class ClientID {
    private InetAddress address;
    private int port, receivedPackets;
    private boolean isProcessing, isImporting;
    private static CopyOnWriteArrayList<ClientID> ids = new CopyOnWriteArrayList<>();

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

    public InetAddress getAddress() {
        return address;
    }

    public boolean isProcessing() {
        return isProcessing;
    }

    public boolean isImporting() {
        return isImporting;
    }

    public void startProcessing() {
        isProcessing = true;
    }

    public void startImporting() {
        isImporting = true;
    }

    public void stopImporting() {
        isImporting = false;
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

    public ClientID resetPacketsCount() {
        receivedPackets = 0;
        return this;
    }

    public void incReceivedPackets() {
        receivedPackets++;
    }

    public int getReceivedPackets() {
        return receivedPackets;
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
