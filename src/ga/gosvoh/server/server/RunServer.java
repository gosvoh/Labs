package ga.gosvoh.server.server;

import ga.gosvoh.server.utils.Defines;
import ga.gosvoh.server.utils.ReceivedData;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class RunServer {
    private static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<DatagramPacket>> packetsParts = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<ClientID, CopyOnWriteArrayList<ReceivedData>> receivedData = new ConcurrentHashMap<>();
    private static DatagramSocket socket;

    public RunServer(int PORT) {
        try {
            socket = new DatagramSocket(PORT);
            socket.setSoTimeout(Defines.CONNECTION_TIMEOUT);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e);
            return;
        }

        System.out.println("Сервер запущен! Порт сервера: " + PORT);

        while (true) {
            byte[] buffer = new byte[Defines.PACKET_LENGTH];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(packet);
            } catch (SocketTimeoutException e) {
                receivedData.clear();
                ClientID.clearAll();
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            ClientID clientID = ClientID.getClientID(address, port);

            if (clientID.isLoadImport())
                continue;

            if (!receivedData.containsKey(clientID)) {
                receivedData.put(clientID, new CopyOnWriteArrayList<>());
                clientID.resetReceivedDatasCout();
            }

            if (!packetsParts.containsKey(clientID)) {
                packetsParts.put(clientID, new CopyOnWriteArrayList<>());
                clientID.resetPacketsCount();
            }

            ByteBuffer packetBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH);
            ByteBuffer dataBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
            packetBuffer.put(packet.getData());
            packetBuffer.flip();

            byte commandCode = packetBuffer.get();
            int countOfPackets = packetBuffer.get();
            int totalCount = packetBuffer.getInt();
            dataBuffer.put(packetBuffer.array(), Defines.METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);

            //receivedData.get(clientID).add(new ReceivedData(commandCode, totalCount, data));

            while (packetsParts.get(clientID).size() < countOfPackets)
                packetsParts.get(clientID).add(null);
            while (receivedData.get(clientID).size() < totalCount)
                receivedData.get(clientID).add(null);
            /*if ((packetsParts.get(clientID).size() == countOfPackets)) {
                packetsParts.get(clientID).add(packet);
                clientID.incReceivedPackets();
            }*/
            if (packetsParts.get(clientID).get(clientID.getReceivedPackets()) == null) {
                packetsParts.get(clientID).set(clientID.getReceivedPackets(), packet);
                clientID.incReceivedPackets();
            }

            if (countOfPackets == clientID.getReceivedPackets()) {
                ByteBuffer bb = ByteBuffer.allocate(packetsParts.size() * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH));
                for (DatagramPacket p : packetsParts.get(clientID))
                    bb.put(p.getData(), Defines.METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);

                if (receivedData.get(clientID).get(clientID.getReceivedDatas()) == null)
                    receivedData.get(clientID).set(clientID.getReceivedDatas(), new ReceivedData(new InetSocketAddress(packet.getAddress(), packet.getPort()), commandCode, totalCount, bb.array()));
                clientID.resetPacketsCount();
                clientID.incReceivedDatasCount();
                packetsParts.remove(clientID);
            }

            if (receivedData.get(clientID).size() == clientID.getReceivedDatas()) {
                clientID.resetReceivedDatasCout();
                newClientConnection(clientID);
            }
        }
    }

    public static DatagramSocket getSocket() {
        return socket;
    }

    private static void newClientConnection(ClientID clientID) {
        ClientConnection clientConnection = new ClientConnection(receivedData.remove(clientID));
        Thread thread = new Thread(clientConnection, "New client thread");
        clientID.startProcessing();
        thread.start();
    }
}
