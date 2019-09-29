package ga.gosvoh.commands;

import ga.gosvoh.StartClient;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;
import ga.gosvoh.utils.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("UnnecessaryContinue")
public class LoadMap implements Command {
    private DatagramChannel channel;
    private ConcurrentSkipListMap<Integer, Universe> map = new ConcurrentSkipListMap<>();

    public LoadMap() {
        channel = StartClient.getChannel();
    }

    @Override
    public void execute() {
        StartClient.sendPacket("load");
        map.clear();

        for (int i = 0; i < Defines.RECEIVING_ATTEMPTS; i++) {
            try {
                Thread.sleep(Defines.RECEIVING_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!receivePacket()) continue;
            else {
                UniverseCollection.setUniverseConcurrentSkipListMap(map);
                new SaveMap().execute();
                System.out.println("Словарь успешно сохранён на диск!");
                return;
            }
        }
        System.out.println("Не удалось получить словарь, попробуйте ещё раз.");
    }

    private boolean receivePacket() {
        CopyOnWriteArrayList<ByteBuffer> packetsParts = new CopyOnWriteArrayList<>();
        ByteBuffer dataBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH);
        ByteBuffer response;
        int countOfUniverses = 0, universeKey, receivedUniverse = 0;
        int countOfPackets, currentPacketNumber, receivedPackets = 0;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < Defines.RECEIVING_TIMEOUT) {
            dataBuffer.clear();
            try {
                channel.receive(dataBuffer);
                if (dataBuffer.position() == 0)
                    continue;
                receivedPackets++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            countOfUniverses = dataBuffer.getInt();
            universeKey = dataBuffer.getInt();
            countOfPackets = dataBuffer.get() & 0xff;
            currentPacketNumber = dataBuffer.get() & 0xff;

            if (countOfUniverses > 0) {
                if (countOfPackets > 1) {
                    while (packetsParts.size() < currentPacketNumber)
                        packetsParts.add(null);
                    if (packetsParts.size() == currentPacketNumber)
                        packetsParts.add(dataBuffer);
                    if (packetsParts.get(currentPacketNumber) == null)
                        packetsParts.set(currentPacketNumber, dataBuffer);

                    if (countOfPackets == receivedPackets) {
                        response = ByteBuffer.allocate(countOfPackets * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH));
                        for (ByteBuffer b : packetsParts) {
                            b.flip();
                            response.put(b.array(), Defines.METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
                        }
                    } else continue;
                }
                response = ByteBuffer.allocate(Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
                response.put(dataBuffer.array(), Defines.METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
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
