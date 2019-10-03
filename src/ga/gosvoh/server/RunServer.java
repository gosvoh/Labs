package ga.gosvoh.server;

import ga.gosvoh.utils.Defines;
import ga.gosvoh.utils.ReceivedData;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class RunServer {
    private static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<DatagramPacket>> packetsParts = new ConcurrentHashMap<>();
    //private static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<DatagramPacket>> kostyl = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<ReceivedData>> receivedData = new ConcurrentHashMap<>();
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
                //kostyl.clear();
                receivedData.clear();
                ClientID.clearAll();
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            ClientID clientID = ClientID.getClientID(address, port);

            if (!receivedData.containsKey(clientID))
                receivedData.put(clientID, new CopyOnWriteArrayList<>());

            if (!packetsParts.containsKey(clientID)) {
                packetsParts.put(clientID, new CopyOnWriteArrayList<>());
                //kostyl.put(clientID, new CopyOnWriteArrayList<>());
                clientID.resetPacketsCount();
            }

            if (clientID.isProcessing()) {
                if (clientID.isImporting())
                    //kostyl.get(clientID).add(packet);
                    System.out.println("IMPORT");
                continue;
            }

            ByteBuffer data = ByteBuffer.allocate(Defines.PACKET_LENGTH);
            data.put(packet.getData());

            int countOfUniverses = data.getInt();
            int universeKey = data.getInt();
            int countOfPackets = data.get() & 0xff;
            int currentPacketNumber = data.get() & 0xff;

            /*while (packetsParts.get(clientID).size() < currentPacketNumber)
                packetsParts.get(clientID).add(null);
            if ((packetsParts.get(clientID).size() == currentPacketNumber)) {
                packetsParts.get(clientID).add(packet);
                clientID.incReceivedPackets();
            }
            if (packetsParts.get(clientID).get(currentPacketNumber) == null) {
                packetsParts.get(clientID).set(currentPacketNumber, packet);
                clientID.incReceivedPackets();
            }*/

            //while (receivedData.get(clientID).get())

            if (countOfPackets == clientID.getReceivedPackets())
                newClientConnection(clientID);
        }
    }

    public static DatagramSocket getSocket() {
        return socket;
    }

    /*public static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<DatagramPacket>> getKostyl() {
        return kostyl;
    }*/

    private static void newClientConnection(ClientID clientID) {
        ClientConnection clientConnection = new ClientConnection(packetsParts.remove(clientID));
        Thread thread = new Thread(clientConnection, "New client thread");
        clientID.startProcessing();
        thread.start();
    }
}
