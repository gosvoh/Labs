package ga.gosvoh.server;

import ga.gosvoh.utils.Defines;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class RunServer {
    private static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<DatagramPacket>> packetsParts = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<DatagramPacket>> kostyl = new ConcurrentHashMap<>();
    private static DatagramSocket socket;

    public RunServer() {

        try {
            socket = new DatagramSocket(Defines.PORT);
            socket.setSoTimeout(Defines.CONNECTION_TIMEOUT);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e);
            return;
        }

        System.out.println("Сервер запущен!");

        while (true) {
            byte[] buffer = new byte[Defines.PACKET_LENGTH];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(packet);
            } catch (SocketTimeoutException e) {
                packetsParts.clear();
                kostyl.clear();
                ClientID.clearAll();
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            ClientID clientID = ClientID.getClientID(address, port);

            if (!packetsParts.containsKey(clientID)) {
                packetsParts.put(clientID, new CopyOnWriteArrayList<>());
                kostyl.put(clientID, new CopyOnWriteArrayList<>());
                clientID.resetPacketsCount();
            }

            if (clientID.isProcessing()) {
                if (clientID.isImporting())
                    //kostyl.get(clientID).add(packet);
                    System.out.println("IMPORT");
                continue;
            }

            int countOfPackets = packet.getData()[0] & 0xff;
            int currentPacketNumber = packet.getData()[1] & 0xff;

            if (!clientID.isImporting()) {
                while (packetsParts.get(clientID).size() < currentPacketNumber)
                    packetsParts.get(clientID).add(null);
                if ((packetsParts.get(clientID).size() == currentPacketNumber)) {
                    packetsParts.get(clientID).add(packet);
                    clientID.incReceivedPackets();
                }
                if (packetsParts.get(clientID).get(currentPacketNumber) == null) {
                    packetsParts.get(clientID).set(currentPacketNumber, packet);
                    clientID.incReceivedPackets();
                }
                if (countOfPackets == clientID.getReceivedPackets())
                    newClientConnection(clientID);
            }
        }
    }

    public static DatagramSocket getSocket() {
        return socket;
    }

    public static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<DatagramPacket>> getKostyl() {
        return kostyl;
    }

    private static void newClientConnection(ClientID clientID) {
        ClientConnection clientConnection = new ClientConnection(packetsParts.remove(clientID));
        Thread thread = new Thread(clientConnection, "New client thread");
        clientID.startProcessing();
        thread.start();
    }
}
