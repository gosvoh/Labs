package ga.gosvoh.Commands;

import ga.gosvoh.StartClient;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;
import ga.gosvoh.Utils.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

import static ga.gosvoh.Utils.Defines.*;

public class ImportMap implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;
    private DatagramChannel channel;

    public ImportMap() {
        map = UniverseCollection.getSortedCollection();
        channel = StartClient.getChannel();
    }

    @Override
    public void execute() {
        StartClient.sendPacket("import");

        try {
            Thread.sleep(RECEIVING_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_LENGTH);

        Iterator iterator = map.keySet().iterator();
        for (Universe universe : map.values()) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(universe);

                int countOfPackets = (int) Math.ceil(byteArrayOutputStream.toByteArray().length / (PACKET_LENGTH - COLLECTION_METADATA_LENGTH)) +
                        (byteArrayOutputStream.toByteArray().length % (PACKET_LENGTH - COLLECTION_METADATA_LENGTH) == 0 ? 0 : 1);
                if (countOfPackets > 256)
                    throw new PacketOverflowException("Too many packets for this request!");

                ByteBuffer wrappedData = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                for (int i = 0; i < countOfPackets; i++) {
                    byteBuffer.clear();
                    byteBuffer.put(PacketUtils.intToBytes(map.size()));
                    byteBuffer.put(PacketUtils.intToBytes((int) iterator.next()));
                    PacketUtils.putDataIntoByteBuffer(byteBuffer, wrappedData, countOfPackets, i, COLLECTION_METADATA_LENGTH);
                    byteBuffer.flip();

                    try {
                        channel.send(byteBuffer, StartClient.getInetSocketAddress());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка во время загрузки словаря!");
            }
        }
        System.out.println("Словарь успешно отправлен!");
    }
}
