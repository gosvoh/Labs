package ga.gosvoh.Commands;

import ga.gosvoh.Server.ClientConnection;
import ga.gosvoh.Server.ClientID;
import ga.gosvoh.Server.RunServer;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;
import ga.gosvoh.Utils.AlreadyHaveKeyException;
import ga.gosvoh.Utils.Defines;
import ga.gosvoh.Utils.PacketUtils;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static ga.gosvoh.Utils.Defines.*;

/**
 * Загрузить словарь на сервер
 */
@SuppressWarnings("UnnecessaryContinue")
public class ImportMap implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;
    private DatagramSocket socket;

    public ImportMap() {
        map = UniverseCollection.getSortedCollection();
        //socket = RunServer.getSocket();
    }

    @Override
    public String execute(String[] cmd) {
        map.clear();

        ClientID.getClientID(ClientConnection.getInetSocketAddress().getAddress(), ClientConnection.getInetSocketAddress().getPort()).startImporting();

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            return "Ошибка во время загрузки словаря!";
        }

        for (int i = 0; i < RECEIVING_ATTEMPTS; i++) {
            try {
                Thread.sleep(Defines.RECEIVING_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!receivePacket()) continue;
            else {
                UniverseCollection.setUniverseConcurrentSkipListMap(map);
                new SaveMap().execute(new String[0]);
                ClientID.getClientID(ClientConnection.getInetSocketAddress().getAddress(), ClientConnection.getInetSocketAddress().getPort()).stopImporting();
                return ("Словарь успешно сохранён на диск!");
            }
        }
        return ("Не удалось получить словарь, попробуйте ещё раз.");
    }

    private boolean receivePacket() {
        CopyOnWriteArrayList<ByteBuffer> packetsParts = new CopyOnWriteArrayList<>();
        ByteBuffer dataBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH);
        ByteBuffer response;
        int countOfUniverses = -1, universeKey, receivedUniverse = 0;
        int countOfPackets, currentPacketNumber, receivedPackets = 0;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < Defines.RECEIVING_TIMEOUT * 5) {
            dataBuffer.clear();
            /*DatagramPacket packet = new DatagramPacket(dataBuffer.array(), dataBuffer.array().length);
            socket.receive(packet);
            System.out.println("GOOD");
            if (dataBuffer.position() == 0)
                continue;*/
            //DatagramPacket packet = RunServer.getKostyl().get(ClientConnection.getCurrentClientID()).get(receivedPackets);
            System.out.println(RunServer.getKostyl().size());
            //dataBuffer.put(packet.getData());

            receivedPackets++;
            countOfUniverses = PacketUtils.bytesToInt(dataBuffer.get(0), dataBuffer.get(1), dataBuffer.get(2), dataBuffer.get(3));
            universeKey = PacketUtils.bytesToInt(dataBuffer.get(4), dataBuffer.get(5), dataBuffer.get(6), dataBuffer.get(7));
            countOfPackets = dataBuffer.get(8) & 0xff;
            currentPacketNumber = dataBuffer.get(9) & 0xff;

            if (countOfUniverses > 0) {
                if (countOfPackets > 1) {
                    while (packetsParts.size() < currentPacketNumber)
                        packetsParts.add(null);
                    if (packetsParts.size() == currentPacketNumber)
                        packetsParts.add(dataBuffer);
                    if (packetsParts.get(currentPacketNumber) == null)
                        packetsParts.set(currentPacketNumber, dataBuffer);

                    if (countOfPackets == receivedPackets) {
                        response = ByteBuffer.allocate(countOfPackets * (Defines.PACKET_LENGTH - Defines.COLLECTION_METADATA_LENGTH));
                        for (ByteBuffer b : packetsParts) {
                            b.flip();
                            response.put(b.array(), Defines.COLLECTION_METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.COLLECTION_METADATA_LENGTH);
                        }
                    } else continue;
                }
                response = ByteBuffer.allocate(Defines.PACKET_LENGTH - Defines.COLLECTION_METADATA_LENGTH);
                response.put(dataBuffer.array(), Defines.COLLECTION_METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.COLLECTION_METADATA_LENGTH);
                try {
                    Universe universe = (Universe) new ObjectInputStream(new ByteArrayInputStream(response.array())).readObject();
                    if (map.containsKey(universeKey))
                        throw new AlreadyHaveKeyException("Ошибка во время получения словаря, попробуйте ещё раз");
                    map.put(universeKey, universe);
                    receivedUniverse++;
                } catch (IOException | ClassNotFoundException | AlreadyHaveKeyException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return receivedUniverse == countOfUniverses;
    }
}
